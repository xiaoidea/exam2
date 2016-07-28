-- �������ھ�ɾ��
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS award;
DROP TABLE IF EXISTS author;

-- ���鼮�����
CREATE TABLE book (
id int unsigned NOT NULL AUTO_INCREMENT COMMENT '����id',
book_id varchar(15) NOT NULL COMMENT 'ͼ����',
author_id varchar(15) NOT NULL COMMENT '���߱��',
book_name varchar(60) NOT NULL COMMENT '����',
pages int DEFAULT 0 COMMENT 'ҳ��',
press varchar(60) DEFAULT NULL COMMENT '������',
PRIMARY KEY(id),
UNIQUE KEY uniq_book_id(book_id),
KEY idx_author_id(author_id),
KEY idx_book_name(book_name)
)ENGINE=innoDB DEFAULT CHARSET=utf8mb4 COMMENT='ͼ����Ϣ��';

-- ���������
CREATE TABLE award (
id int unsigned NOT NULL AUTO_INCREMENT COMMENT '����id',
book_id varchar(15) NOT NULL COMMENT 'ͼ����',
author_id varchar(15) NOT NULL COMMENT '���߱��',
cup_type tinyint NOT NULL COMMENT '������',
cup_time date DEFAULT NULL COMMENT '��ʱ��',
PRIMARY KEY(id),
KEY idx_book_id(book_id),
KEY idx_cup_type(cup_type),
KEY idx_author_id_cup_type(author_id, cup_type),
KEY idx_author_id_cup_time(author_id, cup_time)
)ENGINE=innoDB DEFAULT CHARSET=utf8mb4 COMMENT='����Ϣ��';

-- ��������Ϣ��
CREATE TABLE author (
id int unsigned NOT NULL AUTO_INCREMENT COMMENT '����id',
author_id varchar(15) NOT NULL COMMENT '���߱��',
author_name varchar(30) NOT NULL COMMENT '��������',
content varchar(1000) DEFAULT NULL COMMENT '���߽���',
PRIMARY KEY(id),
UNIQUE KEY uniq_author_id(author_id),
KEY idx_author_name(author_name)
)ENGINE=innoDB DEFAULT CHARSET=utf8mb4 COMMENT='������Ϣ��';

-- �������߱�
INSERT INTO author(author_id,author_name,content) VALUES('101','����','������28�꣬һ���������д�������');
INSERT INTO author(author_id,author_name,content) VALUES('102','�໪','�໪���㽭�����ˣ�1960��������㽭����');
INSERT INTO author(author_id,author_name,content) VALUES('103','��С��','�Ȿ������ʼ�����˼���Ļ����������');
INSERT INTO author(author_id,author_name,content) VALUES('104','Ǯ����','Ǯ����(1910��1998)���й����������ˣ��й�������������');
INSERT INTO author(author_id,author_name,content) VALUES('105','����','test');
INSERT INTO author(author_id,author_name,content) VALUES('106','����','test');
INSERT INTO author(author_id,author_name,content) VALUES('107','����','test');
-- �����鼮��,������񽱱�
INSERT INTO book(book_id,author_id,book_name,pages,press) VALUES('001','101','�����۾���������',159,'���ǳ�����');
INSERT INTO award(book_id,author_id,cup_type,cup_time) VALUES('001','101','0','2015-09-24');
INSERT INTO book(book_id,author_id,book_name,pages,press) VALUES('002','102','����',195,'�Ϻ����湫˾');
INSERT INTO award(book_id,author_id,cup_type,cup_time) VALUES('002','102','0','2015-05-14');
INSERT INTO book(book_id,author_id,book_name,pages,press) VALUES('003','102','��������Ѫ��',269,'�Ϻ����湫˾');
INSERT INTO award(book_id,author_id,cup_type,cup_time) VALUES('003','102','1','2016-05-14');
INSERT INTO book(book_id,author_id,book_name,pages,press) VALUES('004','103','��Ĭ�Ĵ����',555,'�й����������');
INSERT INTO award(book_id,author_id,cup_type,cup_time) VALUES('004','103','1','2012-05-14');
INSERT INTO book(book_id,author_id,book_name,pages,press) VALUES('005','104','Χ��',359,'������ѧ������');
INSERT INTO award(book_id,author_id,cup_type,cup_time) VALUES('005','104','0','2016-05-14');

-- ��ѯ������������
SELECT count(*) FROM author WHERE author_name like '��%';

-- ��ѯ������������
SELECT count(distinct(author_id)) as awarded_author FROM award;

-- ��ѯͬʱ��ù��𽱺���������������
-- ��һ�����ܲ���,��ѯ��������,�ڶ���������ѯȫ��ͨ������ɨ��õ�,��һ������������ʹ��ȫ����ɨ��
-- SELECT author_name FROM author a JOIN (SELECT b.author_id FROM (SELECT author_id FROM award WHERE cup_type=0) b JOIN (SELECT author_id FROM award WHERE cup_type=1) c WHERE b.author_id=c.author_id) d WHERE a.author_id=d.author_id;
SELECT a.author_name FROM author a JOIN award b ON a.author_id=b.author_id AND b.cup_type=0 JOIN award c ON a.author_id=c.author_id AND c.cup_type=1;

-- ��ѯ��ù��𽱵�ͼ���ж��ٱ�
SELECT count(distinct(book_id)) as gold_award FROM award WHERE cup_type=0;

-- ��ѯ��ù�������ͼ���ж��ٱ�
SELECT count(distinct(book_id)) as silver_award FROM award WHERE cup_type=1;

-- ��ѯ���һ����������������
-- ͬ����ʹ�õ�һ�ַ���
-- SELECT author_name FROM author a JOIN (SELECT distinct(author_id) FROM award WHERE cup_time >= DATE_SUB(NOW(), INTERVAL 1 YEAR)) b WHERE a.author_id=b.author_id;
SELECT distinct(a.author_name) FROM author a JOIN award b ON a.author_id=b.author_id AND b.cup_time >= DATE_SUB(NOW(), INTERVAL 1 YEAR);

/*
�����Ƕ���ļ�������:

Q:��β鿴��Ľṹ��Ϣ��
A:����ʹ�� show create table tbl_name �� show columns from tbl_name�鿴�ֶ���Ϣ.

Q:���������е��ֶ�˳��Ӧ�������ƣ�
A:�����������ֶ�ѡ��Ӧ���Ǿ�����һ���ѯ�Ķ���ֶ�,���ֶ�˳��Ӧ���ǽ���Ϣ����ϴ���ֶη���ǰ��.

Q:int(10)��varchar(10)�����ֶε�(10)��ʲô����
A:varchar(10)��ʾ�洢��С�ɱ���ַ�����,���10���ֽ�.int(10)��int����һ��,��Ȼʹ��4���ֽڴ洢.

Q:���²�ѯ��δ��������ܹ�ʵ�ָ��������Ż��������������SQL��

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