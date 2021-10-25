-- Criar Banco de dados
create database dblanchonete;

-- Ativar o banco de dados
use dblanchonete;

-- criar tabela de Usuários
create table tbusuarios(
iduser int primary key,
usuario varchar(50) not null,
fone varchar(15),
login varchar(15) not null unique,
senha varchar(15) not null
);

-- descrever a tabela usuarios
describe tbusuarios;

-- inserir dados na tabela de usuarios
insert into tbusuarios(iduser, usuario, fone, login, senha)
values(1,'Administrador','1111-1111','admin','admin');
insert into tbusuarios(iduser, usuario, fone, login, senha)
values(2,'Gabriel','2222-2222','gabriel','123');
insert into tbusuarios(iduser, usuario, fone, login, senha)
values(3,'Barbara','3333-3333','barbara','123');
insert into tbusuarios(iduser, usuario, fone, login, senha)
values(4,'Luiza','4444-4444','luiza','123');

-- selecionar dados da tabela usuarios
select * from tbusuarios;

-- alterar tabela adicionar campo perfil
alter table tbusuarios add column perfil varchar(20) not null;

-- Adicionar dados no campo perfil
update tbusuarios set perfil = 'admin' where iduser=1;
update tbusuarios set perfil = 'admin' where iduser=2;
update tbusuarios set perfil = 'user' where iduser=3;
update tbusuarios set perfil = 'user' where iduser=4;

-- selecionar usuários com perfil admin
select * from tbusuarios
where  perfil='admin';

-- selecionar usuarios com perfil user
select * from tbusuarios
where  perfil='user';

-- criar tabela clientes
create table tbclientes(
idcli int primary key auto_increment,
nomecli varchar(50) not null,
endcli varchar(100),
bairrocli varchar(50),
cidadecli varchar(50),
cpfcli varchar(20),
nasccli varchar(12),
telcli varchar (20),
emailcli varchar (100),
celcli varchar (20)
);

-- alterar variavel nascli date para varchar
alter table tbclientes modify nasccli varchar(12);

-- descrever a tabela cliente
describe tbclientes;

-- inserir dados na tabela clientes
insert into tbclientes(nomecli, endcli, bairrocli, cidadecli, cpfcli, nasccli, telcli, emailcli, celcli)
values('Fulano de Tall','Rua Sem Fim, 0','Cafundé do Judas','Macedonia','000000000-00','1900-01-01','00000000000','fulano@uol.com','00000000');

-- selecionar dados da tabela clientes
select * from tbclientes;

-- excluir dados da tabela clientes
delete  from tbclientes
where idcli = 1 ;

-- excluir tabela clientes
drop table tbclientes;

-- criar tabela funcionarios
create table tbfuncionarios(
idfunc int primary key auto_increment,
nomefunc varchar(50) not null,
endfunc varchar(100),
bairrofunc varchar(50),
cidadefunc varchar(50),
cpffunc varchar(20),
nascfunc date,
telfunc varchar (20),
emailfunc varchar (100),
celfunc varchar (20),
funcao varchar(30)
);

-- descrever tabela funcionarios
describe tbfuncionarios;

-- Alterar variavel nascli date para varchar na tabela funcionarios
alter table tbfuncionarios modify nascfunc varchar(12);

-- inserir dados na tabela Funcionarios
insert into tbfuncionarios(nomefunc, endfunc, bairrofunc, cidadefunc, cpffunc, nascfunc, telfunc, emailfunc, celfunc, funcao)
values('Cachorro Loko','Rua Da Quebrada, 0','Vl Acabada','Pompeia','000000000-00','1900-01-01','00000000000','boy@gmail.com','00000000','motoboy');

-- selecionar dados da tabela funcionarios
select * from tbfuncionarios;

-- criar tabela lanches 
create table tblanches(
idlanche int primary key auto_increment,
nomelanche varchar(20) not null,
valorlanche decimal(10,2) not null,
descricaolanche varchar(150) not null,
tamanholanche varchar(10) not null,
paolanche varchar(15)
);

