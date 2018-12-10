DROP TABLE public.customers;

CREATE TABLE public.customers
(
    id bigint NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default",
    last_name character varying(255) COLLATE pg_catalog."default",
    record_current_date timestamp without time zone,
    CONSTRAINT customers_pkey PRIMARY KEY (id)
);