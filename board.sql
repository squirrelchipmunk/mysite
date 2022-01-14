drop table board;
drop sequence seq_board_no;

create table board( 
    no number primary key, 
    title varchar2(500) not null, 
    content varchar2(4000), 
    hit number default 0, 
    reg_date date not null,
    user_no number not null,
    
    constraint fk_no foreign key(user_no) references users(no)
);

create sequence seq_board_no
INCREMENT by 1
start with 1
nocache;