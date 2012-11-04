--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: gera_matricula(integer, integer); Type: FUNCTION; Schema: public; Owner: sige
--

CREATE FUNCTION gera_matricula(integer, integer) RETURNS character varying
    LANGUAGE sql
    AS $_$
SELECT trim(substr(now(),0,5))||$1||$2$_$;


ALTER FUNCTION public.gera_matricula(integer, integer) OWNER TO sige;

--
-- Name: pg_file_length(text); Type: FUNCTION; Schema: public; Owner: sige
--

CREATE FUNCTION pg_file_length(text) RETURNS bigint
    LANGUAGE sql STRICT
    AS $_$SELECT len FROM pg_file_stat($1) AS s(len int8, c timestamp, a timestamp, m timestamp, i bool)$_$;


ALTER FUNCTION public.pg_file_length(text) OWNER TO sige;

--
-- Name: pg_file_rename(text, text); Type: FUNCTION; Schema: public; Owner: sige
--

CREATE FUNCTION pg_file_rename(text, text) RETURNS boolean
    LANGUAGE sql STRICT
    AS $_$SELECT pg_file_rename($1, $2, NULL); $_$;


ALTER FUNCTION public.pg_file_rename(text, text) OWNER TO sige;

SET default_tablespace = '';

SET default_with_oids = true;

--
-- Name: alunos; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE alunos (
    cd_aluno integer NOT NULL,
    nome_aluno character varying(60),
    endereco character varying(100),
    telefone character varying(15),
    nome_pai character varying(60),
    nome_mae character varying(60),
    nome_responsavel character varying(60),
    nacionalidade character varying(30),
    naturalidade character varying(30),
    data_nascimento date,
    documentos_ok boolean,
    cd_entidade integer,
    ativo boolean,
    escolaridade integer
);


ALTER TABLE public.alunos OWNER TO sige;

--
-- Name: alunos_cd_aluno_seq; Type: SEQUENCE; Schema: public; Owner: sige
--

CREATE SEQUENCE alunos_cd_aluno_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.alunos_cd_aluno_seq OWNER TO sige;

--
-- Name: alunos_cd_aluno_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sige
--

ALTER SEQUENCE alunos_cd_aluno_seq OWNED BY alunos.cd_aluno;


SET default_with_oids = false;

--
-- Name: boletins; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE boletins (
    cd_boletim integer NOT NULL,
    ano_letivo integer NOT NULL,
    cd_turma integer NOT NULL,
    cd_entidade integer NOT NULL,
    cd_matricula integer NOT NULL
);


ALTER TABLE public.boletins OWNER TO sige;

--
-- Name: boletins_cd_boletim_seq; Type: SEQUENCE; Schema: public; Owner: sige
--

CREATE SEQUENCE boletins_cd_boletim_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.boletins_cd_boletim_seq OWNER TO sige;

--
-- Name: boletins_cd_boletim_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sige
--

ALTER SEQUENCE boletins_cd_boletim_seq OWNED BY boletins.cd_boletim;


--
-- Name: disciplina_serie; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE disciplina_serie (
    cd_disciplina integer,
    cd_serie integer
);


ALTER TABLE public.disciplina_serie OWNER TO sige;

--
-- Name: disciplinas; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE disciplinas (
    cd_disciplina integer NOT NULL,
    nome_disciplina character varying(20) NOT NULL,
    sigla_disciplina character(3) NOT NULL
);


ALTER TABLE public.disciplinas OWNER TO sige;

--
-- Name: disciplinas_cd_disciplina_seq; Type: SEQUENCE; Schema: public; Owner: sige
--

CREATE SEQUENCE disciplinas_cd_disciplina_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.disciplinas_cd_disciplina_seq OWNER TO sige;

--
-- Name: disciplinas_cd_disciplina_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sige
--

ALTER SEQUENCE disciplinas_cd_disciplina_seq OWNED BY disciplinas.cd_disciplina;


SET default_with_oids = true;

--
-- Name: entidades; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE entidades (
    cd_entidade integer NOT NULL,
    nome_entidade character varying(90) NOT NULL,
    endereco character varying(100),
    telefone character varying(15),
    qtde_salas integer,
    ano_letivo integer DEFAULT int4(date_part('year'::text, now())) NOT NULL
);


ALTER TABLE public.entidades OWNER TO sige;

--
-- Name: entidades_cd_entidade_seq; Type: SEQUENCE; Schema: public; Owner: sige
--

CREATE SEQUENCE entidades_cd_entidade_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.entidades_cd_entidade_seq OWNER TO sige;

