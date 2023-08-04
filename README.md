# Minhas Primeiras API's com Java
Neste mini-curso "Minhas Primeiras APIs com Java e Spring", você aprenderá a criar microserviços e como fazer a comunicação entre eles. Criaremos 2 microservios: um para a gestão de produtos e outro para gestão de transporte de produtos. 
Utilizaremos tecnologias como o Lombok, que nos auxiliará na redução da verbosidade do código; o Hibernate, para mapear nossas entidades de banco de dados e realizar validações; e o padrão REST para criar endpoints que seguem as melhores práticas para APIs. Além disso, aprenderemos a utilizar o Feign, uma biblioteca para facilitar a comunicação entre microserviços. Ao final deste curso, você terá adquirido conhecimentos essênciais para desenvolver aplicações seguindo os padrões do mercado e as boas práticas das tecnologias utilizadas.

### 1 - Configurando o banco de dados h2
Antes de criarmos a entidade Produto, vamos configurar o banco de dados H2. O H2 é um banco de dados em memória, adequado para fins de desenvolvimento e testes. É fácil de configurar e não requer instalação adicional. Siga os passos abaixo:

##### Passo 1: Adicione a dependência do H2 no arquivo pom.xml usando maven:
``` xml
<dependency>
   <groupId>com.h2database</groupId>
   <artifactId>h2</artifactId>
   <scope>runtime</scope>
</dependency>
```
##### Passo 2: No arquivo application.properties (ou application.yml, se estiver usando YAML), adicione as configurações do H2:

``` properties
# Configurações do H2
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:loncomerce
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```
### Vamos entender o que fizemos:
##### spring.h2.console.enabled=true:
Essa configuração habilita a console do H2. A console do H2 é uma interface gráfica que nos permite visualizar e interagir com o banco de dados em tempo de execução. Ao habilitar essa opção, podemos acessar a console do H2 através de uma URL específica em nosso navegador durante o desenvolvimento.
A URL do console do H2 é definida automaticamente pelo Spring Boot, e para acessá-la, precisamos adicionar /h2-console ao final da URL base da nossa aplicação.

##### spring.datasource.url=jdbc:h2:mem:loncomerce:
Essa configuração define a URL de conexão com o banco de dados H2 em memória. No caso do H2 em memória, a URL começa com jdbc:h2:mem: seguido de um nome que identifica o banco de dados. Neste exemplo, o nome do banco de dados é "loncomerce". Se esse banco de dados não existir, o H2 criará automaticamente um novo banco de dados em memória com esse nome.

##### spring.datasource.driverClassName=org.h2.Driver:
Essa configuração define a classe do driver JDBC que o Spring Boot utilizará para se conectar ao banco de dados H2. No caso do H2, a classe do driver é org.h2.Driver.

##### spring.datasource.username=sa:
Essa configuração define o nome de usuário para a conexão com o banco de dados. No caso do H2, o usuário padrão é "sa" (superadmin).

##### spring.datasource.password=:
Essa configuração define a senha para a conexão com o banco de dados. No H2 em memória, a senha padrão é vazia (sem senha).

Essas configurações permitem que o Spring Boot configure automaticamente um banco de dados H2 em memória para nossa aplicação.
> É importante ressaltar que o H2 em memória é reiniciado toda vez que a aplicação é encerrada, ou seja, os dados não são persistentes entre as execuções. É ideal para fins de desenvolvimento e testes, mas em ambientes de produção, normalmente usamos um banco de dados persistente, como o MySQL ou o PostgreSQL.

##### Acessando a Console do H2:
Com as configurações acima, podemos acessar a console do H2 no navegador da seguinte forma:

Certifique-se de que sua aplicação Spring Boot esteja em execução
Abra seu navegador web e digite a URL base da sua aplicação Spring Boot. Por padrão, a URL base é http://localhost:8080, mas pode variar dependendo da configuração da sua aplicação.
Adicione /h2-console ao final da URL base e pressione Enter. A URL completa será http://localhost:8080/h2-console.

Aparecerá a tela de login do console do H2. Utilize as informações de conexão definidas nas configurações (spring.datasource.url, spring.datasource.username e spring.datasource.password):

``` properties
JDBC URL: jdbc:h2:mem:loncomerce
User Name: sa
Password: (deixe em branco, pois não há senha)
```
Clique em "Connect" para acessar a console do H2. Agora você pode visualizar e interagir com os dados do banco de dados em memória.

