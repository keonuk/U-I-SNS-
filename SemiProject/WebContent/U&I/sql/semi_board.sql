drop table board

create table board(
id			varchar2(20),
subject		varchar2(100),
image		varchar2(100),
boarddate	varchar2(100),
content		varchar2(400),
foreign key(id) REFERENCES semi_member(m_id) on delete cascade
)


select * from board