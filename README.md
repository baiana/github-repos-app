## "Cadê meu repo ?" Github List App

## :arrow_forward: Como executar 

 1. Gerar [personal access token](https://developer.github.com/v3/auth/#via-oauth-and-personal-access-tokens) e substituir o valor **api_token** no arquivo **gradle.properties**;
 2. sincronizar o gradle (*sync now*);
 3. Executar projeto;


## :computer: Tecnologias utilizadas

**Bibliotecas**
 - Koin;
 - Coroutines; 
 - Navigation component; 
 - CircleImageView;
 - Picasso, Retrofit e Moshi;
 - MocK; 

**Linguagem**: Kotlin

**Arquitetura:** MVVM

## :scroll: Features 

 - [x] Listar repositórios públicos do github consumindo a API disponibilizada; 
 - [x] Pesquisar repositórios públicos;
 - [x] consultar informações básicas sobre o repositório;
 - [x] Direcionar para link do repositório :cherries: 
 - [ ] mostrar meu perfil :cherries:

## :bar_chart: Justificativa para o uso das tecnologias 

**Decisões técnicas:**

**Escolha das bibliotecas de terceiros:**



## :interrobang: Problemáticas encontradas 

 - Ao fazer um commit com um personal token, ele é automaticamente desativado pelo github, necessitando assim da configuração inicial colocada nessa documentação.
 - Ao utilizar a API sem autenticação, as chamadas são bloqueadas após 60 requests por hora ( a autenticação aumenta esse limite para 5000). 
