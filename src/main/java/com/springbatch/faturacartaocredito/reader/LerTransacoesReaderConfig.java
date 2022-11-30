package com.springbatch.faturacartaocredito.reader;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import com.springbatch.faturacartaocredito.dominio.CartaoCredito;
import com.springbatch.faturacartaocredito.dominio.Cliente;
import com.springbatch.faturacartaocredito.dominio.Transacao;

/* 1 - ".rowMapper(rowMapperTransacao()" = necessidade de mapear o resultado
*  porque a transacao possui tipos compostos e não apenas tipos primitivos.
*  2 - order by numero_cartao_credito = foi ordenado por numero_cartao_credito.
*  3. Padrão delegate (primeiro método abaixo): delega a leitura para o leitor de banco de dados, e a classe que está
* delegando a leitura, ela pega estes dados e já vai agrupando. Nesse sentido, quando for ler o item, a fatura cartao
* de credito toda preenchida. Cada item terá tudo que ele precisa para ser escrito.
*  4. É preciso criar uma classe para que seja lida de fato a faturacartaocredito = FaturaCartaoCreditoReader */

@Configuration
public class LerTransacoesReaderConfig {
	@Bean
	public JdbcCursorItemReader<Transacao> lerTransacoesReader(
			@Qualifier("appDataSource") DataSource dataSource) {
		return new JdbcCursorItemReaderBuilder<Transacao>()
				.name("lerTransacoesReader")
				.dataSource(dataSource)
				.sql("select * from transacao join cartao_credito using (numero_cartao_credito) order by numero_cartao_credito")
				.rowMapper(rowMapperTransacao())
				.build();
	}

	private RowMapper<Transacao> rowMapperTransacao() {
		return new RowMapper<Transacao>() {

			@Override
			public Transacao mapRow(ResultSet rs, int rowNum) throws SQLException {
				CartaoCredito cartaoCredito = new CartaoCredito();
				cartaoCredito.setNumeroCartaoCredito(rs.getInt("numero_cartao_credito"));
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("cliente"));
				cartaoCredito.setCliente(cliente);
				
				Transacao transacao = new Transacao();
				transacao.setId(rs.getInt("id"));
				transacao.setCartaoCredito(cartaoCredito);
				transacao.setData(rs.getDate("data"));
				transacao.setValor(rs.getDouble("valor"));
				transacao.setDescricao(rs.getString("descricao"));
				
				return transacao;
			}
			
		};
	}
}
