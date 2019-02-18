/* ������ */
DROP TABLE pc_admin 
	CASCADE CONSTRAINTS;

/* ��ǰ */
DROP TABLE menu 
	CASCADE CONSTRAINTS;

/* �ֹ� */
DROP TABLE ordering 
	CASCADE CONSTRAINTS;

/* ȸ�� */
DROP TABLE pc_member 
	CASCADE CONSTRAINTS;

/* ��ǻ�� */
DROP TABLE pc 
	CASCADE CONSTRAINTS;

/* pc��� */
DROP TABLE pc_price 
	CASCADE CONSTRAINTS;

/* ��ȸ�� */
DROP TABLE pc_guest 
	CASCADE CONSTRAINTS;

/* ȸ�� �α� */
DROP TABLE member_log 
	CASCADE CONSTRAINTS;

/* �����ȣ */
DROP TABLE zipcode 
	CASCADE CONSTRAINTS;

/* �޼��� */
DROP TABLE message 
	CASCADE CONSTRAINTS;

/* ��ȸ�� �α� */
DROP TABLE pc_guest_log 
	CASCADE CONSTRAINTS;

/* �������� */
DROP TABLE pc_notice 
	CASCADE CONSTRAINTS;

/* ��ǻ�� ���� */
DROP TABLE pc_status 
	CASCADE CONSTRAINTS;

/* ������ */
CREATE TABLE pc_admin (
	admin_id VARCHAR2(20) NOT NULL, /* �����ھ��̵� */
	admin_pass VARCHAR2(20) NOT NULL, /* �����ں�й�ȣ */
	admin_name VARCHAR2(30) NOT NULL, /* �������̸� */
	admin_input_date DATE DEFAULT sysdate /* �������Է��� */
);

COMMENT ON TABLE pc_admin IS '������';

COMMENT ON COLUMN pc_admin.admin_id IS '�����ھ��̵�';

COMMENT ON COLUMN pc_admin.admin_pass IS '�����ں�й�ȣ';

COMMENT ON COLUMN pc_admin.admin_name IS '�������̸�';

COMMENT ON COLUMN pc_admin.admin_input_date IS '�������Է���';

CREATE UNIQUE INDEX PK_pc_admin
	ON pc_admin (
		admin_id ASC
	);

ALTER TABLE pc_admin
	ADD
		CONSTRAINT PK_pc_admin
		PRIMARY KEY (
			admin_id
		);

/* ��ǰ */
CREATE TABLE menu (
	menu_code CHAR(8) NOT NULL, /* ��ǰ�ڵ� */
	category VARCHAR2(30) NOT NULL, /* ī�װ� */
	menu_name VARCHAR2(60) NOT NULL, /* ��ǰ�� */
	menu_price NUMBER(5) NOT NULL, /* �ܰ� */
	img VARCHAR2(90) NOT NULL, /* �̹��� */
	menu_input_date DATE DEFAULT sysdate /* �Է��� */
);

COMMENT ON TABLE menu IS '��ǰ';

COMMENT ON COLUMN menu.menu_code IS '��ǰ�ڵ�';

COMMENT ON COLUMN menu.category IS 'ī�װ�';

COMMENT ON COLUMN menu.menu_name IS '��ǰ��';

COMMENT ON COLUMN menu.menu_price IS '�ܰ�';

COMMENT ON COLUMN menu.img IS '�̹���';

COMMENT ON COLUMN menu.menu_input_date IS '�Է���';

CREATE UNIQUE INDEX PK_menu
	ON menu (
		menu_code ASC
	);

ALTER TABLE menu
	ADD
		CONSTRAINT PK_menu
		PRIMARY KEY (
			menu_code
		);

/* �ֹ� */
CREATE TABLE ordering (
	order_num CHAR(12) NOT NULL, /* �ֹ���ȣ */
	quan NUMBER(2) NOT NULL, /* ���� */
	order_date DATE DEFAULT sysdate, /* �ֹ����� */
	status CHAR(1) DEFAULT 'N' NOT NULL, /* ���ۻ��� */
	member_id VARCHAR2(20), /* ȸ�����̵� */
	menu_code CHAR(8), /* ��ǰ�ڵ� */
	card_num NUMBER(3) /* ī���ȣ */
);

COMMENT ON TABLE ordering IS '�ֹ�';

COMMENT ON COLUMN ordering.order_num IS '�ֹ���ȣ';

COMMENT ON COLUMN ordering.quan IS '����';

