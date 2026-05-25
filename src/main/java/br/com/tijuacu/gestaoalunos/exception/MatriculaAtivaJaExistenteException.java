package br.com.tijuacu.gestaoalunos.exception;

public class MatriculaAtivaJaExistenteException extends RuntimeException {
    public MatriculaAtivaJaExistenteException(Long alunoId, Integer anoLetivo) {
        super("Já existe uma matrícula ativa para o aluno " + alunoId + " no ano letivo " + anoLetivo);
    }
}