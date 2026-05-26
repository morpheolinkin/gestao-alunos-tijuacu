package br.com.tijuacu.gestaoalunos.service;

import br.com.tijuacu.gestaoalunos.dto.request.UsuarioRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.UsuarioResponseDTO;
import br.com.tijuacu.gestaoalunos.exception.UsuarioNaoEncontradoException;
import br.com.tijuacu.gestaoalunos.mapper.UsuarioMapper;
import br.com.tijuacu.gestaoalunos.model.entity.Usuario;
import br.com.tijuacu.gestaoalunos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        return usuarioMapper.toResponseDTO(usuario);
    }

    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setRole(dto.role());
        usuario.setAtivo(true);

        Usuario salvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(salvo);
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        usuarioMapper.updateEntityFromDTO(dto, usuario);

        if (dto.senha() != null && !dto.senha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.senha()));
        }

        Usuario atualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(atualizado);
    }

    public void desativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
}