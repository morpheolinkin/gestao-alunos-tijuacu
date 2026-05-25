package br.com.tijuacu.gestaoalunos.dto.response;

public record EnderecoResponseDTO(
        Long id,
        String logradouro,
        String numero,
        String bairro,
        String distrito,
        String cidade,
        String cep,
        String zona
) {}