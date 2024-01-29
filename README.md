# Projeto - Plano de Saúde Ekan
**Feito por Victor P. A. Smirnov.**
Desenvolvido utilizando Java Springboot, banco de dados H2 na IDE Spring Tools Suite.

**Documentação dos Endpoints:** 
https://app.swaggerhub.com/apis/Victor-Arakaki/beneficiario/1.0.0

## Introdução:
Este projeto foi realizado de acordo com o solicitado no desafio, baseado em um software para gerenciamento de beneficiarios de um plano de saúde e seus dados pessoais.

### Funcionalidades do Sistema:

Estão inclusas as seguintes funcionalidades no sistema, de acordo com o solicitado:
- Cadastrar um beneficiário junto com seus documentos; 
- Listar todos os beneficiários cadastrados;
- Listar todos os documentos de um beneficiário a partir de seu id; 
- Atualizar os dados cadastrais de um beneficiário; 
- Remover um beneficiário.

Como funcionalidade adicional, também foram implementados:
- Buscar dados individualmente de um beneficiário;
- Realizar "soft delete" dos beneficiários e documentos por alterar o seus status.
- Paginação no endpoint de listar todos os beneficiários cadastrados, inclusiva, a opção de filtra-los por status (ativos, inativos ou todos)

## Instruções de build

Para executar a aplicação, você pode importa-la na sua IDE favorita, e builda-la por ela, ou pode executa-la via terminal rodando as seguintes linhas de comando
```cmd
mvn spring-boot:run
```
Para executar a aplicação via linha de comando, é necessário possuir um JDK Java instalado e corretamente configurado nas variáveis path, assim como Maven.

### Dependencias

- spring-boot-starter-validation
- spring-boot-starter-data-jpa
- spring-boot-starter-jdbc
- spring-boot-starter-oauth2-resource-server
- spring-boot-starter-web
- h2
- spring-boot-configuration-processor
- lombok
- spring-boot-starter-test 

# Sobre a aplicação

## Funcionamento básico

O funcionamento da aplicação é baseado em realizar operações de CRUD de uma entidade (Beneficiario) e suas entidades filhas (Documentos).

### Arquitetura basica da aplicação
[![ekan-drawio.png](https://i.postimg.cc/N0147Z42/ekan-drawio.png)](https://postimg.cc/bGwkqC4q)

Os objetos trafegam na aplicação através de entities e são transferidas, na entrada e saida através de DTOs, a descrição e instrução de uso dos endpoints pode ser encontrada no Swagger em anexo neste documento.

### Autenticação

Foi optado por utilizar uma autenticação via JDBC na aplicação devido a familiaridade do desenvolvedor e simplicidade de implementação, juntamente com a possibilidade de utilizar chaves RSA assincronas.

A Autenticação a aplicação pode ser encontrada no Swagger em anexo, mas para simplificação, basta seguir o curl abaixo:

#### Acesso Administrador

    	   curl --location --request POST 'http://localhost:8080/api/v1/auth/token' \
    --header 'Authorization: Basic YWRtaW46YWRtaW4='

As credenciais de administrador são admin:admin

#### Acesso Usuário

    curl --location --request POST 'http://localhost:8080/api/v1/auth/token' \
    
    --header 'Authorization: Basic dXNlcjp1c2Vy'
As credenciais de usuário são user:user

O perfil de administrador possui todas as permissões de usuário, entretanto, o perfil de usuário não pode alterar o status de beneficiarios, apenas de seus documentos, e também não pode excluir beneficiarios.

## Entidades
Primariamente, o sistema lida com duas entidades, que possuem os seguintes atributos:

### Beneficiario
|Tipo|Atributo|Descrição|
|---|---|---|
|Long|id|Chave primária do beneficiário
|String|nome|Nome do beneficiário
|String|telefone|Telefone do beneficiário
|LocalDate|dtNascimento|Data de nascimento do beneficiário
|LocalDateTime|dtInclusao|Data de inclusão do beneficiário
|LocalDateTime|dtAtualizacao|Data de atualização do beneficiário
|Integer|status|Status de Ativo (0) ou Inativo (1) do beneficiário

### Documento

|Tipo|Atributo|Descrição|
|---|---|---|
|Long|id|Chave primária do documento
|String|descricao|Descrição do documento
|String|tipo|Tipo do documento
|LocalDateTime|dtInclusao|Data de inclusão do documento
|LocalDateTime|dtAtualizacao|Data de atualização do documento
|Integer|status|Status de Ativo (0) ou Inativo (1) do documento

Status foram adicionados aos beneficiários e aos documentos para permitir realizar soft-deletes nas linhas de banco de dados, sem necessidade de comprometer a integridade da aplicação excluindo diretamente linhas, mas existe um endpoint que permite excluir permanentemente entrada no banco de dados da aplicação, apesar de tal pratica não ser recomendada.

## Objetos de Transferências de dados (DTOs)

### Request
- **BeneficiarioRequestDTO:** É utilizado primáriamente para receber dados de criação e alteração de beneficiários.
- **DocumentoRequestDTO:** Está inclusa na classe BeneficiarioRequestDTO, é utilizado para receber dados de criação e alteração de documentos.

### Response
- **BeneficiarioResponseDTO:** É utilizado primáriamente para devolver dados de beneficiários para o front-end.
- **DocumentoRequestDTO:** É utilizado primariamente para devolver dados de documentos para o front-end, está contido dentro de BeneficiarioResponseDTO.
- **ApiResponseDTO:** Envelopamento genérico de respostas para o front-end, para garantir consistencia na devolução dos objetos e clareza nas operações, possui um método builder e dois campos, o campo "message" que recebe uma mensagem para ser devolvida para o front-end e "content" que guarda o conteúdo do payload a ser trafegado.


# Considerações finais
Possiveis implementações futuras para a aplicação:

 - Deploy em cloud da API.
 - Maquina de estados para gerenciar dinamicamente os status do beneficiarios e documentos
 - Implementação de testes unitários.
 - Banco de dados sólido.

### Em caso de duvidas, encaminhar para philipesan123@hotmail.com
