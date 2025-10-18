package com.clinica_odontologica.clinica_odontologica.controller;

import com.clinica_odontologica.clinica_odontologica.model.Paciente;
import com.clinica_odontologica.clinica_odontologica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private PacienteService service;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.service = pacienteService;
    }

    @RequestMapping("")
    public List<Paciente> buscarTodos() {
        return service.buscarTodos();
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Paciente encontrado = service.buscar(id);

        if (encontrado == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el paciente con ID " + id);
        }

        return ResponseEntity.ok(encontrado);
    }

    @RequestMapping("/crear")
    public ResponseEntity<?> crearPaciente(@RequestBody Paciente paciente) {
        return ResponseEntity.ok(service.guardar(paciente));
    }

    @RequestMapping("/eliminar/{id}")
    public ResponseEntity<?>  eliminarPaciente(@PathVariable Integer id) {
        Paciente encontrado = service.buscar(id);

        if (encontrado == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el paciente con ID " + id);
        }

        return ResponseEntity.ok(service.eliminar(id));
    }

    @RequestMapping("/modificar/{id}")
    public ResponseEntity<?> modificarPaciente(@PathVariable Integer id, @RequestBody Paciente paciente) {
        Paciente encontrado = service.buscar(id);

        if (encontrado == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el paciente con ID " + id);
        }

        paciente.setId(id);
        Paciente modificado = service.modificar(paciente);

        return ResponseEntity.ok(modificado);
    }
}