--
-- Name: entidades_cd_entidade_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sige
--

ALTER SEQUENCE entidades_cd_entidade_seq OWNED BY entidades.cd_entidade;


SET default_with_oids = false;

--
-- Name: horarios; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE horarios (
    cd_horario integer NOT NULL,
    cd_turma integer NOT NULL
);


ALTER TABLE public.horarios OWNER TO sige;

--
-- Name: horarios_cd_horario_seq; Type: SEQUENCE; Schema: public; Owner: sige
--

CREATE SEQUENCE horarios_cd_horario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.horarios_cd_horario_seq OWNER TO sige;

--
-- Name: horarios_cd_horario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sige
--

ALTER SEQUENCE horarios_cd_horario_seq OWNED BY horarios.cd_horario;


--
-- Name: itensboletim; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE itensboletim (
    cd_boletim integer NOT NULL,
    cd_disciplina integer NOT NULL,
    bimes_letivo integer NOT NULL,
    nota integer NOT NULL,
    faltas integer NOT NULL,
    cd_item integer DEFAULT nextval(('itensboletim_cd_item_seq'::text)::regclass) NOT NULL
);


ALTER TABLE public.itensboletim OWNER TO sige;

--
-- Name: itensboletim_cd_item_seq; Type: SEQUENCE; Schema: public; Owner: sige
--

CREATE SEQUENCE itensboletim_cd_item_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.itensboletim_cd_item_seq OWNER TO sige;

--
-- Name: itenshorario; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE itenshorario (
    cd_horario integer NOT NULL,
    cd_disciplina integer NOT NULL,
    dia_da_semana integer NOT NULL,
    numero_aula integer NOT NULL,
    cd_item integer NOT NULL
);


ALTER TABLE public.itenshorario OWNER TO sige;

--
-- Name: matriculas; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE matriculas (
    cd_matricula integer NOT NULL,
    numeracao character(8) NOT NULL,
    cd_aluno integer NOT NULL,
    cd_turma integer NOT NULL
);


ALTER TABLE public.matriculas OWNER TO sige;

--
-- Name: matriculas_cd_matricula_seq; Type: SEQUENCE; Schema: public; Owner: sige
--

CREATE SEQUENCE matriculas_cd_matricula_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.matriculas_cd_matricula_seq OWNER TO sige;

--
-- Name: matriculas_cd_matricula_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sige
--

ALTER SEQUENCE matriculas_cd_matricula_seq OWNED BY matriculas.cd_matricula;


SET default_with_oids = true;

--
-- Name: modulos; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE modulos (
    cd_modulo integer NOT NULL,
    ds_modulo character varying(30) NOT NULL,
    cd_modulo_pai integer NOT NULL
);


ALTER TABLE public.modulos OWNER TO sige;

--
-- Name: modulos_cd_modulo_seq; Type: SEQUENCE; Schema: public; Owner: sige
--

CREATE SEQUENCE modulos_cd_modulo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.modulos_cd_modulo_seq OWNER TO sige;

--
-- Name: modulos_cd_modulo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sige
--

ALTER SEQUENCE modulos_cd_modulo_seq OWNED BY modulos.cd_modulo;


--
-- Name: usuarios; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE usuarios (
    cd_usuario integer NOT NULL,
    cd_entidade integer NOT NULL,
    nome_usuario character varying(60) NOT NULL,
    cpf character varying(11) NOT NULL,
    login character varying(20),
    senha character varying(50),
    ativo boolean
);


ALTER TABLE public.usuarios OWNER TO sige;

--
-- Name: nome_senha; Type: VIEW; Schema: public; Owner: sige
--

CREATE VIEW nome_senha AS
    SELECT usuarios.nome_usuario AS nome, usuarios.login, decode((usuarios.senha)::text, 'base64'::text) AS decode FROM usuarios;


ALTER TABLE public.nome_senha OWNER TO sige;

--
-- Name: penalidades; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE penalidades (
    cd_penalidade integer NOT NULL,
    tipo_penalidade character varying(40) NOT NULL,
    descricao character varying(200),
    cd_matricula integer NOT NULL
);


ALTER TABLE public.penalidades OWNER TO sige;

--
-- Name: penalidades_cd_penalidade_seq; Type: SEQUENCE; Schema: public; Owner: sige
--

CREATE SEQUENCE penalidades_cd_penalidade_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.penalidades_cd_penalidade_seq OWNER TO sige;

--
-- Name: penalidades_cd_penalidade_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sige
--

