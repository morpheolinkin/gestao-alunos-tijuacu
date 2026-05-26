package br.com.tijuacu.gestaoalunos.mapper;

import br.com.tijuacu.gestaoalunos.dto.request.UsuarioRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.UsuarioResponseDTO;
import br.com.tijuacu.gestaoalunos.model.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO toResponseDTO(Usuario entity) {
        if (entity == null) return null;

        return new UsuarioResponseDTO(
                entity.getId(),
                entity.getUsername(),
                entity.getRole(),
                entity.getAtivo()
        );
    }

    public void updateEntityFromDTO(UsuarioRequestDTO dto, Usuario entity) {
        entity.setUsername(dto.username());
        entity.setRole(dto.role());
        // senha será tratada no service (para recriptografar)
    }
}