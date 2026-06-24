================================================================================
                 SISTEMA DE GERENCIAMENTO DE SALÃO DE BELEZA
         Disciplina: APO2 — Algoritmos e Programação Orientada a Objetos II
================================================================================

  Este documento apresenta as informações necessárias para execução e
  avaliação do sistema desenvolvido para gerenciamento de um salão de beleza.

================================================================================
  SOBRE O SISTEMA
================================================================================

  A aplicação foi desenvolvida com o objetivo de auxiliar no controle das
  atividades de um salão de beleza, centralizando informações relacionadas a
  clientes, profissionais, serviços, estoque, pagamentos e agendamentos.

  O projeto foi desenvolvido utilizando Java, banco de dados MySQL e a
  arquitetura MVC, promovendo a separação entre interface gráfica,
  regras de negócio e persistência de dados.

================================================================================
  PRÉ-REQUISITOS
================================================================================

  Antes de iniciar, certifique-se de que os seguintes programas estão
  instalados e funcionando na máquina:

    [1] Java JDK

    [2] Eclipse IDE

    [3] MySQL Server

================================================================================
  CONFIGURAÇÃO DO BANCO DE DADOS
================================================================================

  O script de criação do banco encontra-se no arquivo:

       bancoAPO.sql

  Após a execução do script, a estrutura necessária para utilização do sistema
  estará disponível.

================================================================================
  EXECUÇÃO DO SISTEMA
================================================================================

  1. Importe o projeto no Eclipse.

  2. Configure a biblioteca SWT.

  3. Ajuste os parâmetros de conexão com o banco de dados.

  4. Execute a aplicação pela tela principal do sistema.

================================================================================
  FUNCIONALIDADES DISPONÍVEIS
================================================================================

    • Cadastro de clientes
    • Cadastro de profissionais
    • Cadastro de serviços
    • Controle de estoque
    • Registro de pagamentos
    • Controle de agendamentos

================================================================================
  TECNOLOGIAS UTILIZADAS
================================================================================

  Linguagem:
    • Java

  Interface Gráfica:
    • SWT (Standard Widget Toolkit)
    • WindowBuilder

  Banco de Dados:
    • MySQL
    • JDBC

  Ferramentas:
    • Eclipse IDE
    • Git
    • GitHub

================================================================================
  ESTRUTURA DO PROJETO
================================================================================

  /projetoAPO_salao/

    README.txt
    APO.pdf
    bancoAPO.sql

    /src

      /banco
        Classes de conexão com o banco de dados

      /controller
        Controle das ações do sistema

      /dao
        Operações de persistência de dados

      /model
        Entidades e regras de negócio

      /view
        Interfaces gráficas da aplicação

================================================================================
  ARQUIVOS DISPONÍVEIS
================================================================================

    APO.pdf
      Documentação do projeto

    bancoAPO.sql
      Script de criação do banco de dados

================================================================================
  AUTORES
================================================================================

    • Maria Eduarda
    • Halan William

================================================================================
