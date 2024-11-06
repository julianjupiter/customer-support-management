--liquibase formatted sql
--changeset julianjupiter:20241102-1 splitStatements:true endDelimiter:; context:dev,sit,uat,prod
CREATE TABLE IF NOT EXISTS role (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_role_name UNIQUE (name)
);
INSERT INTO role (id, name, description) VALUES (1, 'ROLE_ADMIN', 'Administrator');
INSERT INTO role (id, name, description) VALUES (2, 'ROLE_AGENT', 'Agent');
CREATE TABLE IF NOT EXISTS user (
    id VARCHAR(36) NOT NULL,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255) NULL,
    last_name VARCHAR(255) NOT NULL,
    extension_name VARCHAR(255) NULL,
    mobile_number VARCHAR(255) NULL,
    account_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,
    credentials_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    mobile_number_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_by VARCHAR(255) NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NULL,
    updated_at datetime NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_user_username UNIQUE (username),
    CONSTRAINT uq_user_email UNIQUE (email)
);
CREATE TABLE IF NOT EXISTS user_role (
    id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    role_id INT NOT NULL,
    created_by VARCHAR(255) NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NULL,
    updated_at datetime NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_user_role_user_id_role_id UNIQUE (user_id, role_id),
    CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES role (id)
);
CREATE TABLE IF NOT EXISTS customer (
    id VARCHAR(36) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255) NULL,
    last_name VARCHAR(255) NOT NULL,
    extension_name VARCHAR(255) NULL,
    date_of_birth DATE NOT NULL,
    mobile_number VARCHAR(255) NOT NULL,
    email_address VARCHAR(255) NULL,
    street VARCHAR(255) NOT NULL,
    barangay VARCHAR(255) NOT NULL,
    city_or_municipality VARCHAR(255) NOT NULL,
    province VARCHAR(255) NOT NULL,
    zip_code INT NOT NULL,
    created_by VARCHAR(255) NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NULL,
    updated_at datetime NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_customer_email_address UNIQUE (email_address)
);
CREATE TABLE IF NOT EXISTS status (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_status_name UNIQUE (name)
  );
INSERT INTO status (id, name, description) VALUES (1, 'Open', 'Open');
INSERT INTO status (id, name, description) VALUES (2, 'In Progress', 'In Progress');
INSERT INTO status (id, name, description) VALUES (3, 'Resolved', 'Resolved');
INSERT INTO status (id, name, description) VALUES (4, 'Closed', 'Closed');
CREATE TABLE IF NOT EXISTS priority (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT uq_priority_name UNIQUE (name)
);
INSERT INTO priority (id, name, description) VALUES (1, 'Low', 'Low');
INSERT INTO priority (id, name, description) VALUES (2, 'Medium', 'Medium');
INSERT INTO priority (id, name, description) VALUES (3, 'High', 'High');
CREATE TABLE IF NOT EXISTS ticket (
    id VARCHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    customer_id VARCHAR(36) NOT NULL,
    priority_id INT NOT NULL DEFAULT 1,
    status_id INT NOT NULL DEFAULT 1,
    agent_id VARCHAR(36) NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NULL,
    updated_at datetime NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_ticket_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id),
    CONSTRAINT fk_ticket_priority_id FOREIGN KEY (priority_id) REFERENCES priority (id),
    CONSTRAINT fk_ticket_status_id FOREIGN KEY (status_id) REFERENCES status (id),
    CONSTRAINT fk_ticket_agent_id FOREIGN KEY (agent_id) REFERENCES user (id)
);
CREATE TABLE IF NOT EXISTS comment (
    id VARCHAR(36) NOT NULL,
    ticket_id VARCHAR(36) NOT NULL,
    content TEXT,
    created_by VARCHAR(255) NOT NULL,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NULL,
    updated_at datetime NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_ticket_comment_ticket_id FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);