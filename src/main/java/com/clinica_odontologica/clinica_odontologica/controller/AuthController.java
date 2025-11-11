package com.clinica_odontologica.clinica_odontologica.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinica_odontologica.clinica_odontologica.dto.RegistroDTO;
import com.clinica_odontologica.clinica_odontologica.entity.Usuario;
import com.clinica_odontologica.clinica_odontologica.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	private final UsuarioService usuarioService;

	@Autowired
	public AuthController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO dto) {
		try {
			Usuario nuevo = usuarioService.registrarNuevoUsuario(dto.getNombre(), dto.getApellido(), dto.getUserName(),
					dto.getEmail(), dto.getPassword());
			return ResponseEntity.ok("Usuario registrado con éxito: " + nuevo.getEmail());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body("Error al registrar usuario");
		}
	}

	@GetMapping("/me")
	public ResponseEntity<?> obtenerUsuarioActual(Authentication authentication) {
		if (authentication == null) {
			return ResponseEntity.status(401).body("No autenticado");
		}

		Usuario usuario = (Usuario) authentication.getPrincipal();
		return ResponseEntity.ok(Map.of("nombre", usuario.getNombre(), "apellido", usuario.getApellido(), "email",
				usuario.getEmail(), "rol", usuario.getUsuarioRol().name()));
	}
}