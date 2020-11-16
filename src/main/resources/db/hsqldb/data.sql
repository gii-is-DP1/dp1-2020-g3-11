INSERT INTO users(username,password,enabled) VALUES ('enrgonboz','enrique',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (85,'enrgonboz','admin');

INSERT INTO users(username,password,enabled) VALUES ('Javier','javi',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (84,'Javier','admin');

INSERT INTO users(username,password,enabled) VALUES ('Alejandro','ale',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (80,'Alejandro','admin');

INSERT INTO users(username,password,enabled) VALUES ('Manuel','manu',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (81,'Manuel','admin');

INSERT INTO users(username,password,enabled) VALUES ('Fernando','fer',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (82,'Fernando','admin');

INSERT INTO users(username,password,enabled) VALUES ('pabsanort2','pablo',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (83,'pabsanort2','admin');

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');


-- AQUI ABAJO SE CREA EL USER GENERAL Y LUEGO SE LE DA ESAS CREDENCIALES AL ADMIN, SPONSOR O USER CORRESPONDIENTE (TIENEN TODOS LA MISMA USERY PASS) PERO SON DISTINTOS
--> CREAMOS ID PARA LOS ADMIN DEL 1 AL 19, USERS DE 20 A 59, SPONSOR DE 60 PALANTE
INSERT INTO users(username,password,enabled) VALUES ('administrador1','adm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'administrador1','admin');

INSERT INTO admin VALUES (1, 'George', 'Franklin', '45899990X', 'jorgito@admin.com', '1995-02-20','692874512','administrador1');

INSERT INTO users(username,password,enabled) VALUES ('user1','us3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'user1','usuario');

INSERT INTO usuario VALUES (20, 'Manue', 'Frank', '45444990X', 'manue@user.com', '1999-02-20','692811112','user1');
INSERT INTO usuario VALUES (22, 'Edu', 'Garcia', '45333990A', 'edu@user.com', '1999-02-20','693333112','user1');
INSERT INTO usuario VALUES (23, 'Juan', 'Gomez', '45222990C', 'juan@user.com', '1999-02-20','692811882','user1');
INSERT INTO usuario VALUES (24, 'Pablo', 'Cubero', '41111990B', 'pablo@user.com', '1999-02-20','699911112','user1');

INSERT INTO users(username,password,enabled) VALUES ('sponsor1','sp0nsor',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'sponsor1','sponsor');

INSERT INTO sponsor VALUES (60, 'Alejandro', 'Rios', '45987990X', 'ale@sponsor.com', '1993-08-10','Redbull','692771112','sponsor1');

INSERT INTO festival VALUES (1,'Cabo de Plata',100,'2020-06-06','2020-06-09','Cádiz');
INSERT INTO festival VALUES (2,'Dreambeach',200,'2021-07-25','2021-07-29','Almería');

INSERT INTO artista VALUES (1,'Los papanatas','papafritas@grupo.com',0,'657412356');
INSERT INTO artista VALUES (2,'Canelones','canelone@grupo.com',2,'657452356');
INSERT INTO artista VALUES (3,'Yung beef','yb@grupo.com',5,'657411236');
INSERT INTO artista VALUES (4,'Dellafuente','della@grupo.com',5,'654112356');
INSERT INTO artista VALUES (5,'Fumeto','fumeto@grupo.com',2,'657466447');
INSERT INTO artista VALUES (6,'El cobra','cobra@grupo.com',1,'663342356');
INSERT INTO artista VALUES (7,'Maikel','maikel@grupo.com',1,'657778136');
INSERT INTO artista VALUES (8,'Flammingo','fmgo@grupo.com',2,'654515556');
INSERT INTO artista VALUES (9,'El manteca','manteca@grupo.com',0,'661112356');
INSERT INTO artista VALUES (10,'Suli de huelva','suli@grupo.com',6,'657114433');
INSERT INTO artista VALUES (11,'Makukote','mkk@grupo.com',1,'688996236');
INSERT INTO artista VALUES (12,'Antonio Martínez','am@grupo.com',3,'622222356');

INSERT INTO entradatypes VALUES (1, 'camping');
INSERT INTO entradatypes VALUES (2, 'no camping');
INSERT INTO entradatypes VALUES (3, 'fiesta');


INSERT INTO entrada VALUES (1,40,1,1,20);
INSERT INTO entrada VALUES (2,30,2,1,23);
INSERT INTO entrada VALUES (3,50,1,1,22);

INSERT INTO oferta VALUES (1,20,'Camisetas y 3 bolsas de hielo');
INSERT INTO oferta VALUES (2,30,'Botella de Ron y 1 bolsa de hielos');
INSERT INTO oferta VALUES (3,40,'Descuento en puestos REDBULL, 5 bolsas de hielo y camiseta');
INSERT INTO oferta VALUES (4,10,'Camiseta festival');
INSERT INTO oferta VALUES (5,15,'Una carga de movil y una camiseta');
INSERT INTO oferta VALUES (6,25,'Camiseta festival');

INSERT INTO recinto VALUES (1,50000,200,4,2,1);
INSERT INTO recinto VALUES (2,40000,100,0,1,1);
INSERT INTO recinto VALUES (3,30000,50,0,0,1);
INSERT INTO recinto VALUES (4,45000,220,4,2,1);
INSERT INTO recinto VALUES (5,35000,150,0,1,1);
INSERT INTO recinto VALUES (6,20000,100,0,0,1);
INSERT INTO recinto VALUES (7,60000,180,4,2,1);
INSERT INTO recinto VALUES (8,60000,120,0,1,1);

INSERT INTO puesto VALUES (1,1000,1,0,1);
INSERT INTO puesto VALUES (2,2000,0,1,1);
INSERT INTO puesto VALUES (3,3500,2,2,1);
INSERT INTO puesto VALUES (4,1500,1,0,1);
INSERT INTO puesto VALUES (5,2200,0,1,1);
INSERT INTO puesto VALUES (6,3600,2,2,1);
INSERT INTO puesto VALUES (7,1450,1,0,1);
INSERT INTO puesto VALUES (8,2250,0,1,1);
INSERT INTO puesto VALUES (9,3650,2,2,1);
INSERT INTO puesto VALUES (10,980,1,0,1);
INSERT INTO puesto VALUES (11,2050,0,1,1);
INSERT INTO puesto VALUES (12,3520,2,2,1);

INSERT INTO concierto VALUES (1,'2021-08-20','2021-08-20 17:00','2021-08-20 18:00',1,2);
INSERT INTO concierto VALUES (2,'2021-09-15','2021-09-15 16:00','2021-09-15 17:00',5,2);
INSERT INTO concierto VALUES (3,'2021-10-01','2021-10-01 18:00','2021-10-01 20:00',6,2);
INSERT INTO concierto VALUES (4,'2021-12-07','2021-12-07 22:00','2021-12-07 23:30',10,2);
INSERT INTO concierto VALUES (5,'2021-11-03','2021-11-03 12:00','2021-11-03 14:00',7,2);

INSERT INTO valoracion VALUES (1,'Muy bueno, me encantó.',4,1);
INSERT INTO valoracion VALUES (2,'Fatal, la limpieza sobre todo.',1,1);
INSERT INTO valoracion VALUES (3,'Genial.',3,1);


