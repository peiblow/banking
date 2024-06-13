## Bank of Pablo

### 🇧🇷 *OBS: Para usar o serviço de email será necessario clonar e executar o serviço de email aqui está a url [LAMBDA EMAIL](https://github.com/peiblow/lambda_email_service)

### 🇺🇸 *OBS: To use email notification service you will have to clone and execute another repo along, here are the service url [LAMBDA EMAIL](https://github.com/peiblow/lambda_email_service)

<br/>
<br/>

#### 🇧🇷: Apenas um sistema de Usuario e Transações monetarias (falsas rsrs), com um esquema de Transação entre Pessoa Fisica e Comercial, aonde Pessoa Física recebe e efetua transações, já Comercial apenas recebe transações

#### 🇺🇸: Just a Rest API, it make fake transactions between User and MERCHANT User when the Simple User can receive and send transaction and the StoMERCHANTre user can only receive new transactions

<br/>
<br/>

## * Como executar a API / How to execute

#### 🇧🇷: Todo o ambiente necessario para a execução do programa está pronto para ser executado via `docker compose`

#### 🇺🇸: All the enviroment you need to execute the project local, are ready to be executed using `docker compose`

```
~ cd config
~ docker compose up --build -d
```

## Tecnologias Usadas / Tech stack on this project:

* Java 17
* Spring Boot 3
* Postgres
* Redis (cache)
* Ekl (logs)
* Docker
* localstack (s3, Sqs)