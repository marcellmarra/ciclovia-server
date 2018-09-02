-- INSERT STATUS --
INSERT INTO STATUS (ID, NAME, PIN_NAME, DESCRIPTION) VALUES (1, 'Ciclovia não esta bem', 'pin-01.png', 'Description');
INSERT INTO STATUS (ID, NAME, PIN_NAME, DESCRIPTION) VALUES (2, 'Manutenção sendo realizada', 'pin-02.png', 'Description 2');
INSERT INTO STATUS (ID, NAME, PIN_NAME, DESCRIPTION) VALUES (3, 'Manutenção sendo realizada 2', 'pin-02.png', 'Description 2');


-- INSERT TYPE --
INSERT INTO OCCURRENCE_TYPE (ID, NAME) VALUES (1, 'Pintura Apagada');
INSERT INTO OCCURRENCE_TYPE (ID, NAME) VALUES (2, 'Tachão solto');
INSERT INTO OCCURRENCE_TYPE (ID, NAME) VALUES (3, 'Buraco');


INSERT INTO SYS_USER(ID, NAME, PASSWORD, ROLE) VALUES (1, 'Penny', 'password', 'admin')