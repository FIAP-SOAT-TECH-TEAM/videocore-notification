# 📧 VideoCore Notification

<div align="center">

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_videocore-notification&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_videocore-notification)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_videocore-notification&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_videocore-notification)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_videocore-notification&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_videocore-notification)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=FIAP-SOAT-TECH-TEAM_videocore-notification&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=FIAP-SOAT-TECH-TEAM_videocore-notification)

</div>

Microsserviço de notificações por e-mail do ecossistema VideoCore, responsável por enviar notificações de status de processamento de vídeos para os usuários. Desenvolvido como parte do curso de Arquitetura de Software da FIAP (Tech Challenge).

<div align="center">
  <a href="#visao-geral">Visão Geral</a> •
  <a href="#arquitetura">Arquitetura</a> •
  <a href="#repositorios">Repositórios</a> •
  <a href="#tecnologias">Tecnologias</a> •
  <a href="#instalacao">Instalação</a> •
  <a href="#deploy">Fluxo de Deploy</a> •
  <a href="#contribuicao">Contribuição</a>
</div><br>

> 📽️ Vídeo de demonstração da arquitetura: [https://youtu.be/k3XbPRxmjCw](https://youtu.be/k3XbPRxmjCw)<br>

---

<h2 id="visao-geral">📋 Visão Geral</h2>

O **VideoCore Notification** é o microsserviço responsável por enviar notificações por e-mail aos usuários sobre o status de processamento de seus vídeos. Ele consome eventos do Azure Service Bus e gera e-mails HTML personalizados para cada etapa do processamento.

### Principais Responsabilidades

- **Consumo de Eventos**: Escuta status de processamento via Azure Service Bus
- **Geração de E-mails**: Criação de e-mails HTML para cada status (início, conclusão, erro)
- **Envio SMTP**: Envio de e-mails via servidor SMTP configurável
- **Consulta de Usuários**: Busca dados do usuário via AWS Cognito
- **Download URLs**: Inclui link de download das imagens no e-mail de conclusão

### Fluxo de Notificação

```
1. Evento de Status (Service Bus)
        ↓
2. Identifica tipo de notificação (STARTED / COMPLETED / FAILED)
        ↓
3. Busca dados do usuário (AWS Cognito)
        ↓
4. Gera e-mail HTML personalizado
        ↓
5. Envia e-mail via SMTP
```

### Tipos de Notificação

| Status | Descrição | Conteúdo do E-mail |
|--------|-----------|-------------------|
| `STARTED` | Processamento iniciado | Confirmação de recebimento do vídeo |
| `COMPLETED` | Processamento concluído | Link de download das imagens extraídas |
| `FAILED` | Erro no processamento | Detalhes do erro ocorrido |

---

<h2 id="arquitetura">🧱 Arquitetura</h2>

<details>
<summary>Expandir para mais detalhes</summary>

### 🎯 Clean Architecture

O projeto segue os princípios de **Clean Architecture** com separação clara de responsabilidades:

```
core/
├── application/          # Casos de uso
│   ├── CreateEmailNotificationStartedProcessUseCase
│   ├── CreateEmailNotificationFinishedProcessUseCase
│   ├── CreateEmailNotificationErrorProcessUseCase
│   ├── SendEmailUseCase
│   ├── GetUserByIdUseCase
│   └── GetVideoImagesDownloadUrlUseCase
├── domain/               # Entidades e regras de negócio
└── interfaceadapters/
    ├── controller/       # Controllers de processamento
    │   ├── ProcessVideoStatusUpdateController
    │   └── ProcessVideoErrorController
    └── mapper/           # Conversão de eventos ↔ domínio

infrastructure/
├── in/                   # Adaptadores de entrada
│   └── event/azsvcbus/   # Listeners do Azure Service Bus
├── out/                  # Adaptadores de saída
│   ├── email/            # Envio de e-mails (SMTP)
│   ├── cognito/          # Cliente AWS Cognito
│   └── blobstorage/      # Acesso ao Azure Blob Storage
└── common/               # Configurações e observabilidade
```

### 🔄 Fluxo de Dados

```
Azure Service Bus (Status Event)
    ↓
ProcessVideoStatusUpdateListener / ProcessVideoErrorListener
    ↓
Controller (Business Logic Orchestration)
    ↓
┌─────────────────────────────────────────┐
│  GetUserByIdUseCase (Cognito)           │
│  GetVideoImagesDownloadUrlUseCase (Blob)│
│  CreateEmailNotificationUseCase         │
│  SendEmailUseCase (SMTP)                │
└─────────────────────────────────────────┘
```

### 🔌 Integrações

| Serviço | Tipo | Descrição |
|---------|------|-----------|
| **Azure Service Bus** | Assíncrona | Consumo de eventos de status e erro |
| **AWS Cognito** | Síncrona | Busca de dados do usuário (nome, e-mail) |
| **Azure Blob Storage** | Síncrona | Geração de URLs de download de imagens |
| **SMTP** | Síncrona | Envio de e-mails de notificação |

### 📊 Observabilidade

- **Traces**: OpenTelemetry (OTLP gRPC)
- **Métricas**: Micrometer (OTLP gRPC)
- **Logs**: Logstash JSON format
- **Health Checks**: Spring Actuator (`/actuator/health`)

### ☸️ Kubernetes

| Recurso | Configuração |
|---------|-------------|
| **Deployment** | Pods com health probes e limites de recursos |
| **ConfigMap** | Configurações não sensíveis |
| **Secrets** | Credenciais (SMTP, Cognito, Service Bus) via Azure Key Vault Provider |

### 📦 Estrutura do Projeto

```
videocore-notification/
├── notification/
│   ├── build.gradle              # Dependências e build config
│   ├── src/main/
│   │   ├── java/com/soat/fiap/videocore/notification/
│   │   │   ├── NotificationApplication.java
│   │   │   ├── core/
│   │   │   │   ├── application/
│   │   │   │   ├── domain/
│   │   │   │   └── interfaceadapters/
│   │   │   └── infrastructure/
│   │   │       ├── in/event/azsvcbus/
│   │   │       ├── out/
│   │   │       │   ├── email/
│   │   │       │   ├── cognito/
│   │   │       │   └── blobstorage/
│   │   │       └── common/
│   │   └── resources/
│   │       ├── application.yaml
│   │       ├── application-local.yaml
│   │       ├── application-prod.yaml
│   │       └── logback-spring.xml
│   └── src/test/
├── docker/
│   └── Dockerfile                # GraalVM Native Image
├── kubernetes/
│   ├── Chart.yaml                # Helm Chart
│   ├── values.yaml               # Configurações Helm
│   └── templates/
│       ├── deploymentset.yaml
│       ├── configmap.yaml
│       └── crd/
│           └── azure_secrets_provider/
├── terraform/
│   ├── main.tf                   # Helm deployment
│   └── variables.tf
└── .github/workflows/
    ├── ci.yaml                   # Build, test, SonarQube
    └── cd.yaml                   # Docker + Helm deploy
```

</details>

---

<h2 id="repositorios">📁 Repositórios do Ecossistema</h2>

| Repositório | Responsabilidade | Tecnologias |
|-------------|------------------|-------------|
| **videocore-infra** | Infraestrutura base (AKS, VNET, APIM, Key Vault) | Terraform, Azure, AWS |
| **videocore-db** | Banco de dados | Terraform, Azure Cosmos DB |
| **videocore-auth** | Autenticação | .NET 9, Azure Functions, Cognito |
| **videocore-frontend** | Interface web do usuário | Next.js 16, React 19, TypeScript |
| **videocore-reports** | Microsserviço de relatórios | Java 25, Spring Boot 4, Cosmos DB |
| **videocore-worker** | Microsserviço de processamento de vídeo | Java 25, Spring Boot 4, FFmpeg |
| **videocore-notification** | Microsserviço de notificações (este repositório) | Java 25, Spring Boot 4, SMTP |

---

<h2 id="tecnologias">🔧 Tecnologias</h2>

| Categoria | Tecnologia |
|-----------|------------|
| **Linguagem** | Java 25 |
| **Framework** | Spring Boot 4.0.1 |
| **Mensageria** | Azure Service Bus |
| **Identity** | AWS Cognito |
| **Storage** | Azure Blob Storage |
| **E-mail** | Spring Mail (SMTP) |
| **Observabilidade** | OpenTelemetry, Micrometer, Logstash |
| **Build** | Gradle |
| **Compilação** | GraalVM Native Image |
| **Container** | Docker |
| **Orquestração** | Kubernetes (Helm) |
| **IaC** | Terraform |
| **CI/CD** | GitHub Actions |
| **Qualidade** | SonarQube |

---

<h2 id="instalacao">🚀 Instalação e Uso</h2>

### Variáveis de Ambiente

```bash
AZ_SVC_BUS_CONNECTION_STRING=               # Connection string Service Bus
AZURE_BLOB_STORAGE_CONNECTION_STRING=       # Connection string Blob Storage
AZURE_BLOB_STORAGE_IMAGE_CONTAINER_NAME=    # Container de imagens
MAIL_HOST=                                  # Host do servidor SMTP
MAIL_PORT=                                  # Porta do servidor SMTP
MAIL_USERNAME=                              # Usuário SMTP
MAIL_PASSWORD=                              # Senha SMTP
AWS_COGNITO_USER_POOL_ID=                   # User Pool ID do Cognito
AWS_REGION=                                 # Região AWS
AWS_ACCESS_KEY_ID=                          # Access Key AWS
AWS_SECRET_ACCESS_KEY=                      # Secret Key AWS
```

### Desenvolvimento Local

```bash
# Clonar repositório
git clone https://github.com/FIAP-SOAT-TECH-TEAM/videocore-notification.git
cd videocore-notification/notification

# Configurar variáveis de ambiente
cp env-example .env

# Compilar
./gradlew build

# Executar
./gradlew bootRun
```

> ⚠️ Ajuste os arquivos `.env` conforme necessário.
> Para receber e-mails localmente, utilize o **MailDev** disponível no Docker Compose do `videocore-infra`.

---

<h2 id="deploy">⚙️ Fluxo de Deploy</h2>

<details>
<summary>Expandir para mais detalhes</summary>

### Pipeline CI

1. **Build**: JDK 25, Gradle com cache
2. **Testes**: Execução de testes automatizados
3. **SonarQube**: Análise de qualidade de código
4. **Terraform**: Format, validate, plan

### Pipeline CD

1. **Docker**: Build de imagem GraalVM Native
2. **ACR**: Push para Azure Container Registry
3. **Helm**: Deploy no AKS com versionamento

### Autenticação

- **OIDC**: Token emitido pelo GitHub
- **Azure AD Federation**: Confia no emissor GitHub
- **Service Principal**: Autentica sem secret

### Ordem de Provisionamento

```
1. videocore-infra          (AKS, VNET, APIM)
2. videocore-db             (Cosmos DB)
3. videocore-auth           (Azure Function Authorizer)
4. videocore-reports        (Microsserviço de relatórios)
5. videocore-worker         (Microsserviço de processamento)
6. videocore-notification   (Microsserviço de notificações - este repositório)
7. videocore-frontend       (Interface web)
```

### Proteções

- Branch `main` protegida
- Nenhum push direto permitido
- Todos os checks devem passar

</details>

---

<h2 id="contribuicao">🤝 Contribuição</h2>

### Fluxo de Contribuição

1. Crie uma branch a partir de `main`
2. Implemente suas alterações
3. Execute os testes unitários: `./gradlew test`
4. Abra um Pull Request
5. Aguarde aprovação de um CODEOWNER

### Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

---

<div align="center">
  <strong>FIAP - Pós-graduação em Arquitetura de Software</strong><br>
  Tech Challenge 4
</div>
