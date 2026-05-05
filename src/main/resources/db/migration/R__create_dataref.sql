CREATE TABLE dataref (
    id SERIAL PRIMARY KEY,
    parentCode VARCHAR(20),
    code VARCHAR(20) NOT NULL,
    description VARCHAR(255) NOT NULL,
    nameEn VARCHAR(255) NOT NULL,
    nameKh VARCHAR(255) NOT NULL,
    isActivate BOOLEAN NOT NULL,
    createBy VARCHAR(255) NOT NULL,
    createDate DATE NOT NULL
);