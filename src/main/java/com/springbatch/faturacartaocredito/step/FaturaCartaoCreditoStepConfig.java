package com.springbatch.faturacartaocredito.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.faturacartaocredito.dominio.FaturaCartaoCredito;
import com.springbatch.faturacartaocredito.dominio.Transacao;
import com.springbatch.faturacartaocredito.reader.FaturaCartaoCreditoReader;
import com.springbatch.faturacartaocredito.writer.TotalTransacoesFooterCallback;

/* - Passos responsáveis pelas transiçoes
*  chunk(1) = liberacao de 1 arquivo por cliente. Ou seja, cada fatura terá apenas um cliente.
*  JDBC Item Reader = componente para ler dados do banco de dados. Possuem dois tipos:
*  1. tipo cursor (usado aqui devido a simplicidade)
*  2. tipo leitura paginada
*  3. Boa prática: ler o dado e já passá-lo com o máximo de informação possível para não termos que ler estes dados
* novamente, já que é uma operação demorada. E com os dados bem formatados, o escritor só faz a parte dele
* sem precisar mudar mais nada.
*  4. Leitor Delegate = .reader(new FaturaCartaoCreditoReader(lerTransacoesReader))
*  5. <Transacao> = é o que será lido.
*  6. Processo de leitura e delegate = o leitor criado 'FaturaCartaoCreditoReader' delega a leitura para o
* leitor de banco de dados 'lerTransacoesReader', ou seja, ele anota as transações numa única fatura de cartão
* de crédito.
*  7. .processor = responsável em buscar os dados do cliente. Neste cenário de negócio, a base de dados possui
* apenas um identificador do cliente, os seus dados pessoais estão em outro sistema.
*  8. Necessário criar um processador, por ex: CarregarDadosClienteProcessor
*  9. TotalTransacoesFooterCallback e .listener = diz respeito a construcao do cabeçalho.
*  10. <FaturaCartaoCredito, FaturaCartaoCredito> =  Ele ler o FaturaCartaoCredito e retornar
* para a escrita a FaturaCartaoCredito. */

@Configuration
public class FaturaCartaoCreditoStepConfig {
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step faturaCartaoCreditoStep(
			ItemStreamReader<Transacao> lerTransacoesReader,
			ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> carregarDadosClienteProcessor,
			ItemWriter<FaturaCartaoCredito> escreverFaturaCartaoCredito,
			TotalTransacoesFooterCallback listener) {
		return stepBuilderFactory
				.get("faturaCartaoCreditoStep")
				.<FaturaCartaoCredito, FaturaCartaoCredito>chunk(1)
				.reader(new FaturaCartaoCreditoReader(lerTransacoesReader))
				.processor(carregarDadosClienteProcessor)
				.writer(escreverFaturaCartaoCredito)
				.listener(listener)
				.build();
	}
}
