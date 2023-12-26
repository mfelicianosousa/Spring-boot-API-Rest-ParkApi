insert into USUARIOS (id, username, password, role) values (100,'ana@gmail.com', '$2a$12$4xfbPfv3.c1pylB3k0H0i.JZifjQprTR9iYvT5mWWKS6YRc5x3cPe', 'ROLE_ADMIN')
insert into USUARIOS (id, username, password, role) values (101,'bia@gmail.com', '$2a$12$4xfbPfv3.c1pylB3k0H0i.JZifjQprTR9iYvT5mWWKS6YRc5x3cPe', 'ROLE_CLIENTE')
insert into USUARIOS (id, username, password, role) values (102,'bob@gmail.com', '$2a$12$4xfbPfv3.c1pylB3k0H0i.JZifjQprTR9iYvT5mWWKS6YRc5x3cPe', 'ROLE_CLIENTE')
insert into USUARIOS (id, username, password, role) values (103,'toby@gmail.com', '$2a$12$4xfbPfv3.c1pylB3k0H0i.JZifjQprTR9iYvT5mWWKS6YRc5x3cPe', 'ROLE_CLIENTE')

insert into CLIENTES (id, nome, cpf, usuario_id) VALUES (10, 'Bianca Silva', '79074426050', 101)
insert into CLIENTES (id, nome, cpf, usuario_id) VALUES (20, 'Roberto Gomes ', '55352517047', 102)
