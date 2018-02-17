DROP TABLE "refs";
DROP TABLE "params";
DROP TABLE "attr_binds";
DROP TABLE "grants";
DROP TABLE "roles";
DROP TABLE "objects";
DROP TABLE "attrs";
DROP TABLE "attr_types";
DROP TABLE "types";

--Creating base metamodel tables
CREATE TABLE "types" (
  "type_id"        NUMBER(20, 0) NOT NULL,
  "type_name"      VARCHAR2(100) NOT NULL,
  "type_desc"      VARCHAR2(1000),
  "parent_type_id" NUMBER(20, 0) DEFAULT 0,
  CONSTRAINT "types_pk" PRIMARY KEY ("type_id"),
  CONSTRAINT "types_types_fk" FOREIGN KEY ("parent_type_id") REFERENCES "types"
);

CREATE TABLE "attr_types" (
  "attr_type_id"   NUMBER(20, 0) NOT NULL,
  "attr_type_name" VARCHAR2(100) NOT NULL,
  CONSTRAINT "attr_types_pk" PRIMARY KEY ("attr_type_id")
);

CREATE TABLE "attrs" (
  "attr_id"      NUMBER(20, 0) NOT NULL,
  "attr_type_id" NUMBER(20, 0) NOT NULL,
  "attr_name"    VARCHAR2(100) NOT NULL,
  "multiple"     NUMBER(1, 0),
  CONSTRAINT "attrs_pk" PRIMARY KEY ("attr_id"),
  CONSTRAINT "attrs_attr_types_fk" FOREIGN KEY ("attr_type_id") REFERENCES "attr_types"
);

CREATE TABLE "attr_binds" (
  "type_id" NUMBER(20, 0) NOT NULL,
  "attr_id" NUMBER(20, 0) NOT NULL,
  CONSTRAINT "attr_bins_pk" PRIMARY KEY ("type_id", "attr_id"),
  CONSTRAINT "attr_binds_attrs_fk" FOREIGN KEY ("attr_id") REFERENCES "attrs",
  CONSTRAINT "attr_binds_types_fk" FOREIGN KEY ("type_id") REFERENCES "types"
);

CREATE TABLE "objects" (
  "object_id"        NUMBER(20, 0) NOT NULL,
  "object_version"   NUMBER(20, 0) DEFAULT 1,
  "type_id"          NUMBER(20, 0) NOT NULL,
  "object_name"      VARCHAR2(100) NOT NULL,
  "object_desc"      VARCHAR2(1000),
  "parent_object_id" NUMBER(20, 0) DEFAULT 0,
  CONSTRAINT "objects_pk" PRIMARY KEY ("object_id"),
  CONSTRAINT "objects_types_fk" FOREIGN KEY ("type_id") REFERENCES "types",
  CONSTRAINT "objects_objects_fk" FOREIGN KEY ("parent_object_id") REFERENCES "objects"
);

CREATE TABLE "params" (
  "object_id"   NUMBER(20, 0) NOT NULL,
  "attr_id"     NUMBER(20, 0) NOT NULL,
  "number_val"  NUMBER,
  "text_val"    VARCHAR2(1000),
  "date_val"    TIMESTAMP(9),
  "interval_val" INTERVAL DAY TO SECOND,
  "boolean_val" NUMBER(1, 0),
  CONSTRAINT "params_pk" PRIMARY KEY ("object_id", "attr_id"),
  CONSTRAINT "params_objects_fk" FOREIGN KEY ("object_id") REFERENCES "objects",
  CONSTRAINT "params_attrs_fk" FOREIGN KEY ("attr_id") REFERENCES "attrs"
);

CREATE TABLE "refs" (
  "object_id"     NUMBER(20, 0) NOT NULL,
  "attr_id"       NUMBER(20, 0) NOT NULL,
  "ref_object_id" NUMBER(20, 0) NOT NULL,
  CONSTRAINT "refs_pk" PRIMARY KEY ("object_id", "attr_id", "ref_object_id"),
  CONSTRAINT "refs_attrs_fk" FOREIGN KEY ("attr_id") REFERENCES "attrs",
  CONSTRAINT "refs_objects_fk_1" FOREIGN KEY ("object_id") REFERENCES "objects",
  CONSTRAINT "refs_objects_fk_2" FOREIGN KEY ("ref_object_id") REFERENCES "objects"
);

CREATE TABLE "roles" (
  "role_id"   NUMBER(20, 0) NOT NULL,
  "role_name" VARCHAR2(100) NOT NULL,
  "role_desc" VARCHAR2(1000),
  CONSTRAINT "roles_pk" PRIMARY KEY ("role_id")
);

CREATE TABLE "grants" (
  "role_id"   NUMBER(20, 0) NOT NULL,
  "type_id"   NUMBER(20, 0),
  "object_id" NUMBER(20, 0),
  "read"      NUMBER(1, 0)  NOT NULL,
  "write"     NUMBER(1, 0)  NOT NULL,
  CONSTRAINT "grants_pk" PRIMARY KEY ("role_id", "type_id", "object_id"),
  CONSTRAINT "grants_roles_fk" FOREIGN KEY ("role_id") REFERENCES "roles",
  CONSTRAINT "grants_types_fk" FOREIGN KEY ("type_id") REFERENCES "types",
  CONSTRAINT "grants_objects_fk" FOREIGN KEY ("object_id") REFERENCES "objects"
);

--Inserting attribute types values
INSERT INTO "attr_types" ("attr_type_id", "attr_type_name") VALUES
  (1, 'REF');

