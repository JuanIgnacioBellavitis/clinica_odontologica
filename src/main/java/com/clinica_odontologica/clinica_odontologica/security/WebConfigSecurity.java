package com.clinica_odontologica.clinica_odontologica.security;

import com.clinica_odontologica.clinica_odontologica.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebConfigSecurity {
    private final UsuarioService usuarioService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebConfigSecurity(UsuarioService usuarioService,  BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioService = usuarioService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider  daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desactivado para esta aplicacion
                .authorizeHttpRequests((auth)
                        -> auth.requestMatchers("/pacienteLista.html").hasRole("ADMIN")
                        .requestMatchers("/odontologoLista.html").permitAll()
                        .requestMatchers("/turnoLista.html").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults()).logout(Customizer.withDefaults());

        return http.build();
    }
}
