package com.ekan.cadastros.teste.dto.responses;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentoResponseDTO {
	private Long id;
	private String descricao;
	private String tipoDocumento;
	private LocalDateTime dtInclusao;
	private LocalDateTime dtAtualizacao;
	private Integer status;
}
