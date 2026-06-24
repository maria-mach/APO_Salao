CREATE DATABASE IF NOT EXISTS sistema_agendamento;
USE sistema_agendamento;
-- drop database sistema_agendamento;
-- tabela estoque serve apenas como composição de Item, a tabela item já
-- possui os dados necessários, por isso estoque table nao será criada

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    cpf VARCHAR(14) UNIQUE,
    preferencia VARCHAR(200)
);


CREATE TABLE prestador (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    cpf VARCHAR(14) UNIQUE,
    profissionalizacao VARCHAR(100)
);

CREATE TABLE item (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_item VARCHAR(100) NOT NULL,
    marca VARCHAR(100),
    modelo VARCHAR(100),
    data_validade DATE,
    fornecedor VARCHAR(100),
    custo DECIMAL(10,2),
    quantidade INT,
    limite_min INT
);

CREATE TABLE servico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_servico VARCHAR(100) NOT NULL,
    especificacao VARCHAR(200),
    preco DECIMAL(10,2) NOT NULL
);

CREATE TABLE servico_item (
    id_servico INT NOT NULL,
    id_item INT NOT NULL,
    quantidade_usada INT DEFAULT 1,
    PRIMARY KEY (id_servico, id_item),
    FOREIGN KEY (id_servico) REFERENCES servico(id),
    FOREIGN KEY (id_item) REFERENCES item(id)
);
ALTER TABLE servico_item
DROP COLUMN quantidade_usada;


CREATE TABLE servico_prestador (
    id_prestador INT NOT NULL,
    id_servico INT NOT NULL,
    PRIMARY KEY (id_prestador, id_servico),
    FOREIGN KEY (id_prestador) REFERENCES prestador(id),
    FOREIGN KEY (id_servico) REFERENCES servico(id)
);

CREATE TABLE agendamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_prestador INT NOT NULL,          -- ligação direta com o prestador
    id_servico INT NOT NULL,
    data_realizacao DATETIME NOT NULL,  -- horário escolhido
    data_criacao DATETIME NOT NULL,
    status ENUM('AGENDADO', 'EM_ANDAMENTO', 'CANCELADO', 'REALIZADO'),
    status_pagamento ENUM('PAGO','EM_ABERTO') DEFAULT 'EM_ABERTO',
    FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    FOREIGN KEY (id_prestador) REFERENCES prestador(id),
    FOREIGN KEY (id_servico) REFERENCES servico(id)
);


CREATE TABLE agenda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_prestador INT NOT NULL,
    FOREIGN KEY (id_prestador) REFERENCES prestador(id)
);
ALTER TABLE agenda ADD UNIQUE (id_prestador);


CREATE TABLE horario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_agenda INT NOT NULL,
    data_hora DATETIME NOT NULL,
    FOREIGN KEY (id_agenda) REFERENCES agenda(id)
);

CREATE TABLE pagamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_agendamento int,
    titular_conta VARCHAR(100),
    numero_cartao VARCHAR(20),
    vencimento VARCHAR(20),
    cvv int,
    metodo_pagamento VARCHAR(30),
    autorizacao VARCHAR(20),
    foreign key (id_agendamento) references agendamento(id)
);

/*/////////////////////////////////////// TRIGGER /////////////////////////////////////////*/
DROP TRIGGER IF EXISTS trg_pagamento_insert;
DROP TRIGGER IF EXISTS trg_pagamento_delete;
DROP TRIGGER IF EXISTS trg_agendamento_cancelado;
DROP TRIGGER IF EXISTS trg_agendamento_insert;
DROP TRIGGER IF EXISTS trg_agendamento_reaberto;
DROP TRIGGER IF EXISTS  trg_agendamento_status;

DELIMITER //
CREATE TRIGGER trg_agendamento_status
BEFORE INSERT ON agendamento
FOR EACH ROW
BEGIN
    -- Se tentar inserir AGENDADO sem estar PAGO, força para EM_ANDAMENTO
    IF NEW.status = 'AGENDADO' AND NEW.status_pagamento <> 'PAGO' THEN
        SET NEW.status = 'EM_ANDAMENTO';
    END IF;
END //
DELIMITER ;


DELIMITER //
CREATE TRIGGER trg_pagamento_insert
AFTER INSERT ON pagamento
FOR EACH ROW
BEGIN
    UPDATE agendamento
    SET status_pagamento = 'PAGO',
        status = 'AGENDADO'
    WHERE id = NEW.id_agendamento;
END //
DELIMITER ;


