## Bank of Pablo

#### 🇧🇷: Apenas um sistema de Usuario e Transações monetarias (falsas rsrs), com um esquema de Transação entre Pessoa Fisica e Comercial, aonde Pessoa Física recebe e efetua transações, já Comercial apenas recebe transações

#### 🇺🇸: Just a Rest API, it make fake transactions between User and MERCHANT User when the Simple User can receive and send transaction and the StoMERCHANTre user can only receive new transactions

## * Como executar a API / How to execute

#### 🇧🇷: Todo o ambiente necessario para a execução do programa está pronto para ser executado via `docker compose`

#### 🇺🇸: All the enviroment you need to execute the project local, are ready to be executed using `docker compose`

```
~ cd config
~ docker compose up --build -d
```

## Tecnologias Usadas:

* Java 20
* Spring Boot 3
* Postgres
* Redis (cache)
* Ekl (logs)
* Docker