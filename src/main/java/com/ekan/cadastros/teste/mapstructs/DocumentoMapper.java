package com.ekan.cadastros.teste.mapstructs;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ekan.cadastros.teste.dto.requests.DocumentoRequestDTO;
import com.ekan.cadastros.teste.dto.responses.DocumentoResponseDTO;
import com.ekan.cadastros.teste.entities.Documento;

@Mapper(componentModel="spring")
public interface DocumentoMapper {
	DocumentoMapper INSTANCE = Mappers.getMapper(DocumentoMapper.class);

	
	@Mapping(target="id", source="id")
	@Mapping(target="descricao", source="descricao")
	@Mapping(target="tipoDocumento", source="tipoDocumento")
	@Mapping(target="dtInclusao", source="dtInclusao")
	@Mapping(target="dtAtualizacao", source="dtAtualizacao")
	@Mapping(target="status", source="status")
	DocumentoResponseDTO toResponseDto(Documento documento);
	
	@Mapping(target="id", source="id")
	@Mapping(target="descricao", source="descricao")
	@Mapping(target="tipoDocumento", source="tipoDocumento")
	@Mapping(target="status", source="status")
	Documento toEntity(DocumentoRequestDTO documento);
}
