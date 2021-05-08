/**
* Projeto 1: Agendar contatos
* @author Leonardo Lima
*/ 

-- listar bancos desponíveis no servidor
show databases;
-- criar um novo banco de dados
create database dbagenda;
-- excluir banco de dados
drop database teste;
-- selecionar o banco de dados
use dbagenda;

/* Tabelas */
-- criar uma nova tabela
/* 
int (tipo de dados: números inteiros)
primary key (chave primária)
auto_increment (numeração autómatica)
varchar (tipo de dados: String de caracteres)
not null (campo com preenchimento obrigatório)
*/
create table contatos (
    idcon int primary key auto_increment,
    nome varchar(50) not null,
    fone varchar(15) not null,
    email varchar(50)
);

-- listar tabela de banco de dados
show tables;

-- deescrever a estrutura da tabela
describe contatos; 

-- excluir uma tabela
drop table contatos;

/* CRUD (Create Read Update Delete) */

/* CRUD Creat */
-- inserir um novo contato

insert into contatos(nome, fone, email)
values ('Ricardo','(11) 98888-9999','vice@email.com');
insert into contatos(nome, fone, email)
values ('Elias','(11) 93333-4444','soldado@email.com');
insert into contatos (nome, fone, email)
values('Leonardo Lima','(11) 95555-4444','leeolima@email.com');
insert into contatos (nome, fone, email)
values('Beuga com u','(11) 91111-2222','Beuga@email.com');
insert into contatos(nome, fone)
values ('Bill gates','(11) 96666-7777');

/* CRUD Read */
-- listar todos os contatos da tabela
select * from contatos;

-- listar os contatos por ordens alfabética
select * from contatos order by nome;

-- listar campos específicos da tabela
select nome, fone from contatos order by nome; 

-- criar um apelido para os campos da tabela
select nome as Contato, fone as Telefone,
email as Email from contatos order by nome;

-- selecionar um contato específico
select * from contatos where nome='Leonardo lima';
select * from contatos where idcon=1;

/* CRUD update */
update contatos set  nome='William Gates', fone ='(11) 2828-2828',
email='bill@outlook.com' where idcon=5;
update contatos set fone ='(21) 99999-8686' where idcon=3;

/* CRUD delete */
delete from contatos where idcon=1;

