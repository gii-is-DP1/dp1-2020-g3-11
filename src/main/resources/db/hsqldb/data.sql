INSERT INTO tabla_tipos_Usuario VALUES (1, 'Usuario');
INSERT INTO tabla_tipos_Usuario VALUES (2, 'Sponsor');
INSERT INTO tabla_tipos_Usuario VALUES (3, 'admin');


--admin1--
INSERT INTO users(username,password,enabled) VALUES ('administrador1','adm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'administrador1','admin');
INSERT INTO usuario VALUES (1, 'George', 'Franklin', '45899990X', 'jorgito@admin.com', '1995-02-20',null,'692874512', 3,'administrador1');


--admin2--
INSERT INTO users(username,password,enabled) VALUES ('administrador2','adm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (28,'administrador2','admin');
INSERT INTO usuario VALUES (2, 'Javi', 'erere', '46899990X', 'javierefdo1@admin.com', '1998-02-20',null,'677888512', 3,'administrador2');




INSERT INTO users(username,password,enabled) VALUES ('administrador3','adm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (40,'administrador3','admin');
INSERT INTO usuario VALUES (3, 'Juanlu', 'erere', '46899990X', 'javierefdo2@admin.com', '1998-02-20',null,'677888512', 3,'administrador3');

INSERT INTO festival VALUES (1,'Cabo de Plata',100,100,'2021-06-06','2021-06-09','Cádiz', 1);
INSERT INTO festival VALUES (2,'Dreambeach',200,200,'2021-07-25','2021-07-29','Almería', 2);
INSERT INTO festival VALUES (3,'Guacamayo',200,200,'2020-07-25','2020-07-29','Almería', 3);

--usuario --
INSERT INTO users(username,password,enabled) VALUES ('usuarioPrueba','usuarioPrueba',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (13,'usuarioPrueba','usuario');
INSERT INTO usuario VALUES (60, 'nombre', 'Apellido', '45987990X', 'ale@sponsor.com', '1993-08-10',null,'692771112', 1,'usuarioPrueba');


--sponsor--
INSERT INTO users(username,password,enabled) VALUES ('sponsorPrueba','sponsorPrueba',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (12,'sponsorPrueba','sponsor');
INSERT INTO usuario VALUES (20, 'Manue', 'Frank', '45444990X', 'manue@user.com',  '1999-02-20','marcaPredeterminada','692811112',  2,'sponsorPrueba');

INSERT INTO users(username,password,enabled) VALUES ('sponsorPrueba1','sponsorPrueba',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (17,'sponsorPrueba1','sponsor');
INSERT INTO usuario VALUES (25, 'joselito', 'cuidaito', '45444990U', 'cuidao@man.com',  '1999-02-20','acme','692854112',  2,'sponsorPrueba1');

INSERT INTO users(username,password,enabled) VALUES ('user1','us3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (14,'user1','usuario');
INSERT INTO usuario VALUES (22, 'Edu', 'Garcia', '45333990A', 'edu@user.com', '1999-02-20', null, '693333112',  1, 'user1');

INSERT INTO users(username,password,enabled) VALUES ('user2','us3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (15,'user2','usuario');
INSERT INTO usuario VALUES (23, 'Juan', 'Gomez', '45222990C', 'juan@user.com', '1999-02-20',null, '692811882', 1,'user2');

INSERT INTO users(username,password,enabled) VALUES ('user3','us3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (16,'user3','usuario');
INSERT INTO usuario VALUES (24, 'Pablo', 'Cubero', '41111990B', 'pablo@user.com', '1999-02-20',null, '699911112', 1,'user3');




--INSERT INTO festival VALUES (3,'Mucha Cumbia BRO',200,'2021-03-15','2021-03-19','Puerto Rico', 2);--
--INSERT INTO festival VALUES (4,'Mucha Cumbia BRO 2.0',200,'2021-03-15','2021-03-19','Puerto Rico', 2);--

INSERT INTO generos VALUES (1, 'pop');
INSERT INTO generos VALUES (2, 'reggaeton');
INSERT INTO generos VALUES (3, 'electronica');
INSERT INTO generos VALUES (4, 'hip-hop');
INSERT INTO generos VALUES (5, 'trap');
INSERT INTO generos VALUES (6, 'flamenco');

INSERT INTO artista VALUES (1,'Los papanatas','papafritas@grupo.com','657412356', 1);
INSERT INTO artista VALUES (2,'Canelones','canelone@grupo.com','657452356',2);
INSERT INTO artista VALUES (3,'Yung beef','yb@grupo.com','657411236', 5);
INSERT INTO artista VALUES (4,'Dellafuente','della@grupo.com','654112356',5);
INSERT INTO artista VALUES (5,'Fumeto','fumeto@grupo.com','657466447',2);
INSERT INTO artista VALUES (6,'El cobra','cobra@grupo.com','663342356',1);
INSERT INTO artista VALUES (7,'Maikel','maikel@grupo.com','657778136',1);
INSERT INTO artista VALUES (8,'Flammingo','fmgo@grupo.com','654515556',2);
INSERT INTO artista VALUES (9,'El manteca','manteca@grupo.com','661112356',4);
INSERT INTO artista VALUES (10,'Suli de huelva','suli@grupo.com','657114433',6);
INSERT INTO artista VALUES (11,'Makukote','mkk@grupo.com','688996236',4);
INSERT INTO artista VALUES (12,'Antonio Martínez','am@grupo.com','622222356',3);
INSERT INTO artista VALUES (13,'Prueba Javi','prueba@grupo.com','622222356',3);

INSERT INTO entradatypes VALUES (1, 'camping');
INSERT INTO entradatypes VALUES (2, 'no camping');
INSERT INTO entradatypes VALUES (3, 'fiesta');


INSERT INTO entrada VALUES (1,40,1,1);
INSERT INTO entrada VALUES (2,30,2,1);
INSERT INTO entrada VALUES (3,50,1,1);
INSERT INTO entrada VALUES (10,41,1,2);
INSERT INTO entrada VALUES (11,31,2,2);
INSERT INTO entrada VALUES (12,51,1,2);

INSERT INTO tipos_oferta VALUES (1, 'Pack bebidas');
INSERT INTO tipos_oferta VALUES (2, 'Camiseta');
INSERT INTO tipos_oferta VALUES (3, 'Tokens');

INSERT INTO oferta VALUES (1,'Camisetas y 3 bolsas de hielo', 10, 2, 2);
INSERT INTO oferta VALUES (2,'Botella de Ron y 1 bolsa de hielos', 13, 2, 1);



INSERT INTO tabla_tipos_recinto VALUES (1, 'Parking');
INSERT INTO tabla_tipos_recinto VALUES (2, 'Camping');
INSERT INTO tabla_tipos_recinto VALUES (3, 'Escenario');

INSERT INTO recinto VALUES (1,'Escenario Terra',200,1,3);
INSERT INTO recinto VALUES (2,'Camping Atlas',100,1,2);
INSERT INTO recinto VALUES (3,'Parking 1',50,1,1);
INSERT INTO recinto VALUES (4,'Escenario Benito Villamarin',220,1,3);
INSERT INTO recinto VALUES (5,'Escenario Electro',150,2,3);
INSERT INTO recinto VALUES (6,'Escenario Musica Urban',150,2,3);
INSERT INTO recinto VALUES (7,'Camping DreamBeach',100,1,2);
INSERT INTO recinto VALUES (8,'Parking DreamBeach',50,1,1);
INSERT INTO recinto VALUES (9,'Escenario Carranza',180,1,3);
INSERT INTO recinto VALUES (10,'Camping Falla',120,1,2);

INSERT INTO tabla_tipos_puesto VALUES (1, 'Comida');
INSERT INTO tabla_tipos_puesto VALUES (2, 'Ropa');
INSERT INTO tabla_tipos_puesto VALUES (3, 'Actividades');

INSERT INTO tabla_tipos_tamanio VALUES (1, 'Reducido');
INSERT INTO tabla_tipos_tamanio VALUES (2, 'Mediano');
INSERT INTO tabla_tipos_tamanio VALUES (3, 'Grande');

INSERT INTO puesto VALUES (1,1000,1,1,20,1,1);
INSERT INTO puesto VALUES (2,2000,1,4,25,3,1);
INSERT INTO puesto VALUES (3,3500,2,5,20,2,2);
INSERT INTO puesto VALUES (4,1500,2,6,25,1,1);
INSERT INTO puesto VALUES (5,2200,1,4,null,3,2);
INSERT INTO puesto VALUES (6,3600,2,6,null,2,3);
INSERT INTO puesto VALUES (7,1450,2,5,null,1,1);
INSERT INTO puesto VALUES (8,2250,1,9,null,1,1);
INSERT INTO puesto VALUES (9,3650,1,9,null,2,1);
INSERT INTO puesto VALUES (10,980,2,5,null,1,2);
INSERT INTO puesto VALUES (11,2050,1,1,null,3,1);
INSERT INTO puesto VALUES (12,3520,1,1,null,2,2);

INSERT INTO concierto VALUES (1,'2021-07-25','2021-07-25 17:00','2021-07-25 18:00',1,2,5);
INSERT INTO concierto VALUES (2,'2021-07-26','2021-07-26 16:00','2021-07-26 17:00',2,2,6);
INSERT INTO concierto VALUES (3,'2021-07-27','2021-07-27 18:00','2021-07-27 20:00',3,2,5);


INSERT INTO opinion VALUES (1, 'Muy bueno','2021-07-25 17:00', 5, 2, 60);
INSERT INTO opinion VALUES (2, 'Muy bueno pero bueno tu sabe','2021-08-25 17:00', 1, 2, 23);
INSERT INTO opinion VALUES (3, 'Muy bueno. Conciertazo flammingo bro','2021-09-25 17:00',2, 2, 22);