DELIMITER //
CREATE TRIGGER trg_pagamento_delete
AFTER DELETE ON pagamento
FOR EACH ROW
BEGIN
    UPDATE agendamento
    SET status_pagamento = 'EM_ABERTO',
    status = 'EM_ABERTO'
    WHERE id = OLD.id_agendamento;
END //
DELIMITER ;	

DELIMITER //
CREATE TRIGGER trg_agendamento_cancelado
BEFORE UPDATE ON agendamento
FOR EACH ROW
BEGIN
    IF NEW.status = 'CANCELADO' THEN
        DELETE FROM horario
        WHERE data_hora = OLD.data_realizacao
          AND id_agenda IN (
              SELECT ag.id
              FROM agenda ag
              WHERE ag.id_prestador = OLD.id_prestador
          );
    END IF;
END //
DELIMITER ;



DELIMITER //
CREATE TRIGGER trg_agendamento_insert
AFTER INSERT ON agendamento
FOR EACH ROW
BEGIN
    IF NEW.status = 'AGENDADO' OR NEW.status = 'EM_ANDAMENTO' THEN
        INSERT INTO horario(id_agenda, data_hora)
        VALUES (
            (SELECT id FROM agenda WHERE id_prestador = NEW.id_prestador),
            NEW.data_realizacao
        );
    END IF;
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER trg_agendamento_reaberto
AFTER UPDATE ON agendamento
FOR EACH ROW
BEGIN
    -- Só executa se o status mudou de CANCELADO para EM_ANDAMENTO
    IF OLD.status = 'CANCELADO' AND NEW.status = 'EM_ANDAMENTO' THEN
        -- Verifica se o horário já existe antes de inserir
        IF NOT EXISTS (
            SELECT 1
            FROM horario h
            WHERE h.id_agenda = (SELECT id FROM agenda WHERE id_prestador = NEW.id_prestador LIMIT 1)
              AND h.data_hora = NEW.data_realizacao
        ) THEN
            INSERT INTO horario(id_agenda, data_hora)
            VALUES (
                (SELECT id FROM agenda WHERE id_prestador = NEW.id_prestador LIMIT 1),
                NEW.data_realizacao
            );
        END IF;
    END IF;
END //
DELIMITER ;




/*////////////////////////////////////// INSERTS //////////////////////////////////////////*/
INSERT INTO cliente (nome, senha, telefone, email, cpf, preferencia)
VALUES 
('Ana Souza', 'senha123', '11987654321', 'ana.souza@email.com', '12345678901', 'Atendimento rápido'),
('Carlos Lima', 'senha456', '11912345678', 'carlos.lima@email.com', '98765432100', 'Prefere manhã'),
('Mariana Alves', 'senha789', '11955554444', 'mariana.alves@email.com', '45678912300', 'Serviços estéticos');

INSERT INTO prestador (nome, senha, telefone, email, cpf, profissionalizacao)
VALUES 
('João Pereira', 'senha1', '11988887777', 'joao.pereira@email.com', '11122233344', 'Cabeleireiro'),
('Fernanda Costa', 'senha2', '11999996666', 'fernanda.costa@email.com', '55566677788', 'Manicure'),
('Ricardo Silva', 'senha3', '11944443333', 'ricardo.silva@email.com', '99988877766', 'Massoterapeuta'),
('Paula Mendes', 'senha4', '11922223333', 'paula.mendes@email.com', '22233344455', 'Esteticista'),
('Gustavo Rocha', 'senha5', '11933334444', 'gustavo.rocha@email.com', '33344455566', 'Barbeiro'),
('Camila Torres', 'senha6', '11944445555', 'camila.torres@email.com', '44455566677', 'Depiladora');

INSERT INTO item (nome_item, marca, modelo, data_validade, fornecedor, custo, quantidade, limite_min)
VALUES 
('Shampoo', 'L\'Oréal', 'NutriGloss', '2026-01-01', 'Distribuidora Beleza', 25.50, 50, 10),
('Esmalte', 'Risqué', 'Vermelho', '2027-05-01', 'Fornecedor Nails', 5.00, 200, 30),
('Óleo de Massagem', 'Granado', 'Relaxante', '2026-12-01', 'Fornecedor Spa', 15.00, 100, 20);

INSERT INTO servico (nome_servico, especificacao, preco)
VALUES 
('Corte de Cabelo', 'Corte masculino ou feminino', 60.00),
('Manicure', 'Limpeza e esmaltação das unhas', 40.00),
('Massagem Relaxante', 'Sessão de 60 minutos', 120.00);

-- Corte de cabelo usa shampoo
INSERT INTO servico_item (id_servico, id_item)
VALUES (1, 1);

