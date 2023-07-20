# Job de Geração de faturas de cartão de crédito

> 1. Implementação com Spring Batch que faz:
> - A leitura no banco de dados com o padrão delegate
> - O processamento acessando um serviço REST
> - E a escrita em múltiplos arquivos, gerando uma fatura de cartão de crédito para cada cliente.
>
> 2. Leitura com agregação de dados e processamento acessando serviços REST são necessidades muito comuns
na implementação de sistemas batch.
>
> 3. Para a realização do teste, remover os arquivos fatura.txt que estao na pasta files e criar as tabelas no banco de dados e as suas inserções.
>
> 4. Para a criação de um serviço Rest para testes, ver informações [aqui](https://github.com/Henderson-da-rocha-porfirio/servico-teste-clientes-json)

## Spring Batch - Framework -

### 1. Rotinas automatizadas para um grande lote de dados.

> - Um sistema que realiza um processamento de uma quantidade finita de dados sem interação ou interrupção.

> - Usado como dependência do Spring Boot.

> - Duas dependências são necessárias para o funcionamento: spring batch e o quartz scheduler

> - Garante a atomicidade da transação

### 2. Perguntas de requisitos não funcionais (desempenho, usabilidade, confiabilidade, segurança, disponibilidade, manutenção e tecnologias envolvidas):

1. Tempo: Quanto tempo o processamento precisa ser realizado sem prejuízo aos serviços que dependem dele?
2. Integridade: Como garantir a integridade dos dados se o sistema falhar?
3. Execuçao: Como o sistema irá agendar tarefas sem a interferência humana?
4. Monitoramento: Como acompanhar a execução do processamento para saber o seu andamento e identificar se houveram falhas?

- Spring Batch consegue tratar bem os problemas relacionados a:
1. Escalibilidade: garante que o processamento desse grande lote de dados seja feito dentro da janela de tempo definida para a execução.
2. Disponibilidade: afetada se o tempo sugerido pela escalibilidade não seja respeitado.
3. Usabilidade: parte gráfica. Mas no sistema batch refere-se ao código em si. Ou seja, é simples de mantê-lo e identificar erros.

3. De maneira prática:
   a. O sistema por ser de transacao bancária, precisa disso.
   b. Eu consigo fazer:
- agendamentos
- cálculos
- comunicaçao com serviços e banco de dados externo sem interferir em outros serviços.
- migração de dados
- ganho de performance

## Job : coleção de estados e transições definidas por steps (tasklets e chunks)

## Trabalha com 3 lógicas:

leitura: ItemReader (ler dados do banco de dados usando componentes jdbc do spring batch):

CSV Item Reader

JSON Item Reader

XML Item Reader

JDBC Item Reader

REST API Item Reader


processador: ItemProcessor (acessa outros serviços também)

escrita: ItemWriter (executa. exemplo: transacoes ou envios ou etc):

CSV Item Writer

JSON Item Writer

XML Item Writer

JDBC Item Writer

REST API Item Writer

Agendador: Da para usar o Quartz para agendamento de jobs


## Sobre webservices - API's: faz leitura de dados em lotes. Caso isso não ocorra, ou seja, não há um comportamento em lote através da paginação, nao fará sentido criar um leitor para ler item a item ou ler de forma nao-paginada e manter
tudo em memória.



## Steps & Chunks: eles são divididos em pedaços(chunks) de Leitura(ItemReader), Processamento(ItemProcessor) e Escrita(ItemWriter) e cada chunk possui sua própria transacao


Diante da grande quantidade de dados que serão processados, eu estou fazendo uns testes com um projeto spring batch
porque ele permite um bom acompanhamento e registro de toda a execução e prevê suporte para transacoes.

Vantagens:

1 _ Garante a atomicidade da transacao (ACID - tudo ocorre ou nada ocorre)
- Se der certo a transacao, ele commita e faz todos os registros no database.
- Caso contrário, não deu certo, ou seja, se um ou mais de um ou todos derem errado, ele faz o rollback de todos os itens do chunck. O commit não acontece, por tanto,
  nenhum registro é feito no database.
- Exemplo:
  Se o primeiro chunck de tamanho 20 funcionar, e o segundo chunck de tamanho 20 der erro, teremos apenas 20 registros e assim sucessivamente.



2 _ Divisao em Chunks (pedaços)
- exemplo:
1. divisao de 100 registros em chuncks, ou seja,  5 pedaços com tamanho de 20 ( 5 chuncks x tamanho 20 = 100 registros )
2. Cada pedaço (chunck) roda numa transacão.



3 _ Transaction

i. job:
- criado um processamento de um arquivo csv: pessoa.csv (dentro do csv terá um registro de 10.000 pessoas) = pessoas serão lidas
- criado a carga desse arquivo no database: "INSERT INTO pessoa (id, nome, email, data_nascimento, idade)
  VALUES (:id, :nome, :email, :dataNascimento, :idade)") = pessoas serão escritas
- No final haverá 10.000 registros de pessoas no database


4 _  Reusibilidade
- capacidade de utilizacao de dados através de várias transacoes de dados sem precisar recriá-los.


5 _ Transações acontecem mesmo que haja algum tipo de impedimento.


Para simular o teste, é preciso:

a _ criar o banco no postgresql
b _ criar as tabelas que estão no exemplo
c _ Para isso verificar o arquivo scripts.sql


## Job : coleção de estados e transições definidas por steps
## Trabalha com 3 lógicas:

leitura: ItemReader (ler dados do banco de dados usando componentes jdbc do spring batch)

processador: ItemProcessor (acessa outros serviços também)

escrita: ItemWriter (executa. exemplo: transacoes ou envios ou etc)

agendador: Da para usar o Quartz para agendamento de jobs

## É possível obter os dados de outros serviços



Ajustes-DoServiçoDaApiPosAlteracoes


## Componentes de leitura:
- tipo cursor
- tipo paginada

## Plus: MERGE pelo intellij

## Usando o Gitlab:

#### I - Merge Into ( enviar as alterações que eu fiz, "da minha" branch, "na" outra branch)

***** Interessante fazer um backup antes(tem que estar na branch a qual vai fazer o backup): `git checkout -b backup_minha_branch`

> 1 _ faz checkout na que deseja enviar as mudanças. Ex: quer jogar da develop(sua branch) para a master(branch principal). Faz checkout na master primeiro.
>
> 2 _ Menu Git -> escolhe Merge -> Em Merge into master -> selecionar a branch de origem onde foram feitas alteracoes para jogar na que fez o checkout. Ex: escolha a develop.

![merge into](https://user-images.githubusercontent.com/46926951/215347045-cef91299-ddfe-40c2-bc91-91581ccc156f.png)

> 3 _ Por último, faz um Push para enviar para o github (ou gitlab) para realmente modificar o que deseja.

> 4 _ Realizar por comando:

a. checkout:
````
git checkout develop (a outra branch)
````

b. merge:
````
git merge develop (sua branch)
````

#### II - Backup, Pull e Merge Request de Outra Branch ( bom verificar antes de fazer um merge onto. Porque elimina a possibilidade de se fazer um merge desnecessário ):

- Passo 1: Backup da sua branch atual:
>
- Verifique em qual branch você está atualmente com o comando:

`git branch`
> 
- O branch no qual você está será indicado por um asterisco. Supondo que você esteja no branch minha_branch, vamos fazer uma cópia dela:

`git checkout -b backup_minha_branch`
>
- Este comando cria um novo branch chamado backup_minha_branch que é uma cópia do branch atual minha_branch.
>
- Passo 2: Atualizando a sua branch original
> Agora, você deve voltar para a sua branch original e atualizá-la com as alterações da branch develop dando os comandos em sequência:

1. `git checkout minha_branch`
2. `git pull origin develop`

- Estes comandos fazem o seguinte:

- `git checkout minha_branch`: muda para a branch minha_branch.
- `git pull origin develop`: busca as atualizações do branch develop e integra com o branch minha_branch.

- Passo 3: Subindo as alterações para a sua branch remota

- Agora que a sua branch `minha_branch` está atualizada, você pode subir as alterações para a branch remota.

`git push origin minha_branch`

- Passo 4: Fazendo um Merge Request (GitLab. Verificar algo parecido no Github):
- *** Para criar um Merge Request no GitLab, siga os passos abaixo:

- a. Faça login na sua conta do GitLab e navegue até o projeto específico no qual você deseja criar um Merge Request.

- b. No menu à esquerda, clique em "Merge Requests":

  ![image](https://github.com/Henderson-da-rocha-porfirio/spring-batch-job-de-geracao-de-fatura/assets/46926951/47debd43-5a6b-4b07-b462-d15c9afd2e87)


- c. Em seguida, clique no botão "New merge request".

  ![image](https://github.com/Henderson-da-rocha-porfirio/spring-batch-job-de-geracao-de-fatura/assets/46926951/6bcb2086-5e9b-47bb-971c-8e196aac4440)


- d. Agora você precisará selecionar o branch de origem (sua branch com as alterações) e o branch de destino (geralmente, a branch `main` ou 
`develop`, dependendo do seu fluxo de trabalho).

- e. No campo `Source branch`, escolha sua branch (a branch onde você fez as alterações).

- f. No campo `Target branch`, escolha a branch para a qual deseja mesclar as alterações (geralmente, `main` ou `develop`).

![image](https://github.com/Henderson-da-rocha-porfirio/spring-batch-job-de-geracao-de-fatura/assets/46926951/a234e794-0a72-48d4-be2f-686e7ad382a4)


- g. Após selecionar os branches, clique em `Compare branches and continue`.

![image](https://github.com/Henderson-da-rocha-porfirio/spring-batch-job-de-geracao-de-fatura/assets/46926951/38fbdd2c-5ae1-4ff0-b289-1768855f3c09)



- h. Na próxima página, forneça um título e uma descrição para o seu `Merge Request`. Isso deve incluir detalhes sobre as alterações que você fez.

- i. Se você quiser que alguém específico revise seu Merge Request, você pode atribuí-lo a essa pessoa na seção `Assignees`.

- j. Se desejar, você também pode adicionar `rótulos` para ajudar a categorizar seu Merge Request na seção `Labels`.

- k. Quando estiver satisfeito com todas as informações fornecidas, clique em `Create merge request`.

![image](https://github.com/Henderson-da-rocha-porfirio/spring-batch-job-de-geracao-de-fatura/assets/46926951/1fbc8e5a-5e25-4c13-9ea5-c4865c458ad0)


- l. E é isso! Agora você criou um Merge Request no GitLab. Lembre-se, uma vez que o Merge Request é criado, os revisores podem solicitar alterações antes de aprová-lo para ser mesclado. Você pode fazer essas alterações diretamente no branch de origem e elas serão automaticamente refletidas no Merge Request.

- m. O `Merge Request` é uma solicitação para mesclar seu branch de trabalho (`branch de origem`) com outro branch, normalmente a branch principal ou de desenvolvimento (`branch de destino`). Enquanto o Merge Request estiver aberto, qualquer commit adicional que você fizer no branch de origem será automaticamente refletido nesse Merge Request. Isso significa que, se os revisores pedirem alterações durante o processo de revisão do código, você pode fazer essas alterações diretamente no seu branch de origem. Após `commitar` e fazer `push` dessas alterações para o seu branch de origem, elas serão automaticamente atualizadas no Merge Request existente. Esses commits adicionais não serão refletidos no branch de destino (como `develop`) até que o Merge Request seja aprovado e o merge seja realizado. Em outras palavras, os commits adicionais não afetam o branch de destino até que o Merge Request seja concluído.

- `WIP`: Work in Progress (WIP) em um Merge Request no GitLab para prevenir que o Merge Request seja mesclado antes de estar pronto. Isso é especialmente útil quando você ainda está trabalhando em uma `feature` ou quando espera feedback de outros.
- 1. Na página do Merge Request, clique no botão "Edit" próximo ao título do Merge Request.
- 2. Adicione `"WIP`:" ou `"Draft: "` ao início do título do Merge Request e depois clique em `"Save changes"`.
- 3. Por exemplo, se o título do seu Merge Request era `"Adicionar nova funcionalidade"`, você mudaria para:
  ````
   "WIP: Adicionar nova funcionalidade" ou "Draft: Adicionar nova funcionalidade".
  ````
- 4. Após fazer isso, o botão "Merge" ficará desabilitado no Merge Request e ninguém poderá mesclar as alterações até que você remova o "WIP: " ou "Draft: " do título.
- 5. Para remover o status de `Work in Progress` e permitir a mesclagem:

- a. Na página do `Merge Request`, clique no botão `"Edit"` próximo ao título do Merge Request.
- 
- b. Remova `"WIP: "` ou `"Draft: "` do início do título e clique em `"Save changes"`.
> 
- c. Agora o botão `"Merge"` estará habilitado novamente e outros poderão mesclar suas alterações.

Lembre-se de que, mesmo quando um Merge Request está definido como Work in Progress, quaisquer novos commits que você fizer no branch de origem ainda serão refletidos nesse Merge Request. O status de Work in Progress apenas impede que o Merge Request seja mesclado prematuramente.

- Passo 5: Resolver conflitos de Merge e Pull (vale tanto para a parte gráfica do Intellij quanto para o Visual Studio Code):

  ![image](https://github.com/Henderson-da-rocha-porfirio/spring-batch-job-de-geracao-de-fatura/assets/46926951/2f043aa2-6641-46a3-bf57-2c6e4ee8ffca)


- Exemplo de como o conflito é mostrado no código e de como resolver :

![image](https://github.com/Henderson-da-rocha-porfirio/spring-batch-job-de-geracao-de-fatura/assets/46926951/a4041dac-be4f-41b6-abd1-50f0b8b84b97)

- `HEAD`: Manter o código da sua branch: `<<<<<<< HEAD`:
  
![image](https://github.com/Henderson-da-rocha-porfirio/spring-batch-job-de-geracao-de-fatura/assets/46926951/f010db0c-59c4-4fc9-a92f-0858347d3e9f)


- `=======`: Divisão entre o seu código e o da outra branch ( ver a imagem do exemplo como fica ).

- `725ae55a005d6146aa3a28cf48b54d4bc7b5f350`: Manter o código da outra branch (cujo último commit é 725ae55a005d6146aa3a28cf48b54d4bc7b5f350): `>>>>>>> 725ae55a005d6146aa3a28cf48b54d4bc7b5f350`:
  
![image](https://github.com/Henderson-da-rocha-porfirio/spring-batch-job-de-geracao-de-fatura/assets/46926951/d8775f4e-9c91-48b1-902d-c09defdadae0)

- Manter ambas as linhas de código, ou seja:

![image](https://github.com/Henderson-da-rocha-porfirio/spring-batch-job-de-geracao-de-fatura/assets/46926951/a505b203-6480-456b-9b68-9cad8d3b570e)


- Depois de decidir qual código manter, remova as linhas de conflito `<<<<<<< HEAD`, `=======`, `>>>>>>> 725ae55a005d6146aa3a28cf48b54d4bc7b5f350` e salve o arquivo.

- INTELLIJ:
- Após salvar o arquivo, você precisa adicionar esse arquivo ao `Git` para indicar que o conflito foi resolvido ( arquivo de exemplo: `application-default.yml` ):
- a. `git add src/main/resources/application-default.yml`
- b. `git commit -m "Conflito resolvido em application-default.yml"`

- VISUAL STUDIO CODE:

-  Marque o conflito como resolvido: Após resolver o conflito e testar suas alterações, você precisará adicionar e commitar o arquivo. Isso pode ser feito através do painel de Source Control (Controlador de Fonte). Primeiro, clique no sinal de '+' ao lado do arquivo para adicioná-lo ao próximo commit. Em seguida, digite uma mensagem de commit e pressione 'Ctrl+Enter' para commitar. Repita esses passos para cada arquivo com conflito. Quando todos os conflitos tiverem sido resolvidos e commitados, você poderá concluir o merge.
  

![image](https://github.com/Henderson-da-rocha-porfirio/spring-batch-job-de-geracao-de-fatura/assets/46926951/311852cd-773c-45fa-8613-6f40fb21f374)




#### III - Merge Onto ( aceitar as alterações "da outra" branch, "no" que fiz, sobrescrevendo ou não a minha branch dependendo do conflito, se tiver, claro )

***** Interessante fazer um backup antes(tem que estar na branch a qual vai fazer o backup):`git checkout -b backup_minha_branch`

> 1 _ Dê uma olhada se precisa fazer uma merge ou apenas uma atualização seja suficiente. Verifique o tópico acima. Seguindo, a sua branch deve estar selecionada. Caso contrário, faça um checkout na sua. Ex: quer jogar da master(branch principal) para develop(sua branch). 
>
> 2 _ Menu Git -> escolhe Merge -> Em Merge into develop -> selecionar a branch que servirá de update para a sua. Ex: baseando no contexto aqui do exemplo, a master, já que hipoteticamente a sua seria a develop.

![merge onto](https://user-images.githubusercontent.com/46926951/215346418-4d310ee1-9042-451d-a0a8-45001de8efec.png)

> 3 _ Faz um Push para enviar para o github (ou gitlab)
>
> 4 _ Apenas este processo é que dá a mensagem de Merged tanto no github/gitlab ou no intellij.
>
> 5 _ Se der conflito, aceitar ou negar em sua branch.
>
> 6. _ Realizar por comando:

a. checkout:
````
git checkout example (sua branch)
````

b. merge:
````
git merge develop (a outra branch)
````


#### III - Observacoes
> Nenhuma das opções sobrescreve código. Isso serve apenas para a 'adição' e/ou 'remoção' de alterações. A única possibilidade de sobrescrever é se mudar numa classe que seja a mesma em todas as branches. Daí é preciso resolver os conflitos.


## REBASE pelo intellij
> Ele puxa as alterações na integra da branch que desejo que faça o processo 'onto' na minha.
