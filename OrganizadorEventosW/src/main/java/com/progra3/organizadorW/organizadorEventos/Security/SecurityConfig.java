package com.progra3.organizadorW.organizadorEventos.Security;

import com.progra3.organizadorW.organizadorEventos.Service.UsuarioDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UsuarioDetailsServiceImp usuarioDetailsServiceImpService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/login","/error", "/img/**", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin( view ->
                        view.loginPage("/public/login").
                                defaultSuccessUrl("/public/home", true) // Define la URL de redirección después de un inicio de sesión exitoso
                                .failureUrl("/public/login?error=true") // Define la URL de redirección después de un inicio de sesión fallido
                                .loginProcessingUrl("/login")
                )
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

   /* @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

}