ALTER SEQUENCE penalidades_cd_penalidade_seq OWNED BY penalidades.cd_penalidade;


--
-- Name: permissao_usuario; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE permissao_usuario (
    cd_permissao integer NOT NULL,
    cd_usuario integer NOT NULL
);


ALTER TABLE public.permissao_usuario OWNER TO sige;

--
-- Name: permissoes; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE permissoes (
    cd_permissao integer NOT NULL,
    ds_permissao character varying(20) NOT NULL,
    cd_modulo integer NOT NULL
);


ALTER TABLE public.permissoes OWNER TO sige;

--
-- Name: permissao_por_usuario; Type: VIEW; Schema: public; Owner: sige
--

CREATE VIEW permissao_por_usuario AS
    SELECT u.nome_usuario AS usuario, p.ds_permissao AS permissao, m.ds_modulo AS modulo FROM modulos m, permissao_usuario pu, usuarios u, permissoes p WHERE (((p.cd_permissao = pu.cd_permissao) AND (m.cd_modulo = p.cd_modulo)) AND (u.cd_usuario = pu.cd_usuario));


ALTER TABLE public.permissao_por_usuario OWNER TO sige;

--
-- Name: permissoes_cd_permissao_seq; Type: SEQUENCE; Schema: public; Owner: sige
--

CREATE SEQUENCE permissoes_cd_permissao_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.permissoes_cd_permissao_seq OWNER TO sige;

--
-- Name: permissoes_cd_permissao_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sige
--

ALTER SEQUENCE permissoes_cd_permissao_seq OWNED BY permissoes.cd_permissao;


--
-- Name: sequencia_turma; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE sequencia_turma (
    cd_turma integer NOT NULL,
    sequencia integer DEFAULT 0
);


ALTER TABLE public.sequencia_turma OWNER TO sige;

SET default_with_oids = false;

--
-- Name: series; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE series (
    cd_serie integer NOT NULL,
    numero integer NOT NULL
);


ALTER TABLE public.series OWNER TO sige;

--
-- Name: series_cd_serie_seq; Type: SEQUENCE; Schema: public; Owner: sige
--

CREATE SEQUENCE series_cd_serie_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.series_cd_serie_seq OWNER TO sige;

--
-- Name: series_cd_serie_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sige
--

ALTER SEQUENCE series_cd_serie_seq OWNED BY series.cd_serie;


--
-- Name: turmas; Type: TABLE; Schema: public; Owner: sige; Tablespace: 
--

CREATE TABLE turmas (
    cd_turma integer NOT NULL,
    ano integer NOT NULL,
    literal character(1) NOT NULL,
    cd_entidade integer NOT NULL,
    cd_serie integer NOT NULL
);


ALTER TABLE public.turmas OWNER TO sige;

--
-- Name: turmas_cd_turma_seq; Type: SEQUENCE; Schema: public; Owner: sige
--

CREATE SEQUENCE turmas_cd_turma_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.turmas_cd_turma_seq OWNER TO sige;

--
-- Name: turmas_cd_turma_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sige
--

ALTER SEQUENCE turmas_cd_turma_seq OWNED BY turmas.cd_turma;


--
-- Name: usuarios_cd_usuario_seq; Type: SEQUENCE; Schema: public; Owner: sige
--

CREATE SEQUENCE usuarios_cd_usuario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuarios_cd_usuario_seq OWNER TO sige;

--
-- Name: usuarios_cd_usuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sige
--

ALTER SEQUENCE usuarios_cd_usuario_seq OWNED BY usuarios.cd_usuario;


--
-- Name: cd_aluno; Type: DEFAULT; Schema: public; Owner: sige
--

ALTER TABLE ONLY alunos ALTER COLUMN cd_aluno SET DEFAULT nextval('alunos_cd_aluno_seq'::regclass);


--
-- Name: cd_boletim; Type: DEFAULT; Schema: public; Owner: sige
--

ALTER TABLE ONLY boletins ALTER COLUMN cd_boletim SET DEFAULT nextval('boletins_cd_boletim_seq'::regclass);


--
-- Name: cd_disciplina; Type: DEFAULT; Schema: public; Owner: sige
--

ALTER TABLE ONLY disciplinas ALTER COLUMN cd_disciplina SET DEFAULT nextval('disciplinas_cd_disciplina_seq'::regclass);


--
-- Name: cd_entidade; Type: DEFAULT; Schema: public; Owner: sige
--

ALTER TABLE ONLY entidades ALTER COLUMN cd_entidade SET DEFAULT nextval('entidades_cd_entidade_seq'::regclass);


