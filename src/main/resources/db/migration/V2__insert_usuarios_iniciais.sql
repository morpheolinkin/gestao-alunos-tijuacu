INSERT INTO usuario (id, username, senha, role, ativo, criado_em, criado_por)
VALUES (1, 'admin', '$2a$10$kX6M6exZwD4.S8M.Bvm37u.qGSRBKrJsMwYVSqfdslLFP78E76VYSm', 'ADMIN', true, now(), 'SYSTEM'),
       (2, 'secretario', '$2a$10$kX6M6exZwD4.S8M.Bvm37u.qGSRBKrJsMwYVSqfdslLFP78E76VYSm', 'SECRETARIO', true, now(),
        'SYSTEM') ON CONFLICT (username) DO
UPDATE
    SET senha = EXCLUDED.senha,
    role = EXCLUDED.role,
    ativo = EXCLUDED.ativo;