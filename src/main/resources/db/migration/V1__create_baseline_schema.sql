CREATE TABLE IF NOT EXISTS usuario
(
    id
    BIGSERIAL
    PRIMARY
    KEY,
    username
    VARCHAR
(
    100
) NOT NULL UNIQUE,
    senha VARCHAR
(
    255
) NOT NULL,
    role VARCHAR
(
    50
) NOT NULL
    );