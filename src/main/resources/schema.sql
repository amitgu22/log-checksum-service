DROP TABLE IF EXISTS STUDENT;
CREATE TABLE STUDENT (
                               id INT NOT NULL,
                               name VARCHAR(50) NOT NULL,
                               roll_number VARCHAR(20) NOT NULL

);

DROP TABLE IF EXISTS EVENT;
CREATE TABLE EVENT (
                         id VARCHAR(50) NOT NULL,
                         host VARCHAR(50) ,
                         type VARCHAR(20) ,
                         duration BIGINT NOT NULL,
                         alert VARCHAR(20) NOT NULL


);

