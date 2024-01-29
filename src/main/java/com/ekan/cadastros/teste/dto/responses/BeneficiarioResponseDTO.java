package com.ekan.cadastros.teste.dto.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
public class BeneficiarioResponseDTO {
	private Long id;
	private String nome;
	private String telefone;
	private LocalDate dtNascimento;
	private LocalDateTime dtInclusao;
	private LocalDateTime dtAtualizacao;
	private List<DocumentoResponseDTO> documentos;
	private Integer status;
}
