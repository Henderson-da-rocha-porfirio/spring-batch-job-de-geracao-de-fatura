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
> 
## MERGE pelo intellij

#### I - Merge Into ( enviar as alterações que eu fiz, "da minha" branch, "na" outra branch)

> 1 _ faz checkout na que deseja enviar as mudanças
>
> 2 _ menu Git -> escolhe Merge -> Em Merge into develop -> selecionar a branch que deseja jogar as
as minhas alterações nela.
>
> 3 _ Por último, faz um Push para enviar para o github (ou gitlab)



#### II - Merge Onto ( aceitar as alterações "da outra" branch, "no" que fiz, sobrescrevendo ou não a minha branch dependendo do conflito, se tiver, claro )

> 1 _ faz checkout na sua mesma.
>
> 2 _ menu Git -> escolhe Merge -> Em Merge into develop -> selecionar a branch que vai jogar as
alterações dela na sua branch.
>
> 3 _ Faz um Push para enviar para o github (ou gitlab)
>
> 4 _ Apenas este processo é que dá a mensagem de Merged tanto no github/gitlab ou no intellij.
>
> 5 _ Se der conflito, aceitar ou negar em sua branch.


#### III - Observacoes
> Nenhuma das opções sobrescreve código. Isso serve apenas para a 'adição' e/ou 'remoção' de alterações. A única possibilidade de sobrescrever é se mudar numa classe que seja a mesma em todas as branches. Daí é preciso resolver os conflitos.


## REBASE pelo intellij
> Faz processo semelhante ao MERGE, contudo, ele aplica as alterações do tipo "onto"(no) na branch que deseja sobrescrever ou alterar integralmente.
