package com.ekan.cadastros.teste.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class Beneficiario {

	@Id @GeneratedValue
	private Long id;
	private String nome;
	private String telefone;
	private LocalDate dtNascimento;
	private LocalDateTime dtInclusao;
	private LocalDateTime dtAtualizacao;
}
