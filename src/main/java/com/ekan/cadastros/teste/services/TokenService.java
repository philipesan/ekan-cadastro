package com.ekan.cadastros.teste.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ekan.cadastros.teste.dto.responses.ApiResponseDTO;

@Service
public interface TokenService {

	ResponseEntity<ApiResponseDTO> generateToken(Authentication authentication);

}
