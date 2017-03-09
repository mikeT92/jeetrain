CREATE DATABASE jeeshop_db CHARACTER SET = 'utf8';
CREATE USER 'jeeshop-admin'@'localhost' IDENTIFIED BY 'admin123';
GRANT USAGE ON *.* TO 'jeeshop-admin'@'localhost';
GRANT ALL ON jeeshop_db.* TO 'jeeshop-admin'@'localhost';
CREATE USER 'jeeshop-connect'@'localhost' IDENTIFIED BY 'connect123';
GRANT INSERT, SELECT, UPDATE, DELETE ON jeeshop_db.* TO 'jeeshop-connect'@'localhost';
FLUSH PRIVILEGES;
