drop table semi_diary

create table semi_diary(
	id         varchar2(20) REFERENCES semi_member(m_id) on delete cascade,
	start_date   varchar2(100),
	end_date   varchar2(100),
	title      varchar2(50)
)

select * from semi_diary
