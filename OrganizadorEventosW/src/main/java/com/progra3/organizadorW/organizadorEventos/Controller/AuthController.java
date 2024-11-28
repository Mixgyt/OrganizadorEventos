package com.progra3.organizadorW.organizadorEventos.Controller;

import com.progra3.organizadorW.organizadorEventos.Models.CorreoModel;
import com.progra3.organizadorW.organizadorEventos.Models.UsuarioModel;
import com.progra3.organizadorW.organizadorEventos.Repository.CorreosRepository;
import com.progra3.organizadorW.organizadorEventos.Repository.UsuarioRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    public final UsuarioRepository usuarioRepository;
    public final CorreosRepository correosRepository;

    public AuthController(UsuarioRepository usuarioRepository, CorreosRepository correosRepository) {
        this.usuarioRepository = usuarioRepository;
        this.correosRepository = correosRepository;
    }


    @GetMapping("/login")
    public String login(@Param("msg") Boolean msg, Model model) {
        model.addAttribute("msg", msg);
        return "login";
    }

    @GetMapping("/register")
    public String register(@Param("correo") String correo,Model model) {
        model.addAttribute("correo", correo);
        return "register";
    }

    @PostMapping("/register")
    public String registrarUsuario(@RequestParam("nombre") String nombre, @RequestParam("correo") String correo, @RequestParam("password") String password, Model model) {
        UsuarioModel usuarioExist = usuarioRepository.findByNombre(nombre);
        if (usuarioExist == null) {
            CorreoModel correoExist = correosRepository.findByCorreo(correo);
            if (correoExist == null) {
                correoExist = new CorreoModel();
                correoExist.setCorreo(correo);
                correosRepository.save(correoExist);
            }
            UsuarioModel usuario = new UsuarioModel();
            usuario.setNombre(nombre);
            usuario.setIdCorreo(correoExist);
            usuario.setPass(BCrypt.hashpw(password, BCrypt.gensalt(12)));
            usuarioRepository.save(usuario);
            return "redirect:/auth/login?msg=true";
        }
        model.addAttribute("msg","Ya existe un usuario con dicho nombre");
        return "register";
    }
}
