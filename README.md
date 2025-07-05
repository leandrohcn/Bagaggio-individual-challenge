# INDIVIDUAL-CHALLENGE
Foram utilizados: (JAVA, SpringBoot, Docker e PostgreSQL)

## Pré-requisitos

Antes de começar, você precisará ter as seguintes ferramentas instaladas em sua máquina:
- [Git](https://git-scm.com/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

## Como Executar o Projeto

Siga os passos abaixo para ter a aplicação rodando localmente em sua máquina.

**1. Clonar o Repositório**

```bash
git clone https://github.com/leandrohcn/Baggaggio-individual-challenge.git
```

**2. Configurar Variáveis de Ambiente**

Este projeto utiliza um arquivo `.env` para configurar as credenciais do banco de dados. Crie seu próprio arquivo a partir do modelo de exemplo.

*No Linux ou macOS:*
```bash
cp .env.example .env
```
*No Windows (CMD):*
```powershell
copy .env.example .env
```
Depois de criar o arquivo `.env`, você pode alterar os valores dentro dele se desejar.

**3. Subir os Contêineres com Docker Compose**

Este comando irá construir a imagem da aplicação e iniciar os contêineres do backend e do banco de dados.

```bash
docker compose up --build
```

Aguarde o processo terminar. Você verá os logs de inicialização do Spring Boot no seu terminal.

## Acessando a API
A API estará disponível em seu navegador ou cliente de API (como o Postman) no seguinte endereço:
`http://localhost:8080`

### Endpoints de Exemplo

Aqui estão alguns exemplos de endpoints que você pode testar:

* **`GET /produtos`**: Lista todos os produtos.
* **`GET /produtos?categoria=EXEMPLO`**Lista todos os produtos de uma categoria especifica (Enum de categoria contem: MALAS, MOCHILAS, BOLSAS, ESCOLAR, TERMICOS, CARTEIRAS, PASTAS, ACESSORIOS). Precisa ser escrito tudo em maiusculo.
* **`GET /produtos/{id}`**: Busca um produto específico por ID.
* **`POST /produtos`**: Cria um novo produto. (Verifique o corpo JSON necessário).
* **`DELETE/produtos/{id}`**: Exclui um produto pelo ID.
* **`PUT/produtos/{id}`**: Atualiza/Altera dados de um produto pelo ID.
## Como Parar a Aplicação

Para parar e remover os contêineres, pressione `Ctrl + C` no terminal onde a aplicação está rodando e depois execute o seguinte comando:

```bash
docker compose down
```