--
-- Name: cd_horario; Type: DEFAULT; Schema: public; Owner: sige
--

ALTER TABLE ONLY horarios ALTER COLUMN cd_horario SET DEFAULT nextval('horarios_cd_horario_seq'::regclass);


--
-- Name: cd_matricula; Type: DEFAULT; Schema: public; Owner: sige
--

ALTER TABLE ONLY matriculas ALTER COLUMN cd_matricula SET DEFAULT nextval('matriculas_cd_matricula_seq'::regclass);


--
-- Name: cd_modulo; Type: DEFAULT; Schema: public; Owner: sige
--

ALTER TABLE ONLY modulos ALTER COLUMN cd_modulo SET DEFAULT nextval('modulos_cd_modulo_seq'::regclass);


--
-- Name: cd_penalidade; Type: DEFAULT; Schema: public; Owner: sige
--

ALTER TABLE ONLY penalidades ALTER COLUMN cd_penalidade SET DEFAULT nextval('penalidades_cd_penalidade_seq'::regclass);


--
-- Name: cd_permissao; Type: DEFAULT; Schema: public; Owner: sige
--

ALTER TABLE ONLY permissoes ALTER COLUMN cd_permissao SET DEFAULT nextval('permissoes_cd_permissao_seq'::regclass);


--
-- Name: cd_serie; Type: DEFAULT; Schema: public; Owner: sige
--

ALTER TABLE ONLY series ALTER COLUMN cd_serie SET DEFAULT nextval('series_cd_serie_seq'::regclass);


--
-- Name: cd_turma; Type: DEFAULT; Schema: public; Owner: sige
--

ALTER TABLE ONLY turmas ALTER COLUMN cd_turma SET DEFAULT nextval('turmas_cd_turma_seq'::regclass);


--
-- Name: cd_usuario; Type: DEFAULT; Schema: public; Owner: sige
--

ALTER TABLE ONLY usuarios ALTER COLUMN cd_usuario SET DEFAULT nextval('usuarios_cd_usuario_seq'::regclass);


--
-- Name: PK_boletim; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY boletins
    ADD CONSTRAINT "PK_boletim" PRIMARY KEY (cd_boletim);


--
-- Name: PK_disciplinas; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY disciplinas
    ADD CONSTRAINT "PK_disciplinas" PRIMARY KEY (cd_disciplina);


--
-- Name: PK_horarios; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY horarios
    ADD CONSTRAINT "PK_horarios" PRIMARY KEY (cd_horario);


--
-- Name: PK_itensboletim; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY itensboletim
    ADD CONSTRAINT "PK_itensboletim" PRIMARY KEY (cd_item);


--
-- Name: PK_itenshorario; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY itenshorario
    ADD CONSTRAINT "PK_itenshorario" PRIMARY KEY (cd_item);


--
-- Name: PK_matriculas; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY matriculas
    ADD CONSTRAINT "PK_matriculas" PRIMARY KEY (cd_matricula);


--
-- Name: PK_penalidade; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY penalidades
    ADD CONSTRAINT "PK_penalidade" PRIMARY KEY (cd_penalidade);


--
-- Name: PK_series; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY series
    ADD CONSTRAINT "PK_series" PRIMARY KEY (cd_serie);


--
-- Name: PK_turmas; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY turmas
    ADD CONSTRAINT "PK_turmas" PRIMARY KEY (cd_turma);


--
-- Name: pk_alunos; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY alunos
    ADD CONSTRAINT pk_alunos PRIMARY KEY (cd_aluno);


--
-- Name: pk_entidades; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY entidades
    ADD CONSTRAINT pk_entidades PRIMARY KEY (cd_entidade);


--
-- Name: pk_modulos; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY modulos
    ADD CONSTRAINT pk_modulos PRIMARY KEY (cd_modulo);


--
-- Name: pk_permissoes; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY permissoes
    ADD CONSTRAINT pk_permissoes PRIMARY KEY (cd_permissao);


--
-- Name: pk_sequencia; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY sequencia_turma
    ADD CONSTRAINT pk_sequencia PRIMARY KEY (cd_turma);


--
-- Name: pk_usuarios; Type: CONSTRAINT; Schema: public; Owner: sige; Tablespace: 
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT pk_usuarios PRIMARY KEY (cd_usuario);


--
-- Name: FK_Matricula; Type: INDEX; Schema: public; Owner: sige; Tablespace: 
--

CREATE INDEX "FK_Matricula" ON boletins USING btree (cd_matricula);


