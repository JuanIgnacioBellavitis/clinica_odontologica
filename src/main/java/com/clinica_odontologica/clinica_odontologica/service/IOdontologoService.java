package com.clinica_odontologica.clinica_odontologica.service;

import com.clinica_odontologica.clinica_odontologica.dto.OdontologoDTO;

import java.util.List;

public interface IOdontologoService {
    OdontologoDTO guardarOdontologo(OdontologoDTO odontologoDTO);
    OdontologoDTO buscarOdontologoPorId(Long id);
    List<OdontologoDTO> listarOdontologos();
    OdontologoDTO editarOdontologos(Long id, OdontologoDTO odontologoDTODTO);
    String eliminarOdontologo(Long id);
    OdontologoDTO buscarOdontologoPorNombre(String nobre);
}