COMMENT ON COLUMN ordering.order_date IS '�ֹ�����';

COMMENT ON COLUMN ordering.status IS '���ۻ���';

COMMENT ON COLUMN ordering.member_id IS 'ȸ�����̵�';

COMMENT ON COLUMN ordering.menu_code IS '��ǰ�ڵ�';

COMMENT ON COLUMN ordering.card_num IS 'ī���ȣ';

CREATE UNIQUE INDEX PK_ordering
	ON ordering (
		order_num ASC
	);

ALTER TABLE ordering
	ADD
		CONSTRAINT PK_ordering
		PRIMARY KEY (
			order_num
		);

/* ȸ�� */
CREATE TABLE pc_member (
	member_id VARCHAR2(20) NOT NULL, /* ȸ�����̵� */
	member_pass VARCHAR2(20) NOT NULL, /* ��й�ȣ */
	member_name VARCHAR2(30) NOT NULL, /* �̸� */
	member_birth VARCHAR2(8), /* ������� */
	member_gender CHAR(1), /* ���� */
	member_tel VARCHAR2(13) NOT NULL, /* ����ó */
	member_email VARCHAR2(50), /* �̸��� */
	member_detailadd VARCHAR2(500), /* ���ּ� */
	member_rest_time NUMBER(4), /* �ܿ��ð� */
	member_total_price NUMBER(7), /* �ѻ���ѿ�� */
	member_input_date DATE DEFAULT sysdate, /* �Է��� */
	SEQ NUMBER(5) /* ���й�ȣ(seq) */
);

COMMENT ON TABLE pc_member IS 'ȸ��';

COMMENT ON COLUMN pc_member.member_id IS 'ȸ�����̵�';

COMMENT ON COLUMN pc_member.member_pass IS '��й�ȣ';

COMMENT ON COLUMN pc_member.member_name IS '�̸�';

COMMENT ON COLUMN pc_member.member_birth IS '�������';

COMMENT ON COLUMN pc_member.member_gender IS '����';

COMMENT ON COLUMN pc_member.member_tel IS '����ó';

COMMENT ON COLUMN pc_member.member_email IS '�̸���';

COMMENT ON COLUMN pc_member.member_detailadd IS '���ּ�';

COMMENT ON COLUMN pc_member.member_rest_time IS '�ܿ��ð�';

COMMENT ON COLUMN pc_member.member_total_price IS '�ѻ���ѿ��';

COMMENT ON COLUMN pc_member.member_input_date IS '�Է���';

COMMENT ON COLUMN pc_member.SEQ IS '���й�ȣ(seq)';

CREATE UNIQUE INDEX PK_pc_member
	ON pc_member (
		member_id ASC
	);

CREATE UNIQUE INDEX Uk_pc_member_tel
	ON pc_member (
		member_tel ASC
	);

ALTER TABLE pc_member
	ADD
		CONSTRAINT PK_pc_member
		PRIMARY KEY (
			member_id
		);

ALTER TABLE pc_member
	ADD
		CONSTRAINT UK_pc_member
		UNIQUE (
			member_tel
		);

/* ��ǻ�� */
CREATE TABLE pc (
	seat_num NUMBER(3) NOT NULL, /* �¼���ȣ */
	pc_ip VARCHAR2(15) NOT NULL, /* ip */
	x_coor NUMBER(2) NOT NULL, /* X */
	y_coor NUMBER(2) NOT NULL, /* Y */
	member_id VARCHAR2(20), /* ȸ�����̵� */
	card_num NUMBER(3) /* ī���ȣ */
);

COMMENT ON TABLE pc IS '��ǻ��';

COMMENT ON COLUMN pc.seat_num IS '�¼���ȣ';

COMMENT ON COLUMN pc.pc_ip IS 'ip';

COMMENT ON COLUMN pc.x_coor IS 'X';

COMMENT ON COLUMN pc.y_coor IS 'Y';

COMMENT ON COLUMN pc.member_id IS 'ȸ�����̵�';

COMMENT ON COLUMN pc.card_num IS 'ī���ȣ';

CREATE UNIQUE INDEX PK_pc
	ON pc (
		seat_num ASC
	);

ALTER TABLE pc
	ADD
		CONSTRAINT PK_pc
		PRIMARY KEY (
			seat_num
		);

/* pc��� */
CREATE TABLE pc_price (
	charge_time NUMBER(4) NOT NULL, /* �ð� */
	member_price NUMBER(5) NOT NULL, /* ȸ������ */
	guest_price NUMBER(5) NOT NULL /* ��ȸ������ */
);

