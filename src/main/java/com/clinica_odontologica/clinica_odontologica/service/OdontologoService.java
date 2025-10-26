package com.clinica_odontologica.clinica_odontologica.service;

import java.util.List;
import java.util.stream.Collectors;

import com.clinica_odontologica.clinica_odontologica.dto.OdontologoDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Odontologo;
import com.clinica_odontologica.clinica_odontologica.exceptions.NotFoundException;
import com.clinica_odontologica.clinica_odontologica.repository.OdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OdontologoService implements IOdontologoService{

    @Autowired
	private OdontologoRepository  odontologoRepository;
    private final ObjectMapper mapper;

    public OdontologoService(OdontologoRepository odontologoRepository, ObjectMapper mapper) {
        this.odontologoRepository = odontologoRepository;
        this.mapper = mapper;
    }

    @Override
    public List<OdontologoDTO> listarOdontologos() {
        return odontologoRepository.findAll()
                .stream()
                .map(this::odontologoAOdontologoDTO)
                .collect(Collectors.toList());
    }

    public OdontologoDTO guardarOdontologo(OdontologoDTO odontologoDTO) {
        Odontologo odontologo = odontologoDTOAOdontologo(odontologoDTO);

        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);

        return odontologoAOdontologoDTO(odontologoGuardado);
	}

	public OdontologoDTO buscarOdontologoPorId(Long id) {

        Odontologo odontologo = odontologoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el odontologo con el ID " + id));

		return mapper.convertValue(odontologo, OdontologoDTO.class);
	}

	@Override
	public OdontologoDTO buscarOdontologoPorNombre(String nombre) {

        Odontologo odontologo = odontologoRepository.findByNombre(nombre).orElseThrow(
                () -> new NotFoundException("No se encontró odontologo con el nombre " + nombre));

		return odontologoAOdontologoDTO(odontologo);
	}

	@Override
    public String eliminarOdontologo(Long id) {
        Odontologo odontologo = odontologoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "No se encontró el odontologo con ID " + id));

        odontologoRepository.delete(odontologo);
        return "El odontologo " + odontologo.getNombre() + " ha sido eliminado correctamente.";
	}

	@Override
	public OdontologoDTO editarOdontologos(Long id, OdontologoDTO odontologoDTO) {
        Odontologo odontologoExistente = odontologoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "No se encontró el odontologo con ID " + odontologoDTO.getId()));

        odontologoExistente.setNombre(odontologoDTO.getNombre());
        odontologoExistente.setApellido(odontologoDTO.getApellido());
        odontologoExistente.setMatricula(odontologoDTO.getMatricula());

        Odontologo odontologoActualizado = odontologoRepository.save(odontologoExistente);

        return odontologoAOdontologoDTO(odontologoActualizado);
	}

    private OdontologoDTO odontologoAOdontologoDTO(Odontologo odontologo) {
        return mapper.convertValue(odontologo, OdontologoDTO.class);
    }

    private Odontologo odontologoDTOAOdontologo(OdontologoDTO odontologoDTO) {
        return mapper.convertValue(odontologoDTO, Odontologo.class);
    }
}
