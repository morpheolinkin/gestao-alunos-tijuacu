package br.com.tijuacu.gestaoalunos.dto.response;

import br.com.tijuacu.gestaoalunos.model.enums.Role;

public record UsuarioResponseDTO(
        Long id,
        String username,
        Role role,
        Boolean ativo
) {}