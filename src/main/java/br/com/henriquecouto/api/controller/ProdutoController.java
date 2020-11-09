package br.com.henriquecouto.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.henriquecouto.api.converter.ProdutoConverter;
import br.com.henriquecouto.api.model.ProdutoDTO;
import br.com.henriquecouto.domain.model.Produto;
import br.com.henriquecouto.domain.service.ProdutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Produtos")
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

	private ProdutoService produtoService;
	private ProdutoConverter produtoConverter;
	
	

	public ProdutoController(ProdutoService produtoService, ProdutoConverter produtoConverter) {
		this.produtoService = produtoService;
		this.produtoConverter = produtoConverter;
	}

	@ApiOperation("Cadastra um produto")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO salvar(
			@RequestBody ProdutoDTO produtoDTO) {
			
			Produto produto = produtoService.salvar(produtoConverter.toDomainObject(produtoDTO));
			
			return produtoConverter.toDTO(produto);
	}

	@ApiOperation("Lista um produto por id")
	@GetMapping("/{produtoId}")
	public ResponseEntity<ProdutoDTO> buscarPorId(
			@PathVariable Long produtoId) {

		return produtoService.buscarPorId(produtoId)
				.map(p -> ResponseEntity.ok().body(produtoConverter.toDTO(p)))
				.orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation("Lista todos os produtos")
	@GetMapping
	public List<ProdutoDTO> listar() {
		List<Produto> produtos = produtoService.listarTodos();

		return produtoConverter.toCollectionDTO(produtos);
	}

	@ApiOperation("Atualiza um produto por id")
	@PutMapping(value = "/{produtoId}")
	public ResponseEntity<ProdutoDTO> atualizar(@PathVariable("produtoId") Long produtoId,
			@RequestBody @Valid ProdutoDTO produtoDTO) {
		
		return produtoService.buscarPorId(produtoId)
				.map(p -> {
					produtoConverter.copyToDomainObject(produtoDTO, p);
					Produto produto = produtoService.salvar(p);
				
					return ResponseEntity.ok().body(produtoConverter.toDTO(produto));
				
				}).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation("Exclui um produto por id")
	@DeleteMapping("/{produtoId}")
	public ResponseEntity <?> delete(@PathVariable Long produtoId) {
		return produtoService.buscarPorId(produtoId)
				.map(p -> {
					produtoService.excluir(produtoId);
				
					return ResponseEntity.ok().build();
				
				}).orElse(ResponseEntity.notFound().build());
	}
}