### 2 - Criando a classe Produto com Hibernate, Lombok e Spring Validation
Agora que configuramos o H2, podemos criar a classe de entidade Produto utilizando as anotações do Lombok e aplicando regras de validação com o Spring Validation.

``` java
package com.ederlonbarbosa.mpa.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produto")
public class Produto {


   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;


   @NotBlank(message = "A descrição do produto é obrigatória")
   private String descricao;


   @NotNull(message = "O peso do produto é obrigatório")
   @DecimalMin(value = "0.1", message = "O peso do produto deve ser maior que 0")
   private Double peso;


   @NotNull(message = "A altura do produto é obrigatória")
   @DecimalMin(value = "0.1", message = "A altura do produto deve ser maior que 0")
   private Double altura;


   @NotNull(message = "A largura do produto é obrigatória")
   @DecimalMin(value = "0.1", message = "A largura do produto deve ser maior que 0")
   private Double largura;


   @NotBlank(message = "A cor do produto é obrigatória")
   private String cor;


   @NotNull(message = "O preço do produto é obrigatório")
   @DecimalMin(value = "0.01", message = "O preço do produto deve ser maior que 0")
   private Double preco;


   @NotBlank(message = "O SKU do produto é obrigatório")
   private String sku;
}
```
Para que possamos usar as anotações do Spring Validation, Lombok e Hibernate, precisamos adicionar as seguintes dependências no arquivo pom.xml:
``` xml
<!--Lombok-->
<dependency>
   <groupId>org.projectlombok</groupId>
   <artifactId>lombok</artifactId>
   <optional>true</optional>
</dependency>
<!--Hibernate-->
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<!--Spring Validation-->
<dependency>
   <groupId>jakarta.validation</groupId>
   <artifactId>jakarta.validation-api</artifactId>
   <version>2.0.2</version>
</dependency>
```
### Vamos entender o que fizemos:
Neste passo, criamos a classe Produto utilizando as anotações do Lombok e adicionamos as regras de validação com o Spring Validation e fizemos o mapeamento da entidade usando hibernate.

Ao anotarmos a classe Produto com `@Entity`, estamos indicando ao Hibernate que ela representa uma entidade que será mapeada para uma tabela do banco de dados. Cada instância da classe Produto corresponderá a uma linha na tabela de produtos.

A anotação `@Table(name = "produto")` permite definir o nome da tabela no banco de dados em que a entidade será persistida. No exemplo, estamos definindo que os objetos da classe Produto serão armazenados na tabela "produto".

O `@Id` indica que o atributo id é a chave primária da entidade. No exemplo, utilizamos uma estratégia de geração automática de ID com `@GeneratedValue(strategy = GenerationType.IDENTITY)`. Essa anotação `@GeneratedValue` é utilizada em conjunto com a anotação `@Id` para definir a estratégia de geração de valores para a chave primária de uma entidade JPA (Java Persistence API). Essa anotação é comumente usada quando trabalhamos com bancos de dados relacionais e desejamos que o próprio banco de dados seja responsável pela geração dos valores para a chave primária. Existem várias estratégias de geração de valores para a chave primária, e a escolha da estratégia correta depende do banco de dados que está sendo utilizado e dos requisitos específicos do projeto. As principais estratégias suportadas pelo Hibernate/JPA são:
``` properties
IDENTITY: O banco de dados é responsável por gerar valores autoincrementais para a coluna da chave primária.
SEQUENCE: Utiliza um gerador de sequência para obter valores únicos para a chave primária. Suportado por bancos de dados que possuem sequências (por exemplo banco de dados Oracle).
TABLE: Cria uma tabela no banco de dados para armazenar os valores da chave primária.
AUTO: O provedor JPA decide a estratégia com base no banco de dados e configurações.
```

A anotação `@Data` é uma combinação das anotações `@Getter`, `@Setter`, `@EqualsAndHashCode`, e `@ToString`. Ela gera automaticamente os métodos getters e setters para todos os atributos da classe, implementa os métodos equals e hashCode com base nos atributos e cria um método toString que exibe uma representação em string da instância do objeto.

A anotação `@NoArgsConstructor` cria um construtor padrão (sem argumentos) para a classe Produto. Esse construtor é útil, por exemplo, quando estamos trabalhando com frameworks que exigem um construtor vazio, como o Spring.

