-- Table: public.tb_user_role

-- DROP TABLE IF EXISTS public.tb_user_role;

CREATE TABLE IF NOT EXISTS public.tb_user_role
(
    role_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT tb_user_role_pkey PRIMARY KEY (role_id, user_id),
    CONSTRAINT fk7vn3h53d0tqdimm8cp45gc0kl FOREIGN KEY (user_id)
    REFERENCES public.tb_user (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fkea2ootw6b6bb0xt3ptl28bymv FOREIGN KEY (role_id)
    REFERENCES public.tb_role (role_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_user_role
    OWNER to postgres;