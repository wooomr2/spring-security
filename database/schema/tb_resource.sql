-- Table: public.tb_resource

-- DROP TABLE IF EXISTS public.tb_resource;

CREATE TABLE IF NOT EXISTS public.tb_resource
(
    resource_id bigint NOT NULL,
    order_num integer,
    http_method character varying(255) COLLATE pg_catalog."default",
    resource_name character varying(255) COLLATE pg_catalog."default",
    resource_type character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tb_resource_pkey PRIMARY KEY (resource_id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_resource
    OWNER to postgres;



insert into tb_resource (resource_id, order_num, http_method, resource_name, resource_type) values (1, 1, null, '/api/v1/admin/**', 'url');
insert into tb_resource (resource_id, order_num, http_method, resource_name, resource_type) values (2, 1, null, '/api/v1/manager/**', 'url');
insert into tb_resource (resource_id, order_num, http_method, resource_name, resource_type) values (6, 1, null, '/api/v1/user/**', 'url');
insert into tb_resource (resource_id, order_num, http_method, resource_name, resource_type) values (3, 1, null, '/api/v1/store/**', 'url');
insert into tb_resource (resource_id, order_num, http_method, resource_name, resource_type) values (4, 1, null, '/api/v1/resident/**', 'url');
insert into tb_resource (resource_id, order_num, http_method, resource_name, resource_type) values (5, 1, null, '/api/v1/visitor/**', 'url');
