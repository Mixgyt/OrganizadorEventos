package com.progra3.organizadorW.organizadorEventos.Controller;

import com.progra3.organizadorW.organizadorEventos.Models.CorreosEventoModel;
import com.progra3.organizadorW.organizadorEventos.Models.EventoModel;
import com.progra3.organizadorW.organizadorEventos.Models.UsuarioModel;
import com.progra3.organizadorW.organizadorEventos.Repository.CorreosEventoRepository;
import com.progra3.organizadorW.organizadorEventos.Repository.EventoRepository;
import com.progra3.organizadorW.organizadorEventos.Repository.UsuarioRepository;
import com.progra3.organizadorW.organizadorEventos.Service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Objects;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final CookieService cookieService;
    private final UsuarioRepository usuarioRepository;
    private final CorreosEventoRepository correosEventoRepository;
    private final EventoRepository eventoRepository;

    public UsuarioController(CookieService cookieService, UsuarioRepository usuarioRepository, CorreosEventoRepository correosEventoRepository, EventoRepository eventoRepository) {
        this.cookieService = cookieService;
        this.usuarioRepository = usuarioRepository;
        this.correosEventoRepository = correosEventoRepository;
        this.eventoRepository = eventoRepository;
    }

    @GetMapping("/home")
    public String inicio(HttpServletRequest res, HttpServletResponse response, Model model) {
        String cookie = cookieService.getCookie(res,"login");
        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioModel usuarioModel = usuarioRepository.findByNombre(usuario);
        if(!Objects.isNull(cookie)){
            String[] ids = cookie.split("-");
            cookieService.deleteCookie(response,"login");
            if(usuarioModel.getIdCorreo().getId_correo()==Integer.parseInt(ids[1])){
                return "redirect:/invitaciones/evento?id="+ids[0]+"&usuario="+ids[1];
            }
        }
        model.addAttribute("usuario", usuarioModel);
        return "index";
    }

    @GetMapping("/eventos")
    public String eventos(Model model) {
        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioModel usuarioModel = usuarioRepository.findByNombre(usuario);
        ArrayList<CorreosEventoModel> correosEventoModels = correosEventoRepository.findByIdCorreo(usuarioModel.getIdCorreo());
        ArrayList<EventoModel> eventosModels = new ArrayList<>();

        for(CorreosEventoModel correosEventoModel : correosEventoModels){
            eventosModels.add(correosEventoModel.getIdEvento());
        }
        model.addAttribute("eventos", eventosModels);
        model.addAttribute("usuario", usuarioModel);
        return "eventos";
    }

}
