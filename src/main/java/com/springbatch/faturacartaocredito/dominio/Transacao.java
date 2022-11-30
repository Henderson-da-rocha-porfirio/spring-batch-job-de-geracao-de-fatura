package com.springbatch.faturacartaocredito.dominio;

import lombok.Data;

import java.util.Date;

@Data
public class Transacao {

    private int id;
    private CartaoCredito cartaoCredito;
    private String descricao;
    private Double valor;
    private Date data;

}