-- excluir dados da tabela lanches
delete  from tblanches
where idlanche = 1;

-- descrever tabela lanches
describe tblanches;

-- selecionar dados da tabela lanches
select * from tblanches;

-- criar tabela de bebida
create table tbbebidas(
idbebida int primary key auto_increment,
nomebebida varchar(20) not null,
valorbebida decimal(10,2) not null,
volumebebida varchar(10) not null
);

-- excluir dados da tabela bebidas
delete  from tbbebidas
where idbebida =0;

-- descrever tabela bebidas
describe tbbebidas;

-- inserir dados na tabela bebidas
insert into tbbebidas(nomebebida, volumebebida, valorbebida)
values('Água Mineral','500 ml', 3.50);

-- selecionar dados da tabela bebidas
select * from tbbebidas;

-- criar tabela itensvenda
create table tbitensvenda(
datavenda timestamp default current_timestamp,
iditemvenda int primary key auto_increment,
nome varchar(30),
qtd int not null,
valor decimal (10,2),
total decimal (10,2),
idcli int,
foreign key(idcli) references tbclientes(idcli),
nomecli varchar(50),
iditem int,
tipo varchar(10),
mesa varchar (3)
);

-- *** ATENÇÂ TABELA ITENS VENDA SEM CHAVE ESTRANGEIRA FUNCIONOU***
-- Selecionar dados das tabelas intensvenda com as tabelas tblanches e tbbebidas
select * from tbitensvenda
inner join tblanches
on tblanches.idlanche = tbitensvenda.idlanche
inner join tbbebidas
on tbbebidas.idbebida = tbitensvenda.idbebida ;

-- selecionar dados das tabelas itensvenda, venda e cliente para impressão dos pedidos Delivery
select * from tbvenda
inner join tbitensvenda
on tbitensvenda.idcli = tbvenda.idcli
inner join tbclientes
on tbclientes.idcli = tbvenda.idcli
where (tbvenda.idvenda =  1   )
and tbvenda.situacao <>'Finalizado'
and tipo = 'Delivery' ;

-- selecionar tabela  tbvenda por numero de mesa
select tbvenda.idvenda as Pedido,
tbvenda.nomecli as Cliente,
tbvenda.situacao as Status,
tbvenda.tipo as Tipo,
tbvenda.totalvenda as Valor,
tbvenda.mesa as Mesa
from tbvenda
where  tbvenda.situacao <> 'Finalizado'
and tbvenda.mesa = '2';

-- selecionar vinculando clientes com itens vendido
select * from tbitensvenda
inner join tbclientes
on tbitensvenda.idcli = tbclientes.idcli;

-- selecionar todo registros
select * from tbitensvenda;
select * from tbvenda;
select * from tblanches;
select * from tbclientes;
select * from tbusuarios;
select * from tbbebidas;
select * from tbfuncionarios;

describe tbitensvenda;

select * from tbitensvenda;

-- criar tabela venda
create table tbvenda(
idvenda int primary key auto_increment,
datavenda timestamp default current_timestamp,
idcli int,
foreign key(idcli) references tbclientes(idcli),
iditemvenda int,
foreign key(iditemvenda) references tbitensvenda(iditemvenda),
mesa varchar (3),
nomefunc varchar (30),
situacao varchar(15),
tipo varchar(10),
totalvenda decimal(10,2),
pagto varchar(10),
troco decimal(10,2),
nomecli varchar(50),
nome varchar(30)
);

describe tbvenda;

select * from tbvenda;

-- selecionar clientes por ordem alfabética de nomes
select * from tbclientes order by nomecli;

-- excluir dados da tabela clientes
delete  from tbitensvenda
where iditemvenda =1;

-- contar na coluna tipo a quantidade de registro da tbvenda em ordem por tipo
SELECT
tipo,
COUNT(tipo) AS quantidade
FROM tbvenda
GROUP BY tipo;

