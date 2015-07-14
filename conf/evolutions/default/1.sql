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

create table arquivo (
  id                        bigserial not null,
  data                      timestamp,
  anexo_id                  bigint,
  relatorio_id              bigint,
  aluno_id                  bigint,
  constraint uq_arquivo_anexo_id unique (anexo_id),
  constraint pk_arquivo primary key (id))
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
  descricao                 TEXT,
  data_de_entrega           timestamp,
  status_relatorio          varchar(12),
  anexo_id                  bigint,
  constraint ck_relatorio_status_relatorio check (status_relatorio in ('NAO_INICIADO','INICIADO','FECHADO')),
  constraint uq_relatorio_anexo_id unique (anexo_id),
  constraint pk_relatorio primary key (id))
;

create table situacao_projeto (
  id                        bigserial not null,
  status_projeto            varchar(7),
  data                      timestamp,
  projeto_id                bigint,
  usuario_id                bigint,
  constraint ck_situacao_projeto_status_projeto check (status_projeto in ('ABERTO','FECHADO')),
  constraint pk_situacao_projeto primary key (id))
;

create table situacao_relatorio (
  id                        bigserial not null,
  data                      timestamp,
  status_relatorio          varchar(12),
  relatorio_id              bigint,
  usuario_id                bigint,
  constraint ck_situacao_relatorio_status_relatorio check (status_relatorio in ('NAO_INICIADO','INICIADO','FECHADO')),
  constraint pk_situacao_relatorio primary key (id))
;

create table solicitacao_de_participacao (
  id                        bigserial not null,
  data                      timestamp,
  observacao                TEXT,
  status_solicitacao_de_participacao varchar(6),
  aluno_id                  bigint,
  projeto_id                bigint,
  constraint ck_solicitacao_de_participacao_status_solicitacao_de_participacao check (status_solicitacao_de_participacao in ('ACEITO','NEGADO')),
  constraint pk_solicitacao_de_participacao primary key (id))
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
alter table arquivo add constraint fk_arquivo_anexo_3 foreign key (anexo_id) references anexo (id);
create index ix_arquivo_anexo_3 on arquivo (anexo_id);
alter table arquivo add constraint fk_arquivo_relatorio_4 foreign key (relatorio_id) references relatorio (id);
create index ix_arquivo_relatorio_4 on arquivo (relatorio_id);
alter table arquivo add constraint fk_arquivo_aluno_5 foreign key (aluno_id) references aluno (id);
create index ix_arquivo_aluno_5 on arquivo (aluno_id);
alter table documento add constraint fk_documento_anexo_6 foreign key (anexo_id) references anexo (id);
create index ix_documento_anexo_6 on documento (anexo_id);
alter table professor add constraint fk_professor_usuario_7 foreign key (usuario_id) references usuario (id);
create index ix_professor_usuario_7 on professor (usuario_id);
alter table projeto add constraint fk_projeto_anexo_8 foreign key (anexo_id) references anexo (id);
create index ix_projeto_anexo_8 on projeto (anexo_id);
alter table projeto add constraint fk_projeto_professor_9 foreign key (professor_id) references professor (id);
create index ix_projeto_professor_9 on projeto (professor_id);
alter table relatorio add constraint fk_relatorio_anexo_10 foreign key (anexo_id) references anexo (id);
create index ix_relatorio_anexo_10 on relatorio (anexo_id);
alter table situacao_projeto add constraint fk_situacao_projeto_projeto_11 foreign key (projeto_id) references projeto (id);
create index ix_situacao_projeto_projeto_11 on situacao_projeto (projeto_id);
alter table situacao_projeto add constraint fk_situacao_projeto_usuario_12 foreign key (usuario_id) references usuario (id);
create index ix_situacao_projeto_usuario_12 on situacao_projeto (usuario_id);
alter table situacao_relatorio add constraint fk_situacao_relatorio_relator_13 foreign key (relatorio_id) references relatorio (id);
create index ix_situacao_relatorio_relator_13 on situacao_relatorio (relatorio_id);
alter table situacao_relatorio add constraint fk_situacao_relatorio_usuario_14 foreign key (usuario_id) references usuario (id);
create index ix_situacao_relatorio_usuario_14 on situacao_relatorio (usuario_id);
alter table solicitacao_de_participacao add constraint fk_solicitacao_de_participaca_15 foreign key (aluno_id) references aluno (id);
create index ix_solicitacao_de_participaca_15 on solicitacao_de_participacao (aluno_id);
alter table solicitacao_de_participacao add constraint fk_solicitacao_de_participaca_16 foreign key (projeto_id) references projeto (id);
create index ix_solicitacao_de_participaca_16 on solicitacao_de_participacao (projeto_id);



# --- !Downs

drop table if exists aluno cascade;

drop table if exists anexo cascade;

drop table if exists arquivo cascade;

drop table if exists documento cascade;

drop table if exists professor cascade;

drop table if exists projeto cascade;

drop table if exists relatorio cascade;

drop table if exists situacao_projeto cascade;

drop table if exists situacao_relatorio cascade;

drop table if exists solicitacao_de_participacao cascade;

drop table if exists usuario cascade;

