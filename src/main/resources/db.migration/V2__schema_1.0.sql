DROP TABLE refs;
DROP TABLE params;
DROP TABLE objects;
DROP TABLE attrs;
DROP TABLE types;

CREATE TABLE types (
  type_id        NUMBER(20, 0)  NOT NULL,
  type_name      VARCHAR2(100)  NOT NULL,
  type_desc      VARCHAR2(1000) NULL,
  parent_type_id NUMBER(20, 0)  NULL
);

ALTER TABLE TYPES
  ADD (PRIMARY KEY (type_id));

CREATE TABLE objects (
  object_id        NUMBER(20, 0)  NOT NULL,
  type_id          NUMBER(20, 0)  NOT NULL,
  object_name      VARCHAR2(100)  NOT NULL,
  object_desc      VARCHAR2(1000) NULL,
  parent_object_id NUMBER(20, 2)  NULL
);

ALTER TABLE objects
  ADD (PRIMARY KEY (object_id));

ALTER TABLE objects
  ADD (FOREIGN KEY (type_id)
REFERENCES TYPES (type_id)
ON DELETE CASCADE);

CREATE TABLE attrs (
  attr_id     NUMBER(20, 0) NOT NULL,
  attr_name   VARCHAR2(100) NOT NULL,
  type_id     NUMBER(20, 0) NOT NULL,
  is_multiple NUMBER(1, 0)  NOT NULL
);

ALTER TABLE attrs
  ADD (PRIMARY KEY (attr_id));

ALTER TABLE attrs
  ADD (FOREIGN KEY (type_id)
REFERENCES TYPES (type_id)
ON DELETE CASCADE);

CREATE TABLE params (
  object_id  NUMBER(20, 0)  NOT NULL,
  attr_id    NUMBER(20, 0)  NOT NULL,
  text_val   VARCHAR2(1000) NULL,
  number_val NUMBER(20, 0)  NULL,
  date_val   TIMESTAMP(0)   NULL
);

ALTER TABLE params
  ADD (PRIMARY KEY (object_id, attr_id));

ALTER TABLE params
  ADD (FOREIGN KEY (object_id)
REFERENCES objects (object_id)
ON DELETE CASCADE);

ALTER TABLE params
  ADD (FOREIGN KEY (attr_id)
REFERENCES attrs (attr_id)
ON DELETE CASCADE);

CREATE TABLE refs (
  ref_object_id NUMBER(20, 0) NOT NULL,
  object_id     NUMBER(20, 0) NOT NULL,
  attr_id       NUMBER(20, 0) NOT NULL
);

ALTER TABLE refs
  ADD (PRIMARY KEY (ref_object_id));

ALTER TABLE refs
  ADD (FOREIGN KEY (object_id)
REFERENCES objects (object_id)
ON DELETE CASCADE);

ALTER TABLE refs
  ADD ( FOREIGN KEY (attr_id)
REFERENCES attrs (attr_id)
ON DELETE CASCADE);