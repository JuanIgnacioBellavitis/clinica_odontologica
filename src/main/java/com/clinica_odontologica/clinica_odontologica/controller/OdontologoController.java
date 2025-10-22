package com.clinica_odontologica.clinica_odontologica.controller;

import com.clinica_odontologica.clinica_odontologica.model.Odontologo;
import com.clinica_odontologica.clinica_odontologica.model.Paciente;
import com.clinica_odontologica.clinica_odontologica.service.OdontologoService;
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
@RequestMapping("/odontologo")
public class OdontologoController {
    private OdontologoService service;

    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.service = odontologoService;
    }

    @RequestMapping("")
    public List<Odontologo> buscarTodos() {
        return service.buscarTodos();
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Odontologo encontrado = service.buscar(id);

        if (encontrado == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el odontologo con ID " + id);
        }

        return ResponseEntity.ok(encontrado);
    }

    @RequestMapping("/crear")
    public ResponseEntity<?> crearOdontologo(@RequestBody Odontologo odontologo) {
        return ResponseEntity.ok(service.guardar(odontologo));
    }

    @RequestMapping("/eliminar/{id}")
    public ResponseEntity<?>  eliminarOdontologo(@PathVariable Integer id) {
        Odontologo encontrado = service.buscar(id);

        if (encontrado == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el odontologo con ID " + id);
        }

        return ResponseEntity.ok(service.eliminar(id));
    }

    @RequestMapping("/modificar/{id}")
    public ResponseEntity<?> modificarOdontologo(@PathVariable Integer id, @RequestBody Odontologo odontologo) {
        Odontologo encontrado = service.buscar(id);

        if (encontrado == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el odontologo con ID " + id);
        }

        odontologo.setId(id);
        Odontologo modificado = service.modificar(odontologo);

        return ResponseEntity.ok(modificado);
    }
}
