package com.springbatch.faturacartaocredito.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* start = é a ativação de um step no job.
*  step = é o step configurado em FaturaCartaoCreditoStepConfig
*  1. @ComponentScan: scaneia todos os beans dentro dos outros pacotes.
*  2. @EnableBatchProcessing: Ative os recursos do Spring Batch e forneça
* uma configuração básica para configurar trabalhos em lote em uma classe
* @Configuration, aproximadamente equivalente ao uso do
* <batch:*>namespace XML. Geralmente utilizado na classe de configuração do Job.
* Mas neste caso aqui, foi usado no main mostrando outra possibilidade de uso.
*/

@EnableBatchProcessing
@Configuration
public class FaturaCartaoCreditoJobConfig {
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Bean
	public Job faturaCartaoCreditoJob(Step faturaCartaoCreditoStep) {
		return jobBuilderFactory
				.get("faturaCartaoCreditoStep")
				.start(faturaCartaoCreditoStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
