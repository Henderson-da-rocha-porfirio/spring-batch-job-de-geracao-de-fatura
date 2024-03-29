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
