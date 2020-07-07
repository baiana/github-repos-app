
## Github List App

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
 - [x] Direcionar para link do perfil :cherries:

## :bar_chart: Justificativa para o uso das tecnologias 

**Decisões técnicas:**
- Como solicitado, dentro das opções disponibilizadas, optei por utilizar a linguagem Kotlin e a arquitetura MVVM por sua testabilidade, escalabilidade e integração com recursos do jetpack. 
- O Navigation component também possui diversos recursos de gerenciamento de fragments que me chamam atenção. A ideia inicial era de incluir outros fragments e um bottomNavigation, contudo não consegui concluir a tempo, mas isso não prejudica em nada o funcionamento da aplicação com o Nav component. 
- Para evitar delegar a responsabilidade de guarda estados e dados diretamente ao viewModel, criei classes viewState para fazer esse gerenciamento (essa classe é observada pela activity/fragment e manipulada pelo viewModel).
- Os testes unitários visaram cobrir o máximo possível das classes de ViewModel e viewState.
 - Uma decisão simples mas importante de ser elucidada foi a de substituir o nome repository por projects ao utilizar objetos que representem os repositórios do github. Como repository é uma palavra de uso comum na arquitetura que escolhi, decidi substituir para evitar conflitos na leitura. 

**Escolha das bibliotecas de terceiros:**
As bibliotecas da Square (retrofit, moshi e picasso) são open-source e possuem uma certa confiabilidade dentro da comunidade Android devido as suas constantes atualizações e usos em diversas aplicações comerciais. 
Já possuia familiaridade com o retrofit e Picasso mas decidi experimentar o Moshi após ler uma [tread muito interessante no reddit](https://www.reddit.com/r/androiddev/comments/684flw/why_use_moshi_over_gson/) sobre as suas vantagens em relação ao Gson (minha primeira escolha) e não me arrependi! A implementação foi bem mais rápida e fluida, principalmente com a facilidade das annotations.
Já o MockK e Koin foram escolhidos pelas otimizações focadas em Kotlin, documentação acessível e curva de aprendizado reduzida em comparação ao Dagger e Mockito (escolhas mais tradicionais para DI e mock). 
Esses mesmos fatores também se aplicam para a escolha do Coroutines (que é desenvolvida pela Jetbrains mas ainda precisa ser importada ao projeto).
E por fim, acredito que uma das grandes frustações de todo Dev. Android e a falta de flexibilidade para modificar o formato de ImageViews, pra isso utilizei a biblioteca CircleImageView para tornar os avatares mais parecidos com a UI do feed do github (a qual me inspirei para elaborar os layouts).

## :interrobang: Problemáticas encontradas 

 - Ao fazer um commit com um personal token, ele é automaticamente desativado pelo github, necessitando assim da configuração inicial colocada nessa documentação.
 - Ao utilizar a API sem autenticação, as chamadas são bloqueadas após 60 requests por hora ( a autenticação aumenta esse limite para 5000). 
