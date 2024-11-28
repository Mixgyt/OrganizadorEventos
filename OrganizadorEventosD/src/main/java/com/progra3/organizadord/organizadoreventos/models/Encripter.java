package com.progra3.organizadord.organizadoreventos.models;

import org.mindrot.jbcrypt.BCrypt;

import java.util.function.Function;

public class Encripter {
    public static String hash(String password) {
        String a = BCrypt.hashpw(password, BCrypt.gensalt(12));
        System.out.println(a);
        return a;
    }

    public static boolean verify(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
