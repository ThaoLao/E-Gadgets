--
-- PostgreSQL database dump
--

-- Dumped from database version 16.8 (Debian 16.8-1.pgdg120+1)
-- Dumped by pg_dump version 17.0

-- Started on 2026-04-04 08:49:39

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3480 (class 1262 OID 24578)
-- Name: giadung_shop; Type: DATABASE; Schema: -; Owner: linhnd1899
--

CREATE DATABASE giadung_shop WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';


ALTER DATABASE giadung_shop OWNER TO linhnd1899;

\connect giadung_shop

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3481 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 24580)
-- Name: blogs; Type: TABLE; Schema: public; Owner: linhnd1899
--

CREATE TABLE public.blogs (
    id bigint NOT NULL,
    active_flag boolean,
    create_date timestamp(6) without time zone,
    created_by character varying(255),
    delete_flag boolean,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by character varying(255),
    content text,
    summary text,
    thumbnail character varying(255),
    title character varying(255),
    user_id bigint
);


ALTER TABLE public.blogs OWNER TO linhnd1899;

--
-- TOC entry 215 (class 1259 OID 24579)
-- Name: blogs_id_seq; Type: SEQUENCE; Schema: public; Owner: linhnd1899
--

CREATE SEQUENCE public.blogs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.blogs_id_seq OWNER TO linhnd1899;

--
-- TOC entry 3482 (class 0 OID 0)
-- Dependencies: 215
-- Name: blogs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: linhnd1899
--

ALTER SEQUENCE public.blogs_id_seq OWNED BY public.blogs.id;


--
-- TOC entry 218 (class 1259 OID 24590)
-- Name: categories; Type: TABLE; Schema: public; Owner: linhnd1899
--

CREATE TABLE public.categories (
    id bigint NOT NULL,
    active_flag boolean,
    create_date timestamp(6) without time zone,
    created_by character varying(255),
    delete_flag boolean,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by character varying(255),
    description oid,
    name character varying(255)
);


ALTER TABLE public.categories OWNER TO linhnd1899;

--
-- TOC entry 217 (class 1259 OID 24589)
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: linhnd1899
--

CREATE SEQUENCE public.categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categories_id_seq OWNER TO linhnd1899;

--
-- TOC entry 3483 (class 0 OID 0)
-- Dependencies: 217
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: linhnd1899
--

ALTER SEQUENCE public.categories_id_seq OWNED BY public.categories.id;


--
-- TOC entry 220 (class 1259 OID 24600)
-- Name: comments; Type: TABLE; Schema: public; Owner: linhnd1899
--

CREATE TABLE public.comments (
    id bigint NOT NULL,
    active_flag boolean,
    create_date timestamp(6) without time zone,
    created_by character varying(255),
    delete_flag boolean,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by character varying(255),
    content text,
    blog_id bigint,
    user_id bigint
);


ALTER TABLE public.comments OWNER TO linhnd1899;

--
-- TOC entry 219 (class 1259 OID 24599)
-- Name: comments_id_seq; Type: SEQUENCE; Schema: public; Owner: linhnd1899
--

CREATE SEQUENCE public.comments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.comments_id_seq OWNER TO linhnd1899;

--
-- TOC entry 3484 (class 0 OID 0)
-- Dependencies: 219
-- Name: comments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: linhnd1899
--

ALTER SEQUENCE public.comments_id_seq OWNED BY public.comments.id;


--
-- TOC entry 222 (class 1259 OID 24610)
-- Name: contacts; Type: TABLE; Schema: public; Owner: linhnd1899
--

CREATE TABLE public.contacts (
    id bigint NOT NULL,
    active_flag boolean,
    create_date timestamp(6) without time zone,
    created_by character varying(255),
    delete_flag boolean,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by character varying(255),
    email character varying(255),
    message oid,
    name character varying(255),
    subject character varying(255)
);


ALTER TABLE public.contacts OWNER TO linhnd1899;

--
-- TOC entry 221 (class 1259 OID 24609)
-- Name: contacts_id_seq; Type: SEQUENCE; Schema: public; Owner: linhnd1899
--

CREATE SEQUENCE public.contacts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.contacts_id_seq OWNER TO linhnd1899;

--
-- TOC entry 3485 (class 0 OID 0)
-- Dependencies: 221
-- Name: contacts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: linhnd1899
--

ALTER SEQUENCE public.contacts_id_seq OWNED BY public.contacts.id;


--
-- TOC entry 223 (class 1259 OID 24619)
-- Name: favorite_items; Type: TABLE; Schema: public; Owner: linhnd1899
--

