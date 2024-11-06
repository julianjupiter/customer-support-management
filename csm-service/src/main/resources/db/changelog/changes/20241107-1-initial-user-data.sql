--liquibase formatted sql
--changeset julianjupiter:20241107-1 splitStatements:true endDelimiter:; context:dev,sit,uat,prod
INSERT INTO user(id, username, email, password, first_name, last_name, created_by)
VALUES ('019303ad-c1d0-7dea-8508-ac976d91bd80', 'julez', 'julez@gmail.com', '{bcrypt}$2a$10$JDRMW8NObKWmKJ/F/O7SRO/E6geQulRGyRUgcui4jZp1Y9BqedyCK', 'Julian', 'Jupiter', 'julez');
INSERT INTO user_role(id, user_id, role_id) VALUES('019303b5-eb03-786b-9d29-3a4420108797', '019303ad-c1d0-7dea-8508-ac976d91bd80', 1);