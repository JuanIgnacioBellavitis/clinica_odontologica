package com.clinica_odontologica.clinica_odontologica.service;

import com.clinica_odontologica.clinica_odontologica.entity.Usuario;
import com.clinica_odontologica.clinica_odontologica.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService( UsuarioRepository usuarioRepository, ObjectMapper mapper) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(username);

        if(!usuario.isPresent()) {
            throw new UsernameNotFoundException("No se enccontró el usuario");
        }

        return usuario.get();
    }
}
