package br.com.henriquecouto.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.henriquecouto.domain.model.Produto;
import br.com.henriquecouto.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	private ProdutoRepository produtoRepository;
	
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	@Transactional
	public Produto salvar(Produto produto) {
		produto.setDataCadastro(new Date());
		
		return produtoRepository.save(produto);
	}
	
	@Transactional
	public Produto atualizar(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	@Transactional
	public void excluir(Long produtoId) {
		produtoRepository.deleteById(produtoId);
	}

	public Optional<Produto> buscarPorId(Long produtoId) {
		return produtoRepository.findById(produtoId);
	}
	
	public List<Produto> listarTodos() {
		return produtoRepository.findAll();
	}
	
}
