package com.clinica_odontologica.clinica_odontologica.security;

import com.clinica_odontologica.clinica_odontologica.entity.Usuario;
import com.clinica_odontologica.clinica_odontologica.entity.UsuarioRol;
import com.clinica_odontologica.clinica_odontologica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements ApplicationRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder codificador;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String pass = "admin";
        String passCod = codificador.encode(pass);
        Usuario usuario =
                new Usuario("Juan", "Bellavitis", "juancito", passCod, "juan@mail.com",
                        UsuarioRol.ROLE_ADMIN);

        usuarioRepository.save(usuario);
        System.out.println("Pass sin codificar: " + pass + " - Codificador: " + passCod);
    }
}
