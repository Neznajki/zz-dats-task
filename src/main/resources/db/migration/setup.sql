CREATE DATABASE IF NOT EXISTS `kindergarten`;
CREATE USER IF NOT EXISTS `user`@'localhost';
GRANT ALL ON kindergarten.* TO 'user'@'localhost';
FLUSH PRIVILEGES;