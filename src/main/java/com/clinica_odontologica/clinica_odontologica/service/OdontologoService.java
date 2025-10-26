package com.clinica_odontologica.clinica_odontologica.service;

import java.util.List;
import java.util.Optional;

import com.clinica_odontologica.clinica_odontologica.dto.OdontologoDTO;
import com.clinica_odontologica.clinica_odontologica.dto.TurnoDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Odontologo;
import com.clinica_odontologica.clinica_odontologica.entity.Paciente;
import com.clinica_odontologica.clinica_odontologica.entity.Turno;
import com.clinica_odontologica.clinica_odontologica.exceptions.NotFoundException;
import com.clinica_odontologica.clinica_odontologica.repository.OdontologoRepository;
import com.clinica_odontologica.clinica_odontologica.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OdontologoService {

    @Autowired
	private OdontologoRepository  odontologoRepository;
    private final ObjectMapper mapper;

    public OdontologoService(OdontologoRepository odontologoRepository, ObjectMapper mapper) {
        this.odontologoRepository = odontologoRepository;
        this.mapper = mapper;
    }

    public OdontologoDTO guardar(OdontologoDTO odontologoDTO) {
        Odontologo odontologo = odontologoDTOAOdontologo(odontologoDTO);

        Odontologo odontologoGuardado = odontologoRepository.save(odontologo);

        return odontologoAOdontologoDTO(odontologoGuardado);
	}

	public OdontologoDTO buscarOdontologoPorId(Long id) {

        Odontologo odontologo = odontologoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el odontologo con el ID " + id));

		return mapper.convertValue(odontologo, OdontologoDTO.class);
	}

	public List<Odontologo> buscarTodos() {
		return odontologoRepository.findAll();
	}

    private OdontologoDTO odontologoAOdontologoDTO(Odontologo odontologo) {
        return mapper.convertValue(odontologo, OdontologoDTO.class);
    }

    private Odontologo odontologoDTOAOdontologo(OdontologoDTO odontologoDTO) {
        return mapper.convertValue(odontologoDTO, Odontologo.class);
    }
    /*

	@Override
	public Odontologo buscarPorNombre(String parametro) {
		return odontologoDAO.buscarPorString(parametro);
	}
	@Override
	public List<Odontologo> eliminar(Integer id) {
		odontologoDAO.eliminar(id);
		List<Odontologo> odontologoRestantes = odontologoDAO.buscarTodos();

		return odontologoRestantes;

	}

	@Override
	public Odontologo modificar(Odontologo odontologo) {
		return odontologoDAO.modificar(odontologo);

	}
*/
}
