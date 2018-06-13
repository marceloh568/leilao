CREATE TABLE IF NOT EXISTS leilao.func
(
  codfunc integer NOT NULL DEFAULT nextval(('clin.func_codfunc_seq'::text)::regclass),
  nome character varying(30) NOT NULL,
  senha character varying(10),
  codfuncao integer,
  dtnasc date,
  endereco character varying(200),
  complemento character varying(200),
  codbairro integer,
  codcidade integer,
  codestado integer,
  cep character varying(8),
  rg character varying(20),
  cpf character varying(11) NOT NULL,
  dtcadastro date,
  dtentrada date,
  dtdemissao date,
  titulo character varying(20),
  titzona character varying(5),
  titsecao character varying(5),
  titlocal character varying(10),
  ctps character varying(20),
  ctserie character varying(10),
  login character varying(25),
  nivseg character varying(1),
  opcad integer,
  gerente character varying(1),
  codestadocivil integer,
  ativo character varying(1) DEFAULT 'S'::character varying,
  adm boolean,
  id_perfil integer,
  CONSTRAINT func_pkey PRIMARY KEY (codfunc),
  CONSTRAINT func_chk CHECK (ativo::text = ANY (ARRAY['S'::character varying::text, 'N'::character varying::text]))
);

CREATE TABLE IF NOT EXISTS leilao.raca
(
  id serial NOT NULL,
  nome character varying(30) NOT NULL,
  criacao_tipo character varying(10),
  criacao boolean,
  CONSTRAINT raca_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS leilao.banco (
  id SERIAL,
  numero VARCHAR(3) NOT NULL,
  descricao VARCHAR(80) NOT NULL,
  CONSTRAINT banco_numero_key UNIQUE(numero),
  CONSTRAINT banco_pkey PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS leilao.vendedor
(
  id serial NOT NULL,
  nome character varying,
  apelido character varying,
  situacao character varying,
  cpf_cgc character varying,
  endereco character varying,
  bairro character varying,
  cidade character varying,
  cep character varying,
  uf character varying,
  email character varying,
  fone character varying,
  email2 character varying,
  fone2 character varying,
  fax character varying,
  residencial character varying,
  comercial character varying,
  data_atualizacao date,
  CONSTRAINT vendedor_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS leilao.conta_corrente
(
  id serial NOT NULL,
  agencia character varying,
  titular character varying,
  conta character varying,
  id_banco integer,
  id_vendedor integer,
  CONSTRAINT conta_corrente_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS leilao.vendedor_raca
(
  id serial NOT NULL,
  id_raca integer,
  id_vendedor integer,
  CONSTRAINT vendedor_raca_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS leilao.leilao
(
  id serial NOT NULL,
  nome character varying,
  endereco character varying,
  bairro character varying,
  cidade character varying,
  uf character varying,
  leiloeiro character varying,
  gerente character varying,
  ativo boolean,
  fechado boolean,
  data date,
  utiliza_comissao_vendedor boolean,
  utiliza_taxa_inscricao boolean,
  comissao_vendedor double precision,
  comissao_comprador double precision,
  total_vendido double precision,
  total_comissao double precision,
  total_taxa_inscricao double precision,
  numero_parcela integer,
  CONSTRAINT leilao_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS leilao.agrupar_parcela
(
  id serial NOT NULL,
  agrupar_parcela integer
);

CREATE TABLE IF NOT EXISTS leilao.leilao_parcela
(
  id serial NOT NULL,
  id_leilao integer,
  id_agrupar_parcela integer
);

CREATE TABLE IF NOT EXISTS leilao.imagem
(
  id integer,
  imagem bytea[]
);

CREATE TABLE IF NOT EXISTS leilao.leilao_imagem
(
  id serial NOT NULL,
  id_leilao integer,
  id_imagem integer
);

CREATE TABLE IF NOT EXISTS leilao.exceptions
(
  id integer,
  mensagem character varying(4000),
  nome_metodo character varying(255),
  id_usuario integer,
  data_hora time with time zone
);

CREATE TABLE if NOT EXISTS leilao.animal
(
  id serial NOT NULL,
  pelagem character varying(255),
  nome character varying(255),
  data_nascimento date,
  numero_de_registro character varying(255),
  numero_de_controle character varying(255),
  sexo character varying(1),
  criador character varying(255),
  id_raca integer,
  pai character varying(255),
  avo_masculino_pai character varying(255),
  avo_feminino_pai character varying(255),
  bisavo_masculino_parte_avo_masculino_pai character varying(255),
  bisavo_feminimo_parte_avo_masculino_pai character varying(255),
  mae character varying(255),
  avo_masculino_mae character varying(255),
  avo_feminino_mae character varying(255),
  bisavo_masculino_parte_avo_masculino_mae character varying(255),
  bisavo_feminimo_parte_avo_masculino_mae character varying(255)
);

CREATE TABLE IF NOT EXISTS leilao.lote
(
  id serial NOT NULL,
  preposto character varying(255),
  criador character varying(255),
  observacao character varying(255),
  id_vendedor integer,
  tipo_compra character varying(255),
  data_compra date,
  valor_lance double precision,
  numero_parcela integer,
  valor_total double precision,
  valor_desconto double precision,
  taxa_inscricao double precision,
  taxa_antecipada double precision,
  comissao_vendedor double precision,
  comissao_comprador double precision,
  numero character varying(255),
  id_leilao integer,
  id_lote_origem integer
);

CREATE TABLE IF NOT EXISTS leilao.lote_animal
(
  id serial NOT NULL,
  id_lote integer,
  id_animal integer
);

CREATE TABLE IF NOT EXISTS leilao.compra_lote
(
  id serial NOT NULL,
  id_vendedor integer,
  tipo_compra character varying(255),
  data_compra date,
  valor_lance double precision,
  numero_parcela integer,
  valor_total double precision,
  valor_desconto double precision,
  taxa_inscricao double precision,
  taxa_antecipada double precision,
  comissao_vendedor double precision,
  comissao_comprador double precision,
  id_leilao integer
);

CREATE TABLE IF NOT EXISTS leilao.leilao_lote
(
  id serial NOT NULL,
  id_leilao integer,
  id_lote integer,
  CONSTRAINT leilao_lote_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS leilao.lote2
(
  idleilao integer,
  idlote integer,
  idanimal integer,
  idraca integer,
  pelagem character varying(255),
  nome character varying(255),
  data_nascimento date,
  numero_de_registro character varying(255),
  numero_de_controle character varying(255),
  sexo character varying(1),
  criador character varying(255),
  id_raca integer,
  pai character varying(255),
  avo_masculino_pai character varying(255),
  avo_feminino_pai character varying(255),
  bisavo_masculino_parte_avo_masculino_pai character varying(255),
  bisavo_feminimo_parte_avo_masculino_pai character varying(255),
  mae character varying(255),
  avo_masculino_mae character varying(255),
  avo_feminino_mae character varying(255),
  bisavo_masculino_parte_avo_masculino_mae character varying(255),
  bisavo_feminimo_parte_avo_masculino_mae character varying(255)
);

CREATE TABLE IF NOT EXISTS leilao.lote_vendedores
(
  id serial NOT NULL,
  id_lote integer,
  id_vendedor integer,
  percentual double precision
);

CREATE TABLE IF NOT EXISTS leilao.lote_compradores
(
  id serial NOT NULL,
  id_lote integer,
  id_comprador integer,
  percentual double precision,
  valor_lance double precision,
  valor_desconto double precision,
  numero_parcela integer
);

CREATE TABLE IF NOT EXISTS leilao.parcelas_comprador
(
  id integer,
  id_lote integer,
  numero_parcela integer,
  valor_parcela double precision,
  id_comprador integer
);


/**
 * add campo table compradores..
 */
ALTER TABLE leilao.lote_compradores ADD editavel boolean;
ALTER TABLE leilao.compra_lote ADD vendedor_proporcional double precision;
ALTER TABLE leilao.compra_lote ADD id_lote integer;
ALTER TABLE leilao.compra_lote ADD id_comprador integer;
ALTER TABLE leilao.parcelas_comprador add data_vencimento date;


