package com.springbatch.faturacartaocredito.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.List;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.file.FlatFileFooterCallback;

import com.springbatch.faturacartaocredito.dominio.FaturaCartaoCredito;

/* 1. O footer só escreve 'total'
*  2. @BeforeWrite = é um evento que acontece antes da escrita para que o listener fique 'ouvindo', para que
* antes de consolidar a escrita, ele some todas as transações daquelas faturas de cartões que estão sendo escritas.
*  3. @AfterChunk = este evento vem logo após ele terminar a soma, ele irá zerar o total para que a próxima fatura
* não acumule este total, e comece do zero novamente.  */

public class TotalTransacoesFooterCallback implements FlatFileFooterCallback {
	private Double total = 0.0;
	
	@Override
	public void writeFooter(Writer writer) throws IOException {
		writer.write(String.format("\n%121s", "Total: " + NumberFormat.getCurrencyInstance().format(total)));
	}

	@BeforeWrite
	public void beforeWrite(List<FaturaCartaoCredito> faturas) {
		for (FaturaCartaoCredito faturaCartaoCredito : faturas)
			total += faturaCartaoCredito.getTotal();
	}
	
	@AfterChunk
	public void afterChunk(ChunkContext chunkContext) {
		total = 0.0;
	}
}
