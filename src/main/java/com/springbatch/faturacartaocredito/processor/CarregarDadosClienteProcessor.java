package com.springbatch.faturacartaocredito.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.springbatch.faturacartaocredito.dominio.Cliente;
import com.springbatch.faturacartaocredito.dominio.FaturaCartaoCredito;

/* 1. Processador para carregar os dados do cliente.
*  2. Invocando um serviço: 'restTemplate'. Para isso no Spring, é preciso adicionar uma nova dependência:
* spring-web (verficar o pom)
*  3. Serviço invocado: https://my-json-server.typicode.com/henderson-da-rocha-porfirio/servico-teste-clientes-json/profile
*  4. Bind  = é feito com Cliente
*  5. ValidationException = caso nao encontre o cliente.
*  6. Dando certo, ele vai preencher os dados do cliente com o body da response
*  7. %d = buscando um cliente por vez. Ou seja, a fatura já vem com um cliente associado. E o body é completamente
* mapeado para o cliente da fatura de crédito. */

@Component
public class CarregarDadosClienteProcessor implements ItemProcessor<FaturaCartaoCredito, FaturaCartaoCredito> {
	private RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public FaturaCartaoCredito process(FaturaCartaoCredito faturaCartaoCredito) throws Exception {
		String uri = String.format("https://my-json-server.typicode.com/henderson-da-rocha-porfirio/servico-teste-clientes-json/profile/%d", faturaCartaoCredito.getCliente().getId());
		ResponseEntity<Cliente> response = restTemplate.getForEntity(uri, Cliente.class);
		
		if (response.getStatusCode() != HttpStatus.OK)
			throw new ValidationException("Cliente não encontrado!");
		
		faturaCartaoCredito.setCliente(response.getBody());
		return faturaCartaoCredito;
	}

}