-- Manicure usa esmalte
INSERT INTO servico_item (id_servico, id_item)
VALUES (2, 2);

-- Massagem usa óleo de massagem
INSERT INTO servico_item (id_servico, id_item)
VALUES (3, 3);

-- João faz corte de cabelo
INSERT INTO servico_prestador (id_prestador, id_servico)
VALUES (1, 1);

-- Fernanda faz manicure
INSERT INTO servico_prestador (id_prestador, id_servico)
VALUES (2, 2);

-- Ricardo faz massagem relaxante
INSERT INTO servico_prestador (id_prestador, id_servico)
VALUES (3, 3);

INSERT INTO agenda (id_prestador)
VALUES 
(1), -- João Pereira
(2), -- Fernanda Costa
(3); -- Ricardo Silva

INSERT INTO agendamento (id_cliente, id_prestador, id_servico, data_realizacao, data_criacao, status, status_pagamento)
VALUES 
(1, 1, 1, '2025-12-06 10:00:00', NOW(), 'EM_ANDAMENTO', 'PAGO'), -- Ana Souza com João (corte de cabelo)
(2, 2, 2, '2025-12-06 09:00:00', NOW(), 'EM_ANDAMENTO', 'EM_ABERTO'),      -- Carlos Lima com Fernanda (manicure)
(3, 3, 3, '2025-12-06 11:00:00', NOW(), 'EM_ANDAMENTO', 'EM_ABERTO'); -- Mariana Alves com Ricardo (massagem)


-- Novos agendamentos de teste

INSERT INTO agendamento (id_cliente, id_prestador, id_servico, data_realizacao, data_criacao, status, status_pagamento)
VALUES
(1, 2, 2, '2025-12-07 14:00:00', NOW(), 'EM_ANDAMENTO', 'EM_ABERTO'),
(2, 3, 3, '2025-12-07 15:30:00', NOW(), 'EM_ANDAMENTO', 'EM_ABERTO'),
(3, 1, 1, '2025-12-08 09:00:00', NOW(), 'EM_ANDAMENTO', 'EM_ABERTO'),
(1, 3, 3, '2025-12-08 11:00:00', NOW(), 'EM_ANDAMENTO', 'EM_ABERTO'),
(2, 1, 1, '2025-12-09 10:00:00', NOW(), 'EM_ANDAMENTO', 'EM_ABERTO');


INSERT INTO pagamento (id_agendamento, titular_conta, numero_cartao, vencimento, cvv, metodo_pagamento, autorizacao)
VALUES 
(1, 'Ana Souza', '4111111111111111', '12/27', 123, 'Cartão de Crédito', 'AUT123');

INSERT INTO pagamento (id_agendamento, titular_conta, numero_cartao, vencimento, cvv, metodo_pagamento, autorizacao)
VALUES 
(2, 'Carlos Lima', '5555444433332222', '11/28', 456, 'Cartão de Crédito', 'AUT456');

INSERT INTO pagamento (id_agendamento, titular_conta, numero_cartao, vencimento, cvv, metodo_pagamento, autorizacao)
VALUES 
(3, 'Mariana Alves', '4444333322221111', '10/28', 789, 'Cartão de Débito', 'AUT789');


/*/////////////////////////////////////// LISTAGEM /////////////////////////////////////////*/

SELECT * FROM cliente;
SELECT * FROM prestador;
SELECT * FROM servico;
SELECT * FROM item;
SELECT * FROM servico_item;
SELECT * FROM servico_prestador;
SELECT * FROM agenda;
SELECT * FROM horario;
SELECT * FROM agendamento;
SELECT * FROM pagamento;

/*/////////////////////////////////////// ALTERAÇÕES ////////////////////////////////////////*/
-- Atualiza telefone do cliente
UPDATE cliente
SET telefone = '11922223333'
WHERE id = 1;  -- Ana Souza

-- Atualiza telefone do prestador
UPDATE prestador
SET telefone = '11933334444'
WHERE id = 2;  -- Fernanda Costa

-- Atualiza quantidade em estoque
UPDATE item
SET quantidade = quantidade - 10
WHERE id = 1;  -- Shampoo

-- Atualiza preço do serviço
UPDATE servico
SET preco = preco + 10.00
WHERE id = 3;  -- Massagem Relaxante

-- Atualiza quantidade usada de item em serviço
UPDATE servico_item
SET quantidade_usada = 2
WHERE id_servico = 2 AND id_item = 2;  -- Manicure usa 2 esmaltes