CREATE TABLE public.favorite_items (
    user_id bigint NOT NULL,
    product_id bigint NOT NULL
);


ALTER TABLE public.favorite_items OWNER TO linhnd1899;

--
-- TOC entry 224 (class 1259 OID 24624)
-- Name: order_details; Type: TABLE; Schema: public; Owner: linhnd1899
--

CREATE TABLE public.order_details (
    order_id bigint NOT NULL,
    product_id bigint NOT NULL,
    price double precision,
    quantity integer
);


ALTER TABLE public.order_details OWNER TO linhnd1899;

--
-- TOC entry 226 (class 1259 OID 24630)
-- Name: orders; Type: TABLE; Schema: public; Owner: linhnd1899
--

CREATE TABLE public.orders (
    id bigint NOT NULL,
    active_flag boolean,
    create_date timestamp(6) without time zone,
    created_by character varying(255),
    delete_flag boolean,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by character varying(255),
    code character varying(255),
    email_address character varying(255),
    payment_method character varying(255),
    phone_number character varying(255),
    reciever character varying(255),
    shipping_address character varying(255),
    status character varying(255),
    total_price double precision,
    vnp_txn_ref character varying(255),
    user_id bigint
);


ALTER TABLE public.orders OWNER TO linhnd1899;

--
-- TOC entry 225 (class 1259 OID 24629)
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: linhnd1899
--

CREATE SEQUENCE public.orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orders_id_seq OWNER TO linhnd1899;

--
-- TOC entry 3486 (class 0 OID 0)
-- Dependencies: 225
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: linhnd1899
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- TOC entry 228 (class 1259 OID 24640)
-- Name: products; Type: TABLE; Schema: public; Owner: linhnd1899
--

CREATE TABLE public.products (
    id bigint NOT NULL,
    active_flag boolean,
    create_date timestamp(6) without time zone,
    created_by character varying(255),
    delete_flag boolean,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by character varying(255),
    buy_count integer,
    cover_image character varying(255),
    description oid NOT NULL,
    extra_data jsonb,
    original_price double precision,
    producer character varying(255),
    published_date timestamp(6) without time zone,
    publisher character varying(255),
    qty integer,
    sale_price double precision,
    title character varying(255),
    total_revenue double precision,
    weight integer,
    category_id bigint,
    promotion_id bigint
);


ALTER TABLE public.products OWNER TO linhnd1899;

--
-- TOC entry 227 (class 1259 OID 24639)
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: linhnd1899
--

CREATE SEQUENCE public.products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.products_id_seq OWNER TO linhnd1899;

--
-- TOC entry 3487 (class 0 OID 0)
-- Dependencies: 227
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: linhnd1899
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- TOC entry 230 (class 1259 OID 24650)
-- Name: promotions; Type: TABLE; Schema: public; Owner: linhnd1899
--

CREATE TABLE public.promotions (
    id bigint NOT NULL,
    active_flag boolean,
    create_date timestamp(6) without time zone,
    created_by character varying(255),
    delete_flag boolean,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by character varying(255),
    description character varying(255),
    discount_percentage double precision,
    end_date timestamp(6) without time zone,
    image character varying(255),
    start_date timestamp(6) without time zone,
    title character varying(255)
);


ALTER TABLE public.promotions OWNER TO linhnd1899;

--
-- TOC entry 229 (class 1259 OID 24649)
-- Name: promotions_id_seq; Type: SEQUENCE; Schema: public; Owner: linhnd1899
--

CREATE SEQUENCE public.promotions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.promotions_id_seq OWNER TO linhnd1899;

--
-- TOC entry 3488 (class 0 OID 0)
-- Dependencies: 229
-- Name: promotions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: linhnd1899
--

ALTER SEQUENCE public.promotions_id_seq OWNED BY public.promotions.id;


--
-- TOC entry 232 (class 1259 OID 24660)
-- Name: roles; Type: TABLE; Schema: public; Owner: linhnd1899
--

CREATE TABLE public.roles (
    id bigint NOT NULL,
    active_flag boolean,
    create_date timestamp(6) without time zone,
    created_by character varying(255),
    delete_flag boolean,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by character varying(255),
    name character varying(255)
);


ALTER TABLE public.roles OWNER TO linhnd1899;

--
-- TOC entry 231 (class 1259 OID 24659)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: linhnd1899
--

CREATE SEQUENCE public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.roles_id_seq OWNER TO linhnd1899;

