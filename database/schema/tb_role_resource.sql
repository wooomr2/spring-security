-- Table: public.tb_role_resource

-- DROP TABLE IF EXISTS public.tb_role_resource;

CREATE TABLE IF NOT EXISTS public.tb_role_resource
(
    resource_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT tb_role_resource_pkey PRIMARY KEY (resource_id, role_id),
    CONSTRAINT fk7ffc7h6obqxflhj1aq1mk20jk FOREIGN KEY (role_id)
    REFERENCES public.tb_role (role_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fk868kc8iic48ilv5npa80ut6qo FOREIGN KEY (resource_id)
    REFERENCES public.tb_resource (resource_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_role_resource
    OWNER to postgres;

--
insert into tb_role_resource (role_id, resource_id) values (1, 1);
insert into tb_role_resource (role_id, resource_id) values (2, 2);
insert into tb_role_resource (role_id, resource_id) values (3, 3);
insert into tb_role_resource (role_id, resource_id) values (4, 4);
insert into tb_role_resource (role_id, resource_id) values (5, 5);
insert into tb_role_resource (role_id, resource_id) values (6, 6);
