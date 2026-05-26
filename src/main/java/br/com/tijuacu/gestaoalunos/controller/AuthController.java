package br.com.tijuacu.gestaoalunos.controller;

import br.com.tijuacu.gestaoalunos.dto.request.LoginRequest;
import br.com.tijuacu.gestaoalunos.dto.response.AuthResponse;
import br.com.tijuacu.gestaoalunos.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Login e emissão de tokens JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    @Operation(summary = "Autentica e retorna um token JWT")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()
            );

            authentication = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String username = authentication.getName();
            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(Object::toString)
                    .orElse("ROLE_USER");

            String token = jwtTokenProvider.generateToken(username, role);

            AuthResponse response = new AuthResponse(
                    token,
                    "Bearer",
                    username,
                    role
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException ex) {
            // aqui você pode lançar uma exceção customizada ou deixar o handler global cuidar
            throw new RuntimeException("Usuário inexistente ou senha inválida");
        }
    }
}