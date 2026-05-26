package br.com.tijuacu.gestaoalunos.aux;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeraSenha {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("secret123");
        System.out.println(hash);
    }
}