A anotação `@AllArgsConstructor` cria um construtor que aceita todos os atributos da classe como argumentos. Esse construtor nos permite instanciar um objeto Produto e definir os valores dos atributos ao mesmo tempo.

As anotações de validação, como `@NotBlank`, `@NotNull`, `@DecimalMin`, etc., são usadas para definir regras de validação para cada atributo. Por exemplo, `@NotBlank` valida que a descrição do produto não seja vazia ou contenha apenas espaços em branco, e `@NotNull` garante que os atributos numéricos (como peso, altura, largura e preço) não sejam nulos.

As mensagens em cada anotação são personalizadas para indicar ao usuário o motivo da falha na validação, caso algum atributo não esteja de acordo com as regras.

Ao adicionar a validação aos atributos da classe Produto, garantimos que os dados inseridos sejam consistentes e válidos antes de persisti-los no banco de dados.

### 3. Criando o Repositório do Produto com Spring Data JPA
Agora, criaremos o repositório para o cadastro de produtos utilizando o Spring Data JPA. O Spring Data JPA nos permite criar um repositório JPA de forma simples, através de uma interface.
``` java
package com.ederlonbarbosa.mpa;


import com.ederlonbarbosa.mpa.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
```
### Vamos entender o que fizemos:

##### @Repository: 
Essa anotação é do Spring Framework e é usada para marcar a interface `ProdutoRepository` como um componente do tipo repositório. Com essa anotação, o Spring reconhece a interface como um repositório gerenciado por ele e permite que ela seja injetada em outros componentes da aplicação.

##### public interface ProdutoRepository extends JpaRepository<Produto, Long> {: 
Essa linha declara a interface `ProdutoRepository`. Essa interface estende `JpaRepository`, que é uma interface fornecida pelo Spring Data JPA. Ao fazer isso, a interface `ProdutoRepository` herda todos os métodos definidos em `JpaRepository`, como métodos de CRUD (Create, Read, Update e Delete) para a entidade `Produto`.

##### <Produto, Long>: 
Essa parte da declaração de interface indica que a interface `ProdutoRepository` trabalha com a entidade `Produto` e usa o tipo `Long` como o tipo de dado para o ID da entidade.


### 4. Implementando o Serviço de Produto
Agora, criaremos o serviço ProdutoService, que será responsável por conter a lógica de negócio relacionada aos produtos e utilizará o repositório ProdutoRepository para interagir com o banco de dados.
``` java
package com.ederlonbarbosa.mpa.service;


import com.ederlonbarbosa.mpa.model.Produto;
import com.ederlonbarbosa.mpa.repository.ProdutoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;


@Service
public class ProdutoService {


   private final ProdutoRepository produtoRepository;


   public ProdutoService(ProdutoRepository produtoRepository) {
       this.produtoRepository = produtoRepository;
   }


   public Produto criarProduto(Produto produto) {
       return produtoRepository.save(produto);
   }


   public List<Produto> buscarTodosProdutos() {
       return produtoRepository.findAll();
   }


   public Produto buscarProdutoPorId(Long id) {
       return produtoRepository.findById(id).orElse(null);
   }


   public Produto atualizarProduto(Produto produto) {
       return produtoRepository.save(produto);
   }


   public void deletarProduto(Long id) {
       produtoRepository.deleteById(id);
   }


   public Produto atualizarParcialmenteProduto(Long id, Map<String, Object> camposAtualizados) throws JsonProcessingException {
       Produto produtoExistente = produtoRepository.findById(id).orElse(null);


       if (produtoExistente != null) {
           ObjectMapper objectMapper = new ObjectMapper();
           ObjectReader reader = objectMapper.readerForUpdating(produtoExistente);
           Produto produtoAtualizado = reader.readValue(objectMapper.writeValueAsString(camposAtualizados));


           return produtoRepository.save(produtoAtualizado);
       } else {
           return null;
       }
   }
}
```
### Vamos entender o que fizemos:

##### @Service: 
Essa anotação indica que a classe é um componente de serviço do Spring. Ela é usada para marcar classes que contêm a lógica de negócio da aplicação.

##### private final ProdutoRepository produtoRepository;: 
Esse é um atributo do tipo `ProdutoRepository`, que é uma interface que estende `JpaRepository`. Ele é injetado automaticamente pelo Spring por meio da injeção de dependência.

