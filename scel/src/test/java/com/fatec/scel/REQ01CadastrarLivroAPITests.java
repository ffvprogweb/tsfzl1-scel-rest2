package com.fatec.scel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class REQ01CadastrarLivroAPITests {

	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Autowired
	LivroRepository repository;

	@Test
	void ct01_quando_seleciona_cadastrar_livro_retorna_htttp_200() {
		// Dado - que o servico esta disponivel
		// Quando - o usuario faz uma requisicao POST para cadastrar Livro
		Livro livro = new Livro("3333", "User Stories", "Cohn");
		HttpEntity<Livro> httpEntity = new HttpEntity<>(livro);
		ResponseEntity<String> resposta = testRestTemplate.exchange("/api/v1/livro", HttpMethod.POST, httpEntity,
				String.class);
		// Entao - retorna HTTP 201
		assertEquals("201 CREATED", resposta.getStatusCode().toString());
	}

	@Test
	void ct02_quando_metodo_http_nao_disponivel_retorna_405() {
		// Dado - que o servico esta disponivel
		// Quando - o usuario faz uma requisicao POST para cadastrar Livro
		Livro livro = new Livro("3333", "User Stories", "Cohn");
		HttpEntity<Livro> httpEntity = new HttpEntity<>(livro);
		ResponseEntity<String> resposta = testRestTemplate.exchange("/api/v1/livro", HttpMethod.PUT, httpEntity,
				String.class);
		// Entao - retorna HTTP 201
		assertEquals("405 METHOD_NOT_ALLOWED", resposta.getStatusCode().toString());
	}

	@Test
	void ct03_quando_requisicao_invalida_retorna_400() {
		// Dado - que o servico esta disponivel
		// Quando - o usuario faz uma requisicao POST para cadastrar Livro
		Livro livro = new Livro("3333", "", "Cohn");
		HttpEntity<Livro> httpEntity = new HttpEntity<>(livro);
		ResponseEntity<String> resposta = testRestTemplate.exchange("/api/v1/livro", HttpMethod.POST, httpEntity,
				String.class);
		// Entao - retorna HTTP 400
		assertEquals("400 BAD_REQUEST", resposta.getStatusCode().toString());
	}
	@Test
	void ct04_quando_requisicao_invalida_retorna_400() {
		//Dado que o livro esta cadastrado
		repository.deleteAll();
		Livro livro = new Livro("5555", "Teste de Desempenho", "Bayo");
		repository.save(livro);
		//Quando o usuario faz uma requisicao POST invalida
		livro = new Livro("5555", "Teste de Desempenho", "Bayo");
		HttpEntity<Livro> httpEntity = new HttpEntity<>(livro);
		ResponseEntity<String> resposta = testRestTemplate.exchange("/api/v1/livro", HttpMethod.POST, httpEntity,
				String.class);
		//Entao retorna 400;
		assertEquals("400 BAD_REQUEST", resposta.getStatusCode().toString());
	}
}
