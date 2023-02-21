INSERT INTO web_module6.role (name)
VALUES ('ROLE_ADMIN');

INSERT INTO web_module6.role (name)
VALUES ('ROLE_EMPLOYEE');

INSERT INTO web_module6.role (name)
VALUES ('ROLE_SELLER');

INSERT INTO web_module6.role (name)
VALUES ('ROLE_BUYER');


INSERT INTO web_module6.gender (name)
VALUES ('Nam');

INSERT INTO web_module6.gender (name)
VALUES ('Nữ');


INSERT INTO web_module6.position (name, salary)
VALUES ('Tổ trưởng', 15000000);

INSERT INTO web_module6.position (name, salary)
VALUES ('Trưởng nhóm', 12000000);

INSERT INTO web_module6.position (name, salary)
VALUES ('Nhân viên', 8000000);


INSERT INTO web_module6.status (id, name, type)
VALUES (1, 'Mở', 'U');

INSERT INTO web_module6.status (id, name, type)
VALUES (2, 'Khóa', 'U');

INSERT INTO web_module6.status (id, name, type)
VALUES (3, 'Đang bán', 'P');

INSERT INTO web_module6.status (id, name, type)
VALUES (4, 'Ngừng bán', 'P');

INSERT INTO web_module6.status (id, name, type)
VALUES (5, 'Chờ xử lý', 'O');

INSERT INTO web_module6.status (id, name, type)
VALUES (6, 'Hoàn thành', 'O');

INSERT INTO web_module6.status (id, name, type)
VALUES (7, 'Đã hủy', 'O');

INSERT INTO web_module6.category (id, name)
VALUES (1, 'Máy tính');

INSERT INTO web_module6.category (name)
VALUES ('Điện thoại');

INSERT INTO web_module6.category (name)
VALUES ('Laptop');

INSERT INTO web_module6.category (name)
VALUES ('Máy tính bảng');

INSERT INTO web_module6.category (name)
VALUES ('Thời trang');

INSERT INTO web_module6.category (name)
VALUES ('Dày dép');

INSERT INTO web_module6.category (name)
VALUES ('Thú cưng');

INSERT INTO web_module6.category (name)
VALUES ('Mẹ và bé');

INSERT INTO web_module6.category (name)
VALUES ('Truyện');

INSERT INTO web_module6.category (name)
VALUES ('Gia dụng');

INSERT INTO web_module6.category (name)
VALUES ('Điện tử');

INSERT INTO web_module6.category (name)
VALUES ('Đồ chơi');

INSERT INTO web_module6.address (id, detail, district_id, province_id, ward_id)
VALUES (1, '1 Nguyễn Đức Cảnh', 1, 1, 324);

INSERT INTO web_module6.address (id, detail, district_id, province_id, ward_id)
VALUES (2, '2 Nguyễn Họa', 4, 6, 666);

INSERT INTO web_module6.address (id, detail, district_id, province_id, ward_id)
VALUES (3, '3 Tôn Đức Thắng', 3, 7, 232);

INSERT INTO web_module6.user (id, password, username, role_id, status_id)
VALUES (1, '1234567', 'nin123', 3, 1);

INSERT INTO web_module6.user (id, password, username, role_id, status_id)
VALUES (2, '1234567', 'linh123', 3, 1);

INSERT INTO web_module6.user (id, password, username, role_id, status_id)
VALUES (3, '1234567', 'dat123', 3, 1);


INSERT INTO web_module6.seller (description, name, phone_number, address_id, user_id)
VALUES ('Không có', 'Ninh', '0123409876', 1, 1);

INSERT INTO web_module6.seller (description, name, phone_number, address_id, user_id)
VALUES ('Không có', 'Linh', '0098712346', 1, 2);

INSERT INTO web_module6.seller (description, name, phone_number, address_id, user_id)
VALUES ('Không có', 'Dat', '0908712346', 1, 3);