##### public ProdutoService(ProdutoRepository produtoRepository) { ... }: 
Este é o construtor da classe `ProdutoService`. Ele recebe uma instância de `ProdutoRepository` como parâmetro e, em seguida, atribui esse objeto ao atributo `produtoRepository`. Poderíamos ter usado a anotação do lombok `@RequiredArgsConstructor` e ele se encarregaria de gerar automáticamente esse construtor.

##### public Produto criarProduto(Produto produto) { ... }: 
Esse método é responsável por criar um novo produto. Ele recebe um objeto `Produto` como parâmetro e chama o método `save` do `produtoRepository` para salvá-lo no banco de dados. O método `save` retorna o objeto `Produto` salvo no banco, que é retornado por este método.

##### public List<Produto> buscarTodosProdutos() { ... }: 
Esse método busca todos os produtos no banco de dados. Ele chama o método `findAll` do `produtoRepository`, que retorna uma lista contendo todos os produtos armazenados.

##### public Produto buscarProdutoPorId(Long id) { ... }: 
Esse método busca um produto pelo seu ID. Ele chama o método `findById` do `produtoRepository`, passando o ID como parâmetro. O método `findById` retorna um `Optional<Produto>`, que contém o produto encontrado, se existir. Usando o método `orElse(null)`, o método retorna o produto encontrado ou `null` se nenhum produto for encontrado com o ID fornecido.

##### public Produto atualizarProduto(Produto produto) { ... }: 
Esse método é responsável por atualizar um produto existente. Ele chama o método `save` do `produtoRepository`, passando o produto como parâmetro. Como o objeto `produto` já tem um ID associado (pois é um produto existente), o método `save` realiza uma atualização no banco de dados em vez de criar um novo registro.

##### public void deletarProduto(Long id) { ... }: 
Esse método é responsável por deletar um produto pelo seu ID. Ele chama o método `deleteById` do `produtoRepository`, passando o ID como parâmetro. O método `deleteById` remove o produto do banco de dados com o ID fornecido.

##### public Produto atualizarParcialmenteProduto(Long id, Map<String, Object> camposAtualizados) throws JsonProcessingException { ... }: 
Esse método permite a atualização parcial de um produto. Ele recebe o ID do produto e um `Map` contendo os atributos a serem atualizados. Primeiro, ele busca o produto existente usando o método `findById` do `produtoRepository`. Se o produto existir, ele utiliza a biblioteca Jackson para realizar uma atualização parcial, mapeando os valores contidos no `Map` para o objeto `produtoExistente`. Em seguida, o método `save` é chamado no `produtoRepository` para atualizar o produto no banco de dados com as alterações.

### 4. Criando o controller de Produto
Por fim, criaremos o controller (ProdutoController) que será responsável por receber as requisições HTTP e invocar as operações do serviço ProdutoService.
``` java
package com.ederlonbarbosa.mpa.controller;


import com.ederlonbarbosa.mpa.model.Produto;
import com.ederlonbarbosa.mpa.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {


   private final ProdutoService produtoService;


   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public Produto criarProduto(@RequestBody @Valid Produto produto) {
       return produtoService.criarProduto(produto);
   }


   @GetMapping
   public List<Produto> buscarTodosProdutos() {
       return produtoService.buscarTodosProdutos();
   }


   @GetMapping("/{id}")
   public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
       Produto produto = produtoService.buscarProdutoPorId(id);
       if (produto != null) {
           return ResponseEntity.ok(produto);
       } else {
           return ResponseEntity.notFound().build();
       }
   }

   @PutMapping("/{id}")
   public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody @Valid Produto produto) {
       Produto produtoExistente = produtoService.buscarProdutoPorId(id);
       if (produtoExistente != null) {
           produto.setId(id);
           Produto produtoAtualizado = produtoService.atualizarProduto(produto);
           return ResponseEntity.ok(produtoAtualizado);
       } else {
           return ResponseEntity.notFound().build();
       }
   }


   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
       Produto produtoExistente = produtoService.buscarProdutoPorId(id);
       if (produtoExistente != null) {
           produtoService.deletarProduto(id);
           return ResponseEntity.noContent().build();
       } else {
           return ResponseEntity.notFound().build();
       }
   }


   @PatchMapping("/{id}")
   public ResponseEntity<Produto> atualizarParcialmenteProduto(@PathVariable Long id, @RequestBody Map<String, Object> camposAtualizados) {
       Produto produtoAtualizado = produtoService.atualizarParcialmenteProduto(id, camposAtualizados);
       if (produtoAtualizado != null) {
           return ResponseEntity.ok(produtoAtualizado);
       } else {
           return ResponseEntity.notFound().build();
       }
   }
}

```
### Vamos entender o que fizemos:

