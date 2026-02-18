package com.soat.fiap.videocore.notification.infrastructure.out.persistence.blobstorage.repository;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.common.sas.SasProtocol;
import com.soat.fiap.videocore.notification.infrastructure.common.config.azure.blobstorage.AzureBlobStorageProperties;
import com.soat.fiap.videocore.notification.infrastructure.common.config.environment.EnvironmentProperties;
import com.soat.fiap.videocore.notification.infrastructure.common.source.VideoDataSource;

import lombok.RequiredArgsConstructor;

/**
 * Repositório de acesso a vídeos no Azure Blob Storage.
 */
@Component @RequiredArgsConstructor
public class AzureBlobStorageRepository implements VideoDataSource {

	private final AzureBlobStorageProperties properties;
	private final EnvironmentProperties environmentProperties;

	/**
	 * Obtém a url de download para as imagens capturadas de um dado vídeo,
	 * protegida por SAS Token
	 *
	 * @param userId
	 *            ID do usuário responsável por encaminhar o vídeo
	 * @param requestId
	 *            ID da requisição
	 * @param videoName
	 *            Nome do vídeo
	 * @param expirationMinuteTime
	 *            Minutos de expiração para a URL de download
	 *
	 * @return a URL para download das imagens do vídeo
	 */
	@Override
	public String getVideoImagesDownloadUrl(String userId, String requestId, String videoName,
			long expirationMinuteTime) {
		var blobName = String.format("%s/%s/%s.zip", userId, requestId, videoName);

		var blobClient = new BlobClientBuilder().connectionString(properties.getConnectionString())
				.containerName(properties.getImageContainerName())
				.blobName(blobName)
				.buildClient();

		var permissions = new BlobSasPermission().setReadPermission(true);

		var expiryTime = OffsetDateTime.now().plusMinutes(expirationMinuteTime);

		var sasValues = new BlobServiceSasSignatureValues(expiryTime, permissions)
				.setContentDisposition("attachment; filename=\"" + removeExtension(videoName) + ".zip\"")
				.setContentType("application/zip");

		if (environmentProperties.isProd())
			sasValues.setProtocol(SasProtocol.HTTPS_ONLY);

		var sasToken = blobClient.generateSas(sasValues);

		return blobClient.getBlobUrl() + "?" + sasToken;
	}

	/**
	 * Remove a extensão do nome de um arquivo, considerando apenas o último ponto.
	 * <p>
	 * Exemplo:
	 * <ul>
	 * <li>{@code video.mp4} → {@code video}</li>
	 * <li>{@code meu.video.final.mkv} → {@code meu.video.final}</li>
	 * <li>{@code video} → {@code video}</li>
	 * </ul>
	 *
	 * @param fileName
	 *            nome do arquivo, com ou sem extensão
	 * @return nome do arquivo sem a extensão
	 */
	private String removeExtension(String fileName) {
		var lastDot = fileName.lastIndexOf('.');
		return (lastDot > 0) ? fileName.substring(0, lastDot) : fileName;
	}

}
