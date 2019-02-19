/* 관리자 */
DROP TABLE pc_admin 
	CASCADE CONSTRAINTS;

/* 상품 */
DROP TABLE menu 
	CASCADE CONSTRAINTS;

/* 주문 */
DROP TABLE ordering 
	CASCADE CONSTRAINTS;

/* 회원 */
DROP TABLE pc_member 
	CASCADE CONSTRAINTS;

/* 컴퓨터 */
DROP TABLE pc 
	CASCADE CONSTRAINTS;

/* pc요금 */
DROP TABLE pc_price 
	CASCADE CONSTRAINTS;

/* 비회원 */
DROP TABLE pc_guest 
	CASCADE CONSTRAINTS;

/* 회원 로그 */
DROP TABLE member_log 
	CASCADE CONSTRAINTS;

/* 우편번호 */
DROP TABLE zipcode 
	CASCADE CONSTRAINTS;

/* 메세지 */
DROP TABLE message 
	CASCADE CONSTRAINTS;

/* 비회원 로그 */
DROP TABLE pc_guest_log 
	CASCADE CONSTRAINTS;

/* 공지사항 */
DROP TABLE pc_notice 
	CASCADE CONSTRAINTS;

/* 컴퓨터 상태 */
DROP TABLE pc_status 
	CASCADE CONSTRAINTS;

/* 관리자 */
CREATE TABLE pc_admin (
	admin_id VARCHAR2(20) NOT NULL, /* 관리자아이디 */
	admin_pass VARCHAR2(20) NOT NULL, /* 관리자비밀번호 */
	admin_name VARCHAR2(30) NOT NULL, /* 관리자이름 */
	admin_input_date DATE DEFAULT sysdate /* 관리자입력일 */
);

COMMENT ON TABLE pc_admin IS '관리자';

COMMENT ON COLUMN pc_admin.admin_id IS '관리자아이디';

COMMENT ON COLUMN pc_admin.admin_pass IS '관리자비밀번호';

COMMENT ON COLUMN pc_admin.admin_name IS '관리자이름';

COMMENT ON COLUMN pc_admin.admin_input_date IS '관리자입력일';

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

/* 상품 */
CREATE TABLE menu (
	menu_code CHAR(8) NOT NULL, /* 상품코드 */
	category VARCHAR2(30) NOT NULL, /* 카테고리 */
	menu_name VARCHAR2(60) NOT NULL, /* 상품명 */
	menu_price NUMBER(5) NOT NULL, /* 단가 */
	img VARCHAR2(90) NOT NULL, /* 이미지 */
	menu_input_date DATE DEFAULT sysdate /* 입력일 */
);

COMMENT ON TABLE menu IS '상품';

COMMENT ON COLUMN menu.menu_code IS '상품코드';

COMMENT ON COLUMN menu.category IS '카테고리';

COMMENT ON COLUMN menu.menu_name IS '상품명';

COMMENT ON COLUMN menu.menu_price IS '단가';

COMMENT ON COLUMN menu.img IS '이미지';

COMMENT ON COLUMN menu.menu_input_date IS '입력일';

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

/* 주문 */
CREATE TABLE ordering (
	order_num CHAR(12) NOT NULL, /* 주문번호 */
	quan NUMBER(2) NOT NULL, /* 수량 */
	order_date DATE DEFAULT sysdate, /* 주문일자 */
	status CHAR(1) DEFAULT 'N' NOT NULL, /* 제작상태 */
	member_id VARCHAR2(20), /* 회원아이디 */
	menu_code CHAR(8), /* 상품코드 */
	card_num NUMBER(3) /* 카드번호 */
);

COMMENT ON TABLE ordering IS '주문';

COMMENT ON COLUMN ordering.order_num IS '주문번호';

COMMENT ON COLUMN ordering.quan IS '수량';

COMMENT ON COLUMN ordering.order_date IS '주문일자';

