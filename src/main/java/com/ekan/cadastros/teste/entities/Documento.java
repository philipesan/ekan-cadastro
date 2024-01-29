package com.ekan.cadastros.teste.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Documento {
	
	@Id @GeneratedValue
	private Long id;
	private String descricao;
	private String tipoDocumento;
	private LocalDateTime dtInclusao;
	private LocalDateTime dtAtualizacao;
	private Integer status;
	
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(columnDefinition="integer", name = "fk_beneficiario")
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Beneficiario beneficiario;
}
