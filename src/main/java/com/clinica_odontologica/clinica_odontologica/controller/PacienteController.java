package com.clinica_odontologica.clinica_odontologica.controller;

import com.clinica_odontologica.clinica_odontologica.dto.PacienteDTO;
import com.clinica_odontologica.clinica_odontologica.service.IPacienteService;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private IPacienteService service;

    public PacienteController(PacienteService pacienteService) {
        this.service = pacienteService;
    }

    @GetMapping("")
    public List<PacienteDTO> buscarTodos() {
        return service.listarPacientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {

        PacienteDTO pacienteDTO = service.buscarPacientePorId(id);

        return ResponseEntity.ok(pacienteDTO);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPaciente(@RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(service.guardarPaciente(pacienteDTO));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Long id) {
        String mensaje = service.eliminarPaciente(id);
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarPaciente(@PathVariable Long id, @RequestBody PacienteDTO paciente) {
        return ResponseEntity.ok(service.editarPaciente(id, paciente));
    }

    @GetMapping("/buscar-email")
    public PacienteDTO buscarPacienteEmail(@RequestBody String email) {
        return service.buscarPacientePorEmail(email);
    }
}
