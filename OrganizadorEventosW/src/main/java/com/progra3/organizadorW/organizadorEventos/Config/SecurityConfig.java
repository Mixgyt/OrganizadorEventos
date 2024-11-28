package com.progra3.organizadorW.organizadorEventos.Config;

import com.progra3.organizadorW.organizadorEventos.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csf->csf.disable()).authorizeHttpRequests(auth->
                auth.requestMatchers("/auth/register","/auth/login", "/css/**","/js/**").permitAll()
                .anyRequest().authenticated()
                ).formLogin(form ->
                form.loginPage("/auth/login") // pagina de inicio de sesión
                .defaultSuccessUrl("/usuario/home", true) // Define la URL de redirección después de un inicio de sesión exitoso
                .failureUrl("/auth/login?error=true") // Define la URL de redirección después de un inicio de sesión fallido
                .loginProcessingUrl("/login"));
        return http.build();
    }

    @Autowired // Configura la autenticación global
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance()); // Configura el servicio de detalles de usuario y el codificador de contraseñas
    }
}
