package com.fatec.scel;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.scel.model.Livro;
import com.fatec.scel.model.LivroRepository;

@SpringBootTest
class REQ03ExcluirLivroTests {
	@Autowired
	LivroRepository repository;

	@Test
	void quando_exclui_livro_pelo_id_consultar_por_isbn_retorna_null() {
		// Dado que o livro esta cadastrado
		repository.deleteAll();
		Livro livro = new Livro("3333", "Teste de Software", "Delemaro");
		repository.save(livro);
		// Quando o usuario solicita exclusao
		Livro ro = repository.findByIsbn("3333");
		repository.deleteById(ro.getId());
		// Entao o resultado obtido da consulta deve ser null
		assertThat(repository.findByIsbn("3333")).isEqualTo(null);
	}

}
