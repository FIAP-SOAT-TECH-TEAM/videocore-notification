package com.soat.fiap.videocore.notification.infrastructure.out.persistence.blobstorage.repository;


import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.common.sas.SasProtocol;
import com.soat.fiap.videocore.notification.infrastructure.common.config.azure.blobstorage.AzureBlobStorageProperties;
import com.soat.fiap.videocore.notification.infrastructure.common.config.environment.EnvironmentProperties;
import com.soat.fiap.videocore.notification.infrastructure.common.source.VideoDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;


/**
 * Repositório de acesso a vídeos no Azure Blob Storage.
 */
@Component
@RequiredArgsConstructor
public class AzureBlobStorageRepository implements VideoDataSource {

    private final AzureBlobStorageProperties properties;
    private final EnvironmentProperties environmentProperties;

    /**
     * Obtém a url de download para as imagens capturadas de um dado vídeo, protegida por SAS Token
     *
     * @param userId ID do usuário responsável por encaminhar o vídeo
     * @param requestId ID da requisição
     * @param videoName Nome do vídeo
     * @param expirationMinuteTime Minutos de expiração para a URL de download
     *
     * @return a URL para download das imagens do vídeo, ou nulo caso o blob não exista
     */
    @Override
    public String getVideoImagesDownloadUrl(String userId, String requestId, String videoName, long expirationMinuteTime) {
        var blobName = String.format("%s/%s/%s.zip", userId, requestId, videoName);

        var blobClient = new BlobClientBuilder()
                .connectionString(properties.getConnectionString())
                .containerName(properties.getImageContainerName())
                .blobName(blobName)
                .buildClient();

        if (!blobClient.exists())
            return null;

        var permissions = new BlobSasPermission()
                .setReadPermission(true);

        var expiryTime = OffsetDateTime.now().plusMinutes(expirationMinuteTime);

        var sasValues = new BlobServiceSasSignatureValues(expiryTime, permissions);

        if (environmentProperties.isProd())
            sasValues.setProtocol(SasProtocol.HTTPS_ONLY);

        var sasToken = blobClient.generateSas(sasValues);

        return blobClient.getBlobUrl() + "?" + sasToken;
    }

}