-- contar total da coluna tipo na tbvenda
SELECT COUNT(tipo) FROM tbvenda;

-- selecionar e contar na coluna tipo , Balcao, Delivery e Mesa que a situação seja diferente de Finalizado
select tipo, count(tipo)
from tbvenda
where tipo = 'Balcao'
and tipo = 'Delivery'
and tipo = 'Mesa'
and situacao <> 'Finalizado';

-- selecionar tudo da tbvenda realcionado a tbclientes
select * from tbvenda
inner join tbclientes;

-- selecionar esses dados da tbvenda e da tbclientes que tenham o idcliente iguais e que o idvenda seja 1
select tbvenda.datavenda,
tbclientes.idcli,
tbvenda.mesa,
tbvenda.nomefunc,
tbvenda.situacao,
tbvenda.tipo,
tbvenda.totalvenda,
tbclientes.nomecli,
tbclientes.endcli,
tbclientes.telcli,
tbclientes.celcli,
tbvenda.idvenda,
tbvenda.pagto,
tbvenda.troco
from tbvenda
inner join tbclientes
where tbclientes.idcli = tbvenda.idcli
and tbvenda.idvenda= 1;

-- selecionar esses dados substituindo os nomes das colunas da tbvenda relacionado com a tbclientes
select tbvenda.idvenda as Pedido,
tbclientes.telcli as Telefone,
tbvenda.idcli as ID,
tbclientes.nomecli as Cliente,
tbvenda.tipo as Tipo,
tbvenda.situacao as Status,
tbclientes.celcli as Celular,
tbvenda.totalvenda as Valor,
tbvenda.mesa as Mesa
from tbvenda inner join tbclientes;

-- selecionar idvenda , telcli, idcli, nomecli, tipo, situacao, celcli, totalvenda, mesa que o idcliente da tbvenda seja igual idcliente da tbclientes
select tbvenda.idvenda as Pedido,
tbclientes.telcli as Telefone,
tbvenda.idcli as ID,
tbclientes.nomecli as Cliente,
tbvenda.tipo as Tipo,
tbvenda.situacao as Status,
tbclientes.celcli as Celular,
tbvenda.totalvenda as Valor,
tbvenda.mesa as Mesa
from tbvenda
inner join tbclientes
on tbclientes.idcli = tbvenda.idcli;

-- selecionar tbvenda relacionado com tbclientes onde nome do cliente seja igual o tipo igual a Delivery que situação seja diferente de Finalizado
select tbvenda.datavenda,
tbclientes.idcli,
tbvenda.nomefunc,
tbvenda.situacao,
tbvenda.tipo,
tbvenda.totalvenda,
tbclientes.nomecli,
tbclientes.endcli,
tbclientes.telcli,
tbclientes.celcli,
tbvenda.idvenda,
tbvenda.pagto,
tbvenda.troco
from tbvenda
inner join tbclientes
where tbvenda.nomecli = tbclientes.nomecli
and tbvenda.tipo ='Delivery'
and tbvenda.situacao <> 'Finalizado'
and tbvenda.idvenda= 3;

-- Selecionar dados das tabelas tbvenda com as tabelas tbfuncionarios e tbclientes
select * from tbvenda
inner join tbclientes on tbclientes.idcli = tbvenda.idcli
inner join tbfuncionarios on tbfuncionarios.nomefunc = tbvenda.nomefunc
where idvenda = 1;

-- Altera a situação na tbvenda para Fianlizado onde o Registro é igual a 1
update tbvenda set  situacao='Finalizado'
where idvenda=1;

-- teste para Imprimir venda
-- selecione tudo da tbvenda relacionado com tbitensvenda que o idcli seja igual e que tbclientes o idcli seja igual tbvenda
-- que o idvenda seja 4 e a situação seja diferente de Finalizado
select * from tbvenda
inner join tbitensvenda on tbitensvenda.idcli = tbvenda.idcli
inner join tbclientes on tbclientes.idcli = tbvenda.idcli
where (tbvenda.idvenda =  4   )  and tbvenda.situacao <>'Finalizado'  ;

