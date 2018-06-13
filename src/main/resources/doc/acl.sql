--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.5
-- Dumped by pg_dump version 9.4.0
-- Started on 2016-03-24 21:02:47

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 10 (class 2615 OID 18213)
-- Name: acl; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA acl;


ALTER SCHEMA acl OWNER TO postgres;

SET search_path = acl, pg_catalog;

--
-- TOC entry 503 (class 1255 OID 18214)
-- Name: gravarfuncao(character varying, character varying, integer, integer, boolean); Type: FUNCTION; Schema: acl; Owner: postgres
--

CREATE FUNCTION gravarfuncao(vdescricao character varying, vcodigo character varying, vidrotina integer, vidsistema integer, vativa boolean) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
  idretorno integer;
BEGIN

insert into acl.funcao (descricao, codigo, id_rotina, id_sistema, ativa) values (vdescricao, vcodigo, vidrotina, vidsistema, vativa);
 
select currval ('acl.funcao_id_seq') into idretorno;

return idretorno;

END;
$$;


ALTER FUNCTION acl.gravarfuncao(vdescricao character varying, vcodigo character varying, vidrotina integer, vidsistema integer, vativa boolean) OWNER TO postgres;

--
-- TOC entry 504 (class 1255 OID 18215)
-- Name: gravarmenu(character varying, character varying, character varying, character varying, character varying, character varying, character varying, integer, boolean, character varying, character varying); Type: FUNCTION; Schema: acl; Owner: postgres
--

