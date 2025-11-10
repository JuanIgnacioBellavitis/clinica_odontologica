package com.clinica_odontologica.clinica_odontologica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinica_odontologica.clinica_odontologica.dto.TurnoDTO;
import com.clinica_odontologica.clinica_odontologica.exceptions.NotFoundException;
import com.clinica_odontologica.clinica_odontologica.service.OdontologoService;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;
import com.clinica_odontologica.clinica_odontologica.service.TurnoService;

@RestController
@RequestMapping("/turno")
public class TurnoController {
	private final OdontologoService odontologoService;
	private final PacienteService pacienteService;
	private final TurnoService turnoService;

	@Autowired
	public TurnoController(OdontologoService odontologoService, PacienteService pacienteService,
			TurnoService turnoService) {
		this.odontologoService = odontologoService;
		this.pacienteService = pacienteService;
		this.turnoService = turnoService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

		TurnoDTO turnoDTO = turnoService.buscarTurnoPorId(id);

		return ResponseEntity.ok(turnoDTO);
	}

	@PostMapping("/crear")
	public ResponseEntity<TurnoDTO> registrarTurno(@RequestBody TurnoDTO turnoDTO) {
		return ResponseEntity.ok(turnoService.guardarTurno(turnoDTO));
	}

	@GetMapping("/todos")
	public List<TurnoDTO> obtenerTodos() {
		return turnoService.listarTurnos();
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarTurno(@PathVariable Long id) {
		try {
			String mensaje = turnoService.eliminarTurnoPorId(id);
			return ResponseEntity.ok(mensaje);
		} catch (NotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error al eliminar el turno.");
		}
	}

	@PutMapping("/modificar/{id}")
	public ResponseEntity<?> modificarTurno(@PathVariable Long id, @RequestBody TurnoDTO turnoDTO) {
		return ResponseEntity.ok(turnoService.editarTurno(id, turnoDTO));
	}
}
