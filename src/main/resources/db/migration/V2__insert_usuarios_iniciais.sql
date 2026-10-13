INSERT INTO usuario (id, username, senha, role, ativo, criado_em, criado_por)
VALUES (1, 'admin', '$2a$10$TYtWbm2ORW30g438LLF9zupynWZompB4jV26RlBtMrgSuKF3fuPQu', 'ADMIN', true, now(), 'SYSTEM'),
       (2, 'secretario', '$2a$10$PB52s8auwJMXmQBJbWIvxeDAnY8mFYu0ZP2lbhNQ13TTHnDvF6rki', 'SECRETARIO', true, now(), 'SYSTEM')
    ON CONFLICT (username) DO
UPDATE
    SET senha = EXCLUDED.senha,
    role = EXCLUDED.role,
    ativo = EXCLUDED.ativo;