COMMENT ON COLUMN ordering.status IS '제작상태';

COMMENT ON COLUMN ordering.member_id IS '회원아이디';

COMMENT ON COLUMN ordering.menu_code IS '상품코드';

COMMENT ON COLUMN ordering.card_num IS '카드번호';

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

/* 회원 */
CREATE TABLE pc_member (
	member_id VARCHAR2(20) NOT NULL, /* 회원아이디 */
	member_pass VARCHAR2(20) NOT NULL, /* 비밀번호 */
	member_name VARCHAR2(30) NOT NULL, /* 이름 */
	member_birth VARCHAR2(8), /* 생년월일 */
	member_gender CHAR(1), /* 성별 */
	member_tel VARCHAR2(13) NOT NULL, /* 연락처 */
	member_email VARCHAR2(50), /* 이메일 */
	member_detailadd VARCHAR2(500), /* 상세주소 */
	member_rest_time NUMBER(4), /* 잔여시간 */
	member_total_price NUMBER(7), /* 총사용한요금 */
	member_input_date DATE DEFAULT sysdate, /* 입력일 */
	SEQ NUMBER(5) /* 구분번호(seq) */
);

COMMENT ON TABLE pc_member IS '회원';

COMMENT ON COLUMN pc_member.member_id IS '회원아이디';

COMMENT ON COLUMN pc_member.member_pass IS '비밀번호';

COMMENT ON COLUMN pc_member.member_name IS '이름';

COMMENT ON COLUMN pc_member.member_birth IS '생년월일';

COMMENT ON COLUMN pc_member.member_gender IS '성별';

COMMENT ON COLUMN pc_member.member_tel IS '연락처';

COMMENT ON COLUMN pc_member.member_email IS '이메일';

COMMENT ON COLUMN pc_member.member_detailadd IS '상세주소';

COMMENT ON COLUMN pc_member.member_rest_time IS '잔여시간';

COMMENT ON COLUMN pc_member.member_total_price IS '총사용한요금';

COMMENT ON COLUMN pc_member.member_input_date IS '입력일';

COMMENT ON COLUMN pc_member.SEQ IS '구분번호(seq)';

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

/* pc요금 */
CREATE TABLE pc_price (
	charge_time NUMBER(4) NOT NULL, /* 시간 */
	member_price NUMBER(5) NOT NULL, /* 회원가격 */
	guest_price NUMBER(5) NOT NULL /* 비회원가격 */
);

COMMENT ON TABLE pc_price IS 'pc요금';

COMMENT ON COLUMN pc_price.charge_time IS '시간';

COMMENT ON COLUMN pc_price.member_price IS '회원가격';

COMMENT ON COLUMN pc_price.guest_price IS '비회원가격';

/* 비회원 */
CREATE TABLE pc_guest (
	card_num NUMBER(3) NOT NULL, /* 카드번호 */
	guest_total_time NUMBER(4) NOT NULL, /* 총 충전시간 */
	guest_total_price NUMBER(7) NOT NULL /* 총 사용한요금 */
);

COMMENT ON TABLE pc_guest IS '비회원';

COMMENT ON COLUMN pc_guest.card_num IS '카드번호';

COMMENT ON COLUMN pc_guest.guest_total_time IS '총 충전시간';

COMMENT ON COLUMN pc_guest.guest_total_price IS '총 사용한요금';

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

/* 회원 로그 */
CREATE TABLE member_log (
	member_id VARCHAR2(20) NOT NULL, /* 회원아이디 */
	member_usetime NUMBER(4) NOT NULL, /* 사용시간 */
	member_usedate DATE DEFAULT sysdate NOT NULL, /* 사용날짜 */
	member_chargeprice NUMBER(5) DEFAULT 0 /* 충전금액 */
);

COMMENT ON TABLE member_log IS '회원 로그';

COMMENT ON COLUMN member_log.member_id IS '회원아이디';

