package com.fatec.scel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;
@SpringBootTest
class REQ01CadastrarLivroTests {
	@Autowired
	LivroRepository repository;
	Validator validator;
	ValidatorFactory validatorFactory;

	@Test
	void ct01_quando_dados_do_livro_sao_validos_cadastra_com_sucesso() {
		//Dado-que o atendente tem um livro nao cadastrado
		repository.deleteAll();
		Livro livro = new Livro("3333","Teste Software", "Delamaro");
		//Quando - o atendente confirma o cadastro do livro
		repository.save(livro);
		//Entao - o sistema valida os dados E confirma a operação
		assertEquals(1, repository.count());
	}
	@Test
	void ct02_quando_titulo_invalido_msg_titulo_invalido() {
		//Dado que o atendente tem um livro nao cadastro
		Livro livro = new Livro("3333","", "Delamaro");
		//Quando o atende confirma a operacao de cadastro
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		//Entao o sistema verifica os dados e retorna titulo invalido
		assertFalse(violations.isEmpty());
		assertEquals(1, violations.size());
		assertEquals("Titulo deve ter entre 1 e 50 caracteres", violations.iterator().next().getMessage());
	}
	@Test
	void ct03_quando_isbn_ja_cadastrado_retorna_violacao_integridade() {
		//Dado que o ISBN do livro ja esta cadastrado
		repository.deleteAll();
		Livro livro = new Livro("4444","Teste de Software", "Delamaro");
		repository.save(livro);
		//Quando o usuario registra as informacoes do livro
		livro = new Livro("4444","Teste de Software", "Delamaro");
		//Entao o sistema valida as informacoes E retorna violacao de integridade
		try {
			repository.save(livro);
		}catch(DataIntegrityViolationException e) {
			assertEquals(1, repository.count());
		}
	}

}