-- Não faz sentido alterar muito aqui, mas podemos adicionar outro serviço ao prestador
-- Exemplo: João também faz massagem
INSERT INTO servico_prestador (id_prestador, id_servico)
VALUES (1, 3);

-- Atualiza agenda para outro prestador (exemplo simples)
UPDATE agenda
SET id_prestador = 4
WHERE id = 1;

UPDATE agenda
SET id_prestador = 1
WHERE id = 1;

-- Atualiza horário de uma agenda
UPDATE horario
SET data_hora = '2025-12-07 10:00:00'
WHERE id = 1;

-- Atualiza método de pagamento
UPDATE pagamento
SET metodo_pagamento = 'Cartão de Débito'
WHERE id = 1;  -- Pagamento da Ana Souza

-- Atualiza status de um agendamento para CONCLUIDO
UPDATE agendamento
SET status = 'REALIZADO'
WHERE id = 1;  -- Agendamento da Ana Souza


/*/////////////////////////////////////// CONSULTA //////////////////////////////////////////*/
SELECT id, nome, telefone, email, cpf, preferencia
FROM cliente;

SELECT id, nome, telefone, email, cpf, profissionalizacao
FROM prestador;

SELECT id, nome_item, marca, modelo, data_validade, fornecedor, custo, quantidade, limite_min
FROM item;

SELECT id, nome_servico, especificacao, preco
FROM servico;

SELECT si.id_servico, s.nome_servico, si.id_item, i.nome_item, si.quantidade_usada
FROM servico_item si
JOIN servico s ON si.id_servico = s.id
JOIN item i ON si.id_item = i.id;

SELECT sp.id_prestador, p.nome AS nome_prestador, sp.id_servico, s.nome_servico
FROM servico_prestador sp
JOIN prestador p ON sp.id_prestador = p.id
JOIN servico s ON sp.id_servico = s.id;

SELECT a.id, c.nome AS cliente, p.nome AS prestador, s.nome_servico, 
       a.data_realizacao, a.status, a.status_pagamento
FROM agendamento a
JOIN cliente c ON a.id_cliente = c.id
JOIN prestador p ON a.id_prestador = p.id
JOIN servico s ON a.id_servico = s.id;

SELECT ag.id, p.nome AS prestador
FROM agenda ag
JOIN prestador p ON ag.id_prestador = p.id;

SELECT h.id, h.data_hora, ag.id AS id_agenda, p.nome AS prestador
FROM horario h
JOIN agenda ag ON h.id_agenda = ag.id
JOIN prestador p ON ag.id_prestador = p.id;

SELECT p.id, a.id AS id_agendamento, c.nome AS cliente, p.metodo_pagamento, p.autorizacao
FROM pagamento p
JOIN agendamento a ON p.id_agendamento = a.id
JOIN cliente c ON a.id_cliente = c.id;


/*////////////////////////////// PROCEDURE AGENDAMENTO///////////////////////////////////*/
DROP PROCEDURE IF EXISTS cancelar_agendamento;
DROP PROCEDURE IF EXISTS buscar_agendamentos_cliente_validos;
DROP PROCEDURE IF EXISTS BuscarClientePorLogin;
DROP PROCEDURE IF EXISTS BuscarAgendaDoCliente ;

DELIMITER //
CREATE PROCEDURE BuscarClientePorLogin (
    IN p_nome VARCHAR(100),
    IN p_senha VARCHAR(100)
)
BEGIN
    SELECT *
    FROM cliente
    WHERE nome = p_nome
      AND senha = p_senha;
END //
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE BuscarAgendaDoCliente (
    IN p_id_cliente INT
)
BEGIN
    SELECT 
        h.data_hora, 
        a.id AS id_agenda, 
        p.id AS id_prestador,
        p.nome AS nome_prestador  
    FROM agendamento ag
    INNER JOIN prestador p ON ag.id_prestador = p.id
    INNER JOIN agenda a ON p.id = a.id_prestador
    INNER JOIN horario h ON a.id = h.id_agenda AND h.data_hora = ag.data_realizacao
    WHERE ag.id_cliente = p_id_cliente
    ORDER BY h.data_hora;
END $$
DELIMITER ;


DELIMITER //
CREATE PROCEDURE buscar_agendamentos_cliente_validos(IN cliente_id INT)
BEGIN
    SELECT a.id AS id_agendamento,
           a.data_realizacao,
           a.data_criacao,
           a.status AS status_agendamento,
           a.status_pagamento,
           c.id AS id_cliente,
           c.nome AS nome_cliente,
           p.id AS id_prestador,
           p.nome AS nome_prestador,
           s.id AS id_servico,
           s.nome_servico,
           s.especificacao,
           s.preco
    FROM agendamento a
    JOIN cliente c ON a.id_cliente = c.id
    JOIN prestador p ON a.id_prestador = p.id
    JOIN servico s ON a.id_servico = s.id
    WHERE a.id_cliente = cliente_id
      AND a.status NOT IN ('CANCELADO', 'REALIZADO');
