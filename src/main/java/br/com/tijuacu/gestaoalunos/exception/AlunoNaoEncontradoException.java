package br.com.tijuacu.gestaoalunos.exception;

public class AlunoNaoEncontradoException extends RuntimeException{

    public AlunoNaoEncontradoException(Long id){
        super("Aluno não encontrado: " + id);
    }
}
