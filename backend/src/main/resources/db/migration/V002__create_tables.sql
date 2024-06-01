CREATE TABLE repo
(
    id INT NOT NULL AUTO_INCREMENT,
    commit_time TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE code
(
    id INT NOT NULL AUTO_INCREMENT,
    repo_id INT,
    code TEXT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE execution_result
(
    id INT NOT NULL AUTO_INCREMENT,
    code_id INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    runtime BIGINT,
    memory BIGINT,
    emission FLOAT,
    PRIMARY KEY (id),
    FOREIGN KEY (code_id) REFERENCES Code (id)
);

CREATE TABLE code_match
(
    id INT NOT NULL AUTO_INCREMENT,
    before_id INT NOT NULL,
    after_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (before_id) REFERENCES Code (id),
    FOREIGN KEY (after_id) REFERENCES Code (id)
);