--
-- TOC entry 3489 (class 0 OID 0)
-- Dependencies: 231
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: linhnd1899
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- TOC entry 234 (class 1259 OID 24670)
-- Name: users; Type: TABLE; Schema: public; Owner: linhnd1899
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    active_flag boolean,
    create_date timestamp(6) without time zone,
    created_by character varying(255),
    delete_flag boolean,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_by character varying(255),
    address character varying(255),
    email character varying(255) NOT NULL,
    full_name character varying(255),
    password character varying(255) NOT NULL,
    phone_number character varying(255),
    status smallint
);


ALTER TABLE public.users OWNER TO linhnd1899;

--
-- TOC entry 233 (class 1259 OID 24669)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: linhnd1899
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_id_seq OWNER TO linhnd1899;

--
-- TOC entry 3490 (class 0 OID 0)
-- Dependencies: 233
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: linhnd1899
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- TOC entry 235 (class 1259 OID 24679)
-- Name: users_roles; Type: TABLE; Schema: public; Owner: linhnd1899
--

CREATE TABLE public.users_roles (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.users_roles OWNER TO linhnd1899;

--
-- TOC entry 3255 (class 2604 OID 24583)
-- Name: blogs id; Type: DEFAULT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.blogs ALTER COLUMN id SET DEFAULT nextval('public.blogs_id_seq'::regclass);


--
-- TOC entry 3257 (class 2604 OID 24593)
-- Name: categories id; Type: DEFAULT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.categories ALTER COLUMN id SET DEFAULT nextval('public.categories_id_seq'::regclass);


--
-- TOC entry 3259 (class 2604 OID 24603)
-- Name: comments id; Type: DEFAULT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.comments ALTER COLUMN id SET DEFAULT nextval('public.comments_id_seq'::regclass);


--
-- TOC entry 3261 (class 2604 OID 24613)
-- Name: contacts id; Type: DEFAULT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.contacts ALTER COLUMN id SET DEFAULT nextval('public.contacts_id_seq'::regclass);


--
-- TOC entry 3263 (class 2604 OID 24633)
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- TOC entry 3265 (class 2604 OID 24643)
-- Name: products id; Type: DEFAULT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- TOC entry 3267 (class 2604 OID 24653)
-- Name: promotions id; Type: DEFAULT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.promotions ALTER COLUMN id SET DEFAULT nextval('public.promotions_id_seq'::regclass);


--
-- TOC entry 3269 (class 2604 OID 24663)
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- TOC entry 3271 (class 2604 OID 24673)
-- Name: users id; Type: DEFAULT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- TOC entry 3455 (class 0 OID 24580)
-- Dependencies: 216
-- Data for Name: blogs; Type: TABLE DATA; Schema: public; Owner: linhnd1899
--



--
-- TOC entry 3457 (class 0 OID 24590)
-- Dependencies: 218
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: linhnd1899
--

INSERT INTO public.categories VALUES (1, true, '2025-10-24 21:26:29.558', NULL, false, '2025-10-24 21:32:10.482', NULL, 24747, 'Quạt, Máy làm mát');
INSERT INTO public.categories VALUES (2, true, '2025-10-24 21:26:29.558', NULL, false, '2025-10-24 21:32:10.482992', NULL, 24747, 'Máy hút bụi');
INSERT INTO public.categories VALUES (3, true, '2025-10-24 21:26:29.558', NULL, false, '2025-10-24 21:32:10.488809', NULL, 24747, 'Nồi cơm điện');
INSERT INTO public.categories VALUES (4, true, '2025-10-24 21:26:29.558', NULL, false, '2025-10-24 21:32:10.491874', NULL, 24747, 'Lò nướng, Bếp nướng');
INSERT INTO public.categories VALUES (5, true, '2025-10-24 21:26:29.558', NULL, false, '2025-10-24 21:32:10.494653', NULL, 24747, 'Đồ dùng nhà bếp');
INSERT INTO public.categories VALUES (6, true, '2025-10-24 21:26:29.558', NULL, false, '2025-10-24 21:32:10.497516', NULL, 24747, 'Sản phẩm khác');


--
-- TOC entry 3459 (class 0 OID 24600)
-- Dependencies: 220
-- Data for Name: comments; Type: TABLE DATA; Schema: public; Owner: linhnd1899
--



--
-- TOC entry 3461 (class 0 OID 24610)
-- Dependencies: 222
-- Data for Name: contacts; Type: TABLE DATA; Schema: public; Owner: linhnd1899
--



--
-- TOC entry 3462 (class 0 OID 24619)
-- Dependencies: 223
-- Data for Name: favorite_items; Type: TABLE DATA; Schema: public; Owner: linhnd1899
--



--
-- TOC entry 3463 (class 0 OID 24624)
-- Dependencies: 224
-- Data for Name: order_details; Type: TABLE DATA; Schema: public; Owner: linhnd1899
--



--
-- TOC entry 3465 (class 0 OID 24630)
-- Dependencies: 226
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: linhnd1899
--



--
-- TOC entry 3467 (class 0 OID 24640)
-- Dependencies: 228
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: linhnd1899
--

INSERT INTO public.products VALUES (1, true, NULL, NULL, false, NULL, NULL, NULL, 'http://res.cloudinary.com/duylinhmedia/image/upload/v1761395572/6bfd2fa0-f2d1-444d-8863-dcdccad3bc47.png', 24755, '{"Cân nặng": "123"}', 123, '123', '2025-10-25 00:00:00', '123', 123, 123, '123', NULL, 123, 2, NULL);


--
-- TOC entry 3469 (class 0 OID 24650)
-- Dependencies: 230
-- Data for Name: promotions; Type: TABLE DATA; Schema: public; Owner: linhnd1899
--



--
-- TOC entry 3471 (class 0 OID 24660)
-- Dependencies: 232
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: linhnd1899
--

INSERT INTO public.roles VALUES (1, true, '2025-10-24 21:16:13.628', NULL, false, NULL, NULL, 'ROLE_USER');
INSERT INTO public.roles VALUES (2, true, '2025-10-24 21:16:13.628', NULL, false, '2025-10-24 21:20:01.472412', NULL, 'ROLE_ADMIN');


--
-- TOC entry 3473 (class 0 OID 24670)
-- Dependencies: 234
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: linhnd1899
--

INSERT INTO public.users VALUES (1, true, '2025-10-24 21:16:13.726', NULL, false, NULL, NULL, 'Ha Noi', 'admin@gmail.com', 'Admin', '$2a$10$oTc5L1rw1qqxzYa1h9NoHu0RfCn1xURZcusD9g1Y2.K.ZaqudHQO.', '0388125368', NULL);
INSERT INTO public.users VALUES (2, true, '2025-10-24 21:16:32.415', NULL, false, NULL, NULL, 'Ha Noi', 'user@gmail.com', 'user', '$2a$10$UzT9CHPBGUOTFsIQk0krPOW.OkZatTp..yUUxoAItF.1mMwp4xAaO', '0388125368', NULL);


--
-- TOC entry 3474 (class 0 OID 24679)
-- Dependencies: 235
-- Data for Name: users_roles; Type: TABLE DATA; Schema: public; Owner: linhnd1899
--

INSERT INTO public.users_roles VALUES (1, 1);
INSERT INTO public.users_roles VALUES (2, 1);
INSERT INTO public.users_roles VALUES (1, 2);


--
-- TOC entry 3491 (class 0 OID 0)
-- Dependencies: 215
-- Name: blogs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: linhnd1899
--

SELECT pg_catalog.setval('public.blogs_id_seq', 1, false);


--
-- TOC entry 3492 (class 0 OID 0)
-- Dependencies: 217
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: linhnd1899
--

SELECT pg_catalog.setval('public.categories_id_seq', 6, true);


--
-- TOC entry 3493 (class 0 OID 0)
-- Dependencies: 219
-- Name: comments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: linhnd1899
--

SELECT pg_catalog.setval('public.comments_id_seq', 1, false);


--
-- TOC entry 3494 (class 0 OID 0)
-- Dependencies: 221
-- Name: contacts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: linhnd1899
--

SELECT pg_catalog.setval('public.contacts_id_seq', 1, false);


--
-- TOC entry 3495 (class 0 OID 0)
-- Dependencies: 225
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: linhnd1899
--

SELECT pg_catalog.setval('public.orders_id_seq', 1, false);


--
-- TOC entry 3496 (class 0 OID 0)
-- Dependencies: 227
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: linhnd1899
--

SELECT pg_catalog.setval('public.products_id_seq', 1, true);


--
-- TOC entry 3497 (class 0 OID 0)
-- Dependencies: 229
-- Name: promotions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: linhnd1899
--

SELECT pg_catalog.setval('public.promotions_id_seq', 1, false);


--
-- TOC entry 3498 (class 0 OID 0)
-- Dependencies: 231
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: linhnd1899
--

SELECT pg_catalog.setval('public.roles_id_seq', 2, true);


--
-- TOC entry 3499 (class 0 OID 0)
-- Dependencies: 233
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: linhnd1899
--

SELECT pg_catalog.setval('public.users_id_seq', 2, true);


--
-- TOC entry 3274 (class 2606 OID 24588)
-- Name: blogs blogs_pkey; Type: CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.blogs
    ADD CONSTRAINT blogs_pkey PRIMARY KEY (id);


--
-- TOC entry 3276 (class 2606 OID 24598)
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- TOC entry 3278 (class 2606 OID 24608)
-- Name: comments comments_pkey; Type: CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT comments_pkey PRIMARY KEY (id);


--
-- TOC entry 3280 (class 2606 OID 24618)
-- Name: contacts contacts_pkey; Type: CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT contacts_pkey PRIMARY KEY (id);


--
-- TOC entry 3282 (class 2606 OID 24623)
-- Name: favorite_items favorite_items_pkey; Type: CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.favorite_items
    ADD CONSTRAINT favorite_items_pkey PRIMARY KEY (user_id, product_id);


--
-- TOC entry 3284 (class 2606 OID 24628)
-- Name: order_details order_details_pkey; Type: CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.order_details
    ADD CONSTRAINT order_details_pkey PRIMARY KEY (order_id, product_id);


--
-- TOC entry 3286 (class 2606 OID 24638)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 3288 (class 2606 OID 24648)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 3290 (class 2606 OID 24658)
-- Name: promotions promotions_pkey; Type: CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.promotions
    ADD CONSTRAINT promotions_pkey PRIMARY KEY (id);


--
-- TOC entry 3292 (class 2606 OID 24668)
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- TOC entry 3294 (class 2606 OID 24685)
-- Name: users uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 3296 (class 2606 OID 24678)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3298 (class 2606 OID 24683)
-- Name: users_roles users_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 3309 (class 2606 OID 24741)
-- Name: users_roles fk2o0jvgh89lemvvo17cbqvdxaa; Type: FK CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fk2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3306 (class 2606 OID 24721)
-- Name: orders fk32ql8ubntj5uh44ph9659tiih; Type: FK CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3304 (class 2606 OID 24716)
-- Name: order_details fk4q98utpd73imf4yhttm3w0eax; Type: FK CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.order_details
    ADD CONSTRAINT fk4q98utpd73imf4yhttm3w0eax FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- TOC entry 3302 (class 2606 OID 24701)
-- Name: favorite_items fk5brdgvyks5q5koqksotlu8105; Type: FK CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.favorite_items
    ADD CONSTRAINT fk5brdgvyks5q5koqksotlu8105 FOREIGN KEY (product_id) REFERENCES public.products(id);


--
-- TOC entry 3300 (class 2606 OID 24696)
-- Name: comments fk8omq0tc18jd43bu5tjh6jvraq; Type: FK CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fk8omq0tc18jd43bu5tjh6jvraq FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3301 (class 2606 OID 24691)
-- Name: comments fk9aakob3a7aghrm94k9kmbrjqd; Type: FK CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT fk9aakob3a7aghrm94k9kmbrjqd FOREIGN KEY (blog_id) REFERENCES public.blogs(id);


--
-- TOC entry 3307 (class 2606 OID 24731)
-- Name: products fk_product_promotion; Type: FK CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT fk_product_promotion FOREIGN KEY (promotion_id) REFERENCES public.promotions(id);


--
-- TOC entry 3310 (class 2606 OID 24736)
-- Name: users_roles fkj6m8fwv7oqv74fcehir1a9ffy; Type: FK CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.users_roles
    ADD CONSTRAINT fkj6m8fwv7oqv74fcehir1a9ffy FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- TOC entry 3305 (class 2606 OID 24711)
-- Name: order_details fkjyu2qbqt8gnvno9oe9j2s2ldk; Type: FK CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.order_details
    ADD CONSTRAINT fkjyu2qbqt8gnvno9oe9j2s2ldk FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- TOC entry 3303 (class 2606 OID 24706)
-- Name: favorite_items fkmcicth3vly9ytekmo9es5vtri; Type: FK CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.favorite_items
    ADD CONSTRAINT fkmcicth3vly9ytekmo9es5vtri FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3308 (class 2606 OID 24726)
-- Name: products fkog2rp4qthbtt2lfyhfo32lsw9; Type: FK CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT fkog2rp4qthbtt2lfyhfo32lsw9 FOREIGN KEY (category_id) REFERENCES public.categories(id);


--
-- TOC entry 3299 (class 2606 OID 24686)
-- Name: blogs fkpg4damav6db6a6fh5peylcni5; Type: FK CONSTRAINT; Schema: public; Owner: linhnd1899
--

ALTER TABLE ONLY public.blogs
    ADD CONSTRAINT fkpg4damav6db6a6fh5peylcni5 FOREIGN KEY (user_id) REFERENCES public.users(id);


-- Completed on 2026-04-04 08:49:39

--
-- PostgreSQL database dump complete
--

