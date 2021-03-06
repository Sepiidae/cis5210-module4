CREATE table APP.PATIENT (
    ID          INTEGER NOT NULL 
  PRIMARY KEY GENERATED ALWAYS AS IDENTITY 
  (START WITH 1, INCREMENT BY 1),
    LASTNAME    VARCHAR(30), 
    FIRSTNAME   VARCHAR(30),
    MIDDLENAME  VARCHAR(30),
    PHONE       VARCHAR(20),
    EMAIL       VARCHAR(30), 
    ADDRESS1    VARCHAR(30),
    ADDRESS2    VARCHAR(30),
    CITY        VARCHAR(30),
    STATE       VARCHAR(30),
    POSTALCODE  VARCHAR(20),
    COUNTRY     VARCHAR(30),
    DOB         DATE,
    GENDER_AT_BIRTH VARCHAR(1), -- M/F
    GENDER_IDENTITY VARCHAR(30),
    PURGED      INT, -- ORACLE DOESN'T SUPPORT BOOLEAN, SO OUT OF HABIT USE INT
    CLOSED      INT,
    CREATED_AT  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    
    
    
    )