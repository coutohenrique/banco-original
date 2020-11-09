package br.com.henriquecouto.api.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {

	@ApiModelProperty(value = "Id do Produto", example = "1")
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dataCadastro;
	
	@ApiModelProperty(value ="Descrição do Produto", example = "Celular")
	private String descricao;
}
