package com.progra3.organizadorW.organizadorEventos.Service;

import com.progra3.organizadorW.organizadorEventos.Models.SecurityUsuario;
import com.progra3.organizadorW.organizadorEventos.Models.UsuarioModel;
import com.progra3.organizadorW.organizadorEventos.Repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioDetailsServiceImp implements UserDetailsService {

    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        UsuarioModel user = userRepository.findByNombre(nombre);
        if(user == null) {
            throw new UsernameNotFoundException(nombre);
        }

        return new SecurityUsuario(user);
    }
}