COMMENT ON COLUMN member_log.member_usetime IS '사용시간';

COMMENT ON COLUMN member_log.member_usedate IS '사용날짜';

COMMENT ON COLUMN member_log.member_chargeprice IS '충전금액';

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

/* 우편번호 */
CREATE TABLE zipcode (
	SEQ NUMBER(5) NOT NULL, /* 구분번호(seq) */
	zipcode CHAR(7), /* 우편번호 */
	sido CHAR(6), /* 시도 */
	gugun VARCHAR2(25), /* 구군 */
	dong VARCHAR2(100), /* 동 */
	bunji VARCHAR2(25) /* 번지 */
);

COMMENT ON TABLE zipcode IS '우편번호';

COMMENT ON COLUMN zipcode.SEQ IS '구분번호(seq)';

COMMENT ON COLUMN zipcode.zipcode IS '우편번호';

COMMENT ON COLUMN zipcode.sido IS '시도';

COMMENT ON COLUMN zipcode.gugun IS '구군';

COMMENT ON COLUMN zipcode.dong IS '동';

COMMENT ON COLUMN zipcode.bunji IS '번지';

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

/* 메세지 */
CREATE TABLE message (
	message VARCHAR2(2000), /* 메세지 */
	msg_account CHAR(1), /* 계정 */
	msg_date DATE DEFAULT sysdate, /* 입력일 */
	msg_status CHAR(1) NOT NULL, /* 확인상태 */
	seat_num NUMBER(3) NOT NULL /* 좌석번호 */
);

COMMENT ON TABLE message IS '메세지';

COMMENT ON COLUMN message.message IS '메세지';

COMMENT ON COLUMN message.msg_account IS '계정';

COMMENT ON COLUMN message.msg_date IS '입력일';

COMMENT ON COLUMN message.msg_status IS '확인상태';

COMMENT ON COLUMN message.seat_num IS '좌석번호';

/* 비회원 로그 */
CREATE TABLE pc_guest_log (
	card_num NUMBER(3) NOT NULL, /* 카드번호 */
	guest_usetime NUMBER(4) NOT NULL, /* 사용시간 */
	guest_usedate DATE NOT NULL, /* 사용날짜 */
	guest_chargeprice NUMBER(5) DEFAULT 0 /* 충전금액 */
);

COMMENT ON TABLE pc_guest_log IS '비회원 로그';

COMMENT ON COLUMN pc_guest_log.card_num IS '카드번호';

COMMENT ON COLUMN pc_guest_log.guest_usetime IS '사용시간';

COMMENT ON COLUMN pc_guest_log.guest_usedate IS '사용날짜';

COMMENT ON COLUMN pc_guest_log.guest_chargeprice IS '충전금액';

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

/* 공지사항 */
CREATE TABLE pc_notice (
	admin_notice VARCHAR2(2000) NOT NULL, /* 내용 */
	notice_input_date DATE NOT NULL /* 입력일 */
);

COMMENT ON TABLE pc_notice IS '공지사항';

COMMENT ON COLUMN pc_notice.admin_notice IS '내용';

COMMENT ON COLUMN pc_notice.notice_input_date IS '입력일';

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

/* 컴퓨터 상태 */
CREATE TABLE pc_status (
	seat_num NUMBER(3) NOT NULL, /* 좌석번호 */
	pc_status CHAR(1) NOT NULL, /* 가동상태 */
	message_status CHAR(1) NOT NULL, /* 메세지상태 */
	order_status CHAR(1) NOT NULL /* 주문상태 */
);

COMMENT ON TABLE pc_status IS '컴퓨터 상태';

COMMENT ON COLUMN pc_status.seat_num IS '좌석번호';

COMMENT ON COLUMN pc_status.pc_status IS '가동상태';

COMMENT ON COLUMN pc_status.message_status IS '메세지상태';

COMMENT ON COLUMN pc_status.order_status IS '주문상태';

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