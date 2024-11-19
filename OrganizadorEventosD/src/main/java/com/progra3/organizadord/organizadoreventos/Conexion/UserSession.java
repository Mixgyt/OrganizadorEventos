package com.progra3.organizadord.organizadoreventos.Conexion;

import com.progra3.organizadord.organizadoreventos.models.UsuarioModel;

public class UserSession {
    private static UsuarioModel usuario;

    //Se utiliza para obtener el usuario que inicio sesion
    public static UsuarioModel getUsuario() {
        return usuario;
    }

    //Se utiliza para guardar el usuario que inicio sesion
    public static void setUsuario(UsuarioModel usuario) {
        UserSession.usuario = usuario;
    }
}
