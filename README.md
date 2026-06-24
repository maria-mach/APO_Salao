# Sistema de Gerenciamento de Salão de Beleza

## Sobre o Sistema

A aplicação foi desenvolvida com o objetivo de auxiliar no controle das atividades de um salão de beleza, centralizando informações relacionadas a clientes, profissionais, serviços, estoque, pagamentos e agendamentos.

O projeto foi desenvolvido utilizando Java, banco de dados MySQL e a arquitetura MVC, promovendo a separação entre interface gráfica, regras de negócio e persistência de dados.

## Pré-requisitos

* Java JDK
* Eclipse IDE
* MySQL Server

## Configuração do Banco de Dados

O script de criação do banco encontra-se no arquivo:

```text
bancoAPO.sql
```

Após a execução do script, a estrutura necessária para utilização do sistema estará disponível.

## Execução do Sistema

1. Importe o projeto no Eclipse.
2. Configure a biblioteca SWT.
3. Ajuste os parâmetros de conexão com o banco de dados.
4. Execute a aplicação pela tela principal do sistema.

## Funcionalidades Disponíveis

* Cadastro de clientes
* Cadastro de profissionais
* Cadastro de serviços
* Controle de estoque
* Registro de pagamentos
* Controle de agendamentos

## Tecnologias Utilizadas

### Linguagem

* Java

### Interface Gráfica

* SWT (Standard Widget Toolkit)
* WindowBuilder

### Banco de Dados

* MySQL
* JDBC

### Ferramentas

* Eclipse IDE
* Git
* GitHub

## Estrutura do Projeto

```text
projetoAPO_salao/
│
├── APO.pdf
├── bancoAPO.sql
├── README.md
│
└── src
    ├── banco
    ├── controller
    ├── dao
    ├── model
    └── view
```

## Arquivos Disponíveis

### APO.pdf

Documentação do projeto.

### bancoAPO.sql

Script de criação do banco de dados.

## Autores

* Maria Eduarda
* Halan William