-- selecione os campos substituindo os nomes na tbvenda onde na coluna situação seja diferente de Finalizado
-- que o nomecli começe com a letra P
select tbvenda.idvenda as Pedido,
tbvenda.nomecli as Cliente,
tbvenda.situacao as Status,
tbvenda.tipo as Tipo,
tbvenda.totalvenda as Valor,
tbvenda.mesa as Mesa from tbvenda
where  tbvenda.situacao <> 'Finalizado'
and tbvenda.nomecli like 'p%';

-- selecione esses dados da tbvenda
SELECT idvenda, datavenda, idcli, iditemvenda, mesa, nomefunc, situacao, tipo, totalvenda
FROM tbvenda;

-- selecione os dados substituindo os nomes na tbvenda que se relaciona com tbclientes ond o idcli seja igula e a situação do pedido eja Aberto
select tbvenda.idvenda as Pedido,
tbclientes.telcli as Telefone,
tbvenda.idcli as ID,
tbclientes.nomecli as Cliente,
tbvenda.tipo as Tipo,
tbvenda.situacao as Status,
tbvenda.totalvenda as Valor,
tbvenda.mesa as NºMesa
from tbvenda
inner join tbclientes
on tbclientes.idcli = tbvenda.idcli
where (tbvenda.situacao = 'Aberto');

-- selecione tudo da tbvenda que se realcione com tbclientes que o idvenda seja 1
select * from tbvenda
inner join tbclientes
on tbclientes.idcli = tbvenda.idcli
where idvenda= 1;

-- selecione tudo da tbvenda que se realcione com tbclientes que o idvenda seja 2
select * from tbvenda
inner join tbclientes
on tbclientes.idcli = tbvenda.idcli
where idvenda= 2;

-- selecione esse dados da tbvenda que se relacionam com a tbclientes onde o idcli das tabelas sejam iguais
select tbvenda.datavenda,
tbclientes.idcli,
tbvenda.mesa,
tbvenda.nomefunc,
tbvenda.situacao,
tbvenda.tipo,
tbvenda.totalvenda,
tbclientes.nomecli,
tbclientes.endcli,
tbclientes.telcli,
tbclientes.celcli,
tbvenda.idvenda
from tbvenda
inner join tbclientes
on tbclientes.idcli = tbvenda.idcli;

-- selecione esses dados da tbvenda que se relaciona com a tbclientes com idcliente iguais e que o idvenda seja 1
select tbvenda.datavenda,
tbclientes.idcli,
tbvenda.mesa,
tbvenda.nomefunc,
tbvenda.situacao,
tbvenda.tipo,
tbvenda.totalvenda,
tbclientes.nomecli,
tbclientes.endcli,
tbclientes.telcli,
tbclientes.celcli,
tbvenda.idvenda
from tbvenda
inner join tbclientes
on tbvenda.idcli = tbclientes.idcli
where  tbvenda.idvenda= 1;

-- selecione tudo da tbvenda e da tbitensvenda onde nomecli seja igual e o idvenda seja 3
select * from tbvenda
inner join tbitensvenda
on tbitensvenda.nomecli = tbvenda.nomecli
and tbvenda.idvenda = 3;

-- excluir dados da tabela venda
delete  from tbvenda
where (idvenda = 3) and (idcli = 3);

-- excluir dados da tabela venda
delete  from tbvenda
where idvenda = 1 ;

-- selecionar pedidos Balcao
select datavenda,
situacao,
tipo,
totalvenda,
pagto,
troco,
nomecli
from tbvenda
where tbvenda.situacao <> 'Finalizado'
and tbvenda.idvenda= 5;

-- excluir dados da tabela itensvenda
delete  from tbitensvenda
where iditemvenda = 28;

-- excluir tabela
drop table tbitensvenda;
drop table tbvenda;
drop table tbbebidas;
drop table tblanches;


