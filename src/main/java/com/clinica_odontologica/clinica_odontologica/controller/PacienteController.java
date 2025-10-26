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


    @DeleteMapping("/eliminar")
    public ResponseEntity<?>  eliminarPaciente(@RequestBody PacienteDTO pacienteDTO) {
        return ResponseEntity.ok(service.eliminarPaciente(pacienteDTO));
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarPaciente(@PathVariable Long id, @RequestBody PacienteDTO paciente) {
        return ResponseEntity.ok(service.editarPaciente(paciente));
    }
}
