package com.clinica_odontologica.clinica_odontologica.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.clinica_odontologica.clinica_odontologica.entity.Usuario;
import com.clinica_odontologica.clinica_odontologica.entity.UsuarioRol;
import com.clinica_odontologica.clinica_odontologica.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UsuarioService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository, ObjectMapper mapper,
			BCryptPasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

		if (!usuario.isPresent()) {
			throw new UsernameNotFoundException("No se enccontró el usuario");
		}

		return usuario.get();
	}

	public Usuario registrarNuevoUsuario(String nombre, String apellido, String userName, String email,
			String password) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

		if (usuario.isPresent()) {
			throw new IllegalArgumentException("El correo ya está registrado");
		}

		Usuario nuevoUsuario = new Usuario(nombre, apellido, userName, passwordEncoder.encode(password), email,
				UsuarioRol.ROLE_USER);

		return usuarioRepository.save(nuevoUsuario);
	}
}