END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE cancelar_agendamento(IN agendamento_id INT)
BEGIN
    -- Atualiza status do agendamento para CANCELADO
    UPDATE agendamento
    SET status = 'CANCELADO'
    WHERE id = agendamento_id;
    
    -- Exclui pagamento, se existir
    DELETE FROM pagamento
    WHERE id_agendamento = agendamento_id;
    
END //
DELIMITER ;
select * from pagamento;
/*////////////////////////////// PROCEDURE BUSCA DE SERVICO ///////////////////////////////////*/
DROP PROCEDURE IF EXISTS BuscaServicos;

DELIMITER $$

CREATE PROCEDURE BuscaServicos()
BEGIN
    SELECT * FROM servico;
END $$

DELIMITER ;


/*////////////////////////////// PROCEDURE CADASTRAR ITEM ///////////////////////////////////*/

DROP PROCEDURE IF EXISTS inserir_item;

-- Procedure para inserir item
DELIMITER //
CREATE PROCEDURE inserir_item(
    IN nome_item VARCHAR(100),
    IN marca VARCHAR(100),
    IN modelo VARCHAR(100),
    IN data_validade DATE,
    IN fornecedor VARCHAR(100),
    IN custo DECIMAL(10,2),
    IN quantidade INT,
    IN limite_min INT,
    IN id_servico INT
)
BEGIN
    -- Insere o item
    INSERT INTO item (nome_item, marca, modelo, data_validade, fornecedor, custo, quantidade, limite_min)
    VALUES (nome_item, marca, modelo, data_validade, fornecedor, custo, quantidade, limite_min);

    -- Pega o id gerado
    SET @novo_id_item = LAST_INSERT_ID();

    -- Insere na tabela servico_item
    INSERT INTO servico_item (id_servico, id_item)
    VALUES (id_servico, @novo_id_item);
END //
DELIMITER ;



/*////////////////////////////// PROCEDURE DE PAGAMENTO ///////////////////////////////////*/

DROP PROCEDURE IF EXISTS get_preco_por_agendamento;


/*//////////////////////////////  TESTES  ///////////////////////////////////*/

/* DELETE FROM agendamento WHERE id > 0;
DELETE FROM agenda WHERE id > 0;
DELETE FROM servico WHERE id > 0;
DELETE FROM prestador WHERE id > 0;
DELETE FROM cliente WHERE id > 0;


ALTER TABLE agendamento AUTO_INCREMENT = 1;
ALTER TABLE agenda AUTO_INCREMENT = 1;
ALTER TABLE servico AUTO_INCREMENT = 1;
ALTER TABLE prestador AUTO_INCREMENT = 1;
ALTER TABLE cliente AUTO_INCREMENT = 1;

*/

select * from agendamento;
select * from horario;
select * from agenda;
select * from pagamento;
select * from servico_item;
UPDATE agendamento
SET status = 'AGENDADO'
WHERE id = 3;  

SELECT id_agenda, data_hora FROM horario WHERE id_agenda = (SELECT id FROM agenda WHERE id_prestador = 3) ORDER BY data_hora;	


/*////////////////////////////// PROCEDURE DE PAGAMENTO ///////////////////////////////////*/
DROP PROCEDURE IF EXISTS inserir_pagemento;

DELIMITER //
CREATE PROCEDURE inserir_pagamento (
    IN p_id_agendamento INT,
    IN p_titular_conta VARCHAR(100),
    IN p_numero_cartao VARCHAR(20),
    IN p_vencimento VARCHAR(20),
    IN p_cvv INT,
    IN p_metodo_pagamento VARCHAR(30),
    IN p_autorizacao VARCHAR(20)
)
BEGIN
    -- Insere o pagamento
    INSERT INTO pagamento (
        id_agendamento,
        titular_conta,
        numero_cartao,
        vencimento,
        cvv,
        metodo_pagamento,
        autorizacao
    )
    VALUES (
        p_id_agendamento,
        p_titular_conta,
        p_numero_cartao,
        p_vencimento,
        p_cvv,
        p_metodo_pagamento,
        p_autorizacao
    );

    -- O trigger trg_pagamento_insert já atualiza o status do agendamento para PAGO/AGENDADO
END //
DELIMITER ;
    