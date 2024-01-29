package com.ekan.cadastros.teste.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ekan.cadastros.teste.dto.requests.BeneficiarioRequestDTO;
import com.ekan.cadastros.teste.dto.responses.ApiResponseDTO;
import com.ekan.cadastros.teste.services.BeneficiarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="api/v1/beneficiario")
public class BeneficiarioController {
	
	@Autowired BeneficiarioService beneficiarioService;
	
	@GetMapping(path=("/busca/{idBeneficiario}"))
	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public ResponseEntity<ApiResponseDTO> buscaBeneficiario(@PathVariable Long idBeneficiario) {
		return beneficiarioService.buscaBeneficiario(idBeneficiario);
	}
	
	@GetMapping(path=("/documentos/{idBeneficiario}"))
	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public ResponseEntity<ApiResponseDTO> listaDocumentos(@PathVariable Long idBeneficiario) {
		return beneficiarioService.listaDocumentos(idBeneficiario);
	}
	
	@GetMapping(path=("/lista/"))
	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public ResponseEntity<ApiResponseDTO> listaBeneficiario(
			@RequestParam Integer page, 
    		@RequestParam Integer items,
    		@RequestParam(defaultValue = "id") String sort,
    		@RequestParam(defaultValue = "0") String direction,
    		@RequestParam(defaultValue = "2") Integer status) {
		return beneficiarioService.listaBeneficiarios(page, items, sort, direction, status);
	}
		
	@PostMapping(path=("/novo"))
	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public ResponseEntity<ApiResponseDTO> cadastraBeneficiario(@Valid @RequestBody BeneficiarioRequestDTO beneficiario) {
		return beneficiarioService.cadastraBeneficiario(beneficiario);
	}
	
	@PatchMapping(path=("/atualiza/{idBeneficiario}"))
	@PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
	public ResponseEntity<ApiResponseDTO> atualizaBeneficiario(@PathVariable Long idBeneficiario, @Valid @RequestBody BeneficiarioRequestDTO beneficiario) {
		return beneficiarioService.atualizaBeneficiario(beneficiario, idBeneficiario);
	}
	
	@DeleteMapping(path=("/inativa/{idBeneficiario}"))
	@PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
	public ResponseEntity<ApiResponseDTO> inativaBeneficiario(@PathVariable Long idBeneficiario) {
		return beneficiarioService.inativaBeneficiario(idBeneficiario);
	}
}
