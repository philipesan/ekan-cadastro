package com.ekan.cadastros.teste.sevice.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ekan.cadastros.teste.dto.requests.BeneficiarioRequestDTO;
import com.ekan.cadastros.teste.dto.responses.ApiResponseDTO;
import com.ekan.cadastros.teste.dto.responses.BeneficiarioResponseDTO;
import com.ekan.cadastros.teste.dto.responses.DocumentoResponseDTO;
import com.ekan.cadastros.teste.entities.Beneficiario;
import com.ekan.cadastros.teste.entities.Documento;
import com.ekan.cadastros.teste.mapstructs.BeneficiarioMapper;
import com.ekan.cadastros.teste.mapstructs.DocumentoMapper;
import com.ekan.cadastros.teste.repositories.BeneficiarioRepository;
import com.ekan.cadastros.teste.repositories.DocumentoRepository;
import com.ekan.cadastros.teste.services.BeneficiarioService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BeneficiarioServiceImpl implements BeneficiarioService{

	@Autowired BeneficiarioRepository beneficiarioRepository;
	@Autowired DocumentoRepository documentoRepository;
	@Autowired BeneficiarioMapper beneficiarioMapper;
	@Autowired DocumentoMapper documentoMapper;
	
	@Override
	public ResponseEntity<ApiResponseDTO> listaBeneficiarios(Integer page, Integer items, String sort, String direction,
			Integer status) {

		
		Pageable pageable;
		if (direction.equals("0")) {
			pageable = PageRequest.of(page, items, Sort.by(sort).ascending());
		} else {
			pageable = PageRequest.of(page, items, Sort.by(sort).descending());
		}
		
		Page<BeneficiarioResponseDTO> search;
		
		if(status.equals(2)) {
			search = beneficiarioRepository.findAll(pageable).map(beneficiarioMapper::toResponseDto);
		} else if(status.equals(0) || status.equals(1)) {
			search = beneficiarioRepository.findAllByStatus(pageable, status).map(beneficiarioMapper::toResponseDto);			
		} else {
			log.warn("Parametro invalido");
			return ResponseEntity.status(500).body(ApiResponseDTO.builder()
					.message("Status inválido como parametro da requisição")
					.content(Map.of("status", status))
					.build());
		}
		
		return ResponseEntity.status(200).body(ApiResponseDTO.builder()
				.message("Beneficiarios listados!")
				.content(search)
				.build());

	}

	@Override
	public ResponseEntity<ApiResponseDTO> cadastraBeneficiario(BeneficiarioRequestDTO beneficiario) {
		beneficiario.setTelefone(Beneficiario.formataTelefone(beneficiario.getTelefone()));
		
		if (beneficiario.getTelefone().isEmpty() || beneficiario.getTelefone().length() < 10) {
			log.warn("Telefone inválido!");
			return ResponseEntity.status(500).body(ApiResponseDTO.builder()
					.message("Erro ao criar novo usuario")
					.content(Map.of("telefone", beneficiario.getTelefone()))
					.build());
		}
		
		Beneficiario novoBeneficiario = beneficiarioMapper.toEntity(beneficiario);
		BeneficiarioResponseDTO beneficiarioResponse;
		
		log.info("Cadastrando Beneficiario: " + beneficiario.getNome());
		
		List<Documento> documentos = beneficiario.getDocumentos()
				.stream()
				.map(documento -> {
					Documento novoDocumento = documentoMapper.toEntity(documento);
					novoDocumento.setDtInclusao(LocalDateTime.now());
					return novoDocumento;
				})
				.collect(Collectors.toList());
		
		novoBeneficiario.setDtInclusao(LocalDateTime.now());
		novoBeneficiario.setStatus(0);
		
		try {
			final Beneficiario novoBeneficiarioFinal = beneficiarioRepository.save(novoBeneficiario);
			novoBeneficiario = novoBeneficiarioFinal;
			documentos = documentos.stream().map(documento -> {
				documento.setBeneficiario(novoBeneficiarioFinal);
				documento.setStatus(0);
				documento = documentoRepository.save(documento);
				return documento;
			}).collect(Collectors.toList());
			
			beneficiarioResponse = this.converteParaResponse(novoBeneficiario, documentos);
			
			
			log.info("Beneficiario criado");
			
			
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(500).body(ApiResponseDTO.builder()
					.message("Erro ao criar novo Beneficiario")
					.content(Map.of("error", e.getMessage()))
					.build());
			
		}
		

		return ResponseEntity.status(201).body(ApiResponseDTO.builder()
				.message("Beneficiario criado com sucesso")
				.content(beneficiarioResponse)
				.build());
	}

	@Override
	public ResponseEntity<ApiResponseDTO> buscaBeneficiario(Long beneficiarioId) {
		
		log.info("Buscando beneficiario: " + beneficiarioId);
		Optional<Beneficiario> beneficiarioEntity = beneficiarioRepository.findById(beneficiarioId);
		
		if(beneficiarioEntity.isEmpty()) return ResponseEntity.status(400).body(ApiResponseDTO.builder()
				.message("Nenhum Beneficiario Encontrado")
				.content(Map.of("id", beneficiarioId))
				.build());
		
		return ResponseEntity.status(200).body(ApiResponseDTO.builder()
				.message("Beneficiario encontrado!")
				.content(beneficiarioMapper.toResponseDto(beneficiarioEntity.get()))
				.build());
	}

	@Override
	public ResponseEntity<ApiResponseDTO> atualizaBeneficiario(BeneficiarioRequestDTO beneficiario,
			Long beneficiarioId) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		log.info("Editando beneficiario: " + beneficiarioId);
		Optional<Beneficiario> beneficiarioAtualizadoOpt = beneficiarioRepository.findById(beneficiarioId);
		
		if(beneficiarioAtualizadoOpt.isEmpty()) {
			log.info("Beneficiario inexistente");
			return ResponseEntity.status(400).body(ApiResponseDTO.builder()
				.message("Nenhum Beneficiario Encontrado")
				.content(Map.of("id", beneficiarioId))
				.build());
		}
		
		if(!beneficiarioAtualizadoOpt.get().getStatus().equals(beneficiario.getStatus()) &&
				!auth.getAuthorities().parallelStream().anyMatch(a -> a.getAuthority().equals("SCOPE_ROLE_USER"))) 
		{
		
			log.warn("Tentativa de alterar o status do beneficiario!");
			return ResponseEntity.status(403).body(ApiResponseDTO.builder()
				.message("Erro ao atualizar beneficiario, status não pode ser alterado neste endpoint")
			    .content(Map.of("error", "acesso negado"))
				.build());
		}
		Beneficiario beneficiarioAtualizado = beneficiarioMapper.toEntity(beneficiario);
		beneficiarioAtualizado.setId(beneficiarioId);
		beneficiarioAtualizado.setDtInclusao(beneficiarioAtualizadoOpt.get().getDtInclusao());
		beneficiarioAtualizado.setTelefone(Beneficiario.formataTelefone(beneficiarioAtualizado.getTelefone()));
		beneficiarioAtualizado.setDtAtualizacao(LocalDateTime.now());
		
		List<Documento> documentos = beneficiario.getDocumentos()
				.stream()
				.map(documento -> {
					Documento novoDocumento = documentoMapper.toEntity(documento);
					if(novoDocumento.getDtInclusao() == null) { 
						novoDocumento.setDtInclusao(LocalDateTime.now());
					} else {
						novoDocumento.setDtAtualizacao(LocalDateTime.now());
					}
					return novoDocumento;
				})
				.collect(Collectors.toList());
		
		BeneficiarioResponseDTO beneficiarioResponse;
		
		try {
			final Beneficiario novoBeneficiarioFinal = beneficiarioRepository.save(beneficiarioAtualizado);
			beneficiarioAtualizado = novoBeneficiarioFinal;
			documentos = documentos.stream().map(documento -> {
				documento.setBeneficiario(novoBeneficiarioFinal);
				documento.setStatus(0);
				documento = documentoRepository.save(documento);
				return documento;
			}).collect(Collectors.toList());
			
			beneficiarioResponse = this.converteParaResponse(beneficiarioAtualizado, documentos);
			
			
			log.info("Beneficiario criado");
			
			
			
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(500).body(ApiResponseDTO.builder()
					.message("Erro ao atualizar beneficiario")
					.content(Map.of("error", e.getMessage()))
					.build());
			
		}		
				
		return ResponseEntity.status(200).body(ApiResponseDTO.builder()
				.message("Beneficiario atualizado com sucesso")
				.content(beneficiarioResponse)
				.build());
	}

	@Override
	public ResponseEntity<ApiResponseDTO> inativaBeneficiario(Long beneficiarioId) {
		
		log.info("Alterando status do beneficiario: " + beneficiarioId);
		Optional<Beneficiario> beneficiarioOpt = beneficiarioRepository.findById(beneficiarioId);
					
		if(beneficiarioOpt.isEmpty()) {
			log.info("Beneficiario inexistente");
			return ResponseEntity.status(400).body(ApiResponseDTO.builder()
				.message("Nenhum Beneficiario Encontrado")
				.content(Map.of("id", beneficiarioId))
				.build());
		}
		
		Beneficiario beneficiarioUpd = beneficiarioOpt.get();
		
		try {
			beneficiarioRepository.delete(beneficiarioUpd);
			
			
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(500).body(ApiResponseDTO.builder()
					.message("Erro ao excluir beneficiario")
					.content(Map.of("error", e.getMessage()))
					.build());
			
		}	
		
		return ResponseEntity.status(200).body(ApiResponseDTO.builder()
				.message("Beneficiario excluido com sucesso")
				.content(beneficiarioMapper.toResponseDto(beneficiarioUpd))
				.build());
	}

	private BeneficiarioResponseDTO converteParaResponse(Beneficiario beneficiario, List<Documento> documentos) {		
		BeneficiarioResponseDTO beneficiarioCriado = beneficiarioMapper.toResponseDto(beneficiario);
		List<DocumentoResponseDTO> documentoCriado = documentos.stream().map(documentoMapper::toResponseDto).collect(Collectors.toList());
		beneficiarioCriado.setDocumentos(documentoCriado);
		return beneficiarioCriado;
	}

	@Override
	public ResponseEntity<ApiResponseDTO> listaDocumentos(Long beneficiarioId) {
		Optional<Beneficiario> beneficiario = beneficiarioRepository.findById(beneficiarioId);
		
		if(beneficiario.isEmpty()) {
			log.info("Beneficiario inexistente");
			return ResponseEntity.status(400).body(ApiResponseDTO.builder()
				.message("Nenhum Beneficiario Encontrado")
				.content(Map.of("id", beneficiarioId))
				.build());
		}
		
		List<Documento> documentos = documentoRepository.findByBeneficiario(beneficiario.get());

		List<DocumentoResponseDTO> documentosDto = documentos.stream()
				.map(documentoMapper::toResponseDto)
				.collect(Collectors.toList());
	
		
		return ResponseEntity.status(200).body(ApiResponseDTO.builder()
				.message("Documentos listados")
				.content(documentosDto)
				.build());
	}
	
}
