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
> MERGE

I - Merge Into (envio de alterações que fiz para outra branch principal)

1 _ faz checkout na que deseja enviar as mudanças

2 _ menu Git -> escolhe Merge -> Em Merge into develop -> selecionar a branch que deseja jogar as
as suas alterações nela.

3 _ Faz um Push para enviar para o github (ou gitlab)



II - Merge Onto (envio de alterações que fiz para outra branch)

1 _ 1 _ faz checkout na sua mesma.

2 _ menu Git -> escolhe Merge -> Em Merge into develop -> selecionar a branch que vai jogar as
alterações dela na sua branch.

3 _ Faz um Push para enviar para o github (ou gitlab)

4 _ Apenas este processo é que dá a mensagem de Merged tanto no github/gitlab ou no intellij.