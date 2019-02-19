delete pc;
delete pc_status;

insert into pc (seat_num, pc_ip, x_coor, y_coor, member_id) values (1, '1.1.1.1', 0, 0, 'frank');
insert into pc_status (seat_num, pc_status, message_status, order_status) values (1, 'N', 'N', 'Y');

insert into pc (seat_num, pc_ip, x_coor, y_coor, member_id) values (2, '2.2.2.2', 0, 1, 'user');
insert into pc_status (seat_num, pc_status, message_status, order_status) values (2, 'N', 'Y', 'N');

insert into pc (seat_num, pc_ip, x_coor, y_coor, member_id) values (3, '3.3.3.3', 0, 2, 'silvergod');
insert into pc_status (seat_num, pc_status, message_status, order_status) values (3, 'N', 'Y', 'Y');

insert into pc (seat_num, pc_ip, x_coor, y_coor, member_id) values (4, '4.4.4.4', 0, 3, 'bamidele');
insert into pc_status (seat_num) values (4);

insert into pc (seat_num, pc_ip, x_coor, y_coor) values (5, '5.5.5.5', 0, 4);
insert into pc_status (seat_num) values (5);

insert into pc (seat_num, pc_ip, x_coor, y_coor) values (6, '6.6.6.6', 2, 0);
insert into pc_status (seat_num) values (6);

insert into pc (seat_num, pc_ip, x_coor, y_coor) values (7, '7.7.7.7', 2, 1);
insert into pc_status (seat_num) values (7);

insert into pc (seat_num, pc_ip, x_coor, y_coor) values (8, '8.8.8.8', 2, 2);
insert into pc_status (seat_num) values (8);

insert into pc (seat_num, pc_ip, x_coor, y_coor) values (9, '9.9.9.9', 2, 3);
insert into pc_status (seat_num) values (9);

insert into pc (seat_num, pc_ip, x_coor, y_coor) values (10, '10.10.10.10', 2, 4);
insert into pc_status (seat_num) values (10);

insert into pc (seat_num, pc_ip, x_coor, y_coor) values (100, '211.63.89.150', 9, 9);
insert into pc_status (seat_num) values (100);

insert into pc (seat_num, pc_ip, x_coor, y_coor) values (99, '211.63.89.151', 9, 8);
insert into pc_status (seat_num) values (99);

insert into pc (seat_num, pc_ip, x_coor, y_coor) values (98, '211.63.89.152', 9, 7);
insert into pc_status (seat_num) values (98);

insert into pc (seat_num, pc_ip, x_coor, y_coor) values (97, '211.63.89.153', 9, 6);
insert into pc_status (seat_num) values (97);

insert into pc (seat_num, pc_ip, x_coor, y_coor) values (96, '211.63.89.154', 9, 5);
insert into pc_status (seat_num) values (96);

commit;

