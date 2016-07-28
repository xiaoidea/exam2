-- 如果表存在就删除
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS award;
DROP TABLE IF EXISTS author;

-- 建书籍情况表
CREATE TABLE book (
id int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
book_id varchar(15) NOT NULL COMMENT '图书编号',
author_id varchar(15) NOT NULL COMMENT '作者编号',
book_name varchar(60) NOT NULL COMMENT '书名',
pages int DEFAULT 0 COMMENT '页数',
press varchar(60) DEFAULT NULL COMMENT '出版社',
PRIMARY KEY(id),
UNIQUE KEY uniq_book_id(book_id),
KEY idx_author_id(author_id),
KEY idx_book_name(book_name)
)ENGINE=innoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书信息表';

-- 建获奖情况表
CREATE TABLE award (
id int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
book_id varchar(15) NOT NULL COMMENT '图书编号',
author_id varchar(15) NOT NULL COMMENT '作者编号',
cup_type tinyint NOT NULL COMMENT '获奖类型',
cup_time date DEFAULT NULL COMMENT '获奖时间',
PRIMARY KEY(id),
KEY idx_book_id(book_id),
KEY idx_cup_type(cup_type),
KEY idx_author_id_cup_type(author_id, cup_type),
KEY idx_author_id_cup_time(author_id, cup_time)
)ENGINE=innoDB DEFAULT CHARSET=utf8mb4 COMMENT='获奖信息表';

-- 建作者信息表
CREATE TABLE author (
id int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
author_id varchar(15) NOT NULL COMMENT '作者编号',
author_name varchar(30) NOT NULL COMMENT '作者姓名',
content varchar(1000) DEFAULT NULL COMMENT '作者介绍',
PRIMARY KEY(id),
UNIQUE KEY uniq_author_id(author_id),
KEY idx_author_name(author_name)
)ENGINE=innoDB DEFAULT CHARSET=utf8mb4 COMMENT='作者信息表';

-- 插入作者表
INSERT INTO author(author_id,author_name,content) VALUES('101','朱岳','朱岳，28岁，一人像他书中大多数人物。');
INSERT INTO author(author_id,author_name,content) VALUES('102','余华','余华，浙江海盐人，1960年出生于浙江杭州');
INSERT INTO author(author_id,author_name,content) VALUES('103','王小波','这本杂文随笔集包括思想文化方面的文章');
INSERT INTO author(author_id,author_name,content) VALUES('104','钱钟书','钱钟书(1910－1998)，中国江苏无锡人，中国近代著名作家');
INSERT INTO author(author_id,author_name,content) VALUES('105','张三','test');
INSERT INTO author(author_id,author_name,content) VALUES('106','李四','test');
INSERT INTO author(author_id,author_name,content) VALUES('107','王五','test');
-- 插入书籍表,并插入获奖表
INSERT INTO book(book_id,author_id,book_name,pages,press) VALUES('001','101','蒙着眼睛的旅行者',159,'新星出版社');
INSERT INTO award(book_id,author_id,cup_type,cup_time) VALUES('001','101','0','2015-09-24');
INSERT INTO book(book_id,author_id,book_name,pages,press) VALUES('002','102','活着',195,'南海出版公司');
INSERT INTO award(book_id,author_id,cup_type,cup_time) VALUES('002','102','0','2015-05-14');
INSERT INTO book(book_id,author_id,book_name,pages,press) VALUES('003','102','许三观卖血记',269,'南海出版公司');
INSERT INTO award(book_id,author_id,cup_type,cup_time) VALUES('003','102','1','2016-05-14');
INSERT INTO book(book_id,author_id,book_name,pages,press) VALUES('004','103','沉默的大多数',555,'中国青年出版社');
INSERT INTO award(book_id,author_id,cup_type,cup_time) VALUES('004','103','1','2012-05-14');
INSERT INTO book(book_id,author_id,book_name,pages,press) VALUES('005','104','围城',359,'人民文学出版社');
INSERT INTO award(book_id,author_id,cup_type,cup_time) VALUES('005','104','0','2016-05-14');

-- 查询姓王的作者数
SELECT count(*) FROM author WHERE author_name like '王%';

-- 查询获奖作者总人数
SELECT count(distinct(author_id)) as awarded_author FROM award;

-- 查询同时获得过金奖和银奖的作者姓名
-- 第一个性能不好,查询了派生表,第二个三个查询全部通过索引扫描得到,第一个对于派生表使用全索引扫描
-- SELECT author_name FROM author a JOIN (SELECT b.author_id FROM (SELECT author_id FROM award WHERE cup_type=0) b JOIN (SELECT author_id FROM award WHERE cup_type=1) c WHERE b.author_id=c.author_id) d WHERE a.author_id=d.author_id;
SELECT a.author_name FROM author a JOIN award b ON a.author_id=b.author_id AND b.cup_type=0 JOIN award c ON a.author_id=c.author_id AND c.cup_type=1;

-- 查询获得过金奖的图书有多少本
SELECT count(distinct(book_id)) as gold_award FROM award WHERE cup_type=0;

-- 查询获得过银奖的图书有多少本
SELECT count(distinct(book_id)) as silver_award FROM award WHERE cup_type=1;

-- 查询最近一年获过奖的作者姓名
-- 同样不使用第一种方法
-- SELECT author_name FROM author a JOIN (SELECT distinct(author_id) FROM award WHERE cup_time >= DATE_SUB(NOW(), INTERVAL 1 YEAR)) b WHERE a.author_id=b.author_id;
SELECT distinct(a.author_name) FROM author a JOIN award b ON a.author_id=b.author_id AND b.cup_time >= DATE_SUB(NOW(), INTERVAL 1 YEAR);

/*
下面是额外的几个问题:

Q:如何查看表的结构信息？
A:可以使用 show create table tbl_name 或 show columns from tbl_name查看字段信息.

Q:联合索引中的字段顺序应该如何设计？
A:联合索引中字段选择应该是经常被一起查询的多个字段,而字段顺序应该是将信息增益较大的字段放在前面.

Q:int(10)和varchar(10)两个字段的(10)有什么区别？
A:varchar(10)表示存储大小可变的字符串型,最大10个字节.int(10)和int作用一样,仍然使用4个字节存储.

Q:以下查询如何创建索引能够实现覆盖索引优化？（请给出具体SQL）

select invalid_time_flag from pushtoken_android_62 

where uid = 'AC54E24E-FB73-3981-C4BC-CED8D69407F8' 

and pid = '10010'

select count(*) from pushtoken_android_62 

where uid = 'AC54E24E-FB73-3981-C4BC-CED8D69407F8' 

and pid = '10010'
A: 
ALTER TABLE pushtoken_android_62 ADD INDEX idx_uid_pid_invalid_time_flag(uid, pid, invalid_time_flag);
ALTER TABLE pushtoken_android_62 ADD INDEX idx_uid_pid(uid, pid);
*/