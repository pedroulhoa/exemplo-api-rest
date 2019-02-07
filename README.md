# exemplo-api-rest
Exemplo de API REST utilizando Java(Jersey), MySQL(JDBC) e Javascript puro do lado do cliente.

## Ferramentas Utilizadas
```
Jersey 2.25.1
Eclipse IDE 2018‑12
Tomcat 8.5
MySQL 5.7.27
Apache Maven 3.6.0
Bootstrap v4.2.1
```

# Execução da aplicação
Após fazer o clone do repositório e abri-lo no Eclipse é necessário atualizar as dependências do Maven.
```
Click direito no projeto > Maven > Update Project
```

Também é necessário criar o banco de dados no MySQL, com o mesmo nome dado no arquivo **Conexao.java** localizado em src>br.com.crud.connect>Conexao.java

Nesse mesmo arquivo também devem ser configuradas as credenciais do banco.

**Estrutura para criar o banco**
```
CREATE TABLE `pessoas` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) NOT NULL,
  `endereco` varchar(50) NOT NULL,
  `email` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

Para execução do projeto foi usado o Tomcat 8.5, que deve instalado e configurado no Eclipse.

Após execução do servidor bastar acessar a url local: 
* [http://localhost:8080/crud-pessoa/](http://localhost:8080/crud-pessoa/)

O Front-end (Javascript puro com XMLHttpRequest) encontra-se na pasta WebContent.