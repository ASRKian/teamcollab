CREATE TABLE users (
    id BIGINT AUTO_INCREMENT NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'MANAGER', 'MEMBER') DEFAULT 'MEMBER',
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE teams (
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    admin_id BIGINT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id),
    CONSTRAINT teams_users_id_fk Foreign Key (admin_id) REFERENCES users (id)
);

CREATE TABLE projects (
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    team_id BIGINT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id),
    CONSTRAINT projects_teams_id_fk Foreign Key (team_id) REFERENCES teams (id)
);

CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT NOT NULL,
    title VARCHAR(255),
    description VARCHAR(255),
    status ENUM('TODO', 'IN_PROGRESS', 'DONE'),
    project_id BIGINT NOT NULL,
    assigned_to BIGINT NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (Id),
    CONSTRAINT tasks_projects_id_fk Foreign Key (project_id) REFERENCES projects (id),
    CONSTRAINT tasks_users_id_fk Foreign Key (assigned_to) REFERENCES users (id)
);

CREATE TABLE messages (
    id BIGINT AUTO_INCREMENT NOT NULL,
    content VARCHAR(255) NOT NULL,
    sender_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id),
    CONSTRAINT messages_senders_id_fk Foreign Key (sender_id) REFERENCES users (id),
    CONSTRAINT messages_teams_id_fk Foreign Key (team_id) REFERENCES teams (id)
);

ALTER TABLE users
ADD COLUMN team_id BIGINT,
ADD CONSTRAINT users_teams_id_fk Foreign Key (team_id) REFERENCES teams (id);