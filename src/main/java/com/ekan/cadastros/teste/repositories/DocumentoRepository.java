package com.ekan.cadastros.teste.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ekan.cadastros.teste.entities.Beneficiario;
import com.ekan.cadastros.teste.entities.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {
	List<Documento> findByBeneficiario(Beneficiario beneficiario);
}
