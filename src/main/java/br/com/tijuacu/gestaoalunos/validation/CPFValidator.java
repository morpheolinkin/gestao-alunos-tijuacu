package br.com.tijuacu.gestaoalunos.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPFValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }

        // Remove tudo que não for dígito (pontos, traços)
        String cpf = value.replaceAll("\\D", "");

        // CPF precisa ter 11 dígitos
        if (cpf.length() != 11) return false;

        // Rejeita CPFs com todos dígitos iguais (ex.: 00000000000, 11111111111...)
        if (cpf.chars().distinct().count() == 1) return false;

        try {
            return validarDigitosVerificadores(cpf);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validarDigitosVerificadores(String cpf) {
        // Cálculo do primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            int num = Character.getNumericValue(cpf.charAt(i));
            soma += num * (10 - i);
        }
        int resto = soma % 11;
        int primeiroDV = (resto < 2) ? 0 : 11 - resto;

        if (primeiroDV != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        // Cálculo do segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            int num = Character.getNumericValue(cpf.charAt(i));
            soma += num * (11 - i);
        }
        resto = soma % 11;
        int segundoDV = (resto < 2) ? 0 : 11 - resto;

        return segundoDV == Character.getNumericValue(cpf.charAt(10));
    }
}
