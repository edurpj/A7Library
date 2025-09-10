# **A7Library**

### **Tags**

`Java` `Java 8` `Swing` `Hibernate` `JPA` `PostgreSQL` `Maven` `Docker` `GUI` `CRUD` `Design Patterns` `Desktop Application`

-----

### **1. Visão Geral**

O **A7Library** é um sistema de gerenciamento de biblioteca desktop desenvolvido em **Java 8** com o framework **Swing**. Ele permite que o usuário gerencie um acervo de livros, oferecendo funcionalidades de pesquisa dinâmica e importação automatizada de dados.

A aplicação foi projetada com uma arquitetura em camadas para garantir a separação de responsabilidades, facilitando a manutenção e a escalabilidade. O projeto utiliza **Hibernate/JPA** para persistência de dados e **Maven** para gerenciar as dependências e o processo de build.

-----

### **2. Tecnologias e Ferramentas**

  * **Linguagem:** Java 8
  * **Interface de Usuário:** Swing
  * **Persistência:** Hibernate/JPA
  * **Banco de Dados:** PostgreSQL
  * **Ferramenta de Build:** Maven
  * **Orquestração:** Docker
  * **Bibliotecas de Terceiros:** OpenCSV, JIconFont, Gson, Junit, Mockito, Jackson, JCalendar

-----

### **3. Arquitetura do Projeto**

A aplicação segue uma arquitetura em camadas, baseada nos princípios do **Model-View-Controller (MVC)** e do **Data Access Object (DAO)**.

  * **Camada de Visão (UI - View):** Construída com **Swing**, é responsável pela interação com o usuário. Ela gerencia as telas de cadastro, listagem, pesquisa e importação, delegando todas as ações para a Camada de Serviço.
  * **Camada de Serviço (Service):** Contém a **lógica de negócio**. Atua como um *Facade*, orquestrando as operações de persistência, validação e comunicação com APIs externas.
  * **Camada de Acesso a Dados (DAO):** Responsável exclusivamente pela **comunicação com o banco de dados**. Utiliza **Hibernate/JPA** para executar operações **CRUD**, abstraindo a complexidade do SQL.
  * **Camada de Modelo (Model):** Representa as entidades do domínio, como a classe `Livro`, que é mapeada para a tabela do banco de dados via anotações **JPA**.

-----

### **4. Padrões de Projeto (Design Patterns)**

O projeto emprega os seguintes padrões para garantir a clareza e a manutenção do código:

  * **Data Access Object (DAO):** Desacopla a lógica de negócio do acesso a dados. O `LivroDAO` define a interface, e o `LivroDAOImpl` fornece a implementação específica com **Hibernate**.
  * **Service Layer:** O `LivroServiceImpl` centraliza as regras de negócio, evitando que a lógica seja espalhada pela interface de usuário.
  * **Singleton:** As instâncias de `LivroDAO` e `LivroServiceImpl` são gerenciadas como **Singletons** para otimizar o uso de recursos e o gerenciamento de sessões do **Hibernate**.

-----

### **5. Funcionalidades Implementadas**

O sistema oferece as seguintes funcionalidades principais:

  * **Gerenciamento de Livros:** **CRUD** completo de registros de livros.
  * **Pesquisa Dinâmica:** Permite pesquisar livros por qualquer campo (**Título**, **ISBN**, **Autores**, etc.).
  * **Importação de Dados Automatizada:**
      * **Importação por ISBN (API):** Preenche automaticamente os dados do livro a partir da **[API do OpenLibrary](https://openlibrary.org/dev/docs/api/books)** usando o ISBN.
      * **Importação por Arquivo (CSV/XML):** Importa múltiplos livros a partir de um arquivo, detectando e atualizando registros existentes ou criando novos.

-----

### **6. Como Instalar e Rodar o Projeto**

#### **Pré-requisitos:**

  * **Java Development Kit (JDK) 8:** [jdk1.8.0\_201](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
  * **Maven:** [Maven 3.9.11](https://maven.apache.org/download.cgi)
  * **PostgreSQL:** [PostgreSQL 17.6](https://www.postgresql.org/download/)
  * **Docker:** [Docker Desktop](https://www.docker.com/products/docker-desktop/)

> **Ferramentas auxiliares:**
>
>   * **IDE:** IntelliJ, NetBeans ou Eclipse.
>   * **Admin de DB:** DBeaver.

-----

#### **Passo 1: Configurar Variáveis de Ambiente**

Crie e configure as variáveis de ambiente **`JAVA_HOME`** e **`MAVEN_HOME`** para apontar para os diretórios de instalação do JDK e do Maven, respectivamente.

-----

#### **Passo 2: Clonar e Configurar o Projeto**

1.  Clone o projeto do repositório Git: `https://github.com/edurpj/A7Library/tree/master`
2.  Importe o projeto em sua IDE.
3.  Nas configurações do projeto na IDE, certifique-se de usar o **JDK 1.8.201** e o **Maven 3.6.11**.

-----

#### **Passo 3: Subir o Banco de Dados com Docker**

> O arquivo `docker-compose.yml` na raiz do projeto já está configurado para o PostgreSQL.

1.  Abra o terminal na pasta raiz do projeto.
2.  Execute o comando para subir o container do banco de dados:
    ```bash
    docker-compose up -d
    ```
3.  Verifique a configuração de conexão no arquivo **`src/main/resources/META-INF/persistence.xml`**. Os dados já devem estar corretos para o container do Docker.

-----

#### **Passo 4: Usar PostgreSQL sem Docker (Alternativa)**

Se você não puder usar o Docker, siga estes passos:

1.  **Instale o PostgreSQL** diretamente em sua máquina.
2.  Crie o banco de dados `postgres` com o seguinte comando no seu cliente de banco de dados (DBeaver, por exemplo):
    ```sql
    CREATE DATABASE postgres;
    ```
3.  **Crie a tabela `livro`** manualmente. Abra o terminal na pasta do projeto e execute o script SQL incluído na documentação original.
4.  ```sql
       CREATE TABLE livro (
	id bigserial NOT NULL,
	titulo varchar(255) NOT NULL,
	autores varchar(255) NOT NULL,
	isbn varchar(255) NOT NULL,
	data_publicacao date NULL,
	editora varchar(255) NULL,
	CONSTRAINT livro_pkey PRIMARY KEY (id),
	CONSTRAINT livro_isbn_key UNIQUE (isbn)
    );
    ```
5.  **Altere o `persistence.xml`** para apontar para a sua instância local do PostgreSQL.
<img width="901" height="235" alt="props" src="https://github.com/user-attachments/assets/64260c42-36ae-4d3f-abc6-aca1bf00c37f" />

-----

#### **Passo 5: Fazer o Build do Projeto**

1.  Na sua IDE, crie uma nova configuração de build Maven.
2.  Adicione os comandos: **`clean install package -X`**.
3.  Execute essa configuração de build. Uma mensagem de "BUILD SUCCESS" será exibida no final do processo.
<img width="722" height="247" alt="image" src="https://github.com/user-attachments/assets/004ae503-6655-4213-8faa-0c9775619dbc" />

-----

#### **Passo 6: Rodar a Aplicação**

1.  Navegue até a pasta **`target`** do seu projeto.
2.  Abra um terminal e execute o arquivo `.jar` com o comando:
    ```bash
    java -jar A7Library-1.0-SNAPSHOT-jar-with-dependencies.jar
    ```

A aplicação **Swing** será inicializada em uma nova janela.
<img width="1090" height="619" alt="image" src="https://github.com/user-attachments/assets/1dbff690-c1f3-4cdf-b8b3-7e1a7c3bfd97" />
