package com.ekan.cadastros.teste.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentoRequestDTO {
	
	private Long id;
	private String descricao;
	private String tipoDocumento;
	private Integer status;
}