--
-- Name: cd_matricula; Type: INDEX; Schema: public; Owner: sige; Tablespace: 
--

CREATE INDEX cd_matricula ON penalidades USING btree (cd_matricula);


--
-- Name: FK_alunos; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY matriculas
    ADD CONSTRAINT "FK_alunos" FOREIGN KEY (cd_aluno) REFERENCES alunos(cd_aluno) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: FK_boletim; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY itensboletim
    ADD CONSTRAINT "FK_boletim" FOREIGN KEY (cd_boletim) REFERENCES boletins(cd_boletim) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: FK_disciplina; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY itensboletim
    ADD CONSTRAINT "FK_disciplina" FOREIGN KEY (cd_disciplina) REFERENCES disciplinas(cd_disciplina) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: FK_disciplina; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY itenshorario
    ADD CONSTRAINT "FK_disciplina" FOREIGN KEY (cd_disciplina) REFERENCES disciplinas(cd_disciplina) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: FK_disciplinas; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY disciplina_serie
    ADD CONSTRAINT "FK_disciplinas" FOREIGN KEY (cd_disciplina) REFERENCES disciplinas(cd_disciplina) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: FK_entidade; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY boletins
    ADD CONSTRAINT "FK_entidade" FOREIGN KEY (cd_entidade) REFERENCES entidades(cd_entidade) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: FK_entidades; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY turmas
    ADD CONSTRAINT "FK_entidades" FOREIGN KEY (cd_entidade) REFERENCES entidades(cd_entidade) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: FK_horario; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY itenshorario
    ADD CONSTRAINT "FK_horario" FOREIGN KEY (cd_horario) REFERENCES horarios(cd_horario) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: FK_matricula; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY penalidades
    ADD CONSTRAINT "FK_matricula" FOREIGN KEY (cd_matricula) REFERENCES matriculas(cd_matricula) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: FK_series; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY disciplina_serie
    ADD CONSTRAINT "FK_series" FOREIGN KEY (cd_serie) REFERENCES series(cd_serie) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: FK_series; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY turmas
    ADD CONSTRAINT "FK_series" FOREIGN KEY (cd_serie) REFERENCES series(cd_serie) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: FK_turma; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY boletins
    ADD CONSTRAINT "FK_turma" FOREIGN KEY (cd_turma) REFERENCES turmas(cd_turma) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: FK_turmas; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY matriculas
    ADD CONSTRAINT "FK_turmas" FOREIGN KEY (cd_turma) REFERENCES turmas(cd_turma) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: cd_matricula; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY boletins
    ADD CONSTRAINT cd_matricula FOREIGN KEY (cd_matricula) REFERENCES matriculas(cd_matricula) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: fk_entidades; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY usuarios
    ADD CONSTRAINT fk_entidades FOREIGN KEY (cd_entidade) REFERENCES entidades(cd_entidade);


--
-- Name: fk_entidades; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY alunos
    ADD CONSTRAINT fk_entidades FOREIGN KEY (cd_entidade) REFERENCES entidades(cd_entidade);


--
-- Name: fk_modulos; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY modulos
    ADD CONSTRAINT fk_modulos FOREIGN KEY (cd_modulo_pai) REFERENCES modulos(cd_modulo);


--
-- Name: fk_modulos; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY permissoes
    ADD CONSTRAINT fk_modulos FOREIGN KEY (cd_modulo) REFERENCES modulos(cd_modulo);


--
-- Name: fk_permissoes; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY permissao_usuario
    ADD CONSTRAINT fk_permissoes FOREIGN KEY (cd_permissao) REFERENCES permissoes(cd_permissao);


--
-- Name: fk_turma; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY horarios
    ADD CONSTRAINT fk_turma FOREIGN KEY (cd_turma) REFERENCES turmas(cd_turma);


--
-- Name: fk_turmas; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY sequencia_turma
    ADD CONSTRAINT fk_turmas FOREIGN KEY (cd_turma) REFERENCES turmas(cd_turma);


--
-- Name: fk_usuarios; Type: FK CONSTRAINT; Schema: public; Owner: sige
--

ALTER TABLE ONLY permissao_usuario
    ADD CONSTRAINT fk_usuarios FOREIGN KEY (cd_usuario) REFERENCES usuarios(cd_usuario);


--
-- Name: public; Type: ACL; Schema: -; Owner: rodolfo
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM rodolfo;
GRANT ALL ON SCHEMA public TO rodolfo;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

