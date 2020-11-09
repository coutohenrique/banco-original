package br.com.henriquecouto.domain.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.CollectionUtils;

import br.com.henriquecouto.domain.model.Produto;
import br.com.henriquecouto.domain.repository.ProdutoRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class ProdutoServiceTest {

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired ProdutoRepository produtoRepository;
	
	private Produto produtoLivro;
	

	@Test
	public void salvarProduto() {

		Produto p = new Produto();
		p.setDescricao("Teste Cadastro Produto");

		assertTrue(produtoService.salvar(p) != null);
	}
	
	@Test
	public void buscarProdutoPorId() {
		prepararDados();
		
		Optional<Produto> p = produtoService.buscarPorId(produtoLivro.getId());
		assertEquals(produtoLivro.getId(), p.get().getId());
		assertEquals(produtoLivro.getDescricao(), p.get().getDescricao());
	}
	
	@Test
	public void buscarTodosProdutos() {
		prepararDados();
		
		List<Produto> p = produtoService.listarTodos();
		
		assertFalse(CollectionUtils.isEmpty(p));
	}
	
	@Test
	public void atualizarProdutoPorId() {
		
		prepararDados();
		String descricao = "Produto Atualizado";
	
		produtoLivro.setDescricao(descricao);
		Produto p = produtoService.atualizar(produtoLivro);

		assertEquals(p.getId(), produtoLivro.getId());
		assertEquals(p.getDescricao(), descricao);
	}
	
	@Test
	public void deletarProdutoPorId() {
		prepararDados();
		
		produtoService.excluir(produtoLivro.getId());
		
		Optional<Produto> p = produtoRepository.findById(produtoLivro.getId());
		
		assertFalse(p.isPresent());
	}
	
	private void prepararDados() {
		Produto p = new Produto();
		 p = new Produto();
		 p.setDescricao("João e Maria");

		produtoLivro = produtoRepository.save( p);
	}
	
}
