-- Table: public.tb_role

-- DROP TABLE IF EXISTS public.tb_role;

CREATE TABLE IF NOT EXISTS public.tb_role
(
    role_id bigint NOT NULL,
    is_expression character varying(255) COLLATE pg_catalog."default",
    role_desc character varying(255) COLLATE pg_catalog."default",
    role_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tb_role_pkey PRIMARY KEY (role_id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_role
    OWNER to postgres;

--
insert into tb_role (role_id, is_expression, role_desc, role_name) values (1, null, 'admin', 'ROLE_ADMIN');
insert into tb_role (role_id, is_expression, role_desc, role_name) values (2, null, 'manager', 'ROLE_MANAGER');
insert into tb_role (role_id, is_expression, role_desc, role_name) values (3, null, 'user', 'ROLE_USER');
insert into tb_role (role_id, is_expression, role_desc, role_name) values (4, null, 'store', 'ROLE_STORE');
insert into tb_role (role_id, is_expression, role_desc, role_name) values (5, null, 'resident', 'ROLE_RESIDENT');
insert into tb_role (role_id, is_expression, role_desc, role_name) values (6, null, 'visitor', 'ROLE_VISITOR');