-- ============================================================
-- ssm_db：SSM 学习专用演示库
-- 两张表：dept(部门) / emp(员工)
-- 约定：关键字、表名、字段名全部小写；字符集 utf8mb4
-- ============================================================

-- 告诉 mysql 客户端：这个文件是 utf8mb4 编码的。
-- 不加这句，Windows 上的客户端会默认按 GBK 解，中文全变乱码
-- （典型报错：Data too long for column 'name'）
set names utf8mb4;

drop database if exists ssm_db;
create database ssm_db default character set utf8mb4 collate utf8mb4_general_ci;
use ssm_db;

-- 先删 emp 再删 dept（将来若有外键，顺序反了会报错；现在单纯养成习惯）
drop table if exists emp;
drop table if exists dept;

-- ------------------------------------------------------------
-- 部门表
-- ------------------------------------------------------------
create table dept (
    id   int         primary key auto_increment comment '部门id',
    name varchar(20) not null                    comment '部门名称'
) comment '部门表';

-- ------------------------------------------------------------
-- 员工表
-- 注意：dept_id 只是普通列，没有建物理外键（和苍穹外卖一致）
-- 关联由应用层保证，这样分库分表、改数据都不会被外键卡住
-- ------------------------------------------------------------
create table emp (
    id      int           primary key auto_increment comment '员工id',
    name    varchar(20)   not null                    comment '姓名',
    age     int                                       comment '年龄',
    gender  char(1)                                   comment '性别：1男 2女',
    salary  decimal(10,2)                             comment '薪资',
    dept_id int                                       comment '所属部门id'
) comment '员工表';

-- ------------------------------------------------------------
-- 初始化数据
-- ------------------------------------------------------------
insert into dept (name) values
('教研部'),
('学工部'),
('销售部'),
('人事部'),
('后勤部');

-- 10 条员工数据：够 关 13 做分页（每页 3 条 = 4 页）
insert into emp (name, age, gender, salary, dept_id) values
('张三', 23, '1',  8000.00, 1),
('李四', 24, '2',  9000.00, 1),
('王五', 31, '1', 15000.00, 2),
('赵六', 28, '2', 12000.00, 3),
('孙七', 35, '1', 20000.00, 3),
('周八', 22, '2',  6500.00, 4),
('吴九', 26, '1', 11000.00, 5),
('郑十', 29, '2', 13500.00, 2),
('陈一', 27, '1', 10500.00, 1),
('林二', 33, '2', 18000.00, 3);
