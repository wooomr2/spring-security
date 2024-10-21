-- Table: public.tb_role_hierarchy

-- DROP TABLE IF EXISTS public.tb_role_hierarchy;

CREATE TABLE IF NOT EXISTS public.tb_role_hierarchy
(
    id bigint NOT NULL,
    parent_id bigint,
    role_name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tb_role_hierarchy_pkey PRIMARY KEY (id),
    CONSTRAINT fkisndjfv65rn54gu0bor24npwa FOREIGN KEY (parent_id)
    REFERENCES public.tb_role_hierarchy (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_role_hierarchy
    OWNER to postgres;

insert into tb_role_hierarchy (id, role_name, parent_id) values (1, 'ROLE_ADMIN', null);
insert into tb_role_hierarchy (id, role_name, parent_id) values (2, 'ROLE_MANAGER', 1);
insert into tb_role_hierarchy (id, role_name, parent_id) values (3, 'ROLE_USER', 2);
insert into tb_role_hierarchy (id, role_name, parent_id) values (4, 'ROLE_STORE', 3);
insert into tb_role_hierarchy (id, role_name, parent_id) values (5, 'ROLE_RESIDENT', 3);
insert into tb_role_hierarchy (id, role_name, parent_id) values (5, 'ROLE_VISITOR', 3);

