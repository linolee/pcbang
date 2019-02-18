/* ��ǻ�� */
DROP TABLE pc
	CASCADE CONSTRAINTS;

/* ��ǻ�� ���� */
DROP TABLE pc_status
	CASCADE CONSTRAINTS;

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

/* ��ǻ�� ���� */
CREATE TABLE pc_status (
	seat_num NUMBER(3) NOT NULL, /* �¼���ȣ */
	pc_status CHAR(1) DEFAULT 'N' NOT NULL, /* �������� */
	message_status CHAR(1) DEFAULT 'N' NOT NULL, /* �޼������� */
	order_status CHAR(1) DEFAULT 'N' NOT NULL /* �ֹ����� */
);

COMMENT ON TABLE pc_status IS '��ǻ�� ����';

COMMENT ON COLUMN pc_status.seat_num IS '�¼���ȣ';

COMMENT ON COLUMN pc_status.pc_status IS '��������';

COMMENT ON COLUMN pc_status.message_status IS '�޼�������';

COMMENT ON COLUMN pc_status.order_status IS '�ֹ�����';

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
