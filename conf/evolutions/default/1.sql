# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table aluno (
  id                        bigserial not null,
  nome                      varchar(255),
  matricula                 varchar(255),
  usuario_id                bigint,
  projeto_id                bigint,
  constraint uq_aluno_usuario_id unique (usuario_id),
  constraint pk_aluno primary key (id))
;

create table anexo (
  id                        bigserial not null,
  descricao                 varchar(255),
  content_type              varchar(255),
  arquivo                   bytea,
  constraint pk_anexo primary key (id))
;

create table documento (
  id                        bigserial not null,
  titulo                    varchar(255),
  descricao                 TEXT,
  numero                    integer,
  anexo_id                  bigint,
  constraint uq_documento_anexo_id unique (anexo_id),
  constraint pk_documento primary key (id))
;

create table professor (
  id                        bigserial not null,
  nome                      varchar(255),
  usuario_id                bigint,
  constraint uq_professor_usuario_id unique (usuario_id),
  constraint pk_professor primary key (id))
;

create table projeto (
  id                        bigserial not null,
  titulo                    varchar(255),
  descricao                 TEXT,
  quantidade_max_de_participantes integer,
  anexo_id                  bigint,
  professor_id              bigint,
  status_projeto            varchar(7),
  constraint ck_projeto_status_projeto check (status_projeto in ('ABERTO','FECHADO')),
  constraint uq_projeto_anexo_id unique (anexo_id),
  constraint pk_projeto primary key (id))
;

create table relatorio (
  id                        bigserial not null,
  quantidade                integer,
  descricao                 TEXT,
  data_de_entrega           timestamp,
  status_relatorio          varchar(12),
  anexo_id                  bigint,
  constraint ck_relatorio_status_relatorio check (status_relatorio in ('NAO_INICIADO','INICIADO','FECHADO')),
  constraint uq_relatorio_anexo_id unique (anexo_id),
  constraint pk_relatorio primary key (id))
;

create table usuario (
  id                        bigserial not null,
  email                     varchar(255),
  senha                     varchar(255),
  token                     varchar(255),
  ativo                     boolean,
  data_de_cadastro          timestamp,
  permissao                 varchar(46),
  constraint ck_usuario_permissao check (permissao in ('ADMINISTRADOR','COORDENADOR','PROFESSOR_DISCIPLINA','PROFESSOR_ORIENTADOR','ALUNO','SEM_ACESSO')),
  constraint pk_usuario primary key (id))
;

alter table aluno add constraint fk_aluno_usuario_1 foreign key (usuario_id) references usuario (id);
create index ix_aluno_usuario_1 on aluno (usuario_id);
alter table aluno add constraint fk_aluno_projeto_2 foreign key (projeto_id) references projeto (id);
create index ix_aluno_projeto_2 on aluno (projeto_id);
alter table documento add constraint fk_documento_anexo_3 foreign key (anexo_id) references anexo (id);
create index ix_documento_anexo_3 on documento (anexo_id);
alter table professor add constraint fk_professor_usuario_4 foreign key (usuario_id) references usuario (id);
create index ix_professor_usuario_4 on professor (usuario_id);
alter table projeto add constraint fk_projeto_anexo_5 foreign key (anexo_id) references anexo (id);
create index ix_projeto_anexo_5 on projeto (anexo_id);
alter table projeto add constraint fk_projeto_professor_6 foreign key (professor_id) references professor (id);
create index ix_projeto_professor_6 on projeto (professor_id);
alter table relatorio add constraint fk_relatorio_anexo_7 foreign key (anexo_id) references anexo (id);
create index ix_relatorio_anexo_7 on relatorio (anexo_id);



# --- !Downs

drop table if exists aluno cascade;

drop table if exists anexo cascade;

drop table if exists documento cascade;

drop table if exists professor cascade;

drop table if exists projeto cascade;

drop table if exists relatorio cascade;

drop table if exists usuario cascade;

