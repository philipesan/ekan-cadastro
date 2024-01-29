package com.ekan.cadastros.teste.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

@Entity
public class Beneficiario {

	@Id @GeneratedValue
	private Long id;
	private String nome;
	private String telefone;
	private LocalDate dtNascimento;
	private LocalDateTime dtInclusao;
	private LocalDateTime dtAtualizacao;
	private Integer status;
	
	public static String formataTelefone(String telefone) {
		return telefone.replaceAll("[^0-9]", "");

	}
}
