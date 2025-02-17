-- Table: public.transaction

-- DROP TABLE IF EXISTS public.transaction;

CREATE TYPE transaction_type_enum AS ENUM ('CREDIT', 'DEBIT');

CREATE TABLE IF NOT EXISTS public.transaction
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    amount double precision,
    date_time timestamp(6) without time zone,
    transaction_type transaction_type_enum NOT NULL,
    user_id bigint,
    CONSTRAINT transaction_pkey PRIMARY KEY (id)
    )

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.transaction
    OWNER to postgres;