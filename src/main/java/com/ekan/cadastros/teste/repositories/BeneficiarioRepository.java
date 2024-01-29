package com.ekan.cadastros.teste.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ekan.cadastros.teste.entities.Beneficiario;
import com.ekan.cadastros.teste.entities.Documento;



@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {
	Optional<Beneficiario> findById(Long id);
	Page<Beneficiario> findAllByStatus(Pageable pageable, Integer status);
	Page<Beneficiario> findAll(Pageable pageable);
	List<Documento> findAllDocumentosById(Long beneficiarioId);
}
