drop table semi_member;

create table semi_member(
	m_Id VARCHAR2(20) not null,
	m_pass varchar2(20) not null,
	m_name varchar2(20) not null,
	m_birth varchar2(20) not null,
	m_gender varchar2(10) not null,
	m_email varchar2(50) not null,
	m_phoneNum varchar2(20) not null,
	m_photoName varchar2(20) default 'profile.png',
	primary key(m_Id)
)

truncate table semi_member
select * from semi_member;

SELECT * FROM semi_member WHERE m_Id='이재용'

UPDATE semi_member SET m_photoName='NaDa.jpg' WHERE m_Id='NaDa';

select m_PhotoName, m_name from semi_member where m_name = '장건욱';

select m_photoName, m_Id, m_name from semi_member where m_Id = 'mscmsc' or m_name = '장건욱'
delete semi_member where m_pass = '123';

INSERT INTO Semi_Member (m_Id, m_pass, m_name, m_birth, m_gender, m_email, m_phoneNum) 
VALUES('admin','admin','관리자','000000','남자','admin@naver.com','0100000000');

select m_photoName, m_Id, m_name
					from ( select * from semi_member where m_id != 'msc')
					where m_Id = 'msc' or m_name = '관리자' 

select m_photoname, m_Id, m_name
from (select fr_id from semi_friend_list
where semi_friend_list.my_id = 'keonuk' ) a, semi_member
where a.fr_id = semi_member.m_Id