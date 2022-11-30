package com.springbatch.faturacartaocredito.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import com.springbatch.faturacartaocredito.dominio.FaturaCartaoCredito;
import com.springbatch.faturacartaocredito.dominio.Transacao;

/* 1. delegate = padrão sendo utilizado
*  2. O jdbc cursor ItemStream é um ItemStreamReader = leitor que abre e fecha recursos
*  3. open, update e close = fazem parte do ciclo de vida da criaçao desse leitor
*  4. peek = ideia do método é espiar. Ou seja, 'espia' a próxima transação. E se ela não for nula e também
* se for do mesmo cartão de crédito da fatura atual, adicione esta transação na fatura atual. Assim é possível
* adicionar todas as transações a uma única fatura.
*  5. Necessário passar este leitor no step: FaturaCartaoCreditoStepConfig */

public class FaturaCartaoCreditoReader implements ItemStreamReader<FaturaCartaoCredito> {
	private ItemStreamReader<Transacao> delegate;
	private Transacao transacaoAtual;

	@Override
	public FaturaCartaoCredito read() throws Exception {
		if (transacaoAtual == null)
			transacaoAtual = delegate.read();
		
		FaturaCartaoCredito faturaCartaoCredito = null;
		Transacao transacao = transacaoAtual;
		transacaoAtual = null;
		
		if (transacao != null) {
			faturaCartaoCredito = new FaturaCartaoCredito();
			faturaCartaoCredito.setCartaoCredito(transacao.getCartaoCredito());
			faturaCartaoCredito.setCliente(transacao.getCartaoCredito().getCliente());
			faturaCartaoCredito.getTransacoes().add(transacao);
			
			while (isTransacaoRelacionada(transacao))
				faturaCartaoCredito.getTransacoes().add(transacaoAtual);
		}
		return faturaCartaoCredito;
	}

	private boolean isTransacaoRelacionada(Transacao transacao) throws Exception {
		return peek() != null && transacao.getCartaoCredito().getNumeroCartaoCredito() == transacaoAtual.getCartaoCredito().getNumeroCartaoCredito();
	}

	private Transacao peek() throws Exception {
		transacaoAtual = delegate.read();
		return transacaoAtual;
	}

	public FaturaCartaoCreditoReader(ItemStreamReader<Transacao> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		delegate.open(executionContext);
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		delegate.update(executionContext);
	}

	@Override
	public void close() throws ItemStreamException {
		delegate.close();
	}

}
