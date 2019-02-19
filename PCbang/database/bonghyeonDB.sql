/* 컴퓨터 */
DROP TABLE pc
	CASCADE CONSTRAINTS;

/* 컴퓨터 상태 */
DROP TABLE pc_status
	CASCADE CONSTRAINTS;

/* 컴퓨터 */
CREATE TABLE pc (
	seat_num NUMBER(3) NOT NULL, /* 좌석번호 */
	pc_ip VARCHAR2(15) NOT NULL, /* ip */
	x_coor NUMBER(2) NOT NULL, /* X */
	y_coor NUMBER(2) NOT NULL, /* Y */
	member_id VARCHAR2(20), /* 회원아이디 */
	card_num NUMBER(3) /* 카드번호 */
);

COMMENT ON TABLE pc IS '컴퓨터';

COMMENT ON COLUMN pc.seat_num IS '좌석번호';

COMMENT ON COLUMN pc.pc_ip IS 'ip';

COMMENT ON COLUMN pc.x_coor IS 'X';

COMMENT ON COLUMN pc.y_coor IS 'Y';

COMMENT ON COLUMN pc.member_id IS '회원아이디';

COMMENT ON COLUMN pc.card_num IS '카드번호';

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

/* 컴퓨터 상태 */
CREATE TABLE pc_status (
	seat_num NUMBER(3) NOT NULL, /* 좌석번호 */
	pc_status CHAR(1) DEFAULT 'N' NOT NULL, /* 가동상태 */
	message_status CHAR(1) DEFAULT 'N' NOT NULL, /* 메세지상태 */
	order_status CHAR(1) DEFAULT 'N' NOT NULL /* 주문상태 */
);

COMMENT ON TABLE pc_status IS '컴퓨터 상태';

COMMENT ON COLUMN pc_status.seat_num IS '좌석번호';

COMMENT ON COLUMN pc_status.pc_status IS '가동상태';

COMMENT ON COLUMN pc_status.message_status IS '메세지상태';

COMMENT ON COLUMN pc_status.order_status IS '주문상태';

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
