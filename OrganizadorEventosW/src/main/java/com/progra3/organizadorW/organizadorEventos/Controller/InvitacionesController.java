package com.progra3.organizadorW.organizadorEventos.Controller;

import com.progra3.organizadorW.organizadorEventos.Models.CorreoModel;
import com.progra3.organizadorW.organizadorEventos.Models.CorreosEventoModel;
import com.progra3.organizadorW.organizadorEventos.Models.EventoModel;
import com.progra3.organizadorW.organizadorEventos.Models.UsuarioModel;
import com.progra3.organizadorW.organizadorEventos.Repository.CorreosEventoRepository;
import com.progra3.organizadorW.organizadorEventos.Repository.CorreosRepository;
import com.progra3.organizadorW.organizadorEventos.Repository.EventoRepository;
import com.progra3.organizadorW.organizadorEventos.Repository.UsuarioRepository;

import com.progra3.organizadorW.organizadorEventos.Service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

@Controller
@RequestMapping("/invitaciones")
public class InvitacionesController {

    private final EventoRepository eventoRepository;
    private final CorreosEventoRepository correosEventoRepository;
    private final CorreosRepository correosRepository;
    private final UsuarioRepository usuarioRepository;
    private final CookieService cookieService;

    public InvitacionesController(EventoRepository eventoRepository, CookieService cookieService,
                                  CorreosEventoRepository correosEventoRepository, CorreosRepository correosRepository, UsuarioRepository usuarioRepository) {
        this.eventoRepository = eventoRepository;
        this.cookieService = cookieService;
        this.correosEventoRepository = correosEventoRepository;
        this.correosRepository = correosRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/evento")
    public String invitaciones(@RequestParam("id")Integer id_evento, @RequestParam("usuario") Integer id_correo, Model model) {
        EventoModel eventoModel = eventoRepository.findById(id_evento).orElse(null);
        String nombreUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioModel usuarioModel = usuarioRepository.findByNombre(nombreUsuario);

        if(eventoModel != null) {
            ArrayList<CorreosEventoModel> correosEventoModels = correosEventoRepository.findByIdEvento(eventoModel);
            CorreoModel correoModel = correosRepository.findById(id_correo).orElse(null);
            if(usuarioModel != null && correoModel != null) {
                if(!usuarioModel.getIdCorreo().getCorreo().equals(correoModel.getCorreo())) {
                    return "redirect:/usuario/home";
                }
            }
            if(correosEventoModels != null && correoModel != null) {
                CorreosEventoModel correoEventoModel = correosEventoModels.stream().filter(x->x.getIdCorreo()==correoModel).findFirst().get();
                    LocalDateTime fechaInicio = LocalDateTime.ofInstant(eventoModel.getFechaInicio(), ZoneId.systemDefault());
                    LocalDateTime fechaFinal = LocalDateTime.ofInstant(eventoModel.getFechaFinal(), ZoneId.systemDefault());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
                    String inicio = fechaInicio.format(formatter);
                    String fin = fechaFinal.format(formatter);

                    model.addAttribute("fechaInicio", inicio);
                    model.addAttribute("fechaFinal", fin);
                    model.addAttribute("correo", correoModel);
                    model.addAttribute("evento", eventoModel);
                    model.addAttribute("estado",correoEventoModel.getEstado());
                    model.addAttribute("usuario", usuarioModel);
                    return "invitacion";
            }
        }
        return "redirect:/usuario/home";
    }

    @PostMapping("/accept")
    public String acceptInvitation(@RequestParam Integer id_evento, @RequestParam Integer id_correo, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth instanceof AnonymousAuthenticationToken){
            CorreoModel correoModel = correosRepository.findById(id_correo).orElse(null);
            if(correoModel != null) {
                UsuarioModel usuarioModel = usuarioRepository.findByIdCorreo(correoModel);
                if(usuarioModel == null) {
                    cookieService.setCookie(response,"login",id_evento+"-"+id_correo,300);
                    return "redirect:/auth/register?correo="+correoModel.getCorreo();
                }
            }
            cookieService.setCookie(response,"login",id_evento+"-"+id_correo,300);
            return "redirect:/auth/login";
        }
        EventoModel eventoModel = eventoRepository.findById(id_evento).get();
        ArrayList<CorreosEventoModel> correosEventoModels = correosEventoRepository.findByIdEvento(eventoModel);
        if(correosEventoModels != null){
            CorreosEventoModel correosEventoModel = correosEventoModels.stream().filter(x-> Objects.equals(x.getIdCorreo().getId_correo(), id_correo)).findFirst().get();
            correosEventoModel.setEstado(1);
            correosEventoRepository.save(correosEventoModel);
        }
        return "redirect:/invitaciones/evento?id="+id_evento+"&usuario="+id_correo;
    }

    @PostMapping("/cancel")
    public String cancelInvitation(@RequestParam Integer id_evento, @RequestParam Integer id_correo, Model model, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth instanceof AnonymousAuthenticationToken){
            CorreoModel correoModel = correosRepository.findById(id_correo).orElse(null);
            if(correoModel != null) {
                UsuarioModel usuarioModel = usuarioRepository.findByIdCorreo(correoModel);
                if(usuarioModel == null) {
                    cookieService.setCookie(response,"login",id_evento+"-"+id_correo,300);
                    return "redirect:/auth/register?correo="+correoModel.getCorreo();
                }
            }
            cookieService.setCookie(response,"login",id_evento+"-"+id_correo,300);
            return "redirect:/auth/login";
        }
        EventoModel eventoModel = eventoRepository.findById(id_evento).get();
        ArrayList<CorreosEventoModel> correosEventoModels = correosEventoRepository.findByIdEvento(eventoModel);
        if(correosEventoModels != null){
            CorreosEventoModel correosEventoModel = correosEventoModels.stream().filter(x-> Objects.equals(x.getIdCorreo().getId_correo(), id_correo)).findFirst().get();
            correosEventoModel.setEstado(2);
            correosEventoRepository.save(correosEventoModel);
        }
        return "redirect:/invitaciones/evento?id="+id_evento+"&usuario="+id_correo;
    }

    @GetMapping("/test")
    public String test(Model model) {
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombre = auth.getName();*/
        //model.addAttribute("evento", new EventoModel());
        return "invitacion";
    }
}
