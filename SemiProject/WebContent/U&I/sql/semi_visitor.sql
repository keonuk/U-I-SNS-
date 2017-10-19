select * from semi_visitor

drop table semi_visitor

truncate table semi_visitor

create table semi_visitor(
	v_num number not null,
	v_id varchar2(20) not null,
	v_content varchar2(4000) not null,
	v_tdate varchar2(30) not null,
	v_photo varchar2(30) not null,
foreign key(v_id) REFERENCES semi_member(m_id) on delete cascade
)