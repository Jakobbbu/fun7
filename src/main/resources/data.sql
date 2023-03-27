INSERT INTO GAME_USER
VALUES
    (1, now(), now(), '$2a$10$ivD7SBQH.3FwftxdhZfu.OKT7JWGPdU37Agr7FYAocisAnMHoEWFG', 0, 'user123'),
    (2, now(), now(), '$2a$10$ivD7SBQH.3FwftxdhZfu.OKT7JWGPdU37Agr7FYAocisAnMHoEWFG', 2, 'gamer007'),
    (3, now(), now(), '$2a$10$ivD7SBQH.3FwftxdhZfu.OKT7JWGPdU37Agr7FYAocisAnMHoEWFG', 30, 'legend2000'),
    (4, now(), now(), '$2a$10$T5SiILnCzFmbkcnVkxMv9uWzXuMfpaPy1hgVx51x2HvYJnZmbYs5q', 30, 'admin');

INSERT INTO PRIVILEGE
VALUES
    (1 ,now(), now(), 'READ_PRIVILEGE'),
    (2 ,now(), now(), 'WRITE_PRIVILEGE'),
    (3 ,now(), now(), 'DELETE_PRIVILEGE');

INSERT INTO ROLE
VALUES
    (1 ,now(), now(), 'ROLE_ADMIN'),
    (2 ,now(), now(), 'ROLE_USER');

INSERT INTO ROLE_PRIVILEGES
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 3);

INSERT INTO GAME_USER_ROLES
VALUES
    (1, 2),
    (2, 2),
    (3, 2),
    (4, 1),
    (4, 2);