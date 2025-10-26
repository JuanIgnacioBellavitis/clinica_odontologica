package com.clinica_odontologica.clinica_odontologica.controller;

import com.clinica_odontologica.clinica_odontologica.dto.OdontologoDTO;
import com.clinica_odontologica.clinica_odontologica.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private OdontologoService service;

    @Autowired
    public OdontologoController(OdontologoService odontologoService) {
        this.service = odontologoService;
    }

    @GetMapping("")
    public List<OdontologoDTO> buscarTodos() {
        return service.listarOdontologos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarOdontologoPorId(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearOdontologo(@RequestBody OdontologoDTO odontologoDTO) {
        return ResponseEntity.ok(service.guardarOdontologo(odontologoDTO));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Long id) {
        String msj = service.eliminarOdontologo(id);

        return ResponseEntity.ok(msj);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> modificarOdontologo(@PathVariable Long id, @RequestBody OdontologoDTO odontologo) {
        return ResponseEntity.ok(service.editarOdontologos(id, odontologo));
    }

    @GetMapping("/buscar-nombre")
    public ResponseEntity<?> buscarOdontologoPorNombre(@RequestBody String nombre) {
        return ResponseEntity.ok(service.buscarOdontologoPorNombre(nombre));
    }
}