COMMENT ON TABLE pc_price IS 'pc���';

COMMENT ON COLUMN pc_price.charge_time IS '�ð�';

COMMENT ON COLUMN pc_price.member_price IS 'ȸ������';

COMMENT ON COLUMN pc_price.guest_price IS '��ȸ������';

/* ��ȸ�� */
CREATE TABLE pc_guest (
	card_num NUMBER(3) NOT NULL, /* ī���ȣ */
	guest_total_time NUMBER(4) NOT NULL, /* �� �����ð� */
	guest_total_price NUMBER(7) NOT NULL /* �� ����ѿ�� */
);

COMMENT ON TABLE pc_guest IS '��ȸ��';

COMMENT ON COLUMN pc_guest.card_num IS 'ī���ȣ';

COMMENT ON COLUMN pc_guest.guest_total_time IS '�� �����ð�';

COMMENT ON COLUMN pc_guest.guest_total_price IS '�� ����ѿ��';

CREATE UNIQUE INDEX PK_pc_guest
	ON pc_guest (
		card_num ASC
	);

ALTER TABLE pc_guest
	ADD
		CONSTRAINT PK_pc_guest
		PRIMARY KEY (
			card_num
		);

/* ȸ�� �α� */
CREATE TABLE member_log (
	member_id VARCHAR2(20) NOT NULL, /* ȸ�����̵� */
	member_usetime NUMBER(4) NOT NULL, /* ���ð� */
	member_usedate DATE DEFAULT sysdate NOT NULL, /* ��볯¥ */
	member_chargeprice NUMBER(5) DEFAULT 0 /* �����ݾ� */
);

COMMENT ON TABLE member_log IS 'ȸ�� �α�';

COMMENT ON COLUMN member_log.member_id IS 'ȸ�����̵�';

COMMENT ON COLUMN member_log.member_usetime IS '���ð�';

COMMENT ON COLUMN member_log.member_usedate IS '��볯¥';

COMMENT ON COLUMN member_log.member_chargeprice IS '�����ݾ�';

CREATE UNIQUE INDEX PK_member_log
	ON member_log (
		member_id ASC
	);

ALTER TABLE member_log
	ADD
		CONSTRAINT PK_member_log
		PRIMARY KEY (
			member_id
		);

/* �����ȣ */
CREATE TABLE zipcode (
	SEQ NUMBER(5) NOT NULL, /* ���й�ȣ(seq) */
	zipcode CHAR(7), /* �����ȣ */
	sido CHAR(6), /* �õ� */
	gugun VARCHAR2(25), /* ���� */
	dong VARCHAR2(100), /* �� */
	bunji VARCHAR2(25) /* ���� */
);

COMMENT ON TABLE zipcode IS '�����ȣ';

COMMENT ON COLUMN zipcode.SEQ IS '���й�ȣ(seq)';

COMMENT ON COLUMN zipcode.zipcode IS '�����ȣ';

COMMENT ON COLUMN zipcode.sido IS '�õ�';

COMMENT ON COLUMN zipcode.gugun IS '����';

COMMENT ON COLUMN zipcode.dong IS '��';

COMMENT ON COLUMN zipcode.bunji IS '����';

CREATE UNIQUE INDEX PK_zipcode
	ON zipcode (
		SEQ ASC
	);

ALTER TABLE zipcode
	ADD
		CONSTRAINT PK_zipcode
		PRIMARY KEY (
			SEQ
		);

/* �޼��� */
CREATE TABLE message (
	message VARCHAR2(2000), /* �޼��� */
	msg_account CHAR(1), /* ���� */
	msg_date DATE DEFAULT sysdate, /* �Է��� */
	msg_status CHAR(1) NOT NULL, /* Ȯ�λ��� */
	seat_num NUMBER(3) NOT NULL /* �¼���ȣ */
);

COMMENT ON TABLE message IS '�޼���';

COMMENT ON COLUMN message.message IS '�޼���';

COMMENT ON COLUMN message.msg_account IS '����';

COMMENT ON COLUMN message.msg_date IS '�Է���';

COMMENT ON COLUMN message.msg_status IS 'Ȯ�λ���';

COMMENT ON COLUMN message.seat_num IS '�¼���ȣ';

