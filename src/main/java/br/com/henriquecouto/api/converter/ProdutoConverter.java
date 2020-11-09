package br.com.henriquecouto.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.henriquecouto.api.model.ProdutoDTO;
import br.com.henriquecouto.domain.model.Produto;

@Component
public class ProdutoConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public ProdutoDTO toDTO(Produto produto) {
		return modelMapper.map(produto, ProdutoDTO.class);
	}
	
	
	public Produto toDomainObject(ProdutoDTO produtoDTO) {
		return modelMapper.map(produtoDTO, Produto.class);
	}
	
	public void copyToDomainObject(ProdutoDTO produtoDTO, Produto produto) {
		// Para evitar org.hibernate.HibernateException: identifier of an instance of 
		// br.com.henriquecouto.domain.model.Produto was altered from 1 to null
		
		produto.setDescricao(produtoDTO.getDescricao());
		produto.setDataCadastro(produtoDTO.getDataCadastro());
	}
	
	public List<ProdutoDTO> toCollectionDTO(List<Produto> funcionarios){
		return funcionarios.stream()
				.map(funcionario -> toDTO(funcionario))
				.collect(Collectors.toList());
	}
	
	public List<Produto> toCollectionDomainObject(List<ProdutoDTO> funcionariosDTO){
		return funcionariosDTO.stream()
				.map(funcionarioDTO -> toDomainObject(funcionarioDTO))
				.collect(Collectors.toList());
	}
}
