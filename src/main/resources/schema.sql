

DROP TABLE IF EXISTS EVENT;
CREATE TABLE EVENT (
                         id VARCHAR(50) NOT NULL,
                         host VARCHAR(50) ,
                         type VARCHAR(20) ,
                         duration BIGINT NOT NULL,
                         alert VARCHAR(20) NOT NULL


);

