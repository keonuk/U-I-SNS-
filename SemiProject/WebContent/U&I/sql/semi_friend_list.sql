drop table semi_friend_list

create table semi_friend_list(
	my_id varchar2(20),
	fr_id varchar2(20),
	foreign key(my_id) REFERENCES semi_member(m_id) on delete cascade,
	foreign key(fr_id) REFERENCES semi_member(m_id) on delete cascade

)

select * from SEMI_FRIEND_LIST;