##### @RequiredArgsConstructor: 
Essa anotação é do projeto Lombok e gera automaticamente um construtor para a classe com os atributos marcados como `final`. Isso é especialmente útil em classes que utilizam injeção de dependências, como no caso do atributo `produtoService`.

##### @RestController:
Essa anotação indica que a classe é um controller que irá lidar com as requisições HTTP e retornar as respostas adequadas.

##### @RequestMapping("/api/produtos"): 
Essa anotação define o caminho base para todos os endpoints gerenciados por esse controller. Todos os métodos dentro dessa classe terão seu caminho concatenado a "/api/produtos".

##### private final ProdutoService produtoService;: 
Esse é um atributo do tipo `ProdutoService`, que é uma classe que lida com a lógica de negócio relacionada aos produtos. Ele é injetado automaticamente pelo Spring Boot por meio da injeção de dependência, já que ele foi declarado como `final` e anotado com `@RequiredArgsConstructor`. Isso significa que o construtor foi gerado automaticamente pelo Lombok e, portanto, não é necessário escrever um construtor manualmente.

##### @PostMapping: 
Essa anotação define o endpoint para a criação de um novo produto. Nesse caso, o método `criarProduto` recebe um objeto `Produto` no corpo da requisição, validado com `@Valid`, e chama o método `criarProduto` do `produtoService` para criar e retornar o novo produto. O status de resposta é configurado como `CREATED` (201) usando `@ResponseStatus(HttpStatus.CREATED)`.

##### @GetMapping: 
Essa anotação define o endpoint para a busca de todos os produtos. O método `buscarTodosProdutos` chama o método `buscarTodosProdutos` do `produtoService`, que retorna uma lista de produtos que é retornada como resposta.

##### @GetMapping("/{id}"): 
Essa anotação define o endpoint para a busca de um produto por ID. O método `buscarProdutoPorId` recebe o ID do produto como um parâmetro de caminho (`@PathVariable`) e chama o método `buscarProdutoPorId` do `produtoService`, que retorna o produto correspondente. Se o produto existir, ele é retornado com o status `OK` (200), caso contrário, é retornado um status `NOT_FOUND` (404).

##### @PutMapping("/{id}"): 
Essa anotação define o endpoint para a atualização de um produto. O método `atualizarProduto` recebe o ID do produto como um parâmetro de caminho (`@PathVariable`) e o novo objeto `Produto` no corpo da requisição, validado com `@Valid`. Ele verifica se o produto existe chamando `buscarProdutoPorId` do `produtoService`. Se o produto existir, ele atualiza o ID do novo produto com o ID fornecido no caminho, chama `atualizarProduto` do `produtoService` para atualizar o produto e retorna o produto atualizado com o status `OK` (200). Se o produto não existir, é retornado um status `NOT_FOUND` (404).

##### @DeleteMapping("/{id}"): 
Essa anotação define o endpoint para a exclusão de um produto. O método `deletarProduto` recebe o ID do produto como um parâmetro de caminho (`@PathVariable`). Ele verifica se o produto existe chamando `buscarProdutoPorId` do `produtoService`. Se o produto existir, ele chama `deletarProduto` do `produtoService` para excluir o produto e retorna um status `NO_CONTENT` (204). Se o produto não existir, é retornado um status `NOT_FOUND` (404).

##### @PatchMapping("/{id}": 
Essa anotação define o endpoint para a atualização parcial de um produto. O método `atualizarParcialmenteProduto` recebe o ID do produto como um parâmetro de caminho (`@PathVariable`) e um `Map` que representa os atributos a serem atualizados no corpo da requisição. Ele chama `atualizarParcialmenteProduto` do `produtoService` para atualizar o produto e retorna o produto atualizado com o status `OK` (200). Se o produto não existir, é retornado um status `NOT_FOUND` (404).

O `ProdutoController` define os principais endpoints para operações CRUD (Create, Read, Update, Delete) relacionados a produtos. As operações são tratadas pelo serviço `ProdutoService`, que contém a lógica de negócio associada a produtos. O controller recebe as requisições HTTP, valida os dados, chama os métodos correspondentes do serviço e retorna as respostas adequadas, incluindo o status code apropriado, em formato JSON.