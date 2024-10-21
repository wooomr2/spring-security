-- Table: public.tb_user

-- DROP TABLE IF EXISTS public.tb_user;

CREATE TABLE IF NOT EXISTS public.tb_user
(
    is_deleted boolean NOT NULL,
    created_at timestamp(6) without time zone NOT NULL,
    updated_at timestamp(6) without time zone NOT NULL,
    user_id bigint NOT NULL,
    created_by character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    updated_by character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tb_user_pkey PRIMARY KEY (user_id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_user
    OWNER to postgres;