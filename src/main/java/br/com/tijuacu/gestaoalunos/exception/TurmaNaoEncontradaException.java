package br.com.tijuacu.gestaoalunos.exception;

public class TurmaNaoEncontradaException extends RuntimeException {

    public TurmaNaoEncontradaException(Long id) {
        super("Turma não encontrada com id: " + id);
    }
}