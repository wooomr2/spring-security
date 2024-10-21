-- Table: public.tb_refresh_token

-- DROP TABLE IF EXISTS public.tb_refresh_token;

CREATE TABLE IF NOT EXISTS public.tb_refresh_token
(
    expired boolean NOT NULL,
    revoked boolean NOT NULL,
    refresh_token_id bigint NOT NULL,
    user_id bigint NOT NULL,
    refresh_token character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tb_refresh_token_pkey PRIMARY KEY (refresh_token_id),
    CONSTRAINT tb_refresh_token_refresh_token_key UNIQUE (refresh_token)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_refresh_token
    OWNER to postgres;