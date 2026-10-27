# Hard Zone

Sistema de gerenciamento para **lan house**, desenvolvido em Java com JPA/Hibernate e banco Oracle. A aplicação roda no console e permite controlar computadores, jogos, usuários, sessões de uso e pagamentos.

## Funcionalidades

- **Computadores**: cadastro, listagem, busca por número da máquina, atualização, alteração de status (`DISPONIVEL`, `OCUPADO`, `MANUTENCAO`, `FORA_DE_USO`), remoção e vínculo/desvínculo de jogos instalados.
- **Jogos**: cadastro com nome e faixa etária, listagem, busca por nome, atualização e remoção. Não permite nomes duplicados.
- **Usuários**: cadastro (nome, data de nascimento e e-mail únicos), busca por nome, e-mail e ID, filtros por período e data de cadastro, ativação/desativação e exclusão permanente.
- **Sessões**: início de sessão (usuário + computador + preço por hora), encerramento com cálculo automático do valor pelo tempo de uso e listagem de sessões ativas. O computador muda para `OCUPADO` ao iniciar e volta para `DISPONIVEL` ao encerrar.
- **Pagamentos**: geração de pagamento com código único (`PAG-HARDZONE-...`), busca por ID ou sessão, filtro por período, faturamento por período e consulta do pagamento de maior valor.

## Tecnologias

| Tecnologia | Versão |
| --- | --- |
| Java | 21 |
| Maven (wrapper incluso) | - |
| Jakarta Persistence (JPA) | 3.1.0 |
| Hibernate ORM | 6.4.4.Final |
| Oracle JDBC (`ojdbc11`) | 21.11.0.0 |
| JUnit Jupiter | 5.10.2 |

## Estrutura do projeto

```
src/main/java/com/ifpb/hard_zone/
├── Main.java            # Menu principal (ponto de entrada)
├── controller/          # Camada de controle
├── service/             # Regras de negócio e validações
├── repository/          # Acesso a dados (JPA)
├── model/               # Entidades: Computador, Jogo, Usuario, Sessao, Pagamento
├── menu/                # Menus de console e utilitários de entrada/formatação
├── exception/           # Exceções de negócio e GlobalExceptionHandler
├── interfaces/          # Contratos (ex.: Repository)
└── util/                # JPAUtil, Validator, CodigoPagamento, enums

src/main/resources/META-INF/persistence.xml   # Configuração do banco
src/test/java/                                # Testes (fluxo de persistência)
```

A arquitetura segue o fluxo **Menu → Controller → Service → Repository → Banco**.

## Modelo de dados

- `Computador` ↔ `Jogo`: relação **muitos-para-muitos** (tabela `computador_jogos`).
- `Sessao` → `Usuario` e `Sessao` → `Computador`: **muitos-para-um**.
- `Pagamento` → `Sessao`: **um-para-um**.

As tabelas são criadas/atualizadas automaticamente pelo Hibernate (`hbm2ddl.auto = update`).

## Pré-requisitos

- JDK 21+
- Banco Oracle (XE) acessível em `localhost:1521`, serviço `XEPDB1`
- Maven (opcional, o projeto inclui `mvnw`)

## Configuração do banco

Edite `src/main/resources/META-INF/persistence.xml` com os dados do seu banco:

```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:oracle:thin:@//localhost:1521/XEPDB1"/>
<property name="jakarta.persistence.jdbc.user" value="SEU_USUARIO"/>
<property name="jakarta.persistence.jdbc.password" value="SUA_SENHA"/>
```

Uma opção rápida para subir um Oracle local com Docker:

```bash
docker run -d --name oracle-xe -p 1521:1521 \
  -e ORACLE_PASSWORD=<senha_admin> \
  -e APP_USER=hardzone \
  -e APP_USER_PASSWORD=<senha_do_usuario> \
  gvenzl/oracle-xe
```

> ⚠️ Evite versionar senhas reais no repositório. Prefira usar valores locais ou variáveis de ambiente.

## Como executar

```bash
# Clonar o repositório
git clone https://github.com/jwgnr1/hard-zone.git
cd hard-zone

# Compilar
./mvnw clean compile

# Executar a aplicação
./mvnw org.codehaus.mojo:exec-maven-plugin:3.1.0:java \
  -Dexec.mainClass=com.ifpb.hard_zone.Main
```

Também é possível abrir o projeto na IDE (IntelliJ, Eclipse, etc.) e executar a classe `Main`.

Ao iniciar, o menu principal é exibido:

```
===== HARD ZONE - LAN HOUSE =====
1 - Computadores
2 - Jogos
3 - Usuários
4 - Sessões
5 - Pagamentos
0 - Sair
```