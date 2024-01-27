package com.ekan.cadastros.teste.sevice.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import com.ekan.cadastros.teste.dto.responses.ApiResponseDTO;
import com.ekan.cadastros.teste.services.TokenService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenServiceImpl implements TokenService {
	
	private final JwtEncoder jwtEncoder;
	
	public TokenServiceImpl(JwtEncoder jwtEncoder) {
		this.jwtEncoder = jwtEncoder;
	}
	
	@Override
	public ResponseEntity<ApiResponseDTO> generateToken(Authentication authentication) {
		ApiResponseDTO response = new ApiResponseDTO();
				
		Instant now = Instant.now();
		String scope = authentication.getAuthorities().stream()
									 .map(GrantedAuthority::getAuthority)
									 .collect(Collectors.joining(" "));
		
		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuedAt(now)
				.issuer("self")
				.expiresAt(now.plus(1, ChronoUnit.HOURS))
				.subject(authentication.getName())
				.claim("scope", scope)
				.build();
		
		response.setMessage("Autenticação bem sucedida");
		response.setContent(this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue());
		return ResponseEntity.status(200).body(response);
									 
	}
	 
}
