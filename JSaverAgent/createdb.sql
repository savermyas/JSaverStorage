DROP TABLE data;
DROP TABLE files;
CREATE TABLE files (
    filename VARCHAR(255) NOT NULL,
    file_id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
);
CREATE TABLE data (
    data VARCHAR(255),
    file_id INTEGER,
    fragment_id INTEGER    
);
ALTER TABLE data 
    ADD CONSTRAINT data_fk 
        FOREIGN KEY(file_id) 
        REFERENCES files 
        ON DELETE CASCADE;
ALTER TABLE files 
    ADD CONSTRAINT filename_uk 
        UNIQUE(filename);
