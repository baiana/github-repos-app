

## GitFeed

## :arrow_forward: Como executar 

 1. Gerar [personal access token](https://developer.github.com/v3/auth/#via-oauth-and-personal-access-tokens) com acesso a leitura de repositórios e usuários.
 2.  Substituir o valor em  **api_token** no arquivo **gradle.properties** pelo token gerado;
 3. sincronizar o gradle (*sync now*);
 4. Executar projeto a partir da branch master;
 5. A aplicação necessita de internet para funcionar;


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
 - [x] Direcionar para link externo do repositório :cherries: 
 - [x] Direcionar para link externo do perfil :cherries:

## :bar_chart: Justificativa para o uso das tecnologias 

**Decisões técnicas:**
Alguns fatores chave nortearam a maioria das decisões técnicas feitas no desenvolvimento dessa aplicação: código reutilizável, testável e facilmente substituível sem grandes refatorações a aplicação. Mesmo sendo uma aplicação pequena, as decisões foram tomadas visualizando um desenvolvimento contínuo e escalável.

**MVVM, ViewBinding e LiveData**
 - Dentre as opções apresentadas para linguagem, optei pelo Kotlin por ter maior familiaridade com a linguagem.
 - A arquitetura MVVM foi escolhida devido a sua testabilidade, separação de responsabilidade e fácil integração com os componentes do Jetpack e outros recursos utilizados no projeto como Coroutines e injeção de dependência. Utilizar o MVVM com repository também facilitou a implementação desses processos e agregou mais uma camada de isolamento entre o viewModel e o datasource, facilitando sua substituição em caso de testes ou até na troca por outra API sem a necessidade de grande refatoração no código. 
 - Para todas as classes que possuem layout foi utilizado o viewBinding. Pois ele garante a segurança de tipo e evita erros oriundos de views nulas (e reduz a possibilidade de erros causados por imports de views de outros layouts).
 - O LiveData foi escolhido para o binding pela sua integração a arquitetura, familiaridade e respeito ao ciclo de vida.
 
 **Injeção de dependência e coroutines**
 
 - Além de ser uma prática que auxilia na criação de uma boa arquitetura, a injeção de dependência apresenta as vantagens que nortearam a maioria das decisões técnicas feitas no desenvolvimento dessa aplicação: código reutilizável, testável e de facil refatoração.
 - O corutines foi escolhido para melhorar a performance e trabalhar com operações assíncronas com maior facilidade, além de ser integrada com ViewModel, facilitando ainda mais o processo. A biblioteca retrofit também possui suporte pra operações assíncronas  com coroutines na versão utilizada nessa aplicação.
 - 
**Navigation**
 - A gestão manual de fragments pode gerar diversos problemas e inconsistências no código, mesmo quando feita através de adapters. O navigation component simplifica esse processo, agrega uma melhor visualização do fluxo e facilita a adição e integração de fragments a aplicação. Mesmo esse app possuindo somente um fragment, a ideia e tornar a adição de mais telas o mais fácil possível, ainda assim, a implementação desse componente e suas vantagens não são condicionais a quantidade de fragments implementados. 

**Escolha das bibliotecas de terceiros:**

- As bibliotecas da Square (retrofit, moshi e picasso) são open-source e possuem  confiabilidade dentro da comunidade Android devido as suas constantes atualizações, inovações e usos em diversas aplicações comerciais (algumas sendo utilizadas pela própria google como referência para suprir demandas não nativas). 
- Pessoalmente, eu tenho familiaridade com o retrofit e Picasso mas decidi experimentar o Moshi após ler uma [tread muito interessante no reddit](https://www.reddit.com/r/androiddev/comments/684flw/why_use_moshi_over_gson/) sobre as suas vantagens em relação ao Gson (minha primeira escolha) e não me arrependi! A implementação foi bem mais rápida e fluida se comparada ao Gson, principalmente com a facilidade das annotations que podem ser observadas nas classes de response e result.
- Já o MockK e Koin foram escolhidos pelas otimizações focadas em Kotlin, documentação acessível e curva de aprendizado reduzida em comparação ao Dagger e Mockito (escolhas mais tradicionais para DI e mock). 
- Esses mesmos fatores também se aplicam para a escolha do Coroutines (que é desenvolvida pela Jetbrains mas ainda precisa ser importada ao projeto).
- Também utilizei o [shapeshifter](https://shapeshifter.design/) para criar um loading através da animação e conversão de um svg estático em um AnimatedVectorDrawable. Esse processo foi minha primeira interação com a plataforma, porém devido a alguns crashs e ANRs oriundo da animação com o vectorDrawable, esse loading foi removido da aplicação :sob:.
- E por fim, acredito que uma das grandes frustações do dia a dia de todo Dev. Android é a falta de flexibilidade para modificar o formato de ImageViews, pra isso utilizei a biblioteca CircleImageView para tornar os avatares mais parecidos com a UI do feed do github.

**Escolhas de design**
- A principal inspiração para a UI foi o feed do Github, desde o formato da lista até a maneira de mostrar os nomes do formato username/reponame remetem a caracteristicas bem reconhecíveis da plataforma. 
- Essa influência também se encontra no formato dos containers tanto da tela de detalhes quanto dos itens da lista.
- As mensagens de erro também foram inspiradas pela linguagem mais informal e bem humorada da plataforma. 
- A paleta de cores escura foi tirada da página inicial do github e dos [guidelines de dark theme](https://material.io/design/color/dark-theme.html#usage) do material design (e também expressam um pouco da minha frustação como dev com a interface light da plataforma :sweat_smile: ) .


## :interrobang: Problemáticas encontradas 

 - Ao fazer um commit com um personal token, ele é automaticamente desativado pelo github, necessitando assim da configuração inicial colocada nessa documentação.
 - Ao utilizar a API sem autenticação, as chamadas são bloqueadas após 60 requests por hora ( a autenticação aumenta esse limite para 5000). 

## :thought_balloon: Será que esqueci alguma coisa? 
Caso tenha alguma dúvida, sugestão ou melhoria é só falar! (ou abrir um PR).
