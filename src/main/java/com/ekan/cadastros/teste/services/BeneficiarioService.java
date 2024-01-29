package com.ekan.cadastros.teste.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ekan.cadastros.teste.dto.requests.BeneficiarioRequestDTO;
import com.ekan.cadastros.teste.dto.responses.ApiResponseDTO;

@Service
public interface BeneficiarioService {
	ResponseEntity<ApiResponseDTO> listaBeneficiarios(Integer page, Integer items, String sort, String direction,
			Integer status);
	
	ResponseEntity<ApiResponseDTO> cadastraBeneficiario(BeneficiarioRequestDTO beneficiario);
	ResponseEntity<ApiResponseDTO> buscaBeneficiario(Long beneficiarioId);
	ResponseEntity<ApiResponseDTO> atualizaBeneficiario(BeneficiarioRequestDTO beneficiario, Long beneficiarioId);
	ResponseEntity<ApiResponseDTO> inativaBeneficiario(Long beneficiarioId);
	ResponseEntity<ApiResponseDTO> listaDocumentos(Long beneficiarioId);

	
}
