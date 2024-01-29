package com.ekan.cadastros.teste.dto.requests;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class BeneficiarioRequestDTO {
	
	@NotEmpty(message = "Campo 'nome' é obrigatório")
	@NotBlank(message = "Campo 'nome' é obrigatório")
	private String nome;
	@NotEmpty(message = "Campo 'telefone' é obrigatório")
	@NotBlank(message = "Campo 'telefone' é obrigatório")
	private String telefone;
	@NotNull(message = "Campo 'dtNascimento' é obrigatório")
	private LocalDate dtNascimento;
	@NotEmpty(message = "Campo 'documentos' é obrigatório")
	@NotNull(message = "Campo 'documentos' é obrigatório")
	private List<DocumentoRequestDTO> documentos;
	private Integer status;
}