CREATE FUNCTION gravarmenu(vdescricao character varying, vdesc_pagina character varying, vdiretorio character varying, vextensao character varying, vcodigo character varying, vindice character varying, vtipo character varying, vidrotina integer, vativo boolean, vaction_rel character varying, vonclick_rel character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
  idretorno integer;
BEGIN

insert into acl.menu (descricao, desc_pagina, diretorio, extensao, codigo, indice, tipo, id_rotina, ativo, action_rel, onclick_rel) values (vdescricao, vdesc_pagina, vdiretorio, vextensao, vcodigo, vindice, vtipo, vidrotina, vativo, vaction_rel, vonclick_rel);
 
select currval ('acl.menu_id_seq') into idretorno;

return idretorno;

END;
$$;


ALTER FUNCTION acl.gravarmenu(vdescricao character varying, vdesc_pagina character varying, vdiretorio character varying, vextensao character varying, vcodigo character varying, vindice character varying, vtipo character varying, vidrotina integer, vativo boolean, vaction_rel character varying, vonclick_rel character varying) OWNER TO postgres;

--
-- TOC entry 505 (class 1255 OID 18216)
-- Name: gravarperfil(character varying); Type: FUNCTION; Schema: acl; Owner: postgres
--

CREATE FUNCTION gravarperfil(vdescricao character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
	idretorno integer;
BEGIN
	insert into acl.perfil (descricao) values (vdescricao);
	select currval ('acl.perfil_id_seq') into idretorno;
	return idretorno;
END;
$$;


ALTER FUNCTION acl.gravarperfil(vdescricao character varying) OWNER TO postgres;

--
-- TOC entry 506 (class 1255 OID 18217)
-- Name: gravarpermissao(character varying); Type: FUNCTION; Schema: acl; Owner: postgres
--

CREATE FUNCTION gravarpermissao(vdescricao character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
  idretorno integer;
BEGIN

insert into acl.permissao (descricao) values (vdescricao);
 
select currval ('acl.permissao_id_seq') into idretorno;

return idretorno;

END;
$$;


ALTER FUNCTION acl.gravarpermissao(vdescricao character varying) OWNER TO postgres;

--
-- TOC entry 507 (class 1255 OID 18218)
-- Name: gravarusuario(character varying, character varying, character varying, character varying, character varying, character varying, integer, boolean); Type: FUNCTION; Schema: acl; Owner: postgres
--

CREATE FUNCTION gravarusuario(vnome character varying, vsexo character varying, vcpf character varying, vemail character varying, vlogin character varying, vsenha character varying, vid_perfil integer, vativo boolean) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
  idretorno integer;
BEGIN

insert into acl.usuario (nome, sexo, cpf, email, login, senha, id_perfil, ativo) values (vnome, vsexo, vcpf, vemail, vlogin, vsenha, vid_perfil, vativo);
 
select currval ('acl.usuario_id_seq') into idretorno;

return idretorno;

END;
$$;


ALTER FUNCTION acl.gravarusuario(vnome character varying, vsexo character varying, vcpf character varying, vemail character varying, vlogin character varying, vsenha character varying, vid_perfil integer, vativo boolean) OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = true;

--
-- TOC entry 404 (class 1259 OID 18219)
-- Name: funcao; Type: TABLE; Schema: acl; Owner: postgres; Tablespace: 
--

CREATE TABLE funcao (
    id integer NOT NULL,
    descricao character varying,
    codigo character varying(20),
    id_rotina integer,
    id_sistema integer,
    ativa boolean
);


ALTER TABLE funcao OWNER TO postgres;

--
-- TOC entry 405 (class 1259 OID 18225)
-- Name: funcao_id_seq; Type: SEQUENCE; Schema: acl; Owner: postgres
--

CREATE SEQUENCE funcao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE funcao_id_seq OWNER TO postgres;

--
-- TOC entry 2886 (class 0 OID 0)
-- Dependencies: 405
-- Name: funcao_id_seq; Type: SEQUENCE OWNED BY; Schema: acl; Owner: postgres
--

ALTER SEQUENCE funcao_id_seq OWNED BY funcao.id;


--
-- TOC entry 406 (class 1259 OID 18227)
-- Name: menu; Type: TABLE; Schema: acl; Owner: postgres; Tablespace: 
--

CREATE TABLE menu (
    id integer NOT NULL,
    descricao character varying,
    desc_pagina character varying,
    diretorio character varying,
    extensao character varying,
    codigo character varying,
    indice character varying,
    tipo character varying,
    id_rotina integer,
    ativo boolean,
    action_rel character varying,
    onclick_rel character varying
);


ALTER TABLE menu OWNER TO postgres;

--
-- TOC entry 407 (class 1259 OID 18233)
-- Name: menu_id_seq; Type: SEQUENCE; Schema: acl; Owner: postgres
--

CREATE SEQUENCE menu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE menu_id_seq OWNER TO postgres;

--
-- TOC entry 2887 (class 0 OID 0)
-- Dependencies: 407
-- Name: menu_id_seq; Type: SEQUENCE OWNED BY; Schema: acl; Owner: postgres
--

ALTER SEQUENCE menu_id_seq OWNED BY menu.id;


--
-- TOC entry 408 (class 1259 OID 18235)
-- Name: menu_sistema; Type: TABLE; Schema: acl; Owner: postgres; Tablespace: 
--

CREATE TABLE menu_sistema (
    id integer NOT NULL,
    id_menu integer,
    id_sistema integer
);


ALTER TABLE menu_sistema OWNER TO postgres;

--
-- TOC entry 409 (class 1259 OID 18238)
-- Name: menu_sistema_id_seq; Type: SEQUENCE; Schema: acl; Owner: postgres
--

CREATE SEQUENCE menu_sistema_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE menu_sistema_id_seq OWNER TO postgres;

--
-- TOC entry 2888 (class 0 OID 0)
-- Dependencies: 409
-- Name: menu_sistema_id_seq; Type: SEQUENCE OWNED BY; Schema: acl; Owner: postgres
--

ALTER SEQUENCE menu_sistema_id_seq OWNED BY menu_sistema.id;


--
-- TOC entry 410 (class 1259 OID 18240)
-- Name: perfil; Type: TABLE; Schema: acl; Owner: postgres; Tablespace: 
--

CREATE TABLE perfil (
    id integer NOT NULL,
    descricao character varying,
    codfilial integer
);


ALTER TABLE perfil OWNER TO postgres;

--
-- TOC entry 411 (class 1259 OID 18246)
-- Name: perfil_id_seq; Type: SEQUENCE; Schema: acl; Owner: postgres
--

CREATE SEQUENCE perfil_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE perfil_id_seq OWNER TO postgres;

--
-- TOC entry 2889 (class 0 OID 0)
-- Dependencies: 411
-- Name: perfil_id_seq; Type: SEQUENCE OWNED BY; Schema: acl; Owner: postgres
--

ALTER SEQUENCE perfil_id_seq OWNED BY perfil.id;


--
-- TOC entry 412 (class 1259 OID 18248)
-- Name: perm_geral; Type: TABLE; Schema: acl; Owner: postgres; Tablespace: 
--

CREATE TABLE perm_geral (
    id integer NOT NULL,
    id_funcao integer,
    id_menu integer,
    id_permissao integer
);


ALTER TABLE perm_geral OWNER TO postgres;

--
-- TOC entry 413 (class 1259 OID 18251)
-- Name: perm_geral_id_seq; Type: SEQUENCE; Schema: acl; Owner: postgres
--

CREATE SEQUENCE perm_geral_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE perm_geral_id_seq OWNER TO postgres;

--
-- TOC entry 2890 (class 0 OID 0)
-- Dependencies: 413
-- Name: perm_geral_id_seq; Type: SEQUENCE OWNED BY; Schema: acl; Owner: postgres
--

ALTER SEQUENCE perm_geral_id_seq OWNED BY perm_geral.id;


--
-- TOC entry 414 (class 1259 OID 18253)
-- Name: perm_perfil; Type: TABLE; Schema: acl; Owner: postgres; Tablespace: 
--

CREATE TABLE perm_perfil (
    id integer NOT NULL,
    id_perfil integer,
    id_permissao integer
);


ALTER TABLE perm_perfil OWNER TO postgres;

--
-- TOC entry 415 (class 1259 OID 18256)
-- Name: perm_perfil_id_seq; Type: SEQUENCE; Schema: acl; Owner: postgres
--

CREATE SEQUENCE perm_perfil_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE perm_perfil_id_seq OWNER TO postgres;

--
-- TOC entry 2891 (class 0 OID 0)
-- Dependencies: 415
-- Name: perm_perfil_id_seq; Type: SEQUENCE OWNED BY; Schema: acl; Owner: postgres
--

ALTER SEQUENCE perm_perfil_id_seq OWNED BY perm_perfil.id;


--
-- TOC entry 416 (class 1259 OID 18258)
-- Name: perm_sistema; Type: TABLE; Schema: acl; Owner: postgres; Tablespace: 
--

CREATE TABLE perm_sistema (
    id integer NOT NULL,
    id_usuario integer,
    id_sistema integer
);


ALTER TABLE perm_sistema OWNER TO postgres;

--
-- TOC entry 417 (class 1259 OID 18261)
-- Name: perm_sistema_id_seq; Type: SEQUENCE; Schema: acl; Owner: postgres
--

CREATE SEQUENCE perm_sistema_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE perm_sistema_id_seq OWNER TO postgres;

--
-- TOC entry 2892 (class 0 OID 0)
-- Dependencies: 417
-- Name: perm_sistema_id_seq; Type: SEQUENCE OWNED BY; Schema: acl; Owner: postgres
--

ALTER SEQUENCE perm_sistema_id_seq OWNED BY perm_sistema.id;


--
-- TOC entry 418 (class 1259 OID 18263)
-- Name: perm_usuario; Type: TABLE; Schema: acl; Owner: postgres; Tablespace: 
--

CREATE TABLE perm_usuario (
    id integer NOT NULL,
    id_usuario integer,
    id_permissao integer
);


ALTER TABLE perm_usuario OWNER TO postgres;

--
-- TOC entry 419 (class 1259 OID 18266)
-- Name: perm_usuario_id_seq; Type: SEQUENCE; Schema: acl; Owner: postgres
--

CREATE SEQUENCE perm_usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE perm_usuario_id_seq OWNER TO postgres;

--
-- TOC entry 2893 (class 0 OID 0)
-- Dependencies: 419
-- Name: perm_usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: acl; Owner: postgres
--

ALTER SEQUENCE perm_usuario_id_seq OWNED BY perm_usuario.id;


--
-- TOC entry 420 (class 1259 OID 18268)
-- Name: permissao; Type: TABLE; Schema: acl; Owner: postgres; Tablespace: 
--

CREATE TABLE permissao (
    id integer NOT NULL,
    descricao character varying
);


ALTER TABLE permissao OWNER TO postgres;

--
-- TOC entry 421 (class 1259 OID 18274)
-- Name: permissao_id_seq; Type: SEQUENCE; Schema: acl; Owner: postgres
--

CREATE SEQUENCE permissao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE permissao_id_seq OWNER TO postgres;

--
-- TOC entry 2894 (class 0 OID 0)
-- Dependencies: 421
-- Name: permissao_id_seq; Type: SEQUENCE OWNED BY; Schema: acl; Owner: postgres
--

ALTER SEQUENCE permissao_id_seq OWNED BY permissao.id;


--
-- TOC entry 422 (class 1259 OID 18276)
-- Name: rotina; Type: TABLE; Schema: acl; Owner: postgres; Tablespace: 
--

CREATE TABLE rotina (
    id integer NOT NULL,
    descricao character varying,
    tipo character varying,
    id_sistema integer
);


ALTER TABLE rotina OWNER TO postgres;

--
-- TOC entry 423 (class 1259 OID 18282)
-- Name: rotina_id_seq; Type: SEQUENCE; Schema: acl; Owner: postgres
--

CREATE SEQUENCE rotina_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE rotina_id_seq OWNER TO postgres;

--
-- TOC entry 2895 (class 0 OID 0)
-- Dependencies: 423
-- Name: rotina_id_seq; Type: SEQUENCE OWNED BY; Schema: acl; Owner: postgres
--

ALTER SEQUENCE rotina_id_seq OWNED BY rotina.id;


--
-- TOC entry 424 (class 1259 OID 18284)
-- Name: sistema; Type: TABLE; Schema: acl; Owner: postgres; Tablespace: 
--

CREATE TABLE sistema (
    id integer NOT NULL,
    descricao character varying,
    sigla character varying,
    url character varying,
    imagem character varying,
    versao character varying,
    ativo boolean
);


ALTER TABLE sistema OWNER TO postgres;

--
-- TOC entry 425 (class 1259 OID 18290)
-- Name: sistema_id_seq; Type: SEQUENCE; Schema: acl; Owner: postgres
--

CREATE SEQUENCE sistema_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sistema_id_seq OWNER TO postgres;

--
-- TOC entry 2896 (class 0 OID 0)
-- Dependencies: 425
-- Name: sistema_id_seq; Type: SEQUENCE OWNED BY; Schema: acl; Owner: postgres
--

ALTER SEQUENCE sistema_id_seq OWNED BY sistema.id;


--
-- TOC entry 2705 (class 2604 OID 18292)
-- Name: id; Type: DEFAULT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY funcao ALTER COLUMN id SET DEFAULT nextval('funcao_id_seq'::regclass);


--
-- TOC entry 2706 (class 2604 OID 18293)
-- Name: id; Type: DEFAULT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY menu ALTER COLUMN id SET DEFAULT nextval('menu_id_seq'::regclass);


--
-- TOC entry 2707 (class 2604 OID 18294)
-- Name: id; Type: DEFAULT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY menu_sistema ALTER COLUMN id SET DEFAULT nextval('menu_sistema_id_seq'::regclass);


--
-- TOC entry 2708 (class 2604 OID 18295)
-- Name: id; Type: DEFAULT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY perfil ALTER COLUMN id SET DEFAULT nextval('perfil_id_seq'::regclass);


--
-- TOC entry 2709 (class 2604 OID 18296)
-- Name: id; Type: DEFAULT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY perm_geral ALTER COLUMN id SET DEFAULT nextval('perm_geral_id_seq'::regclass);


--
-- TOC entry 2710 (class 2604 OID 18297)
-- Name: id; Type: DEFAULT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY perm_perfil ALTER COLUMN id SET DEFAULT nextval('perm_perfil_id_seq'::regclass);


--
-- TOC entry 2711 (class 2604 OID 18298)
-- Name: id; Type: DEFAULT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY perm_sistema ALTER COLUMN id SET DEFAULT nextval('perm_sistema_id_seq'::regclass);


--
-- TOC entry 2712 (class 2604 OID 18299)
-- Name: id; Type: DEFAULT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY perm_usuario ALTER COLUMN id SET DEFAULT nextval('perm_usuario_id_seq'::regclass);


--
-- TOC entry 2713 (class 2604 OID 18300)
-- Name: id; Type: DEFAULT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY permissao ALTER COLUMN id SET DEFAULT nextval('permissao_id_seq'::regclass);


--
-- TOC entry 2714 (class 2604 OID 18301)
-- Name: id; Type: DEFAULT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY rotina ALTER COLUMN id SET DEFAULT nextval('rotina_id_seq'::regclass);


--
-- TOC entry 2715 (class 2604 OID 18302)
-- Name: id; Type: DEFAULT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY sistema ALTER COLUMN id SET DEFAULT nextval('sistema_id_seq'::regclass);


--
-- TOC entry 2860 (class 0 OID 18219)
-- Dependencies: 404
-- Data for Name: funcao; Type: TABLE DATA; Schema: acl; Owner: postgres
--

INSERT INTO funcao VALUES (64, 'Incluir Banco', 'FC-64', 3, 2, true);
INSERT INTO funcao VALUES (1, 'Função Teste', 'FC-1', 1, 1, true);
INSERT INTO funcao VALUES (65, 'Editar Banco', 'FC-65', 3, 2, true);
INSERT INTO funcao VALUES (66, 'Excluir Banco', 'FC-66', 3, 2, true);
INSERT INTO funcao VALUES (7, 'Incluir Funcionário', 'FC-7', 3, 2, true);
INSERT INTO funcao VALUES (8, 'Editar Funcionário', 'FC-8', 3, 2, true);
INSERT INTO funcao VALUES (67, 'Incluir Cliente', 'FC-67', 3, 2, true);
INSERT INTO funcao VALUES (9, 'Excluir Funcionário', 'FC-9', 3, 2, true);
INSERT INTO funcao VALUES (10, 'Incluir Planejamento Venda', 'FC-10', 3, 2, true);
INSERT INTO funcao VALUES (68, 'Editar Cliente', 'FC-68', 3, 2, true);
INSERT INTO funcao VALUES (11, 'Editar Planejamento Venda', 'FC-11', 3, 2, true);
INSERT INTO funcao VALUES (12, 'Abrir Caixa', 'FC-12', 3, 2, true);
INSERT INTO funcao VALUES (69, 'Excluir Cliente', 'FC-69', 3, 2, true);
INSERT INTO funcao VALUES (13, 'Lançar Débito', 'FC-13', 3, 2, true);
INSERT INTO funcao VALUES (14, 'Fechar Caixa', 'FC-14', 3, 2, true);
INSERT INTO funcao VALUES (70, 'Incluir Despesa', 'FC-70', 3, 2, true);
INSERT INTO funcao VALUES (15, 'Incluir Doc. Pagar', 'FC-15', 3, 2, true);
INSERT INTO funcao VALUES (16, 'Editar Doc. Pagar', 'FC-16', 3, 2, true);
INSERT INTO funcao VALUES (71, 'Editar Despesa', 'FC-71', 3, 2, true);
INSERT INTO funcao VALUES (17, 'Excluir Doc. Pagar', 'FC-17', 3, 2, true);
INSERT INTO funcao VALUES (18, 'Incluir Doc. Receber', 'FC-18', 3, 2, true);
INSERT INTO funcao VALUES (72, 'Excluir Despesa', 'FC-72', 3, 2, true);
INSERT INTO funcao VALUES (20, 'Baixa Doc. Receber', 'FC-20', 3, 2, true);
INSERT INTO funcao VALUES (73, 'Incluir Fonte Receita', 'FC-73', 3, 2, true);
INSERT INTO funcao VALUES (21, 'Negociação Doc. Receber', 'FC-21', 3, 2, true);
INSERT INTO funcao VALUES (22, 'Antecipação Doc. Receber', 'FC-22', 3, 2, true);
INSERT INTO funcao VALUES (19, 'Editar Doc. Receber', 'FC-19', 3, 2, true);
INSERT INTO funcao VALUES (74, 'Editar Fonte Receita', 'FC-74', 3, 2, true);
INSERT INTO funcao VALUES (23, 'Incluir Avaliação', 'FC-23', 3, 2, true);
INSERT INTO funcao VALUES (24, 'Editar Avaliação', 'FC-24', 3, 2, true);
INSERT INTO funcao VALUES (75, 'Excluir Fonte Receita', 'FC-75', 3, 2, true);
INSERT INTO funcao VALUES (25, 'Cancelar Avaliação', 'FC-25', 3, 2, true);
INSERT INTO funcao VALUES (26, 'Imprimir Avaliação', 'FC-26', 3, 2, true);
INSERT INTO funcao VALUES (76, 'Incluir Fornecedor', 'FC-76', 3, 2, true);
INSERT INTO funcao VALUES (27, 'Incluir Cortesia', 'FC-27', 3, 2, true);
INSERT INTO funcao VALUES (28, 'Cancelar Cortesia', 'FC-28', 3, 2, true);
INSERT INTO funcao VALUES (77, 'Editar Fornecedor', 'FC-77', 3, 2, true);
INSERT INTO funcao VALUES (29, 'Realizar Pag. Cortesia', 'FC-29', 3, 2, true);
INSERT INTO funcao VALUES (30, 'Imprimir Recibo', 'FC-30', 3, 2, true);
INSERT INTO funcao VALUES (78, 'Excluir Fornecedor', 'FC-78', 3, 2, true);
INSERT INTO funcao VALUES (31, 'Incluir Venda', 'FC-31', 3, 2, true);
INSERT INTO funcao VALUES (32, 'Pagamento Venda', 'FC-32', 3, 2, true);
INSERT INTO funcao VALUES (79, 'Incluir Classe', 'FC-79', 3, 2, true);
INSERT INTO funcao VALUES (33, 'Cancelar Venda', 'FC-33', 3, 2, true);
INSERT INTO funcao VALUES (34, 'Incluir Orçamento', 'FC-34', 3, 2, true);
INSERT INTO funcao VALUES (80, 'Editar Classe', 'FC-80', 3, 2, true);
INSERT INTO funcao VALUES (35, 'Editar Orçamento', 'FC-35', 3, 1, true);
INSERT INTO funcao VALUES (36, 'Confirmar Orçamento', 'FC-36', 3, 2, true);
INSERT INTO funcao VALUES (81, 'Excluir Classe', 'FC-81', 3, 2, true);
INSERT INTO funcao VALUES (37, 'Imprimir Orçamento', 'FC-37', 3, 2, true);
INSERT INTO funcao VALUES (38, 'Incluir Agenda', 'FC-38', 3, 2, true);
INSERT INTO funcao VALUES (39, 'Confirmar Agenda', 'FC-39', 3, 2, true);
INSERT INTO funcao VALUES (40, 'Transferir Agenda', 'FC-40', 3, 2, true);
INSERT INTO funcao VALUES (41, 'Cancelar Agenda', 'FC-41', 3, 2, true);
INSERT INTO funcao VALUES (42, 'Bloquear Agenda', 'FC-42', 3, 2, true);
INSERT INTO funcao VALUES (43, 'Reservar Agenda', 'FC-43', 3, 2, true);
INSERT INTO funcao VALUES (44, 'Atendido Agenda', 'FC-44', 3, 2, true);
INSERT INTO funcao VALUES (45, 'Evoluções Agenda', 'FC-45', 3, 2, true);
INSERT INTO funcao VALUES (46, 'Presença Agenda', 'FC-46', 3, 2, true);
INSERT INTO funcao VALUES (47, 'Cadastro Agenda', 'FC-47', 3, 2, true);
INSERT INTO funcao VALUES (48, 'Orçamento Agenda', 'FC-48', 3, 2, true);
INSERT INTO funcao VALUES (49, 'Emissão Agenda', 'FC-49', 3, 2, true);
INSERT INTO funcao VALUES (50, 'Fechamento Agenda', 'FC-50', 3, 2, true);
INSERT INTO funcao VALUES (51, 'Cancelamento Agenda', 'FC-51', 3, 2, true);
INSERT INTO funcao VALUES (52, 'Imprimir Con. Caixa', 'FC-52', 3, 2, true);
INSERT INTO funcao VALUES (53, 'Presta Contas Con. Caixa', 'FC-53', 3, 2, true);
INSERT INTO funcao VALUES (54, 'Incluir Campanha', 'FC-54', 3, 2, true);
INSERT INTO funcao VALUES (55, 'Editar Campanha', 'FC-55', 3, 2, true);
INSERT INTO funcao VALUES (56, 'Abrir Caixa Tesouraria', 'FC-56', 3, 2, true);
INSERT INTO funcao VALUES (57, 'Fechar Caixa Tesouraria', 'FC-57', 3, 2, true);
INSERT INTO funcao VALUES (58, 'Compensar Tesouraria', 'FC-58', 3, 2, true);
INSERT INTO funcao VALUES (59, 'Emitir Cheque Tesouraria', 'FC-59', 3, 2, true);
INSERT INTO funcao VALUES (60, 'Débito em Conta Tesouraria', 'FC-60', 3, 2, true);
INSERT INTO funcao VALUES (61, 'Pagamento Tesouraria', 'FC-61', 3, 2, true);
INSERT INTO funcao VALUES (62, 'Gravar Lançamento Tesouraria', 'FC-62', 3, 2, true);
INSERT INTO funcao VALUES (63, 'Gravar Acerto Tesouraria', 'FC-63', 3, 2, true);
INSERT INTO funcao VALUES (82, 'Incluir Divisão', 'FC-82', 3, 2, true);
INSERT INTO funcao VALUES (83, 'Editar Divisão', 'FC-83', 3, 2, true);
INSERT INTO funcao VALUES (84, 'Excluir Divisão', 'FC-84', 3, 2, true);
INSERT INTO funcao VALUES (85, 'Incluir Grupo', 'FC-85', 3, 2, true);
INSERT INTO funcao VALUES (86, 'Editar Grupo', 'FC-86', 3, 2, true);
INSERT INTO funcao VALUES (87, 'Excluir Grupo', 'FC-87', 3, 2, true);
INSERT INTO funcao VALUES (88, 'Incluir Procedimento', 'FC-88', 3, 2, true);
INSERT INTO funcao VALUES (90, 'Excluir Procedimento', 'FC-90', 3, 2, true);
INSERT INTO funcao VALUES (91, 'Incluir Seção', 'FC-91', 3, 2, true);
INSERT INTO funcao VALUES (92, 'Editar Seção', 'FC-92', 3, 2, true);
INSERT INTO funcao VALUES (93, 'Excluir Seção', 'FC-93', 3, 2, true);
INSERT INTO funcao VALUES (94, 'Incluir Subgrupo', 'FC-94', 3, 2, true);
INSERT INTO funcao VALUES (95, 'Editar Subgrupo', 'FC-95', 3, 2, true);
INSERT INTO funcao VALUES (96, 'Excluir Subgrupo', 'FC-96', 3, 2, true);
INSERT INTO funcao VALUES (89, 'Editar Procedimento', 'FC-89', 3, 2, true);
INSERT INTO funcao VALUES (97, 'Incluir Categoria', 'FC-97', 3, 2, true);
INSERT INTO funcao VALUES (98, 'Editar Categoria', 'FC-98', 3, 2, true);
INSERT INTO funcao VALUES (99, 'Excluir Categoria', 'FC-99', 3, 2, true);
INSERT INTO funcao VALUES (100, 'Incluir Portador', 'FC-100', 3, 2, true);
INSERT INTO funcao VALUES (101, 'Editar Portador', 'FC-101', 3, 2, true);
INSERT INTO funcao VALUES (102, 'Excluir Portador', 'FC-102', 3, 2, true);
INSERT INTO funcao VALUES (103, 'Incluir Tabela de Preço', 'FC-103', 3, 2, true);
INSERT INTO funcao VALUES (104, 'Editar Tabela de Preço', 'FC-104', 3, 2, true);
INSERT INTO funcao VALUES (105, 'Excluir Tabela de Preço', 'FC-105', 3, 2, true);
INSERT INTO funcao VALUES (109, 'Incluir Tipo Pag.', 'FC-109', 3, 2, true);
INSERT INTO funcao VALUES (110, 'Editar Tipo Pag.', 'FC-110', 3, 2, true);
INSERT INTO funcao VALUES (111, 'Excluir Tipo Pag.', 'FC-111', 3, 2, true);
INSERT INTO funcao VALUES (112, 'Incluir Centro de Custo', 'FC-112', 3, 2, true);
INSERT INTO funcao VALUES (113, 'Editar Centro de Custo', 'FC-113', 3, 2, true);
INSERT INTO funcao VALUES (114, 'Excluir Centro de Custo', 'FC-114', 3, 2, true);
INSERT INTO funcao VALUES (115, 'Incluir Conf. Agenda', 'FC-115', 3, 2, true);
INSERT INTO funcao VALUES (116, 'Editar Conf. Agenda', 'FC-116', 3, 2, true);
INSERT INTO funcao VALUES (117, 'Excluir Conf. Agenda', 'FC-117', 3, 2, true);
INSERT INTO funcao VALUES (118, 'Incluir Estado Civil', 'FC-118', 3, 2, true);
INSERT INTO funcao VALUES (119, 'Editar Estado Civil', 'FC-119', 3, 2, true);
INSERT INTO funcao VALUES (120, 'Excluir Estado Civil', 'FC-120', 3, 2, true);
INSERT INTO funcao VALUES (121, 'Incluir Kit Procedimento', 'FC-121', 3, 2, true);
INSERT INTO funcao VALUES (122, 'Editar Kit Procedimento', 'FC-122', 3, 2, true);
INSERT INTO funcao VALUES (123, 'Excluir Kit Procedimento', 'FC-123', 3, 2, true);
INSERT INTO funcao VALUES (124, 'Incluir Profissão', 'FC-124', 3, 2, true);
INSERT INTO funcao VALUES (125, 'Editar Profissão', 'FC-125', 3, 2, true);
INSERT INTO funcao VALUES (126, 'Excluir Profissão', 'FC-126', 3, 2, true);
INSERT INTO funcao VALUES (127, 'Incluir Sala', 'FC-127', 3, 2, true);
INSERT INTO funcao VALUES (128, 'Editar Sala', 'FC-128', 3, 2, true);
INSERT INTO funcao VALUES (129, 'Excluir Sala', 'FC-129', 3, 2, true);
INSERT INTO funcao VALUES (130, 'Incluir Bairro', 'FC-130', 3, 2, true);
INSERT INTO funcao VALUES (131, 'Editar Bairro', 'FC-131', 3, 2, true);
INSERT INTO funcao VALUES (132, 'Excluir Bairro', 'FC-132', 3, 2, true);
INSERT INTO funcao VALUES (133, 'Incluir Cidade', 'FC-133', 3, 2, true);
INSERT INTO funcao VALUES (134, 'Editar Cidade', 'FC-134', 3, 2, true);
INSERT INTO funcao VALUES (135, 'Excluir Cidade', 'FC-135', 3, 2, true);
INSERT INTO funcao VALUES (136, 'Incluir Estado', 'FC-136', 3, 2, true);
INSERT INTO funcao VALUES (137, 'Editar Estado', 'FC-137', 3, 2, true);
INSERT INTO funcao VALUES (138, 'Excluir Estado', 'FC-138', 3, 2, true);
INSERT INTO funcao VALUES (108, 'Excluir Tipo Doc.', 'FC-108', 3, 2, true);
INSERT INTO funcao VALUES (107, 'Editar Tipo Doc.', 'FC-107', 3, 2, true);
INSERT INTO funcao VALUES (106, 'Incluir Tipo Doc.', 'FC-106', 3, 2, true);
INSERT INTO funcao VALUES (2, 'Principal Clientes', 'FC-2', 3, 2, true);
INSERT INTO funcao VALUES (3, 'Principal Caixa', 'FC-3', 3, 2, true);
INSERT INTO funcao VALUES (4, 'Principal Orçamento', 'FC-4', 3, 2, true);
INSERT INTO funcao VALUES (5, 'Principal Vendas', 'FC-5', 3, 2, true);
INSERT INTO funcao VALUES (6, 'Principal Relatórios', 'FC-6', 3, 2, true);


--
-- TOC entry 2897 (class 0 OID 0)
-- Dependencies: 405
-- Name: funcao_id_seq; Type: SEQUENCE SET; Schema: acl; Owner: postgres
--

SELECT pg_catalog.setval('funcao_id_seq', 138, true);


--
-- TOC entry 2862 (class 0 OID 18227)
-- Dependencies: 406
-- Data for Name: menu; Type: TABLE DATA; Schema: acl; Owner: postgres
--

INSERT INTO menu VALUES (1, 'Gereciar', NULL, NULL, NULL, 'MN-1', NULL, 'menuPai', 1, true, NULL, NULL);
INSERT INTO menu VALUES (2, 'Menus', 'gerenciarMenus', 'acl', '.faces', 'MN-2', 'MN-1', 'menuItem', 1, true, NULL, NULL);
INSERT INTO menu VALUES (3, 'Funções', 'gerenciarFuncoes', 'acl', '.faces', 'MN-3', 'MN-1', 'menuItem', 1, true, NULL, NULL);
INSERT INTO menu VALUES (4, 'Perfis', 'gerenciarPerfis', 'acl', '.faces', 'MN-4', 'MN-1', 'menuItem', 1, true, NULL, NULL);
INSERT INTO menu VALUES (5, 'Rotinas', 'gerenciarRotinas', 'acl', '.faces', 'MN-5', 'MN-1', 'menuItem', 1, true, NULL, NULL);
INSERT INTO menu VALUES (6, 'Permissão', 'gerenciarPermissoes', 'acl', '.faces', 'MN-6', 'MN-1', 'menuItem', 1, true, NULL, NULL);
INSERT INTO menu VALUES (7, 'Sistemas', 'gerenciarSistemas', 'acl', '.faces', 'MN-7', 'MN-1', 'menuItem', 1, true, NULL, NULL);
INSERT INTO menu VALUES (9, 'Movimentação', NULL, NULL, NULL, 'MN-9', NULL, 'menuPai', 2, true, NULL, NULL);
INSERT INTO menu VALUES (10, 'Caixa', NULL, NULL, NULL, 'MN-10', NULL, 'menuPai', 2, true, NULL, NULL);
INSERT INTO menu VALUES (11, 'Financeiro', NULL, NULL, NULL, 'MN-11', NULL, 'menuPai', 2, true, NULL, NULL);
INSERT INTO menu VALUES (12, 'Relatórios', NULL, NULL, NULL, 'MN-12', NULL, 'menuPai', 2, true, NULL, NULL);
INSERT INTO menu VALUES (13, 'Administração', NULL, NULL, NULL, 'MN-13', NULL, 'menuPai', 2, true, NULL, NULL);
INSERT INTO menu VALUES (14, 'Extras', NULL, NULL, NULL, 'MN-14', NULL, 'menuPai', 2, true, NULL, NULL);
INSERT INTO menu VALUES (15, 'Ajuda', NULL, NULL, NULL, 'MN-15', NULL, 'menuPai', 2, false, NULL, NULL);
INSERT INTO menu VALUES (16, 'Cliente', 'cadcliente', 'clin', '.faces', 'MN-16', 'MN-8', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (17, 'Banco', 'gerenciarBanco', 'clin', '.faces', 'MN-17', 'MN-8', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (18, 'Despesa', 'gerenciarDespesa', 'clin', '.faces', 'MN-18', 'MN-8', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (19, 'Fornecedor', 'cadfornecedor', 'clin', '.faces', 'MN-19', 'MN-8', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (20, 'Fonte de Receita', 'gerenciarFonteReceita', 'clin', '.faces', 'MN-20', 'MN-8', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (21, 'Pagamento', NULL, NULL, NULL, 'MN-21', 'MN-8', 'submenu', 2, true, NULL, NULL);
INSERT INTO menu VALUES (22, 'Procedimentos', NULL, NULL, NULL, 'MN-22', 'MN-8', 'submenu', 2, true, NULL, NULL);
INSERT INTO menu VALUES (23, 'Endereço', NULL, NULL, NULL, 'MN-23', 'MN-8', 'submenu', 2, true, NULL, NULL);
INSERT INTO menu VALUES (24, 'Outros Cadastros', NULL, NULL, NULL, 'MN-24', 'MN-8', 'submenu', 2, true, NULL, NULL);
INSERT INTO menu VALUES (25, 'Tipo Pagamento', 'cadastrotipopag', 'clin', '.faces', 'MN-25', 'MN-21', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (26, 'Tipo Documento', 'cadtipodoc', 'clin', '.faces', 'MN-26', 'MN-21', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (27, 'Tabela de Preço', 'tabelapreco', 'clin', '.faces', 'MN-27', 'MN-21', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (28, 'Portador', 'cadport', 'clin', '.faces', 'MN-28', 'MN-21', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (29, 'Categoria', 'cadcategoria', 'clin', '.faces', 'MN-29', 'MN-21', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (30, 'Procedimento', 'cadprocedimento', 'clin', '.faces', 'MN-30', 'MN-22', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (31, 'Grupo', 'cadgrupo', 'clin', '.faces', 'MN-31', 'MN-22', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (32, 'Subgrupo', 'cadsubgrupo', 'clin', '.faces', 'MN-32', 'MN-22', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (33, 'Classe', 'cadclasse', 'clin', '.faces', 'MN-33', 'MN-22', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (34, 'Divisão', 'caddivisao', 'clin', '.faces', 'MN-34', 'MN-22', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (35, 'Seção', 'cadsecao', 'clin', '.faces', 'MN-35', 'MN-22', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (36, 'Cidade', 'cadcidade', 'clin', '.faces', 'MN-36', 'MN-23', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (37, 'Estado', 'cadestado', 'clin', '.faces', 'MN-37', 'MN-23', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (38, 'Bairro', 'cadbairro', 'clin', '.faces', 'MN-38', 'MN-23', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (39, 'Estado Civil', 'gerenciarEstadosCivis', 'clin', '.faces', 'MN-39', 'MN-24', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (40, 'Profissão', 'cadprofis', 'clin', '.faces', 'MN-40', 'MN-24', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (41, 'Kit Procedimento', 'cadkit', 'clin', '.faces', 'MN-41', 'MN-24', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (42, 'Sala', 'cadsala', 'clin', '.faces', 'MN-42', 'MN-24', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (43, 'Configuração de Horário - Agenda', 'cadconfigagenda', 'clin', '.faces', 'MN-43', 'MN-24', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (44, 'Centro de Custo', 'cadccusto', 'clin', '.faces', 'MN-44', 'MN-24', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (45, 'Avaliação', 'avaliacao', 'clin', '.faces', 'MN-45', 'MN-9', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (46, 'Orçamento', 'orcamento', 'clin', '.faces', 'MN-46', 'MN-9', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (47, 'Venda', 'venda', 'clin', '.faces', 'MN-47', 'MN-9', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (48, 'Cortesia', 'cortesia', 'clin', '.faces', 'MN-48', 'MN-9', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (49, 'Agenda', 'agenda2', 'clin', '.faces', 'MN-49', 'MN-9', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (50, 'Recibo', 'recibo', 'clin', '.faces', 'MN-50', 'MN-9', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (51, 'Gerenciamento Caixa', 'gerenciamentoCaixa', 'clin', '.faces', 'MN-51', 'MN-10', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (52, 'Consultar Caixa', 'consultaCaixa', 'clin', '.faces', 'MN-52', 'MN-10', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (55, 'Tesouraria', 'tesouraria', 'clin', '.faces', 'MN-55', 'MN-11', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (56, 'Vendas', NULL, NULL, NULL, 'MN-56', 'MN-12', 'submenu', 2, true, NULL, NULL);
INSERT INTO menu VALUES (57, 'Documentos', NULL, NULL, NULL, 'MN-57', 'MN-12', 'submenu', 2, true, NULL, NULL);
INSERT INTO menu VALUES (58, 'Orçamentos', NULL, NULL, NULL, 'MN-58', 'MN-12', 'submenu', 2, true, NULL, NULL);
INSERT INTO menu VALUES (59, 'Agenda', NULL, NULL, NULL, 'MN-59', 'MN-12', 'submenu', 2, true, NULL, NULL);
INSERT INTO menu VALUES (60, 'Cortesias', NULL, NULL, NULL, 'MN-60', 'MN-12', 'submenu', 2, true, NULL, NULL);
INSERT INTO menu VALUES (61, 'Produção', NULL, NULL, NULL, 'MN-61', 'MN-12', 'submenu', 2, true, NULL, NULL);
INSERT INTO menu VALUES (62, 'Vendas no Período', 'reportVendasPeriodo', 'clin', '.faces', 'MN-62', 'MN-56', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (63, 'Vendas por Produto', 'reportVendasProduto', 'clin', '.faces', 'MN-63', 'MN-56', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (64, 'Recebimentos por Venda', 'reportRecebimentosVendas', 'clin', '.faces', 'MN-64', 'MN-56', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (65, 'Planejamento de Vendas', 'reportPlanejamentoVenda', 'clin', '.faces', 'MN-65', 'MN-56', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (66, 'Serviços X Execuções', 'reportServicosExecucao', 'clin', '.faces', 'MN-66', 'MN-56', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (68, 'Orçamentos por Produto', 'reportOrcamentoProduto', 'clin', '.faces', 'MN-68', 'MN-58', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (67, 'Orçamentos no Período', 'reportOrcamentoPeriodo', 'clin', '.faces', 'MN-67', 'MN-58', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (70, 'Cortesia no Período', 'reportCortesiaPeriodo', 'clin', '.faces', 'MN-70', 'MN-60', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (71, 'Execuções no Período', 'reportExecucoesPeriodo', 'clin', '.faces', 'MN-71', 'MN-61', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (69, 'Agendamento no Período', 'reportAgendamentoPeriodo', 'clin', '.faces', 'MN-69', 'MN-59', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (72, 'Cadastro de Funcionários', 'cadastroUsuario', 'clin', '.faces', 'MN-72', 'MN-13', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (73, 'Campanha', 'campanha', 'clin', '.faces', 'MN-73', 'MN-14', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (74, 'Planejamento de Vendas', 'planejamentovenda', 'clin', '.faces', 'MN-74', 'MN-14', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (75, 'Financeiro', NULL, NULL, NULL, 'MN-75', 'MN-12', 'submenu', 2, true, NULL, NULL);
INSERT INTO menu VALUES (76, 'Pagar', 'reportFinanceiroPagar', 'clin', '.faces', 'MN-76', 'MN-12', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (77, 'Comissão por Indicador', 'reportComissaoIndicador', 'clin', '.faces', 'MN-77', 'MN-56', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (53, 'Gerenciamento de Créditos', 'gerenciamentoFinanceiro', 'clin', '.faces', 'MN-53', 'MN-84', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (54, 'Gerenciamento de Débitos', 'gerenciamentoFinanceiro2', 'clin', '.faces', 'MN-54', 'MN-85', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (79, 'Promissória', 'promissoria', 'clin', '.faces', 'MN-79', 'MN-9', 'menuItem', 1, true, NULL, NULL);
INSERT INTO menu VALUES (8, 'Cadastro', NULL, NULL, NULL, 'MN-8', NULL, 'menuPai', 2, true, NULL, NULL);
INSERT INTO menu VALUES (80, 'Meus Leilões', 'selectLeilao', 'comum', '.faces', 'MN-80', 'MN-8', 'menuItem', 1, true, NULL, NULL);
INSERT INTO menu VALUES (81, 'Desistência de Venda', 'desistvenda', 'clin', '.faces', 'MN-81', 'MN-9', 'menuItem', 1, true, NULL, NULL);
INSERT INTO menu VALUES (83, 'Relatório Pagar', 'reportFinanceiroPagar', 'clin', '.faces', 'MN-83', 'MN-75', 'menuItem', 3, true, NULL, NULL);
INSERT INTO menu VALUES (82, 'Receber', 'reportFinanceiroReceber', 'clin', '.faces', 'MN-82', 'MN-11', 'menuItem', 1, true, NULL, NULL);
INSERT INTO menu VALUES (78, 'Relatório Receber', 'reportFinanceiroReceber', 'clin', '.faces', 'MN-78', 'MN-75', 'menuItem', 2, true, NULL, NULL);
INSERT INTO menu VALUES (85, 'A Pagar', NULL, NULL, NULL, 'MN-85', 'MN-11', 'submenu', 1, true, NULL, NULL);
INSERT INTO menu VALUES (84, 'A Receber', NULL, NULL, NULL, 'MN-84', 'MN-75', 'submenu', 1, true, NULL, NULL);
INSERT INTO menu VALUES (86, 'A Receber', NULL, NULL, NULL, 'MN-86', 'MN-11', 'submenu', 1, true, NULL, NULL);
INSERT INTO menu VALUES (87, 'Gerenciamento de Créditos', 'gerenciamentoFinanceiro', 'clin', '.faces', 'MN-87', 'MN-86', 'menuItem', 1, true, NULL, NULL);
INSERT INTO menu VALUES (88, 'Estorno de Baixas', 'estornoPagar', 'clin', '.faces', 'MN-88', 'MN-85', 'menuItem', 1, true, NULL, NULL);
INSERT INTO menu VALUES (89, 'Clientes', 'reportClientes', 'clin', '.faces', 'MN-89', 'MN-12', 'menuItem', 3, true, NULL, NULL);


--
-- TOC entry 2898 (class 0 OID 0)
-- Dependencies: 407
-- Name: menu_id_seq; Type: SEQUENCE SET; Schema: acl; Owner: postgres
--

SELECT pg_catalog.setval('menu_id_seq', 89, true);


--
-- TOC entry 2864 (class 0 OID 18235)
-- Dependencies: 408
-- Data for Name: menu_sistema; Type: TABLE DATA; Schema: acl; Owner: postgres
--

INSERT INTO menu_sistema VALUES (1, 1, 1);
INSERT INTO menu_sistema VALUES (2, 2, 1);
INSERT INTO menu_sistema VALUES (3, 3, 1);
INSERT INTO menu_sistema VALUES (4, 4, 1);
INSERT INTO menu_sistema VALUES (5, 5, 1);
INSERT INTO menu_sistema VALUES (6, 6, 1);
INSERT INTO menu_sistema VALUES (7, 7, 1);
INSERT INTO menu_sistema VALUES (9, 9, 2);
INSERT INTO menu_sistema VALUES (10, 10, 2);
INSERT INTO menu_sistema VALUES (11, 11, 2);
INSERT INTO menu_sistema VALUES (12, 12, 2);
INSERT INTO menu_sistema VALUES (13, 13, 2);
INSERT INTO menu_sistema VALUES (14, 14, 2);
INSERT INTO menu_sistema VALUES (15, 15, 2);
INSERT INTO menu_sistema VALUES (16, 16, 2);
INSERT INTO menu_sistema VALUES (17, 17, 2);
INSERT INTO menu_sistema VALUES (18, 18, 2);
INSERT INTO menu_sistema VALUES (19, 19, 2);
INSERT INTO menu_sistema VALUES (20, 20, 2);
INSERT INTO menu_sistema VALUES (21, 21, 2);
INSERT INTO menu_sistema VALUES (22, 22, 2);
INSERT INTO menu_sistema VALUES (23, 23, 2);
INSERT INTO menu_sistema VALUES (24, 24, 2);
INSERT INTO menu_sistema VALUES (25, 25, 2);
INSERT INTO menu_sistema VALUES (26, 26, 2);
INSERT INTO menu_sistema VALUES (27, 27, 2);
INSERT INTO menu_sistema VALUES (28, 28, 2);
INSERT INTO menu_sistema VALUES (29, 29, 2);
INSERT INTO menu_sistema VALUES (30, 30, 2);
INSERT INTO menu_sistema VALUES (31, 31, 2);
INSERT INTO menu_sistema VALUES (32, 32, 2);
INSERT INTO menu_sistema VALUES (33, 33, 2);
INSERT INTO menu_sistema VALUES (34, 34, 2);
INSERT INTO menu_sistema VALUES (35, 35, 2);
INSERT INTO menu_sistema VALUES (36, 36, 2);
INSERT INTO menu_sistema VALUES (37, 37, 2);
INSERT INTO menu_sistema VALUES (38, 38, 2);
INSERT INTO menu_sistema VALUES (39, 39, 2);
INSERT INTO menu_sistema VALUES (40, 40, 2);
INSERT INTO menu_sistema VALUES (41, 41, 2);
INSERT INTO menu_sistema VALUES (42, 42, 2);
INSERT INTO menu_sistema VALUES (43, 43, 2);
INSERT INTO menu_sistema VALUES (44, 44, 2);
INSERT INTO menu_sistema VALUES (45, 45, 2);
INSERT INTO menu_sistema VALUES (46, 46, 2);
INSERT INTO menu_sistema VALUES (47, 47, 2);
INSERT INTO menu_sistema VALUES (48, 48, 2);
INSERT INTO menu_sistema VALUES (49, 49, 2);
INSERT INTO menu_sistema VALUES (50, 50, 2);
INSERT INTO menu_sistema VALUES (51, 51, 2);
INSERT INTO menu_sistema VALUES (52, 52, 2);
INSERT INTO menu_sistema VALUES (55, 55, 2);
INSERT INTO menu_sistema VALUES (56, 56, 2);
INSERT INTO menu_sistema VALUES (57, 57, 2);
INSERT INTO menu_sistema VALUES (58, 58, 2);
INSERT INTO menu_sistema VALUES (59, 59, 2);
INSERT INTO menu_sistema VALUES (60, 60, 2);
INSERT INTO menu_sistema VALUES (61, 61, 2);
INSERT INTO menu_sistema VALUES (62, 62, 2);
INSERT INTO menu_sistema VALUES (63, 63, 2);
INSERT INTO menu_sistema VALUES (64, 64, 2);
INSERT INTO menu_sistema VALUES (65, 65, 2);
INSERT INTO menu_sistema VALUES (66, 66, 2);
INSERT INTO menu_sistema VALUES (67, 67, 2);
INSERT INTO menu_sistema VALUES (68, 68, 2);
INSERT INTO menu_sistema VALUES (69, 69, 2);
INSERT INTO menu_sistema VALUES (70, 70, 2);
INSERT INTO menu_sistema VALUES (71, 71, 2);
INSERT INTO menu_sistema VALUES (72, 72, 2);
INSERT INTO menu_sistema VALUES (73, 73, 2);
INSERT INTO menu_sistema VALUES (74, 74, 2);
INSERT INTO menu_sistema VALUES (75, 75, 2);
INSERT INTO menu_sistema VALUES (76, 76, 2);
INSERT INTO menu_sistema VALUES (77, 77, 2);
INSERT INTO menu_sistema VALUES (79, 79, 2);
INSERT INTO menu_sistema VALUES (81, 8, 2);
INSERT INTO menu_sistema VALUES (82, 8, 3);
INSERT INTO menu_sistema VALUES (83, 80, 2);
INSERT INTO menu_sistema VALUES (84, 80, 3);
INSERT INTO menu_sistema VALUES (85, 81, 2);
INSERT INTO menu_sistema VALUES (87, 83, 2);
INSERT INTO menu_sistema VALUES (89, 82, 2);
INSERT INTO menu_sistema VALUES (91, 78, 2);
INSERT INTO menu_sistema VALUES (93, 53, 2);
INSERT INTO menu_sistema VALUES (94, 85, 2);
INSERT INTO menu_sistema VALUES (96, 84, 2);
INSERT INTO menu_sistema VALUES (97, 54, 2);
INSERT INTO menu_sistema VALUES (100, 86, 2);
INSERT INTO menu_sistema VALUES (101, 87, 2);
INSERT INTO menu_sistema VALUES (102, 88, 2);
INSERT INTO menu_sistema VALUES (103, 89, 2);


--
-- TOC entry 2899 (class 0 OID 0)
-- Dependencies: 409
-- Name: menu_sistema_id_seq; Type: SEQUENCE SET; Schema: acl; Owner: postgres
--

SELECT pg_catalog.setval('menu_sistema_id_seq', 103, true);


--
-- TOC entry 2866 (class 0 OID 18240)
-- Dependencies: 410
-- Data for Name: perfil; Type: TABLE DATA; Schema: acl; Owner: postgres
--

INSERT INTO perfil VALUES (1, 'ADMINISTRADOR', NULL);


--
-- TOC entry 2900 (class 0 OID 0)
-- Dependencies: 411
-- Name: perfil_id_seq; Type: SEQUENCE SET; Schema: acl; Owner: postgres
--

SELECT pg_catalog.setval('perfil_id_seq', 1, true);


--
-- TOC entry 2868 (class 0 OID 18248)
-- Dependencies: 412
-- Data for Name: perm_geral; Type: TABLE DATA; Schema: acl; Owner: postgres
--

INSERT INTO perm_geral VALUES (1, NULL, 1, 1);
INSERT INTO perm_geral VALUES (2, NULL, 2, 2);
INSERT INTO perm_geral VALUES (3, NULL, 3, 3);
INSERT INTO perm_geral VALUES (4, NULL, 4, 4);
INSERT INTO perm_geral VALUES (5, NULL, 5, 5);
INSERT INTO perm_geral VALUES (6, NULL, 6, 6);
INSERT INTO perm_geral VALUES (7, NULL, 7, 7);
INSERT INTO perm_geral VALUES (8, 1, NULL, 8);
INSERT INTO perm_geral VALUES (9, NULL, 8, 9);
INSERT INTO perm_geral VALUES (10, NULL, 9, 10);
INSERT INTO perm_geral VALUES (11, NULL, 10, 11);
INSERT INTO perm_geral VALUES (12, NULL, 11, 12);
INSERT INTO perm_geral VALUES (13, NULL, 12, 13);
INSERT INTO perm_geral VALUES (14, NULL, 13, 14);
INSERT INTO perm_geral VALUES (15, NULL, 14, 15);
INSERT INTO perm_geral VALUES (16, NULL, 15, 16);
INSERT INTO perm_geral VALUES (17, NULL, 16, 17);
INSERT INTO perm_geral VALUES (18, NULL, 17, 18);
INSERT INTO perm_geral VALUES (19, NULL, 18, 19);
INSERT INTO perm_geral VALUES (20, NULL, 19, 20);
INSERT INTO perm_geral VALUES (21, NULL, 20, 21);
INSERT INTO perm_geral VALUES (22, NULL, 21, 22);
INSERT INTO perm_geral VALUES (23, NULL, 22, 23);
INSERT INTO perm_geral VALUES (24, NULL, 23, 24);
INSERT INTO perm_geral VALUES (25, NULL, 24, 25);
INSERT INTO perm_geral VALUES (26, NULL, 25, 26);
INSERT INTO perm_geral VALUES (27, NULL, 26, 27);
INSERT INTO perm_geral VALUES (28, NULL, 27, 28);
INSERT INTO perm_geral VALUES (29, NULL, 28, 29);
INSERT INTO perm_geral VALUES (30, NULL, 29, 30);
INSERT INTO perm_geral VALUES (31, NULL, 30, 31);
INSERT INTO perm_geral VALUES (32, NULL, 31, 32);
INSERT INTO perm_geral VALUES (33, NULL, 32, 33);
INSERT INTO perm_geral VALUES (34, NULL, 33, 34);
INSERT INTO perm_geral VALUES (35, NULL, 34, 35);
INSERT INTO perm_geral VALUES (36, NULL, 35, 36);
INSERT INTO perm_geral VALUES (37, NULL, 36, 37);
INSERT INTO perm_geral VALUES (38, NULL, 37, 38);
INSERT INTO perm_geral VALUES (39, NULL, 38, 39);
INSERT INTO perm_geral VALUES (40, NULL, 39, 40);
INSERT INTO perm_geral VALUES (41, NULL, 40, 41);
INSERT INTO perm_geral VALUES (42, NULL, 41, 42);
INSERT INTO perm_geral VALUES (43, NULL, 42, 43);
INSERT INTO perm_geral VALUES (44, NULL, 43, 44);
INSERT INTO perm_geral VALUES (45, NULL, 44, 45);
INSERT INTO perm_geral VALUES (46, NULL, 45, 46);
INSERT INTO perm_geral VALUES (47, NULL, 46, 47);
INSERT INTO perm_geral VALUES (48, NULL, 47, 48);
INSERT INTO perm_geral VALUES (49, NULL, 48, 49);
INSERT INTO perm_geral VALUES (50, NULL, 49, 50);
INSERT INTO perm_geral VALUES (51, NULL, 50, 51);
INSERT INTO perm_geral VALUES (52, NULL, 51, 52);
INSERT INTO perm_geral VALUES (53, NULL, 52, 53);
INSERT INTO perm_geral VALUES (54, NULL, 53, 54);
INSERT INTO perm_geral VALUES (55, NULL, 54, 55);
INSERT INTO perm_geral VALUES (56, NULL, 55, 56);
INSERT INTO perm_geral VALUES (57, NULL, 56, 57);
INSERT INTO perm_geral VALUES (58, NULL, 57, 58);
INSERT INTO perm_geral VALUES (59, NULL, 58, 59);
INSERT INTO perm_geral VALUES (60, NULL, 59, 60);
INSERT INTO perm_geral VALUES (61, NULL, 60, 61);
INSERT INTO perm_geral VALUES (62, NULL, 61, 62);
INSERT INTO perm_geral VALUES (63, NULL, 62, 63);
INSERT INTO perm_geral VALUES (64, NULL, 63, 64);
INSERT INTO perm_geral VALUES (65, NULL, 64, 65);
INSERT INTO perm_geral VALUES (66, NULL, 65, 66);
INSERT INTO perm_geral VALUES (67, NULL, 66, 67);
INSERT INTO perm_geral VALUES (68, NULL, 67, 68);
INSERT INTO perm_geral VALUES (69, NULL, 68, 69);
INSERT INTO perm_geral VALUES (70, NULL, 69, 70);
INSERT INTO perm_geral VALUES (71, NULL, 70, 71);
INSERT INTO perm_geral VALUES (72, NULL, 71, 72);
INSERT INTO perm_geral VALUES (73, NULL, 72, 73);
INSERT INTO perm_geral VALUES (74, NULL, 73, 74);
INSERT INTO perm_geral VALUES (75, NULL, 74, 75);
INSERT INTO perm_geral VALUES (76, 2, NULL, 76);
INSERT INTO perm_geral VALUES (77, 3, NULL, 77);
INSERT INTO perm_geral VALUES (78, 4, NULL, 78);
INSERT INTO perm_geral VALUES (79, 5, NULL, 79);
INSERT INTO perm_geral VALUES (80, 6, NULL, 80);
INSERT INTO perm_geral VALUES (81, 7, NULL, 81);
INSERT INTO perm_geral VALUES (82, 8, NULL, 82);
INSERT INTO perm_geral VALUES (83, 9, NULL, 83);
INSERT INTO perm_geral VALUES (84, 10, NULL, 84);
INSERT INTO perm_geral VALUES (85, 11, NULL, 85);
INSERT INTO perm_geral VALUES (86, 12, NULL, 86);
INSERT INTO perm_geral VALUES (87, 13, NULL, 87);
INSERT INTO perm_geral VALUES (88, 14, NULL, 88);
INSERT INTO perm_geral VALUES (89, 15, NULL, 89);
INSERT INTO perm_geral VALUES (90, 16, NULL, 90);
INSERT INTO perm_geral VALUES (91, 17, NULL, 91);
INSERT INTO perm_geral VALUES (92, 18, NULL, 92);
INSERT INTO perm_geral VALUES (93, 19, NULL, 93);
INSERT INTO perm_geral VALUES (94, 20, NULL, 94);
INSERT INTO perm_geral VALUES (95, 21, NULL, 95);
INSERT INTO perm_geral VALUES (96, 22, NULL, 96);
INSERT INTO perm_geral VALUES (97, 23, NULL, 97);
INSERT INTO perm_geral VALUES (98, 24, NULL, 98);
INSERT INTO perm_geral VALUES (99, 25, NULL, 99);
INSERT INTO perm_geral VALUES (100, 26, NULL, 100);
INSERT INTO perm_geral VALUES (101, 27, NULL, 101);
INSERT INTO perm_geral VALUES (102, 28, NULL, 102);
INSERT INTO perm_geral VALUES (103, 29, NULL, 103);
INSERT INTO perm_geral VALUES (104, 30, NULL, 104);
INSERT INTO perm_geral VALUES (105, 31, NULL, 105);
INSERT INTO perm_geral VALUES (106, 32, NULL, 106);
INSERT INTO perm_geral VALUES (107, 33, NULL, 107);
INSERT INTO perm_geral VALUES (108, 34, NULL, 108);
INSERT INTO perm_geral VALUES (109, 35, NULL, 109);
INSERT INTO perm_geral VALUES (110, 36, NULL, 110);
INSERT INTO perm_geral VALUES (111, 37, NULL, 111);
INSERT INTO perm_geral VALUES (112, 38, NULL, 112);
INSERT INTO perm_geral VALUES (113, 39, NULL, 113);
INSERT INTO perm_geral VALUES (114, 40, NULL, 114);
INSERT INTO perm_geral VALUES (115, 41, NULL, 115);
INSERT INTO perm_geral VALUES (116, 42, NULL, 116);
INSERT INTO perm_geral VALUES (117, 43, NULL, 117);
INSERT INTO perm_geral VALUES (118, 44, NULL, 118);
INSERT INTO perm_geral VALUES (119, 45, NULL, 119);
INSERT INTO perm_geral VALUES (120, 46, NULL, 120);
INSERT INTO perm_geral VALUES (121, 47, NULL, 121);
INSERT INTO perm_geral VALUES (122, 48, NULL, 122);
INSERT INTO perm_geral VALUES (123, 49, NULL, 123);
INSERT INTO perm_geral VALUES (124, 50, NULL, 124);
INSERT INTO perm_geral VALUES (125, 51, NULL, 125);
INSERT INTO perm_geral VALUES (126, 52, NULL, 126);
INSERT INTO perm_geral VALUES (127, 53, NULL, 127);
INSERT INTO perm_geral VALUES (128, 54, NULL, 128);
INSERT INTO perm_geral VALUES (129, 55, NULL, 129);
INSERT INTO perm_geral VALUES (130, 56, NULL, 130);
INSERT INTO perm_geral VALUES (131, 57, NULL, 131);
INSERT INTO perm_geral VALUES (132, 58, NULL, 132);
INSERT INTO perm_geral VALUES (133, 59, NULL, 133);
INSERT INTO perm_geral VALUES (134, 60, NULL, 134);
INSERT INTO perm_geral VALUES (135, 61, NULL, 135);
INSERT INTO perm_geral VALUES (136, 62, NULL, 136);
INSERT INTO perm_geral VALUES (137, 63, NULL, 137);
INSERT INTO perm_geral VALUES (138, 64, NULL, 138);
INSERT INTO perm_geral VALUES (139, 65, NULL, 139);
INSERT INTO perm_geral VALUES (140, 66, NULL, 140);
INSERT INTO perm_geral VALUES (141, 67, NULL, 141);
INSERT INTO perm_geral VALUES (142, 68, NULL, 142);
INSERT INTO perm_geral VALUES (143, 69, NULL, 143);
INSERT INTO perm_geral VALUES (144, 70, NULL, 144);
INSERT INTO perm_geral VALUES (145, 71, NULL, 145);
INSERT INTO perm_geral VALUES (146, 72, NULL, 146);
INSERT INTO perm_geral VALUES (147, 73, NULL, 147);
INSERT INTO perm_geral VALUES (148, 74, NULL, 148);
INSERT INTO perm_geral VALUES (149, 75, NULL, 149);
INSERT INTO perm_geral VALUES (150, 76, NULL, 150);
INSERT INTO perm_geral VALUES (151, 77, NULL, 151);
INSERT INTO perm_geral VALUES (152, 78, NULL, 152);
INSERT INTO perm_geral VALUES (153, 79, NULL, 153);
INSERT INTO perm_geral VALUES (154, 80, NULL, 154);
INSERT INTO perm_geral VALUES (155, 81, NULL, 155);
INSERT INTO perm_geral VALUES (156, 82, NULL, 156);
INSERT INTO perm_geral VALUES (157, 83, NULL, 157);
INSERT INTO perm_geral VALUES (158, 84, NULL, 158);
INSERT INTO perm_geral VALUES (159, 85, NULL, 159);
INSERT INTO perm_geral VALUES (160, 86, NULL, 160);
INSERT INTO perm_geral VALUES (161, 87, NULL, 161);
INSERT INTO perm_geral VALUES (162, 88, NULL, 162);
INSERT INTO perm_geral VALUES (163, 89, NULL, 163);
INSERT INTO perm_geral VALUES (164, 90, NULL, 164);
INSERT INTO perm_geral VALUES (165, 91, NULL, 165);
INSERT INTO perm_geral VALUES (166, 92, NULL, 166);
INSERT INTO perm_geral VALUES (167, 93, NULL, 167);
INSERT INTO perm_geral VALUES (168, 94, NULL, 168);
INSERT INTO perm_geral VALUES (169, 95, NULL, 169);
INSERT INTO perm_geral VALUES (170, 96, NULL, 170);
INSERT INTO perm_geral VALUES (171, 97, NULL, 171);
INSERT INTO perm_geral VALUES (172, 98, NULL, 172);
INSERT INTO perm_geral VALUES (173, 99, NULL, 173);
INSERT INTO perm_geral VALUES (174, 100, NULL, 174);
INSERT INTO perm_geral VALUES (175, 101, NULL, 175);
INSERT INTO perm_geral VALUES (176, 102, NULL, 176);
INSERT INTO perm_geral VALUES (177, 103, NULL, 177);
INSERT INTO perm_geral VALUES (178, 104, NULL, 178);
INSERT INTO perm_geral VALUES (179, 105, NULL, 179);
INSERT INTO perm_geral VALUES (180, 106, NULL, 180);
INSERT INTO perm_geral VALUES (181, 107, NULL, 181);
INSERT INTO perm_geral VALUES (182, 108, NULL, 182);
INSERT INTO perm_geral VALUES (183, 109, NULL, 183);
INSERT INTO perm_geral VALUES (184, 110, NULL, 184);
INSERT INTO perm_geral VALUES (185, 111, NULL, 185);
INSERT INTO perm_geral VALUES (186, 112, NULL, 186);
INSERT INTO perm_geral VALUES (187, 113, NULL, 187);
INSERT INTO perm_geral VALUES (188, 114, NULL, 188);
INSERT INTO perm_geral VALUES (189, 115, NULL, 189);
INSERT INTO perm_geral VALUES (190, 116, NULL, 190);
INSERT INTO perm_geral VALUES (191, 117, NULL, 191);
INSERT INTO perm_geral VALUES (192, 118, NULL, 192);
INSERT INTO perm_geral VALUES (193, 119, NULL, 193);
INSERT INTO perm_geral VALUES (194, 120, NULL, 194);
INSERT INTO perm_geral VALUES (195, 121, NULL, 195);
INSERT INTO perm_geral VALUES (196, 122, NULL, 196);
INSERT INTO perm_geral VALUES (197, 123, NULL, 197);
INSERT INTO perm_geral VALUES (198, 124, NULL, 198);
INSERT INTO perm_geral VALUES (199, 125, NULL, 199);
INSERT INTO perm_geral VALUES (200, 126, NULL, 200);
INSERT INTO perm_geral VALUES (201, 127, NULL, 201);
INSERT INTO perm_geral VALUES (202, 128, NULL, 202);
INSERT INTO perm_geral VALUES (203, 129, NULL, 203);
INSERT INTO perm_geral VALUES (204, 130, NULL, 204);
INSERT INTO perm_geral VALUES (205, 131, NULL, 205);
INSERT INTO perm_geral VALUES (206, 132, NULL, 206);
INSERT INTO perm_geral VALUES (207, 133, NULL, 207);
INSERT INTO perm_geral VALUES (208, 134, NULL, 208);
INSERT INTO perm_geral VALUES (209, 135, NULL, 209);
INSERT INTO perm_geral VALUES (210, 136, NULL, 210);
INSERT INTO perm_geral VALUES (211, 137, NULL, 211);
INSERT INTO perm_geral VALUES (212, 138, NULL, 212);
INSERT INTO perm_geral VALUES (213, NULL, 75, 213);
INSERT INTO perm_geral VALUES (214, NULL, 76, 214);
INSERT INTO perm_geral VALUES (215, NULL, 77, 215);
INSERT INTO perm_geral VALUES (216, NULL, 78, 216);
INSERT INTO perm_geral VALUES (217, NULL, 79, 217);
INSERT INTO perm_geral VALUES (218, NULL, 80, 218);
INSERT INTO perm_geral VALUES (219, NULL, 81, 219);
INSERT INTO perm_geral VALUES (220, NULL, 82, 220);
INSERT INTO perm_geral VALUES (221, NULL, 83, 221);
INSERT INTO perm_geral VALUES (222, NULL, 84, 222);
INSERT INTO perm_geral VALUES (223, NULL, 85, 223);
INSERT INTO perm_geral VALUES (224, NULL, 86, 224);
INSERT INTO perm_geral VALUES (225, NULL, 87, 225);
INSERT INTO perm_geral VALUES (226, NULL, 88, 226);
INSERT INTO perm_geral VALUES (227, NULL, 89, 227);


--
-- TOC entry 2901 (class 0 OID 0)
-- Dependencies: 413
-- Name: perm_geral_id_seq; Type: SEQUENCE SET; Schema: acl; Owner: postgres
--

SELECT pg_catalog.setval('perm_geral_id_seq', 227, true);


--
-- TOC entry 2870 (class 0 OID 18253)
-- Dependencies: 414
-- Data for Name: perm_perfil; Type: TABLE DATA; Schema: acl; Owner: postgres
--

INSERT INTO perm_perfil VALUES (4839, 1, 10);
INSERT INTO perm_perfil VALUES (4840, 1, 50);
INSERT INTO perm_perfil VALUES (4841, 1, 60);
INSERT INTO perm_perfil VALUES (4842, 1, 70);
INSERT INTO perm_perfil VALUES (4843, 1, 13);
INSERT INTO perm_perfil VALUES (4844, 1, 46);
INSERT INTO perm_perfil VALUES (4845, 1, 24);
INSERT INTO perm_perfil VALUES (4846, 1, 39);
INSERT INTO perm_perfil VALUES (4847, 1, 9);
INSERT INTO perm_perfil VALUES (4848, 1, 18);
INSERT INTO perm_perfil VALUES (4849, 1, 14);
INSERT INTO perm_perfil VALUES (4850, 1, 73);
INSERT INTO perm_perfil VALUES (4851, 1, 15);
INSERT INTO perm_perfil VALUES (4852, 1, 74);
INSERT INTO perm_perfil VALUES (4853, 1, 22);
INSERT INTO perm_perfil VALUES (4854, 1, 30);
INSERT INTO perm_perfil VALUES (4855, 1, 25);
INSERT INTO perm_perfil VALUES (4856, 1, 45);
INSERT INTO perm_perfil VALUES (4857, 1, 37);
INSERT INTO perm_perfil VALUES (4858, 1, 23);
INSERT INTO perm_perfil VALUES (4859, 1, 34);
INSERT INTO perm_perfil VALUES (4860, 1, 17);
INSERT INTO perm_perfil VALUES (4861, 1, 57);
INSERT INTO perm_perfil VALUES (4862, 1, 215);
INSERT INTO perm_perfil VALUES (4863, 1, 44);
INSERT INTO perm_perfil VALUES (4864, 1, 11);
INSERT INTO perm_perfil VALUES (4865, 1, 53);
INSERT INTO perm_perfil VALUES (4866, 1, 49);
INSERT INTO perm_perfil VALUES (4867, 1, 61);
INSERT INTO perm_perfil VALUES (4868, 1, 71);
INSERT INTO perm_perfil VALUES (4869, 1, 219);
INSERT INTO perm_perfil VALUES (4870, 1, 19);
INSERT INTO perm_perfil VALUES (4871, 1, 35);
INSERT INTO perm_perfil VALUES (4872, 1, 38);
INSERT INTO perm_perfil VALUES (4873, 1, 40);
INSERT INTO perm_perfil VALUES (4874, 1, 223);
INSERT INTO perm_perfil VALUES (4875, 1, 226);
INSERT INTO perm_perfil VALUES (4876, 1, 12);
INSERT INTO perm_perfil VALUES (4877, 1, 62);
INSERT INTO perm_perfil VALUES (4878, 1, 72);
INSERT INTO perm_perfil VALUES (4879, 1, 21);
INSERT INTO perm_perfil VALUES (4880, 1, 20);
INSERT INTO perm_perfil VALUES (4881, 1, 1);
INSERT INTO perm_perfil VALUES (4882, 1, 3);
INSERT INTO perm_perfil VALUES (4883, 1, 52);
INSERT INTO perm_perfil VALUES (4884, 1, 224);
INSERT INTO perm_perfil VALUES (4885, 1, 225);
INSERT INTO perm_perfil VALUES (4886, 1, 222);
INSERT INTO perm_perfil VALUES (4887, 1, 54);
INSERT INTO perm_perfil VALUES (4888, 1, 213);
INSERT INTO perm_perfil VALUES (4889, 1, 55);
INSERT INTO perm_perfil VALUES (4890, 1, 32);
INSERT INTO perm_perfil VALUES (4891, 1, 42);
INSERT INTO perm_perfil VALUES (4892, 1, 2);
INSERT INTO perm_perfil VALUES (4893, 1, 218);
INSERT INTO perm_perfil VALUES (4894, 1, 218);
INSERT INTO perm_perfil VALUES (4895, 1, 218);
INSERT INTO perm_perfil VALUES (4896, 1, 218);
INSERT INTO perm_perfil VALUES (4897, 1, 218);
INSERT INTO perm_perfil VALUES (4898, 1, 218);
INSERT INTO perm_perfil VALUES (4899, 1, 218);
INSERT INTO perm_perfil VALUES (4900, 1, 218);
INSERT INTO perm_perfil VALUES (4901, 1, 218);
INSERT INTO perm_perfil VALUES (4902, 1, 218);
INSERT INTO perm_perfil VALUES (4903, 1, 218);
INSERT INTO perm_perfil VALUES (4904, 1, 218);
INSERT INTO perm_perfil VALUES (4905, 1, 218);
INSERT INTO perm_perfil VALUES (4906, 1, 218);
INSERT INTO perm_perfil VALUES (4907, 1, 218);
INSERT INTO perm_perfil VALUES (4908, 1, 218);
INSERT INTO perm_perfil VALUES (4909, 1, 218);
INSERT INTO perm_perfil VALUES (4910, 1, 218);
INSERT INTO perm_perfil VALUES (4911, 1, 218);
INSERT INTO perm_perfil VALUES (4912, 1, 218);
INSERT INTO perm_perfil VALUES (4913, 1, 218);
INSERT INTO perm_perfil VALUES (4914, 1, 218);
INSERT INTO perm_perfil VALUES (4915, 1, 218);
INSERT INTO perm_perfil VALUES (4916, 1, 218);
INSERT INTO perm_perfil VALUES (4917, 1, 218);
INSERT INTO perm_perfil VALUES (4918, 1, 218);
INSERT INTO perm_perfil VALUES (4919, 1, 218);
INSERT INTO perm_perfil VALUES (4920, 1, 218);
INSERT INTO perm_perfil VALUES (4921, 1, 218);
INSERT INTO perm_perfil VALUES (4922, 1, 218);
INSERT INTO perm_perfil VALUES (4923, 1, 218);
INSERT INTO perm_perfil VALUES (4924, 1, 218);
INSERT INTO perm_perfil VALUES (4925, 1, 218);
INSERT INTO perm_perfil VALUES (4926, 1, 218);
INSERT INTO perm_perfil VALUES (4927, 1, 218);
INSERT INTO perm_perfil VALUES (4928, 1, 218);
INSERT INTO perm_perfil VALUES (4929, 1, 218);
INSERT INTO perm_perfil VALUES (4930, 1, 218);
INSERT INTO perm_perfil VALUES (4931, 1, 218);
INSERT INTO perm_perfil VALUES (4932, 1, 218);
INSERT INTO perm_perfil VALUES (4933, 1, 218);
INSERT INTO perm_perfil VALUES (4934, 1, 218);
INSERT INTO perm_perfil VALUES (4935, 1, 218);
INSERT INTO perm_perfil VALUES (4936, 1, 218);
INSERT INTO perm_perfil VALUES (4937, 1, 218);
INSERT INTO perm_perfil VALUES (4938, 1, 218);
INSERT INTO perm_perfil VALUES (4939, 1, 218);
INSERT INTO perm_perfil VALUES (4940, 1, 218);
INSERT INTO perm_perfil VALUES (4941, 1, 218);
INSERT INTO perm_perfil VALUES (4942, 1, 218);
INSERT INTO perm_perfil VALUES (4943, 1, 218);
INSERT INTO perm_perfil VALUES (4944, 1, 218);
INSERT INTO perm_perfil VALUES (4945, 1, 218);
INSERT INTO perm_perfil VALUES (4946, 1, 218);
INSERT INTO perm_perfil VALUES (4947, 1, 218);
INSERT INTO perm_perfil VALUES (4948, 1, 218);
INSERT INTO perm_perfil VALUES (4949, 1, 218);
INSERT INTO perm_perfil VALUES (4950, 1, 218);
INSERT INTO perm_perfil VALUES (4951, 1, 218);
INSERT INTO perm_perfil VALUES (4952, 1, 218);
INSERT INTO perm_perfil VALUES (4953, 1, 218);
INSERT INTO perm_perfil VALUES (4954, 1, 218);
INSERT INTO perm_perfil VALUES (4955, 1, 218);
INSERT INTO perm_perfil VALUES (4956, 1, 218);
INSERT INTO perm_perfil VALUES (4957, 1, 218);
INSERT INTO perm_perfil VALUES (4958, 1, 218);
INSERT INTO perm_perfil VALUES (4959, 1, 218);
INSERT INTO perm_perfil VALUES (4960, 1, 218);
INSERT INTO perm_perfil VALUES (4961, 1, 218);
INSERT INTO perm_perfil VALUES (4962, 1, 218);
INSERT INTO perm_perfil VALUES (4963, 1, 218);
INSERT INTO perm_perfil VALUES (4964, 1, 218);
INSERT INTO perm_perfil VALUES (4965, 1, 218);
INSERT INTO perm_perfil VALUES (4966, 1, 218);
INSERT INTO perm_perfil VALUES (4967, 1, 218);
INSERT INTO perm_perfil VALUES (4968, 1, 218);
INSERT INTO perm_perfil VALUES (4969, 1, 218);
INSERT INTO perm_perfil VALUES (4970, 1, 218);
INSERT INTO perm_perfil VALUES (4971, 1, 218);
INSERT INTO perm_perfil VALUES (4972, 1, 218);
INSERT INTO perm_perfil VALUES (4973, 1, 218);
INSERT INTO perm_perfil VALUES (4974, 1, 218);
INSERT INTO perm_perfil VALUES (4975, 1, 218);
INSERT INTO perm_perfil VALUES (4976, 1, 218);
INSERT INTO perm_perfil VALUES (4977, 1, 218);
INSERT INTO perm_perfil VALUES (4978, 1, 218);
INSERT INTO perm_perfil VALUES (4979, 1, 218);
INSERT INTO perm_perfil VALUES (4980, 1, 218);
INSERT INTO perm_perfil VALUES (4981, 1, 218);
INSERT INTO perm_perfil VALUES (4982, 1, 218);
INSERT INTO perm_perfil VALUES (4983, 1, 218);
INSERT INTO perm_perfil VALUES (4984, 1, 218);
INSERT INTO perm_perfil VALUES (4985, 1, 218);
INSERT INTO perm_perfil VALUES (4986, 1, 218);
INSERT INTO perm_perfil VALUES (4987, 1, 218);
INSERT INTO perm_perfil VALUES (4988, 1, 218);
INSERT INTO perm_perfil VALUES (4989, 1, 218);
INSERT INTO perm_perfil VALUES (4990, 1, 218);
INSERT INTO perm_perfil VALUES (4991, 1, 218);
INSERT INTO perm_perfil VALUES (4992, 1, 218);
INSERT INTO perm_perfil VALUES (4993, 1, 218);
INSERT INTO perm_perfil VALUES (4994, 1, 218);
INSERT INTO perm_perfil VALUES (4995, 1, 218);
INSERT INTO perm_perfil VALUES (4996, 1, 218);
INSERT INTO perm_perfil VALUES (4997, 1, 218);
INSERT INTO perm_perfil VALUES (4998, 1, 218);
INSERT INTO perm_perfil VALUES (4999, 1, 218);
INSERT INTO perm_perfil VALUES (5000, 1, 218);
INSERT INTO perm_perfil VALUES (5001, 1, 218);
INSERT INTO perm_perfil VALUES (5002, 1, 218);
INSERT INTO perm_perfil VALUES (5003, 1, 218);
INSERT INTO perm_perfil VALUES (5004, 1, 218);
INSERT INTO perm_perfil VALUES (5005, 1, 218);
INSERT INTO perm_perfil VALUES (5006, 1, 218);
INSERT INTO perm_perfil VALUES (5007, 1, 218);
INSERT INTO perm_perfil VALUES (5008, 1, 218);
INSERT INTO perm_perfil VALUES (5009, 1, 218);
INSERT INTO perm_perfil VALUES (5010, 1, 218);
INSERT INTO perm_perfil VALUES (5011, 1, 218);
INSERT INTO perm_perfil VALUES (5012, 1, 218);
INSERT INTO perm_perfil VALUES (5013, 1, 218);
INSERT INTO perm_perfil VALUES (5014, 1, 218);
INSERT INTO perm_perfil VALUES (5015, 1, 218);
INSERT INTO perm_perfil VALUES (5016, 1, 218);
INSERT INTO perm_perfil VALUES (5017, 1, 218);
INSERT INTO perm_perfil VALUES (5018, 1, 218);
INSERT INTO perm_perfil VALUES (5019, 1, 218);
INSERT INTO perm_perfil VALUES (5020, 1, 218);
INSERT INTO perm_perfil VALUES (5021, 1, 218);
INSERT INTO perm_perfil VALUES (5022, 1, 218);
INSERT INTO perm_perfil VALUES (5023, 1, 218);
INSERT INTO perm_perfil VALUES (5024, 1, 218);
INSERT INTO perm_perfil VALUES (5025, 1, 218);
INSERT INTO perm_perfil VALUES (5026, 1, 218);
INSERT INTO perm_perfil VALUES (5027, 1, 218);
INSERT INTO perm_perfil VALUES (5028, 1, 218);
INSERT INTO perm_perfil VALUES (5029, 1, 218);
INSERT INTO perm_perfil VALUES (5030, 1, 218);
INSERT INTO perm_perfil VALUES (5031, 1, 218);
INSERT INTO perm_perfil VALUES (5032, 1, 218);
INSERT INTO perm_perfil VALUES (5033, 1, 218);
INSERT INTO perm_perfil VALUES (5034, 1, 218);
INSERT INTO perm_perfil VALUES (5035, 1, 218);
INSERT INTO perm_perfil VALUES (5036, 1, 218);
INSERT INTO perm_perfil VALUES (5037, 1, 218);
INSERT INTO perm_perfil VALUES (5038, 1, 218);
INSERT INTO perm_perfil VALUES (5039, 1, 218);
INSERT INTO perm_perfil VALUES (5040, 1, 218);
INSERT INTO perm_perfil VALUES (5041, 1, 218);
INSERT INTO perm_perfil VALUES (5042, 1, 218);
INSERT INTO perm_perfil VALUES (5043, 1, 218);
INSERT INTO perm_perfil VALUES (5044, 1, 218);
INSERT INTO perm_perfil VALUES (5045, 1, 218);
INSERT INTO perm_perfil VALUES (5046, 1, 218);
INSERT INTO perm_perfil VALUES (5047, 1, 218);
INSERT INTO perm_perfil VALUES (5048, 1, 218);
INSERT INTO perm_perfil VALUES (5049, 1, 218);
INSERT INTO perm_perfil VALUES (5050, 1, 218);
INSERT INTO perm_perfil VALUES (5051, 1, 218);
INSERT INTO perm_perfil VALUES (5052, 1, 218);
INSERT INTO perm_perfil VALUES (5053, 1, 218);
INSERT INTO perm_perfil VALUES (5054, 1, 218);
INSERT INTO perm_perfil VALUES (5055, 1, 218);
INSERT INTO perm_perfil VALUES (5056, 1, 218);
INSERT INTO perm_perfil VALUES (5057, 1, 218);
INSERT INTO perm_perfil VALUES (5058, 1, 218);
INSERT INTO perm_perfil VALUES (5059, 1, 218);
INSERT INTO perm_perfil VALUES (5060, 1, 218);
INSERT INTO perm_perfil VALUES (5061, 1, 218);
INSERT INTO perm_perfil VALUES (5062, 1, 218);
INSERT INTO perm_perfil VALUES (5063, 1, 218);
INSERT INTO perm_perfil VALUES (5064, 1, 218);
INSERT INTO perm_perfil VALUES (5065, 1, 218);
INSERT INTO perm_perfil VALUES (5066, 1, 218);
INSERT INTO perm_perfil VALUES (5067, 1, 218);
INSERT INTO perm_perfil VALUES (5068, 1, 218);
INSERT INTO perm_perfil VALUES (5069, 1, 218);
INSERT INTO perm_perfil VALUES (5070, 1, 218);
INSERT INTO perm_perfil VALUES (5071, 1, 218);
INSERT INTO perm_perfil VALUES (5072, 1, 218);
INSERT INTO perm_perfil VALUES (5073, 1, 218);
INSERT INTO perm_perfil VALUES (5074, 1, 218);
INSERT INTO perm_perfil VALUES (5075, 1, 218);
INSERT INTO perm_perfil VALUES (5076, 1, 218);
INSERT INTO perm_perfil VALUES (5077, 1, 218);
INSERT INTO perm_perfil VALUES (5078, 1, 218);
INSERT INTO perm_perfil VALUES (5079, 1, 218);
INSERT INTO perm_perfil VALUES (5080, 1, 218);
INSERT INTO perm_perfil VALUES (5081, 1, 218);
INSERT INTO perm_perfil VALUES (5082, 1, 218);
INSERT INTO perm_perfil VALUES (5083, 1, 218);
INSERT INTO perm_perfil VALUES (5084, 1, 218);
INSERT INTO perm_perfil VALUES (5085, 1, 218);
INSERT INTO perm_perfil VALUES (5086, 1, 218);
INSERT INTO perm_perfil VALUES (5087, 1, 218);
INSERT INTO perm_perfil VALUES (5088, 1, 218);
INSERT INTO perm_perfil VALUES (5089, 1, 218);
INSERT INTO perm_perfil VALUES (5090, 1, 218);
INSERT INTO perm_perfil VALUES (5091, 1, 218);
INSERT INTO perm_perfil VALUES (5092, 1, 218);
INSERT INTO perm_perfil VALUES (5093, 1, 218);
INSERT INTO perm_perfil VALUES (5094, 1, 218);
INSERT INTO perm_perfil VALUES (5095, 1, 218);
INSERT INTO perm_perfil VALUES (5096, 1, 218);
INSERT INTO perm_perfil VALUES (5097, 1, 218);
INSERT INTO perm_perfil VALUES (5098, 1, 218);
INSERT INTO perm_perfil VALUES (5099, 1, 218);
INSERT INTO perm_perfil VALUES (5100, 1, 218);
INSERT INTO perm_perfil VALUES (5101, 1, 218);
INSERT INTO perm_perfil VALUES (5102, 1, 218);
INSERT INTO perm_perfil VALUES (5103, 1, 218);
INSERT INTO perm_perfil VALUES (5104, 1, 218);
INSERT INTO perm_perfil VALUES (5105, 1, 218);
INSERT INTO perm_perfil VALUES (5106, 1, 218);
INSERT INTO perm_perfil VALUES (5107, 1, 218);
INSERT INTO perm_perfil VALUES (5108, 1, 218);
INSERT INTO perm_perfil VALUES (5109, 1, 218);
INSERT INTO perm_perfil VALUES (5110, 1, 218);
INSERT INTO perm_perfil VALUES (5111, 1, 218);
INSERT INTO perm_perfil VALUES (5112, 1, 218);
INSERT INTO perm_perfil VALUES (5113, 1, 218);
INSERT INTO perm_perfil VALUES (5114, 1, 218);
INSERT INTO perm_perfil VALUES (5115, 1, 218);
INSERT INTO perm_perfil VALUES (5116, 1, 218);
INSERT INTO perm_perfil VALUES (5117, 1, 218);
INSERT INTO perm_perfil VALUES (5118, 1, 218);
INSERT INTO perm_perfil VALUES (5119, 1, 218);
INSERT INTO perm_perfil VALUES (5120, 1, 218);
INSERT INTO perm_perfil VALUES (5121, 1, 218);
INSERT INTO perm_perfil VALUES (5122, 1, 218);
INSERT INTO perm_perfil VALUES (5123, 1, 218);
INSERT INTO perm_perfil VALUES (5124, 1, 218);
INSERT INTO perm_perfil VALUES (5125, 1, 218);
INSERT INTO perm_perfil VALUES (5126, 1, 218);
INSERT INTO perm_perfil VALUES (5127, 1, 218);
INSERT INTO perm_perfil VALUES (5128, 1, 218);
INSERT INTO perm_perfil VALUES (5129, 1, 218);
INSERT INTO perm_perfil VALUES (5130, 1, 218);
INSERT INTO perm_perfil VALUES (5131, 1, 218);
INSERT INTO perm_perfil VALUES (5132, 1, 218);
INSERT INTO perm_perfil VALUES (5133, 1, 218);
INSERT INTO perm_perfil VALUES (5134, 1, 218);
INSERT INTO perm_perfil VALUES (5135, 1, 218);
INSERT INTO perm_perfil VALUES (5136, 1, 218);
INSERT INTO perm_perfil VALUES (5137, 1, 218);
INSERT INTO perm_perfil VALUES (5138, 1, 218);
INSERT INTO perm_perfil VALUES (5139, 1, 218);
INSERT INTO perm_perfil VALUES (5140, 1, 218);
INSERT INTO perm_perfil VALUES (5141, 1, 218);
INSERT INTO perm_perfil VALUES (5142, 1, 218);
INSERT INTO perm_perfil VALUES (5143, 1, 218);
INSERT INTO perm_perfil VALUES (5144, 1, 218);
INSERT INTO perm_perfil VALUES (5145, 1, 218);
INSERT INTO perm_perfil VALUES (5146, 1, 218);
INSERT INTO perm_perfil VALUES (5147, 1, 218);
INSERT INTO perm_perfil VALUES (5148, 1, 218);
INSERT INTO perm_perfil VALUES (5149, 1, 47);
INSERT INTO perm_perfil VALUES (5150, 1, 59);
INSERT INTO perm_perfil VALUES (5151, 1, 68);
INSERT INTO perm_perfil VALUES (5152, 1, 69);
INSERT INTO perm_perfil VALUES (5153, 1, 214);
INSERT INTO perm_perfil VALUES (5154, 1, 4);
INSERT INTO perm_perfil VALUES (5155, 1, 6);
INSERT INTO perm_perfil VALUES (5156, 1, 75);
INSERT INTO perm_perfil VALUES (5157, 1, 66);
INSERT INTO perm_perfil VALUES (5158, 1, 29);
INSERT INTO perm_perfil VALUES (5159, 1, 31);
INSERT INTO perm_perfil VALUES (5160, 1, 41);
INSERT INTO perm_perfil VALUES (5161, 1, 217);
INSERT INTO perm_perfil VALUES (5162, 1, 220);
INSERT INTO perm_perfil VALUES (5163, 1, 65);
INSERT INTO perm_perfil VALUES (5164, 1, 51);
INSERT INTO perm_perfil VALUES (5165, 1, 221);
INSERT INTO perm_perfil VALUES (5166, 1, 216);
INSERT INTO perm_perfil VALUES (5167, 1, 5);
INSERT INTO perm_perfil VALUES (5168, 1, 43);
INSERT INTO perm_perfil VALUES (5169, 1, 36);
INSERT INTO perm_perfil VALUES (5170, 1, 67);
INSERT INTO perm_perfil VALUES (5171, 1, 7);
INSERT INTO perm_perfil VALUES (5172, 1, 33);
INSERT INTO perm_perfil VALUES (5173, 1, 28);
INSERT INTO perm_perfil VALUES (5174, 1, 56);
INSERT INTO perm_perfil VALUES (5175, 1, 27);
INSERT INTO perm_perfil VALUES (5176, 1, 26);
INSERT INTO perm_perfil VALUES (5177, 1, 48);
INSERT INTO perm_perfil VALUES (5178, 1, 63);
INSERT INTO perm_perfil VALUES (5179, 1, 64);
INSERT INTO perm_perfil VALUES (5180, 1, 227);
INSERT INTO perm_perfil VALUES (5181, 1, 8);
INSERT INTO perm_perfil VALUES (5182, 1, 76);
INSERT INTO perm_perfil VALUES (5183, 1, 77);
INSERT INTO perm_perfil VALUES (5184, 1, 78);
INSERT INTO perm_perfil VALUES (5185, 1, 79);
INSERT INTO perm_perfil VALUES (5186, 1, 80);
INSERT INTO perm_perfil VALUES (5187, 1, 81);
INSERT INTO perm_perfil VALUES (5188, 1, 82);
INSERT INTO perm_perfil VALUES (5189, 1, 83);
INSERT INTO perm_perfil VALUES (5190, 1, 84);
INSERT INTO perm_perfil VALUES (5191, 1, 85);
INSERT INTO perm_perfil VALUES (5192, 1, 86);
INSERT INTO perm_perfil VALUES (5193, 1, 87);
INSERT INTO perm_perfil VALUES (5194, 1, 88);
INSERT INTO perm_perfil VALUES (5195, 1, 89);
INSERT INTO perm_perfil VALUES (5196, 1, 90);
INSERT INTO perm_perfil VALUES (5197, 1, 91);
INSERT INTO perm_perfil VALUES (5198, 1, 92);
INSERT INTO perm_perfil VALUES (5199, 1, 93);
INSERT INTO perm_perfil VALUES (5200, 1, 94);
INSERT INTO perm_perfil VALUES (5201, 1, 95);
INSERT INTO perm_perfil VALUES (5202, 1, 96);
INSERT INTO perm_perfil VALUES (5203, 1, 97);
INSERT INTO perm_perfil VALUES (5204, 1, 98);
INSERT INTO perm_perfil VALUES (5205, 1, 99);
INSERT INTO perm_perfil VALUES (5206, 1, 100);
INSERT INTO perm_perfil VALUES (5207, 1, 101);
INSERT INTO perm_perfil VALUES (5208, 1, 102);
INSERT INTO perm_perfil VALUES (5209, 1, 103);
INSERT INTO perm_perfil VALUES (5210, 1, 104);
INSERT INTO perm_perfil VALUES (5211, 1, 105);
INSERT INTO perm_perfil VALUES (5212, 1, 106);
INSERT INTO perm_perfil VALUES (5213, 1, 107);
INSERT INTO perm_perfil VALUES (5214, 1, 108);
INSERT INTO perm_perfil VALUES (5215, 1, 109);
INSERT INTO perm_perfil VALUES (5216, 1, 110);
INSERT INTO perm_perfil VALUES (5217, 1, 111);
INSERT INTO perm_perfil VALUES (5218, 1, 112);
INSERT INTO perm_perfil VALUES (5219, 1, 113);
INSERT INTO perm_perfil VALUES (5220, 1, 114);
INSERT INTO perm_perfil VALUES (5221, 1, 115);
INSERT INTO perm_perfil VALUES (5222, 1, 116);
INSERT INTO perm_perfil VALUES (5223, 1, 117);
INSERT INTO perm_perfil VALUES (5224, 1, 118);
INSERT INTO perm_perfil VALUES (5225, 1, 119);
INSERT INTO perm_perfil VALUES (5226, 1, 120);
INSERT INTO perm_perfil VALUES (5227, 1, 121);
INSERT INTO perm_perfil VALUES (5228, 1, 122);
INSERT INTO perm_perfil VALUES (5229, 1, 123);
INSERT INTO perm_perfil VALUES (5230, 1, 124);
INSERT INTO perm_perfil VALUES (5231, 1, 125);
INSERT INTO perm_perfil VALUES (5232, 1, 126);
INSERT INTO perm_perfil VALUES (5233, 1, 127);
INSERT INTO perm_perfil VALUES (5234, 1, 128);
INSERT INTO perm_perfil VALUES (5235, 1, 129);
INSERT INTO perm_perfil VALUES (5236, 1, 130);
INSERT INTO perm_perfil VALUES (5237, 1, 131);
INSERT INTO perm_perfil VALUES (5238, 1, 132);
INSERT INTO perm_perfil VALUES (5239, 1, 133);
INSERT INTO perm_perfil VALUES (5240, 1, 134);
INSERT INTO perm_perfil VALUES (5241, 1, 135);
INSERT INTO perm_perfil VALUES (5242, 1, 136);
INSERT INTO perm_perfil VALUES (5243, 1, 137);
INSERT INTO perm_perfil VALUES (5244, 1, 138);
INSERT INTO perm_perfil VALUES (5245, 1, 139);
INSERT INTO perm_perfil VALUES (5246, 1, 140);
INSERT INTO perm_perfil VALUES (5247, 1, 141);
INSERT INTO perm_perfil VALUES (5248, 1, 142);
INSERT INTO perm_perfil VALUES (5249, 1, 143);
INSERT INTO perm_perfil VALUES (5250, 1, 144);
INSERT INTO perm_perfil VALUES (5251, 1, 145);
INSERT INTO perm_perfil VALUES (5252, 1, 146);
INSERT INTO perm_perfil VALUES (5253, 1, 147);
INSERT INTO perm_perfil VALUES (5254, 1, 148);
INSERT INTO perm_perfil VALUES (5255, 1, 149);
INSERT INTO perm_perfil VALUES (5256, 1, 150);
INSERT INTO perm_perfil VALUES (5257, 1, 151);
INSERT INTO perm_perfil VALUES (5258, 1, 152);
INSERT INTO perm_perfil VALUES (5259, 1, 153);
INSERT INTO perm_perfil VALUES (5260, 1, 154);
INSERT INTO perm_perfil VALUES (5261, 1, 155);
INSERT INTO perm_perfil VALUES (5262, 1, 156);
INSERT INTO perm_perfil VALUES (5263, 1, 157);
INSERT INTO perm_perfil VALUES (5264, 1, 158);
INSERT INTO perm_perfil VALUES (5265, 1, 159);
INSERT INTO perm_perfil VALUES (5266, 1, 160);
INSERT INTO perm_perfil VALUES (5267, 1, 161);
INSERT INTO perm_perfil VALUES (5268, 1, 162);
INSERT INTO perm_perfil VALUES (5269, 1, 163);
INSERT INTO perm_perfil VALUES (5270, 1, 164);
INSERT INTO perm_perfil VALUES (5271, 1, 165);
INSERT INTO perm_perfil VALUES (5272, 1, 166);
INSERT INTO perm_perfil VALUES (5273, 1, 167);
INSERT INTO perm_perfil VALUES (5274, 1, 168);
INSERT INTO perm_perfil VALUES (5275, 1, 169);
INSERT INTO perm_perfil VALUES (5276, 1, 170);
INSERT INTO perm_perfil VALUES (5277, 1, 171);
INSERT INTO perm_perfil VALUES (5278, 1, 172);
INSERT INTO perm_perfil VALUES (5279, 1, 173);
INSERT INTO perm_perfil VALUES (5280, 1, 174);
INSERT INTO perm_perfil VALUES (5281, 1, 175);
INSERT INTO perm_perfil VALUES (5282, 1, 176);
INSERT INTO perm_perfil VALUES (5283, 1, 177);
INSERT INTO perm_perfil VALUES (5284, 1, 178);
INSERT INTO perm_perfil VALUES (5285, 1, 179);
INSERT INTO perm_perfil VALUES (5286, 1, 180);
INSERT INTO perm_perfil VALUES (5287, 1, 181);
INSERT INTO perm_perfil VALUES (5288, 1, 182);
INSERT INTO perm_perfil VALUES (5289, 1, 183);
INSERT INTO perm_perfil VALUES (5290, 1, 184);
INSERT INTO perm_perfil VALUES (5291, 1, 185);
INSERT INTO perm_perfil VALUES (5292, 1, 186);
INSERT INTO perm_perfil VALUES (5293, 1, 187);
INSERT INTO perm_perfil VALUES (5294, 1, 188);
INSERT INTO perm_perfil VALUES (5295, 1, 189);
INSERT INTO perm_perfil VALUES (5296, 1, 190);
INSERT INTO perm_perfil VALUES (5297, 1, 191);
INSERT INTO perm_perfil VALUES (5298, 1, 192);
INSERT INTO perm_perfil VALUES (5299, 1, 193);
INSERT INTO perm_perfil VALUES (5300, 1, 194);
INSERT INTO perm_perfil VALUES (5301, 1, 195);
INSERT INTO perm_perfil VALUES (5302, 1, 196);
INSERT INTO perm_perfil VALUES (5303, 1, 197);
INSERT INTO perm_perfil VALUES (5304, 1, 198);
INSERT INTO perm_perfil VALUES (5305, 1, 199);
INSERT INTO perm_perfil VALUES (5306, 1, 200);
INSERT INTO perm_perfil VALUES (5307, 1, 201);
INSERT INTO perm_perfil VALUES (5308, 1, 202);
INSERT INTO perm_perfil VALUES (5309, 1, 203);
INSERT INTO perm_perfil VALUES (5310, 1, 204);
INSERT INTO perm_perfil VALUES (5311, 1, 205);
INSERT INTO perm_perfil VALUES (5312, 1, 206);
INSERT INTO perm_perfil VALUES (5313, 1, 207);
INSERT INTO perm_perfil VALUES (5314, 1, 208);
INSERT INTO perm_perfil VALUES (5315, 1, 209);
INSERT INTO perm_perfil VALUES (5316, 1, 210);
INSERT INTO perm_perfil VALUES (5317, 1, 211);
INSERT INTO perm_perfil VALUES (5318, 1, 212);


--
-- TOC entry 2902 (class 0 OID 0)
-- Dependencies: 415
-- Name: perm_perfil_id_seq; Type: SEQUENCE SET; Schema: acl; Owner: postgres
--

SELECT pg_catalog.setval('perm_perfil_id_seq', 5318, true);


--
-- TOC entry 2872 (class 0 OID 18258)
-- Dependencies: 416
-- Data for Name: perm_sistema; Type: TABLE DATA; Schema: acl; Owner: postgres
--

INSERT INTO perm_sistema VALUES (4, 43, 2);
INSERT INTO perm_sistema VALUES (9, 26, 1);
INSERT INTO perm_sistema VALUES (10, 26, 2);
INSERT INTO perm_sistema VALUES (11, 20, 1);
INSERT INTO perm_sistema VALUES (12, 20, 2);
INSERT INTO perm_sistema VALUES (13, 31, 2);
INSERT INTO perm_sistema VALUES (3, 2, 3);
INSERT INTO perm_sistema VALUES (2, 3, 2);
INSERT INTO perm_sistema VALUES (1, 2, 1);


--
-- TOC entry 2903 (class 0 OID 0)
-- Dependencies: 417
-- Name: perm_sistema_id_seq; Type: SEQUENCE SET; Schema: acl; Owner: postgres
--

SELECT pg_catalog.setval('perm_sistema_id_seq', 13, true);


--
-- TOC entry 2874 (class 0 OID 18263)
-- Dependencies: 418
-- Data for Name: perm_usuario; Type: TABLE DATA; Schema: acl; Owner: postgres
--

INSERT INTO perm_usuario VALUES (4, 44, 8);


--
-- TOC entry 2904 (class 0 OID 0)
-- Dependencies: 419
-- Name: perm_usuario_id_seq; Type: SEQUENCE SET; Schema: acl; Owner: postgres
--

SELECT pg_catalog.setval('perm_usuario_id_seq', 4, true);


--
-- TOC entry 2876 (class 0 OID 18268)
-- Dependencies: 420
-- Data for Name: permissao; Type: TABLE DATA; Schema: acl; Owner: postgres
--

INSERT INTO permissao VALUES (1, 'Gerenciar');
INSERT INTO permissao VALUES (2, 'Menus');
INSERT INTO permissao VALUES (3, 'Funções');
INSERT INTO permissao VALUES (4, 'Perfis');
INSERT INTO permissao VALUES (5, 'Rotinas');
INSERT INTO permissao VALUES (6, 'Permissão');
INSERT INTO permissao VALUES (7, 'Sistemas');
INSERT INTO permissao VALUES (9, 'Cadastro');
INSERT INTO permissao VALUES (10, 'Movimentação');
INSERT INTO permissao VALUES (11, 'Caixa');
INSERT INTO permissao VALUES (12, 'Financeiro');
INSERT INTO permissao VALUES (13, 'Relatórios');
INSERT INTO permissao VALUES (14, 'Administração');
INSERT INTO permissao VALUES (15, 'Extras');
INSERT INTO permissao VALUES (16, 'Ajuda');
INSERT INTO permissao VALUES (17, 'Cliente');
INSERT INTO permissao VALUES (18, 'Banco');
INSERT INTO permissao VALUES (19, 'Despesa');
INSERT INTO permissao VALUES (20, 'Fornecedor');
INSERT INTO permissao VALUES (21, 'Fonte de Receita');
INSERT INTO permissao VALUES (22, 'Pagamento');
INSERT INTO permissao VALUES (23, 'Procedimentos');
INSERT INTO permissao VALUES (24, 'Endereço');
INSERT INTO permissao VALUES (25, 'Outros Cadastros');
INSERT INTO permissao VALUES (26, 'Tipo Pagamento');
INSERT INTO permissao VALUES (27, 'Tipo Documento');
INSERT INTO permissao VALUES (28, 'Tabela de Preço');
INSERT INTO permissao VALUES (29, 'Portador');
INSERT INTO permissao VALUES (30, 'Categoria');
INSERT INTO permissao VALUES (31, 'Procedimento');
INSERT INTO permissao VALUES (32, 'Grupo');
INSERT INTO permissao VALUES (33, 'Subgrupo');
INSERT INTO permissao VALUES (34, 'Classe');
INSERT INTO permissao VALUES (35, 'Divisão');
INSERT INTO permissao VALUES (36, 'Seção');
INSERT INTO permissao VALUES (37, 'Cidade');
INSERT INTO permissao VALUES (38, 'Estado');
INSERT INTO permissao VALUES (39, 'Bairro');
INSERT INTO permissao VALUES (40, 'Estado Civil');
INSERT INTO permissao VALUES (41, 'Profissão');
INSERT INTO permissao VALUES (42, 'Kit Procedimento');
INSERT INTO permissao VALUES (43, 'Sala');
INSERT INTO permissao VALUES (44, 'Configuração de Horário - Agenda');
INSERT INTO permissao VALUES (45, 'Centro de Custo');
INSERT INTO permissao VALUES (46, 'Avaliação');
INSERT INTO permissao VALUES (47, 'Orçamento');
INSERT INTO permissao VALUES (48, 'Venda');
INSERT INTO permissao VALUES (49, 'Cortesia');
INSERT INTO permissao VALUES (50, 'Agenda');
INSERT INTO permissao VALUES (51, 'Recibo');
INSERT INTO permissao VALUES (52, 'Gerenciamento Caixa');
INSERT INTO permissao VALUES (53, 'Consultar Caixa');
INSERT INTO permissao VALUES (54, 'Documentos a Receber');
INSERT INTO permissao VALUES (55, 'Documentos a Pagar');
INSERT INTO permissao VALUES (56, 'Tesouraria');
INSERT INTO permissao VALUES (57, 'Vendas');
INSERT INTO permissao VALUES (58, 'Documentos');
INSERT INTO permissao VALUES (59, 'Orçamentos');
INSERT INTO permissao VALUES (60, 'Agenda');
INSERT INTO permissao VALUES (61, 'Cortesias');
INSERT INTO permissao VALUES (62, 'Produção');
INSERT INTO permissao VALUES (63, 'Vendas no Período');
INSERT INTO permissao VALUES (64, 'Vendas por Produto');
INSERT INTO permissao VALUES (65, 'Recebimentos por Venda');
INSERT INTO permissao VALUES (66, 'Planejamento de Vendas');
INSERT INTO permissao VALUES (67, 'Serviços X Execuções');
INSERT INTO permissao VALUES (69, 'Orçamentos por Produto');
INSERT INTO permissao VALUES (71, 'Cortesia no Período');
INSERT INTO permissao VALUES (72, 'Execuções no Período');
INSERT INTO permissao VALUES (70, 'Agendamento no Período');
INSERT INTO permissao VALUES (68, 'Orçamentos no Período');
INSERT INTO permissao VALUES (73, 'Cadastro de Funcionários');
INSERT INTO permissao VALUES (74, 'Campanha');
INSERT INTO permissao VALUES (75, 'Planejamento de Vendas');
INSERT INTO permissao VALUES (81, 'Incluir Funcionário');
INSERT INTO permissao VALUES (82, 'Editar Funcionário');
INSERT INTO permissao VALUES (83, 'Excluir Funcionário');
INSERT INTO permissao VALUES (84, 'Incluir Planejamento Venda');
INSERT INTO permissao VALUES (86, 'Abrir Caixa');
INSERT INTO permissao VALUES (87, 'Lançar Débito');
INSERT INTO permissao VALUES (88, 'Fechar Caixa');
INSERT INTO permissao VALUES (89, 'Incluir Doc. Pagar');
INSERT INTO permissao VALUES (90, 'Editar Doc. Pagar');
INSERT INTO permissao VALUES (91, 'Excluir Doc. Pagar');
INSERT INTO permissao VALUES (92, 'Incluir Doc. Receber');
INSERT INTO permissao VALUES (93, 'Alterar Doc. Receber');
INSERT INTO permissao VALUES (94, 'Baixa Doc. Receber');
INSERT INTO permissao VALUES (95, 'Negociação Doc. Receber');
INSERT INTO permissao VALUES (96, 'Antecipação Doc. Receber');
INSERT INTO permissao VALUES (97, 'Incluir Avaliação');
INSERT INTO permissao VALUES (98, 'Editar Avaliação');
INSERT INTO permissao VALUES (99, 'Cancelar Avaliação');
INSERT INTO permissao VALUES (100, 'Imprimir Avaliação');
INSERT INTO permissao VALUES (101, 'Incluir Cortesia');
INSERT INTO permissao VALUES (102, 'Cancelar Cortesia');
INSERT INTO permissao VALUES (103, 'Realizar Pag. Cortesia');
INSERT INTO permissao VALUES (104, 'Imprimir Recibo');
INSERT INTO permissao VALUES (105, 'Incluir Venda');
INSERT INTO permissao VALUES (106, 'Pagamento Venda');
INSERT INTO permissao VALUES (107, 'Cancelar Venda');
INSERT INTO permissao VALUES (108, 'Incluir Orçamento');
INSERT INTO permissao VALUES (109, 'Editar Orçamento');
INSERT INTO permissao VALUES (110, 'Confirmar Orçamento');
INSERT INTO permissao VALUES (111, 'Imprimir Orçamento');
INSERT INTO permissao VALUES (112, 'Incluir Agenda');
INSERT INTO permissao VALUES (113, 'Confirmar Agenda');
INSERT INTO permissao VALUES (114, 'Transferir Agenda');
INSERT INTO permissao VALUES (115, 'Cancelar Agenda');
INSERT INTO permissao VALUES (116, 'Bloquear Agenda');
INSERT INTO permissao VALUES (117, 'Reservar Agenda');
INSERT INTO permissao VALUES (118, 'Atendido Agenda');
INSERT INTO permissao VALUES (119, 'Evoluções Agenda');
INSERT INTO permissao VALUES (120, 'Presença Agenda');
INSERT INTO permissao VALUES (121, 'Cadastro Agenda');
INSERT INTO permissao VALUES (122, 'Orçamento Agenda');
INSERT INTO permissao VALUES (123, 'Emissão Agenda');
INSERT INTO permissao VALUES (124, 'Fechamento Agenda');
INSERT INTO permissao VALUES (125, 'Cancelamento Agenda');
INSERT INTO permissao VALUES (126, 'Imprimir Con. Caixa');
INSERT INTO permissao VALUES (127, 'Presta Contas Con. Caixa');
INSERT INTO permissao VALUES (128, 'Incluir Campanha');
INSERT INTO permissao VALUES (129, 'Editar Campanha');
INSERT INTO permissao VALUES (130, 'Abrir Caixa Tesouraria');
INSERT INTO permissao VALUES (131, 'Fechar Caixa Tesouraria');
INSERT INTO permissao VALUES (132, 'Compensar Tesouraria');
INSERT INTO permissao VALUES (133, 'Emitir Cheque Tesouraria');
INSERT INTO permissao VALUES (134, 'Débito em Conta Tesouraria');
INSERT INTO permissao VALUES (135, 'Pagamento Tesouraria');
INSERT INTO permissao VALUES (136, 'Lançamento Tesouraria');
INSERT INTO permissao VALUES (137, 'Gravar Acerto Tesouraria');
INSERT INTO permissao VALUES (138, 'Incluir Banco');
INSERT INTO permissao VALUES (139, 'Editar Banco');
INSERT INTO permissao VALUES (140, 'Excluir Banco');
INSERT INTO permissao VALUES (141, 'Incluir Cliente');
INSERT INTO permissao VALUES (142, 'Editar Cliente');
INSERT INTO permissao VALUES (143, 'Excluir Cliente');
INSERT INTO permissao VALUES (144, 'Incluir Despesa');
INSERT INTO permissao VALUES (145, 'Editar Despesa');
INSERT INTO permissao VALUES (146, 'Excluir Despesa');
INSERT INTO permissao VALUES (147, 'Incluir Fonte Receita');
INSERT INTO permissao VALUES (148, 'Editar Fonte Receita');
INSERT INTO permissao VALUES (149, 'Excluir Fonte Receita');
INSERT INTO permissao VALUES (150, 'Incluir Fornecedor');
INSERT INTO permissao VALUES (151, 'Editar Fornecedor');
INSERT INTO permissao VALUES (152, 'Excluir Fornecedor');
INSERT INTO permissao VALUES (153, 'Incluir Classe');
INSERT INTO permissao VALUES (154, 'Editar Classe');
INSERT INTO permissao VALUES (155, 'Excluir Classe');
INSERT INTO permissao VALUES (156, 'Incluir Divisão');
INSERT INTO permissao VALUES (157, 'Editar Divisão');
INSERT INTO permissao VALUES (158, 'Excluir Divisão');
INSERT INTO permissao VALUES (159, 'Incluir Grupo');
INSERT INTO permissao VALUES (160, 'Editar Grupo');
INSERT INTO permissao VALUES (161, 'Excluir Grupo');
INSERT INTO permissao VALUES (162, 'Incluir Procedimento');
INSERT INTO permissao VALUES (164, 'Excluir Procedimento');
INSERT INTO permissao VALUES (165, 'Incluir Seção');
INSERT INTO permissao VALUES (166, 'Editar Seção');
INSERT INTO permissao VALUES (167, 'Excluir Seção');
INSERT INTO permissao VALUES (168, 'Incluir Subgrupo');
INSERT INTO permissao VALUES (169, 'Editar Subgrupo');
INSERT INTO permissao VALUES (170, 'Excluir Subgrupo');
INSERT INTO permissao VALUES (171, 'Incluir Categoria');
INSERT INTO permissao VALUES (172, 'Editar Categoria');
INSERT INTO permissao VALUES (173, 'Excluir Categoria');
INSERT INTO permissao VALUES (174, 'Incluir Portador');
INSERT INTO permissao VALUES (175, 'Editar Portador');
INSERT INTO permissao VALUES (176, 'Excluir Portador');
INSERT INTO permissao VALUES (177, 'Incluir Tabela de Preço');
INSERT INTO permissao VALUES (179, 'Excluir Tabela de Preço');
INSERT INTO permissao VALUES (183, 'Incluir Tipo Pag.');
INSERT INTO permissao VALUES (184, 'Editar Tipo Pag.');
INSERT INTO permissao VALUES (185, 'Excluir Tipo Pag.');
INSERT INTO permissao VALUES (186, 'Incluir Centro de Custo');
INSERT INTO permissao VALUES (188, 'Excluir Centro de Custo');
INSERT INTO permissao VALUES (189, 'Incluir Conf. Agenda');
INSERT INTO permissao VALUES (190, 'Editar Conf. Agenda');
INSERT INTO permissao VALUES (191, 'Excluir Conf. Agenda');
INSERT INTO permissao VALUES (192, 'Incluir Estado Civil');
INSERT INTO permissao VALUES (193, 'Editar Estado Civil');
INSERT INTO permissao VALUES (194, 'Excluir Estado Civil');
INSERT INTO permissao VALUES (195, 'Incluir Kit Procedimento');
INSERT INTO permissao VALUES (196, 'Editar Kit Procedimento');
INSERT INTO permissao VALUES (197, 'Excluir Kit Procedimento');
INSERT INTO permissao VALUES (198, 'Incluir Profissão');
INSERT INTO permissao VALUES (199, 'Editar Profissão');
INSERT INTO permissao VALUES (200, 'Excluir Profissão');
INSERT INTO permissao VALUES (201, 'Incluir Sala');
INSERT INTO permissao VALUES (202, 'Editar Sala');
INSERT INTO permissao VALUES (203, 'Excluir Sala');
INSERT INTO permissao VALUES (204, 'Incluir Bairro');
INSERT INTO permissao VALUES (205, 'Editar Bairro');
INSERT INTO permissao VALUES (206, 'Excluir Bairro');
INSERT INTO permissao VALUES (207, 'Incluir Cidade');
INSERT INTO permissao VALUES (208, 'Editar Cidade');
INSERT INTO permissao VALUES (209, 'Excluir Cidade');
INSERT INTO permissao VALUES (210, 'Incluir Estado');
INSERT INTO permissao VALUES (211, 'Editar Estado');
INSERT INTO permissao VALUES (212, 'Excluir Estado');
INSERT INTO permissao VALUES (178, 'Editar Tabela de Preço');
INSERT INTO permissao VALUES (163, 'Editar Procedimento');
INSERT INTO permissao VALUES (85, 'Editar Planejamento Venda');
INSERT INTO permissao VALUES (187, 'Editar Centro de Custo');
INSERT INTO permissao VALUES (182, 'Excluir Tipo Doc.');
INSERT INTO permissao VALUES (181, 'Editar Tipo Doc.');
INSERT INTO permissao VALUES (180, 'Incluir Tipo Doc.');
INSERT INTO permissao VALUES (8, 'Função Teste');
INSERT INTO permissao VALUES (76, 'Principal Clientes');
INSERT INTO permissao VALUES (77, 'Principal Caixa');
INSERT INTO permissao VALUES (78, 'Principal Orçamento');
INSERT INTO permissao VALUES (79, 'Principal Vendas');
INSERT INTO permissao VALUES (80, 'Principal Relatórios');
INSERT INTO permissao VALUES (213, 'Financeiro');
INSERT INTO permissao VALUES (214, 'Pagar');
INSERT INTO permissao VALUES (215, 'Comissão por Indicador');
INSERT INTO permissao VALUES (216, 'Receber');
INSERT INTO permissao VALUES (217, 'Promissória');
INSERT INTO permissao VALUES (218, 'Meus Leilões');
INSERT INTO permissao VALUES (219, 'Desistência de Venda');
INSERT INTO permissao VALUES (220, 'Receber');
INSERT INTO permissao VALUES (221, 'Relatório Pagar');
INSERT INTO permissao VALUES (222, 'A Receber');
INSERT INTO permissao VALUES (223, 'A Pagar');
INSERT INTO permissao VALUES (224, 'sub');
INSERT INTO permissao VALUES (225, 'SubReceber');
INSERT INTO permissao VALUES (226, 'Estorno de Baixas');
INSERT INTO permissao VALUES (227, 'Clientes');


--
-- TOC entry 2905 (class 0 OID 0)
-- Dependencies: 421
-- Name: permissao_id_seq; Type: SEQUENCE SET; Schema: acl; Owner: postgres
--

SELECT pg_catalog.setval('permissao_id_seq', 227, true);


--
-- TOC entry 2878 (class 0 OID 18276)
-- Dependencies: 422
-- Data for Name: rotina; Type: TABLE DATA; Schema: acl; Owner: postgres
--

INSERT INTO rotina VALUES (1, 'ROT-ACL', 'GERAL', 1);
INSERT INTO rotina VALUES (2, 'ROT-CLIN-MENUS', 'MENUS', 2);
INSERT INTO rotina VALUES (3, 'ROT-CLIN-FUNCOES', 'FUNCOES', 2);


--
-- TOC entry 2906 (class 0 OID 0)
-- Dependencies: 423
-- Name: rotina_id_seq; Type: SEQUENCE SET; Schema: acl; Owner: postgres
--

SELECT pg_catalog.setval('rotina_id_seq', 3, true);


--
-- TOC entry 2880 (class 0 OID 18284)
-- Dependencies: 424
-- Data for Name: sistema; Type: TABLE DATA; Schema: acl; Owner: postgres
--

INSERT INTO sistema VALUES (1, 'Acess Control List', 'acl', '/pages/acl/principal.faces', '../../imgs/acl.png', '2.0.0', true);
INSERT INTO sistema VALUES (2, 'Beauty Clinic', 'clin', '/pages/clin/principal.faces', '../../imgs/imagem.png', '1.0.0', true);
INSERT INTO sistema VALUES (3, 'Leilao
', 'lei', '/pages/comum/selectLeilao.faces', '../../imgs/acl.png', '2.0', true);


--
-- TOC entry 2907 (class 0 OID 0)
-- Dependencies: 425
-- Name: sistema_id_seq; Type: SEQUENCE SET; Schema: acl; Owner: postgres
--

SELECT pg_catalog.setval('sistema_id_seq', 3, true);


--
-- TOC entry 2717 (class 2606 OID 19264)
-- Name: pk_funcao_id; Type: CONSTRAINT; Schema: acl; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY funcao
    ADD CONSTRAINT pk_funcao_id PRIMARY KEY (id);


--
-- TOC entry 2721 (class 2606 OID 19266)
-- Name: pk_menu_id; Type: CONSTRAINT; Schema: acl; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT pk_menu_id PRIMARY KEY (id);


--
-- TOC entry 2723 (class 2606 OID 19268)
-- Name: pk_menu_sistema_id; Type: CONSTRAINT; Schema: acl; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY menu_sistema
    ADD CONSTRAINT pk_menu_sistema_id PRIMARY KEY (id);


--
-- TOC entry 2725 (class 2606 OID 19270)
-- Name: pk_perfil_id; Type: CONSTRAINT; Schema: acl; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perfil
    ADD CONSTRAINT pk_perfil_id PRIMARY KEY (id);


--
-- TOC entry 2727 (class 2606 OID 19272)
-- Name: pk_perm_geral_id; Type: CONSTRAINT; Schema: acl; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perm_geral
    ADD CONSTRAINT pk_perm_geral_id PRIMARY KEY (id);


--
-- TOC entry 2729 (class 2606 OID 19274)
-- Name: pk_perm_perfil_id; Type: CONSTRAINT; Schema: acl; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perm_perfil
    ADD CONSTRAINT pk_perm_perfil_id PRIMARY KEY (id);


--
-- TOC entry 2731 (class 2606 OID 19276)
-- Name: pk_perm_sistema_id; Type: CONSTRAINT; Schema: acl; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perm_sistema
    ADD CONSTRAINT pk_perm_sistema_id PRIMARY KEY (id);


--
-- TOC entry 2733 (class 2606 OID 19278)
-- Name: pk_perm_usuario_id; Type: CONSTRAINT; Schema: acl; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perm_usuario
    ADD CONSTRAINT pk_perm_usuario_id PRIMARY KEY (id);


--
-- TOC entry 2735 (class 2606 OID 19280)
-- Name: pk_permissao_id; Type: CONSTRAINT; Schema: acl; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permissao
    ADD CONSTRAINT pk_permissao_id PRIMARY KEY (id);


--
-- TOC entry 2737 (class 2606 OID 19282)
-- Name: pk_rotina_id; Type: CONSTRAINT; Schema: acl; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY rotina
    ADD CONSTRAINT pk_rotina_id PRIMARY KEY (id);


--
-- TOC entry 2739 (class 2606 OID 19284)
-- Name: pk_sistema_id; Type: CONSTRAINT; Schema: acl; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sistema
    ADD CONSTRAINT pk_sistema_id PRIMARY KEY (id);


--
-- TOC entry 2719 (class 2606 OID 19286)
-- Name: un_funcao_codigo; Type: CONSTRAINT; Schema: acl; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY funcao
    ADD CONSTRAINT un_funcao_codigo UNIQUE (codigo);


--
-- TOC entry 2740 (class 2606 OID 19287)
-- Name: fk_funcao_rotina_id; Type: FK CONSTRAINT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY funcao
    ADD CONSTRAINT fk_funcao_rotina_id FOREIGN KEY (id_rotina) REFERENCES rotina(id);


--
-- TOC entry 2741 (class 2606 OID 19292)
-- Name: fk_menu_rotina_id; Type: FK CONSTRAINT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY menu
    ADD CONSTRAINT fk_menu_rotina_id FOREIGN KEY (id_rotina) REFERENCES rotina(id);


--
-- TOC entry 2743 (class 2606 OID 19297)
-- Name: fk_menu_sis_menu_id; Type: FK CONSTRAINT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY menu_sistema
    ADD CONSTRAINT fk_menu_sis_menu_id FOREIGN KEY (id_menu) REFERENCES menu(id);


--
-- TOC entry 2742 (class 2606 OID 19302)
-- Name: fk_menu_sis_sistema_id; Type: FK CONSTRAINT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY menu_sistema
    ADD CONSTRAINT fk_menu_sis_sistema_id FOREIGN KEY (id_sistema) REFERENCES sistema(id);


--
-- TOC entry 2746 (class 2606 OID 19307)
-- Name: fk_perm_geral_funcao_id; Type: FK CONSTRAINT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY perm_geral
    ADD CONSTRAINT fk_perm_geral_funcao_id FOREIGN KEY (id_funcao) REFERENCES funcao(id);


--
-- TOC entry 2745 (class 2606 OID 19312)
-- Name: fk_perm_geral_menu_id; Type: FK CONSTRAINT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY perm_geral
    ADD CONSTRAINT fk_perm_geral_menu_id FOREIGN KEY (id_menu) REFERENCES menu(id);


--
-- TOC entry 2744 (class 2606 OID 19317)
-- Name: fk_perm_geral_permissao_id; Type: FK CONSTRAINT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY perm_geral
    ADD CONSTRAINT fk_perm_geral_permissao_id FOREIGN KEY (id_permissao) REFERENCES permissao(id);


--
-- TOC entry 2748 (class 2606 OID 19322)
-- Name: fk_perm_perf_pefil_id; Type: FK CONSTRAINT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY perm_perfil
    ADD CONSTRAINT fk_perm_perf_pefil_id FOREIGN KEY (id_perfil) REFERENCES perfil(id);


--
-- TOC entry 2747 (class 2606 OID 19327)
-- Name: fk_perm_perf_permissao_id; Type: FK CONSTRAINT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY perm_perfil
    ADD CONSTRAINT fk_perm_perf_permissao_id FOREIGN KEY (id_permissao) REFERENCES permissao(id);


--
-- TOC entry 2749 (class 2606 OID 19332)
-- Name: fk_perm_sis_sistema_id; Type: FK CONSTRAINT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY perm_sistema
    ADD CONSTRAINT fk_perm_sis_sistema_id FOREIGN KEY (id_sistema) REFERENCES sistema(id);


--
-- TOC entry 2750 (class 2606 OID 19337)
-- Name: fk_perm_usuario_permissao_id; Type: FK CONSTRAINT; Schema: acl; Owner: postgres
--

ALTER TABLE ONLY perm_usuario
    ADD CONSTRAINT fk_perm_usuario_permissao_id FOREIGN KEY (id_permissao) REFERENCES permissao(id);


-- Completed on 2016-03-24 21:02:48

--
-- PostgreSQL database dump complete
--