INSERT INTO "attr_types" ("attr_type_id", "attr_type_name") VALUES
  (2, 'NUMBER');

INSERT INTO "attr_types" ("attr_type_id", "attr_type_name") VALUES
  (3, 'TEXT');

INSERT INTO "attr_types" ("attr_type_id", "attr_type_name") VALUES
  (4, 'DATE');

INSERT INTO "attr_types" ("attr_type_id", "attr_type_name") VALUES
  (5, 'BOOLEAN');

--Inserting attributes
INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (1, 3, 'bio', 0);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (2, 1, 'roles', 1);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (3, 1, 'qualification', 0);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (4, 4, 'start_date', 0);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (5, 4, 'end_date', 0);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (6, 5, 'active', 0);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (7, 1, 'manager', 0);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (8, 1, 'customer', 0);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (9, 1, 'project', 0);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (10, 1, 'prev_sprint', 0);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (11, 1, 'sprint', 0);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (12, 4, 'estimate', 0);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (13, 4, 'actual', 0);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (14, 4, 'overtime', 0);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (15, 1, 'subtasks', 1);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (16, 1, 'prev_tasks', 1);

INSERT INTO "attrs" ("attr_id", "attr_type_id", "attr_name", "multiple") VALUES
  (17, 1, 'employee', 1);

--Inserting root type
INSERT INTO "types" ("type_id", "type_name", "type_desc", "parent_type_id") VALUES
  (0, 'Root', 'Root of all types for grants', NULL);

--Inserting metainformation for role
INSERT INTO "types" ("type_id", "type_name", "type_desc") VALUES
  (1, 'Role', 'User role. Give access to certain types, objects and attributes');

--Inserting metainformation for qualification
INSERT INTO "types" ("type_id", "type_name", "type_desc") VALUES
  (2, 'Qualification', 'Employee''s qualification');

--Inserting metainformation for user
INSERT INTO "types" ("type_id", "type_name", "type_desc") VALUES
  (3, 'User', 'Has different grants for access to certain types, objects and attributes');

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (3, 1);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (3, 2);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (3, 3);

--Inserting metainformation for project
INSERT INTO "types" ("type_id", "type_name", "type_desc") VALUES
  (4, 'Project', 'Comprises sprints, has manager and customer');

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (4, 4);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (4, 5);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (4, 6);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (4, 7);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (4, 8);

--Inserting metainformation for sprint
INSERT INTO "types" ("type_id", "type_name", "type_desc", "parent_type_id") VALUES
  (5, 'Sprint', 'Comprises tasks, may have previous sprint', 4);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (5, 6);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (5, 9);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (5, 10);

--Inserting metainformation for task
INSERT INTO "types" ("type_id", "type_name", "type_desc", "parent_type_id") VALUES
  (6, 'Task', 'May comprise subtasks, may depend on some tasks.
    Has estimate, actual and overtime', 5);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (6, 3);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (6, 6);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (6, 11);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (6, 12);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (6, 13);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (6, 14);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (6, 15);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (6, 16);

INSERT INTO "attr_binds" ("type_id", "attr_id") VALUES
  (6, 17);

--Inserting root object
INSERT INTO "objects" ("object_id", "object_version", "type_id", "object_name", "object_desc", "parent_object_id")
VALUES
  (0, NULL, 0, 'Root', 'Root of all objects for grants', NULL);

--Inserting qualification constants
INSERT INTO "objects" ("object_id", "object_version", "type_id", "object_name", "object_desc", "parent_object_id")
VALUES
  (2, NULL, 2, 'JUNIOR', 'The lowest qualification', 0);

INSERT INTO "objects" ("object_id", "object_version", "type_id", "object_name", "object_desc", "parent_object_id")
VALUES
  (3, NULL, 2, 'MIDDLE', 'Middle qualification', 0);

INSERT INTO "objects" ("object_id", "object_version", "type_id", "object_name", "object_desc", "parent_object_id")
VALUES
  (4, NULL, 2, 'SENIOR', 'The highest qualification', 0);

--Inserting admin role and admin grants
INSERT INTO "roles" ("role_id", "role_name", "role_desc") VALUES
  (0, 'ADMIN', 'Role which has access to any type and object');

INSERT INTO "objects" ("object_id", "object_version", "type_id", "object_name", "object_desc", "parent_object_id")
VALUES
  (1, NULL, 1, 'ADMIN', 'Role which has access to any type and object', 0);

--Inserting test roles
INSERT INTO "roles" ("role_id", "role_name", "role_desc") VALUES
  (11, 'TEST 1', 'Test role 1');

INSERT INTO "objects" ("object_id", "object_version", "type_id", "object_name", "object_desc", "parent_object_id")
VALUES
  (11, NULL, 1, 'TEST 1', 'Test role 1', 0);

INSERT INTO "roles" ("role_id", "role_name", "role_desc") VALUES
  (12, 'TEST 2', 'Test role 2');

INSERT INTO "objects" ("object_id", "object_version", "type_id", "object_name", "object_desc", "parent_object_id")
VALUES
  (12, NULL, 1, 'TEST 2', 'Test role 2', 0);

INSERT INTO "roles" ("role_id", "role_name", "role_desc") VALUES
  (13, 'TEST 3', 'Test role 3');

INSERT INTO "objects" ("object_id", "object_version", "type_id", "object_name", "object_desc", "parent_object_id")
VALUES
  (13, NULL, 1, 'TEST 3', 'Test role 3', 0);