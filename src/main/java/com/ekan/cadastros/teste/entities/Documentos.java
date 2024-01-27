package com.ekan.cadastros.teste.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

@Entity
public class Documentos {
	
	@Id @GeneratedValue
	private Long id;
	private String descricao;
	private String tipoDocumento;
	private LocalDateTime dtInclusao;
	private LocalDateTime dtAtualizacao;
	
    @ManyToOne
    @JoinColumn(columnDefinition="integer", name = "fk_beneficiario")
	private Beneficiario beneficiario;
}
