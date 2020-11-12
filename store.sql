DROP DATABASE IF EXISTS `store`;
CREATE DATABASE `store`;
USE `store`;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	`id` INT(10) AUTO_INCREMENT,
	`username` VARCHAR(30) NOT NULL UNIQUE,
	`password` VARCHAR(30) NOT NULL UNIQUE,
	`name` VARCHAR(30) NOT NULL,
	`acc_type` VARCHAR(20) NOT NULL,
	CONSTRAINT `pk_user` PRIMARY KEY (`id`),
	CONSTRAINT `check_acc_type` CHECK (`acc_type` IN (`product_detailsproduct`, `CLIENT`)),
	CONSTRAINT `check_username` CHECK (LENGTH(`username`) BETWEEN 6 AND 20),
	CONSTRAINT `check_password` CHECK (LENGTH(`password`) BETWEEN 8 AND 20)  
);

DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
	`id` INT(10) AUTO_INCREMENT,
	`name` VARCHAR(20),
	CONSTRAINT `pk_author` PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `product_details`;
CREATE TABLE `product_details` (
	`id` INT(10) AUTO_INCREMENT,
	`type` VARCHAR(20) NOT NULL,
	`author` INT(10),
	`title` VARCHAR (20),
	`releaseDate` DATE,
	CONSTRAINT `pk_product_details` PRIMARY KEY (`id`),
	CONSTRAINT `fk_author_product_details` FOREIGN KEY (`author`) REFERENCES `author`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `check_type` CHECK (`type` IN (`product`, `CD`, `CANDY`))
);

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
	`id` INT(10) AUTO_INCREMENT,
	`product_details` INT(10),
	`price` FLOAT(30) DEFAULT 0,
	`quantity` INT(50) DEFAULT 0,
	CONSTRAINT `pk_product` PRIMARY KEY (`id`),
	CONSTRAINT `fk_product_product_details` FOREIGN KEY (`product_details`) REFERENCES `product_details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `check_quantity` CHECK (`quantity` >= 0),
	CONSTRAINT `check_price` CHECK (`price` >=0)
);

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
	`id` INT(10) AUTO_INCREMENT,
	`client` INT(10),
	`order_date` DATE,
	`status` VARCHAR(40),
	CONSTRAINT `pk_order` PRIMARY KEY (`id`),
	CONSTRAINT `fk_client_order` FOREIGN KEY (`client`) REFERENCES `user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `check_status` CHECK (`status` IN (`ACCEPTED`,`DECLINED`,`ONHOLD`))
);

DROP TABLE IF EXISTS `order_line`;
CREATE TABLE `order_line` (
	`id` INT(10) AUTO_INCREMENT,
	`product` INT(10),
	`quantity` INT(10),
	`order` INT(10),
	CONSTRAINT `pk_order_line` PRIMARY KEY (`id`),
	CONSTRAINT `fk_order_line_product` FOREIGN KEY (`product`) REFERENCES `product`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `fk_order_line_order` FOREIGN KEY (`order`) REFERENCES `order`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `check_quantity` CHECK (`quantity` > 0)
); 
