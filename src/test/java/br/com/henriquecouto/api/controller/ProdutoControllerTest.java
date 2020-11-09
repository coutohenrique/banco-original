package br.com.henriquecouto.api.controller;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.henriquecouto.domain.model.Produto;
import br.com.henriquecouto.domain.repository.ProdutoRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProdutoControllerTest {
	
	private static final int PRODUTO_ID_INEXISTENTE = 100;
	
	
	@LocalServerPort
	private int port;
	
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	private Produto produtoLivro;
	private Produto produtoCelular;
	private Produto produtoTelevisao;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/produtos";

		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarProdutos() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarProduto() {
		given()
			.body(produtoTelevisao)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarProdutoExistente() {
		given()
			.pathParam("produtoId", produtoLivro.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{produtoId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("descricao", equalTo(produtoLivro.getDescricao()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarProdutoInexistente() {
		given()
			.pathParam("produtoId", PRODUTO_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{produtoId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	
	@Test 
	public void deveRetornarStatusCorreto_QuandoAtualizarProduto() {
		given()
			.pathParam("produtoId", produtoLivro.getId())
			.body(atualizarProduto(produtoLivro))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/{produtoId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("descricao", equalTo(produtoLivro.getDescricao()));
	}
	
	@Test 
	public void deveRetornarStatus404_QuandoAtualizarProdutoInexistente() {
		given()
			.pathParam("produtoId", PRODUTO_ID_INEXISTENTE)
			.body(atualizarProduto(produtoLivro))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/{produtoId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarStatusCorreto_QuandoDeletarProduto() {
		given()
			.pathParam("produtoId", produtoCelular.getId())
			.accept(ContentType.JSON)
		.when()
			.delete("/{produtoId}")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoDeletarProdutoInexistente() {
		given()
			.pathParam("produtoId", PRODUTO_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.delete("/{produtoId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	
	private void prepararDados() {
		
		produtoLivro = new Produto();
		produtoLivro.setDescricao("João e Maria");

		produtoRepository.save(produtoLivro);
		
		produtoCelular = new Produto();
		produtoCelular.setDescricao("Iphone 11");
		
		produtoRepository.save(produtoCelular);
		
		produtoTelevisao = new Produto();
		
		produtoTelevisao.setDescricao("TV 4k");
		
	}

	private Produto atualizarProduto(Produto p) {
		
		String descricaoAtualizada = p.getDescricao() + " Atualizada";
		
		Produto produto = p;
		produto.setDescricao(descricaoAtualizada);
		
		return produto;
	}
	
}
