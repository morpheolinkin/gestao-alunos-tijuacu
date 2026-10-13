package br.com.tijuacu.gestaoalunos;

import br.com.tijuacu.gestaoalunos.model.entity.Usuario;
import br.com.tijuacu.gestaoalunos.model.enums.Role;
import br.com.tijuacu.gestaoalunos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GestaoAlunosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestaoAlunosApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica se o usuário admin já existe no banco
            if (usuarioRepository.findByUsernameAndAtivoTrue("admin").isEmpty()) {

                // Cria o usuário com a senha já encriptada pelo Spring
                Usuario admin = Usuario.builder()
                        .username("admin")
                        .senha(passwordEncoder.encode("123456"))
                        .role(Role.ADMIN)
                        .ativo(true)
                        .build();

                // Caso a sua classe exija, descomente a linha abaixo:
                // admin.setCriadoPor("SYSTEM");

                usuarioRepository.save(admin);
                System.out.println("=========================================================");
                System.out.println("✅ USUÁRIO ADMIN CRIADO COM SUCESSO DIRETAMENTE PELO JAVA!");
                System.out.println("=========================================================");
            }
        };
    }
}