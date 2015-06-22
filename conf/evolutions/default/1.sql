# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table aluno (
  id                        bigserial not null,
  nome                      varchar(255),
  matricula                 varchar(255),
  usuario_id                bigint,
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
alter table documento add constraint fk_documento_anexo_2 foreign key (anexo_id) references anexo (id);
create index ix_documento_anexo_2 on documento (anexo_id);
alter table professor add constraint fk_professor_usuario_3 foreign key (usuario_id) references usuario (id);
create index ix_professor_usuario_3 on professor (usuario_id);



# --- !Downs

drop table if exists aluno cascade;

drop table if exists anexo cascade;

drop table if exists documento cascade;

drop table if exists professor cascade;

drop table if exists usuario cascade;

