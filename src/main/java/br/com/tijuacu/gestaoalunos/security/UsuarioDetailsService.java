package br.com.tijuacu.gestaoalunos.security;

import br.com.tijuacu.gestaoalunos.model.entity.Usuario;
import br.com.tijuacu.gestaoalunos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        System.out.println("Buscando no banco o usuário: [" + username + "]");

        Usuario usuario = usuarioRepository.findByUsernameAndAtivoTrue(username)
                .orElseThrow(() -> {
                    System.out.println("ERRO FATAL: Usuário não existe no banco ou está inativo!");
                    return new UsernameNotFoundException("Usuário não encontrado: " + username);
                });

        System.out.println("Usuário encontrado! Hash salvo no banco: [" + usuario.getSenha() + "]");

        return new UsuarioDetails(usuario);
    }
}