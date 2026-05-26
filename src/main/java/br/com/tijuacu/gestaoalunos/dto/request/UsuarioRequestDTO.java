package br.com.tijuacu.gestaoalunos.dto.request;

import br.com.tijuacu.gestaoalunos.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank @Size(min = 3, max = 50) String username,
        @NotBlank @Size(min = 6, max = 100) String senha,
        @NotNull Role role
) {}