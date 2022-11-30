package com.springbatch.faturacartaocredito.dominio;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/* 1. Esta classe possui os dados do cliente e todas as transações daquela fatura
*  2. Padrão Delegate = se encontra em LerTransacoesReaderConfig
*  3. Método getTotal = diz respeito ao cabeçalho*/

@Data
public class FaturaCartaoCredito {

    private Cliente cliente;
    private CartaoCredito cartaoCredito;
    private List<Transacao> transacoes = new ArrayList<>();

    public Double getTotal() {
        return transacoes
                .stream()
                .mapToDouble(Transacao::getValor)
                .reduce(0.0, Double::sum);
    }
}
