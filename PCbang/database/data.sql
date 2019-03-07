insert into pc_admin (admin_id, admin_pass,admin_name,admin_input_date,admin_notice) values ('admin', '1234', 'admin', sysdate, '안녕하세요.');
commit;
insert into pc_member (member_id, member_pass,member_name,member_gender, member_tel) values ('user', '1234', 'user', 'M', '010-0000-0000');
commit;
insert into menu (menu_code, category, menu_name, menu_price, img) values ('R_000000','라면','너구리',2500, 'null');
commit;

insert into pc_price(charge_time, member_price, guest_price) values (60, 1000, 1100);
insert into pc_price(charge_time, member_price, guest_price) values (120, 1900, 2200);
insert into pc_price(charge_ti, CARD_NUM, PC_STATUSme, member_price, guest_price) values (180, 2800, 3300);
insert into pc_price(charge_time, member_price, guest_price) values (240, 3700, 4400);
insert into pc_price(charge_time, member_price, guest_price) values (300, 4500, 5500);
insert into pc_price(charge_time, member_price, guest_price) values (360, 5300, 6600);
insert into pc_price(charge_time, member_price, guest_price) values (420, 6100, 7700);
insert into pc_price(charge_time, member_price, guest_price) values (480, 6800, 8800);
insert into pc_price(charge_time, member_price, guest_price) values (540, 7500, 9900);
insert into pc_price(charge_time, member_price, guest_price) values (600, 8200, 11000);

commit;

insert into pc (seat_num, pc_ip, pc_status, x_coor, y_coor) values (1, '211.63.89.151', 'O',0,0);
commit;
insert into pc (seat_num, pc_ip, pc_status, x_coor, y_coor) values (2, '211.63.89.153', 'O',0,1);
commit;
insert into pc (seat_num, pc_ip, pc_status, x_coor, y_coor) values (3, '211.63.89.150', 'O',0,2);
commit;
insert into pc (seat_num, pc_ip, pc_status, x_coor, y_coor) values (4, '211.63.89.149', 'O',1,0);
commit;
insert into pc (seat_num, pc_ip, pc_status, x_coor, y_coor) values (5, '211.63.89.148', 'O',1,1);
commit;
insert into pc (seat_num, pc_ip, pc_status, x_coor, y_coor) values (6, '211.63.89.147', 'O',1,2);
commit;

CREATE table ORDERING2(
		order_num char(12) constraint pk_ordering2 primary key,
		quan number(2) not null,
		order_date date,
		status char(1) not null,
		menu_code char(8),
        seat_num number(3)
);

insert into ORDERING2 (order_num, quan, order_date, status, menu_code, seat_num)
values('OR_000000001','2',sysdate, 'N', 'M_000022', 1);
insert into ORDERING2 (order_num, quan, order_date, status, menu_code, seat_num)
values('OR_000000002','3',sysdate, 'N', 'M_000041', 2);
insert into ORDERING2 (order_num, quan, order_date, status, menu_code, seat_num)
values('OR_000000003','1',sysdate, 'N', 'M_000041', 3);
insert into ORDERING2 (order_num, quan, order_date, status, menu_code, seat_num)
values('OR_000000004','7',sysdate, 'Y', 'M_000004', 2);
insert into ORDERING2 (order_num, quan, order_date, status, menu_code, seat_num)
values('OR_000000005','3',sysdate, 'N', 'M_000041', 1);
insert into ORDERING2 (order_num, quan, order_date, status, menu_code, seat_num)
values('OR_000000006','4',sysdate, 'N', 'M_000041', 1);
insert into ORDERING2 (order_num, quan, order_date, status, menu_code, seat_num)
values('OR_000000007','5',sysdate, 'N', 'M_000041', 4);
commit;


select * from PC_ADMIN;
select * from PC_MEMBER;
select * from menu;
select * from pc;
select * from pc_price;
select * from PC_GUEST;