/* ��ȸ�� �α� */
CREATE TABLE pc_guest_log (
	card_num NUMBER(3) NOT NULL, /* ī���ȣ */
	guest_usetime NUMBER(4) NOT NULL, /* ���ð� */
	guest_usedate DATE NOT NULL, /* ��볯¥ */
	guest_chargeprice NUMBER(5) DEFAULT 0 /* �����ݾ� */
);

COMMENT ON TABLE pc_guest_log IS '��ȸ�� �α�';

COMMENT ON COLUMN pc_guest_log.card_num IS 'ī���ȣ';

COMMENT ON COLUMN pc_guest_log.guest_usetime IS '���ð�';

COMMENT ON COLUMN pc_guest_log.guest_usedate IS '��볯¥';

COMMENT ON COLUMN pc_guest_log.guest_chargeprice IS '�����ݾ�';

CREATE UNIQUE INDEX PK_pc_guest_log
	ON pc_guest_log (
		card_num ASC
	);

ALTER TABLE pc_guest_log
	ADD
		CONSTRAINT PK_pc_guest_log
		PRIMARY KEY (
			card_num
		);

/* �������� */
CREATE TABLE pc_notice (
	admin_notice VARCHAR2(2000) NOT NULL, /* ���� */
	notice_input_date DATE NOT NULL /* �Է��� */
);

COMMENT ON TABLE pc_notice IS '��������';

COMMENT ON COLUMN pc_notice.admin_notice IS '����';

COMMENT ON COLUMN pc_notice.notice_input_date IS '�Է���';

CREATE UNIQUE INDEX PK_pc_notice
	ON pc_notice (
		admin_notice ASC
	);

ALTER TABLE pc_notice
	ADD
		CONSTRAINT PK_pc_notice
		PRIMARY KEY (
			admin_notice
		);

/* ��ǻ�� ���� */
CREATE TABLE pc_status (
	seat_num NUMBER(3) NOT NULL, /* �¼���ȣ */
	pc_status CHAR(1) NOT NULL, /* �������� */
	message_status CHAR(1) NOT NULL, /* �޼������� */
	order_status CHAR(1) NOT NULL /* �ֹ����� */
);

COMMENT ON TABLE pc_status IS '��ǻ�� ����';

COMMENT ON COLUMN pc_status.seat_num IS '�¼���ȣ';

COMMENT ON COLUMN pc_status.pc_status IS '��������';

COMMENT ON COLUMN pc_status.message_status IS '�޼�������';

COMMENT ON COLUMN pc_status.order_status IS '�ֹ�����';

ALTER TABLE ordering
	ADD
		CONSTRAINT FK_menu_TO_ordering
		FOREIGN KEY (
			menu_code
		)
		REFERENCES menu (
			menu_code
		);

ALTER TABLE ordering
	ADD
		CONSTRAINT FK_pc_member_TO_ordering
		FOREIGN KEY (
			member_id
		)
		REFERENCES pc_member (
			member_id
		);

ALTER TABLE ordering
	ADD
		CONSTRAINT FK_pc_guest_TO_ordering
		FOREIGN KEY (
			card_num
		)
		REFERENCES pc_guest (
			card_num
		);

ALTER TABLE pc_member
	ADD
		CONSTRAINT FK_zipcode_TO_pc_member
		FOREIGN KEY (
			SEQ
		)
		REFERENCES zipcode (
			SEQ
		);

ALTER TABLE pc
	ADD
		CONSTRAINT FK_pc_member_TO_pc
		FOREIGN KEY (
			member_id
		)
		REFERENCES pc_member (
			member_id
		);

ALTER TABLE pc
	ADD
		CONSTRAINT FK_pc_guest_TO_pc
		FOREIGN KEY (
			card_num
		)
		REFERENCES pc_guest (
			card_num
		);

ALTER TABLE member_log
	ADD
		CONSTRAINT FK_pc_member_TO_member_log
		FOREIGN KEY (
			member_id
		)
		REFERENCES pc_member (
			member_id
		);

ALTER TABLE message
	ADD
		CONSTRAINT FK_pc_TO_message
		FOREIGN KEY (
			seat_num
		)
		REFERENCES pc (
			seat_num
		);

ALTER TABLE pc_guest_log
	ADD
		CONSTRAINT FK_pc_guest_TO_pc_guest_log
		FOREIGN KEY (
			card_num
		)
		REFERENCES pc_guest (
			card_num
		);

ALTER TABLE pc_status
	ADD
		CONSTRAINT FK_pc_TO_pc_status
		FOREIGN KEY (
			seat_num
		)
		REFERENCES pc (
			seat_num
		)
		ON DELETE CASCADE
		;