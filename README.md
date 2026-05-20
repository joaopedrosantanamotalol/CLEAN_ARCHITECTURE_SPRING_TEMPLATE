# Clean Architecture no seu projeto Spring Boot

## Objetivo da Clean Architecture

A Clean Architecture serve para separar responsabilidades do sistema.

A ideia principal é:

* o domínio não depende do framework
* regras de negócio ficam isoladas
* infraestrutura pode mudar sem quebrar a regra de negócio
* controller não contém lógica
* banco de dados vira apenas detalhe técnico

---

# Estrutura do seu projeto

```text
src/main/java/com/cleantemplate/base
│
├── application
├── domain
├── infrastructure
└── presentation
```

---

# 1. DOMAIN → núcleo do sistema

```text
domain/
├── entities/
├── gateways/
└── repositories/ (opcional)
```

## Responsabilidade

Contém:

* entidades
* regras de negócio
* contratos do sistema

Aqui NÃO deve existir:

* Spring
* JPA
* banco
* controller
* framework

O domínio deve ser puro Java.

---

## entities/

```text
domain/entities/Usuario.java
```

Representa a regra central do sistema.

Exemplo:

* Usuario
* Protocolo
* Produto
* Pedido

A entidade contém:

* atributos importantes
* comportamento de negócio
* validações de domínio

---

## gateways/

```text
domain/gateways/UsuarioGateway.java
```

São interfaces (portas).

O domínio diz:

> "eu preciso salvar usuários"

Mas ele não sabe:

* se usa MySQL
* PostgreSQL
* API externa
* MongoDB

Exemplo:

```java
public interface UsuarioGateway {

    Usuario salvar(Usuario usuario);

    List<Usuario> listarUsuarios();

    Usuario buscarPorId(Long id);

    Usuario atualizar(Usuario usuario);
}
```

---

# 2. APPLICATION → casos de uso

```text
application/
├── dto/
└── usecases/
```

Aqui fica a lógica da aplicação.

---

## usecases/

```text
application/usecases/
```

Cada classe representa UMA ação do sistema.

Exemplos:

* CriarUsuarioUseCase
* AtualizarUsuarioUseCase
* ListarUsuarioUseCase

O UseCase:

* orquestra a lógica
* usa gateway
* aplica regras
* coordena fluxo

---

## Exemplo de fluxo

```text
Controller
   ↓
UseCase
   ↓
Gateway
   ↓
Infraestrutura
```

---

## dto/

```text
application/dto/
```

DTO = Data Transfer Object

Serve para transportar dados.

Exemplos:

* CriarUsuarioDTO
* AtualizarUsuarioDTO

DTO:

* recebe dados da API
* evita expor entidade diretamente
* separa entrada/saída da regra de negócio

---

## Por que DTO separado?

Porque:

* criar usuário ≠ atualizar usuário
* login ≠ cadastro
* resposta ≠ entrada

Cada ação possui dados diferentes.

Exemplo:

```java
public record AtualizarUsuarioDTO(
    String nome,
    String email
) {}
```

Você não quer:

* role
* senha criptografada
* dataCriacao

vindo do frontend.

---

# 3. INFRASTRUCTURE → detalhes técnicos

```text
infrastructure/
└── persistence/
```

Aqui fica:

* banco
* JPA
* Hibernate
* implementação concreta
* mapper
* repository

Tudo técnico.

---

## entities/

```text
infrastructure/persistence/entities/
```

São entidades do banco.

Exemplo:

```java
@Entity
@Table(name = "usuarios")
public class UsuarioEntity
```

Essa classe pertence ao JPA/Hibernate.

Ela representa tabela.

---

## repositories/

```text
infrastructure/persistence/repositories/
```

Interfaces JPA.

Exemplo:

```java
public interface UsuarioRepository
    extends JpaRepository<UsuarioEntity, Long>
```

Aqui o Spring cria consultas automáticas.

---

## gateways/

```text
infrastructure/persistence/gateways/
```

Implementação concreta dos gateways.

Exemplo:

```java
public class UsuarioGatewayImpl
    implements UsuarioGateway
```

Aqui acontece:

* salvar no banco
* buscar no banco
* usar repository
* converter entidade

---

## mappers/

```text
infrastructure/persistence/mappers/
```

Responsável por converter objetos.

Exemplo:

```text
DTO → Domain
Domain → Entity
Entity → Domain
Domain → Response
```

Você está usando MapStruct.

Isso é muito comum em projetos profissionais.

---

# 4. PRESENTATION → camada HTTP/API

```text
presentation/
├── controllers/
└── response/
```

Aqui fica:

* REST API
* entrada HTTP
* saída HTTP

---

## controllers/

```text
presentation/controllers/
```

Recebe requisição HTTP.

O controller:

* recebe DTO
* chama usecase
* retorna response

Ele NÃO deve:

* acessar banco
* conter regra de negócio
* usar repository direto

---

## response/

```text
presentation/response/
```

Objetos enviados ao frontend.

Exemplo:

```java
public record UsuarioResponse(
    Long id,
    String nome,
    String email
) {}
```

Isso evita retornar:

* senha
* dados internos
* campos desnecessários

---

# Fluxo completo do sistema

## POST /usuarios

```text
Controller
↓
CriarUsuarioDTO
↓
UseCase
↓
Gateway
↓
GatewayImpl
↓
Repository
↓
Banco
```

---

# Fluxo do UPDATE

```text
Controller
↓
AtualizarUsuarioDTO
↓
AtualizarUsuarioUseCase
↓
buscarPorId()
↓
MapStruct atualiza objeto existente
↓
Gateway.atualizar()
↓
Repository.save()
```

---

# Ordem recomendada para desenvolver funcionalidades

## 1. Criar entidade de domínio

```text
domain/entities
```

---

## 2. Criar gateway/interface

```text
domain/gateways
```

---

## 3. Criar DTOs

```text
application/dto
```

---

## 4. Criar UseCase

```text
application/usecases
```

---

## 5. Criar Entity JPA

```text
infrastructure/persistence/entities
```

---

## 6. Criar Repository JPA

```text
infrastructure/persistence/repositories
```

---

## 7. Criar Mapper

```text
infrastructure/persistence/mappers
```

---

## 8. Criar GatewayImpl

```text
infrastructure/persistence/gateways
```

---

## 9. Criar Controller

```text
presentation/controllers
```

---

# Regra mais importante da Clean Architecture

Dependências SEMPRE apontam para dentro.

```text
Presentation
    ↓
Application
    ↓
Domain
```

O domínio nunca conhece:

* Spring
* JPA
* HTTP
* banco

---

# O que evitar

## ERRADO

Controller usando Repository:

```java
repository.save()
```

---

## ERRADO

UseCase usando Response:

```java
UsuarioResponse
```

---

## ERRADO

Entidade de domínio com @Entity do JPA

```java
@Entity
class Usuario
```

---

# Seu projeto atualmente

Você já possui:

* separação por camadas
* use cases
* gateway
* mapper
* DTOs
* responses
* entities separadas
* controller limpo

Isso já está bem próximo de uma Clean Architecture real usada em projetos profissionais Spring Boot.
