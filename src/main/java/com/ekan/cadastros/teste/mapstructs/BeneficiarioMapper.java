package com.ekan.cadastros.teste.mapstructs;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ekan.cadastros.teste.dto.requests.BeneficiarioRequestDTO;
import com.ekan.cadastros.teste.dto.responses.BeneficiarioResponseDTO;
import com.ekan.cadastros.teste.entities.Beneficiario;


@Mapper(componentModel="spring")
public interface BeneficiarioMapper {
	BeneficiarioMapper INSTANCE = Mappers.getMapper(BeneficiarioMapper.class);
	
	@Mapping(target="id", source="id")
	@Mapping(target="nome", source="nome")
	@Mapping(target="telefone", source="telefone")
	@Mapping(target="dtNascimento", source="dtNascimento")
	@Mapping(target="dtInclusao", source="dtInclusao")
	@Mapping(target="dtAtualizacao", source="dtAtualizacao")
	@Mapping(target="status", source="status")
	BeneficiarioResponseDTO toResponseDto(Beneficiario beneficiario);
	
	@Mapping(target="nome", source="nome")
	@Mapping(target="telefone", source="telefone")
	@Mapping(target="dtNascimento", source="dtNascimento")
	@Mapping(target="status", source="status")
	Beneficiario toEntity(BeneficiarioRequestDTO beneficiario);
}
