SISTEMA DE GERENCIAMENTO DE SALÃO DE BELEZA Disciplina

Este documento contém as informações necessárias para execução e avaliação
do sistema desenvolvido para gerenciamento de um salão de beleza.

Antes de iniciar, certifique-se de que os seguintes programas estão
instalados e funcionando na máquina:

[1] JDK 21 ou superior
    Verificar: abra o terminal e digite → java -version

[2] Eclipse IDE
    Ambiente utilizado para desenvolvimento e execução do projeto

[3] MySQL Server 8.0 ou superior
    Verificar: abra o terminal e digite → mysql --version
Inicie o MySQL Server.
Crie um banco de dados para o sistema.

Execute o script:

bancoAPO.sql

Este script criará as tabelas necessárias para o funcionamento do sistema.

Abra a classe responsável pela conexão:

   src/banco/DBConnection.java

Ajuste os parâmetros de conexão de acordo com a configuração local:

   URL do banco
   Usuário
   Senha

Salve as alterações.

Importe o projeto no Eclipse.
Certifique-se de que a biblioteca SWT está configurada.
Execute a tela inicial do sistema.

O sistema será iniciado e estará pronto para utilização.

CLIENTES

• Cadastro de clientes
• Consulta de clientes

SERVIÇOS

• Cadastro de serviços
• Consulta de serviços

PROFISSIONAIS

• Cadastro de prestadores
• Consulta de profissionais

AGENDAMENTOS

• Registro de atendimentos
• Consulta de agendamentos

ESTOQUE

• Cadastro de produtos
• Controle de estoque

PAGAMENTOS

• Registro de pagamentos
• Controle financeiro

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

src/

/banco
  Conexão com banco de dados

/controller
  Controle das ações do sistema

/dao
  Operações de persistência

/model
  Entidades e regras de negócio

/view
  Interfaces gráficas
bancoAPO.sql
  Script de criação do banco de dados

APO.pdf
  Documentação do projeto
• Maria Eduarda
• Halan William

Projeto desenvolvido para fins acadêmicos com o objetivo de aplicar
conceitos de Programação Orientada a Objetos, arquitetura MVC,
persistência de dados e desenvolvimento de interfaces gráficas em Java.

================================================================================
