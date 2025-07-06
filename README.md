# INDIVIDUAL-CHALLENGE
Foram utilizados: (JAVA 17, SpringBoot 3.5.3, Docker e PostgreSQL).

SWAGGER para documentar a api: https://app.swaggerhub.com/apis/unirio-c08/Baggaggio-individual-challenge/1.0.0#/

Além de Postman para lidar e testar as requisições.
## Pré-requisitos

Antes de começar, você precisará ter as seguintes ferramentas instaladas em sua máquina:
- [Git](https://git-scm.com/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

## Como Executar o Projeto

Siga os passos abaixo para ter a aplicação rodando localmente em sua máquina.

Escolha uma pasta em seu computador onde deseja salvar o projeto (por exemplo, `C:\dev\`). Navegue até ela e clone o repositório.

```powershell
# Exemplo navegando para a pasta C:\dev
cd C:\dev\

# Clone o projeto do GitHub
git clone https://github.com/leandrohcn/Baggaggio-individual-challenge.git
```
Este comando criará uma pasta chamada `Baggaggio-individual-challenge` com todo o código do projeto dentro dela.

### Entre no Ambiente WSL

Agora que o código está na sua máquina, vamos entrar no ambiente Linux (WSL) para executar os comandos do Docker. No mesmo terminal, digite:

```powershell
wsl
```
Seu prompt de comando mudará, indicando que você agora está dentro do Linux (por exemplo, de `PS C:\...>` para `seu_usuario@NOME-PC:/mnt/c/...$`).

### Navegue até a Pasta do Projeto (Dentro do WSL)

Você precisa navegar para a pasta do projeto que acabou de clonar. O WSL acessa seus drives do Windows através do caminho `/mnt/`.

```bash
# Exemplo acessando a pasta C:\dev\Baggaggio-individual-challenge que clonamos antes
cd /mnt/c/dev/Baggaggio-individual-challenge/
```
**Dica:** Use a tecla `Tab` para autocompletar os nomes das pastas e evitar erros de digitação.

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
**b) Entenda o que o arquivo faz:**

* O arquivo `.env` que você acabou de criar define o usuário, a senha e o nome do banco de dados que serão usados pelo contêiner do PostgreSQL.
* O arquivo `application.properties` da aplicação Spring Boot está configurado para ler essas mesmas variáveis do ambiente, garantindo que tudo se conecte corretamente.
* Os valores padrão no `.env.example` já são adequados para rodar o projeto localmente. Você não precisa editar o arquivo `.env` após criá-lo, a menos que queira usar credenciais diferentes por algum motivo.

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
* **`GET /produtos?categoria=EXEMPLO`**: Lista todos os produtos de uma categoria especifica (Enum de categoria contem: MALAS, MOCHILAS, BOLSAS, ESCOLAR, TERMICOS, CARTEIRAS, PASTAS, ACESSORIOS). Precisa ser escrito tudo em maiusculo.
* **`GET /produtos/{id}`**: Busca um produto específico por ID.
* **`POST /produtos`**: Cria um novo produto. (Verifique o corpo JSON necessário).
* **`DELETE/produtos/{id}`**: Exclui um produto pelo ID.
* **`PUT/produtos/{id}`**: Atualiza/Altera dados de um produto pelo ID.

## Testes Unitarios para os endpoints (caminhos felizes e tristes)
Para visualizar os testes unitarios, vá na IDE de sua escolha, abra o projeto e siga o caminho: **`src/test/java/com/baggaggio/individual_challenge/ControllerTest/ProdutoTest.java`**

Execute a classe **`ProdutoTest`**

Caso os testes nao estejam rodando por conta de algum erro, tente mudar na application.properties o **`"spring.datasource.url=jdbc:postgresql://localhost:5432/baggaggio"`**, para exatamente como está aqui.

## Como Parar a Aplicação

Para parar e remover os contêineres, pressione `Ctrl + C` no terminal onde a aplicação está rodando e depois execute o seguinte comando:

```bash
docker compose down
```
