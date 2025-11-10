package com.clinica_odontologica.clinica_odontologica.service;

import java.util.List;

import com.clinica_odontologica.clinica_odontologica.dto.OdontologoDTO;

public interface IOdontologoService {
	OdontologoDTO guardarOdontologo(OdontologoDTO odontologoDTO);

	OdontologoDTO buscarOdontologoPorId(Long id);

	List<OdontologoDTO> listarOdontologos();

	OdontologoDTO editarOdontologos(Long id, OdontologoDTO odontologoDTODTO);

	String eliminarOdontologo(Long id);

	OdontologoDTO buscarOdontologoPorNombre(String nobre);
}
