----------------------------------------------------------
-- Table structure for table `user`
----------------------------------------------------------
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `login` varchar(20) NOT NULL,
                        `password` varchar(20) NOT NULL,
                        `role` varchar(20) DEFAULT 'USER',
                        `balance` decimal(9,2) DEFAULT '0.00',
                        `email` varchar(50) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

INSERT INTO user (login, password, role)
VALUES ('Manager', '123', 'MANAGER');

INSERT INTO user (login, password, role)
VALUES ('Master not assigned', '123', 'MASTER');

INSERT INTO user (login, password, email)
VALUES ('UserWithMail', '123', 'some@gmail.com');

INSERT INTO user (login, password, email, role)
VALUES ('ManagerWithMail', '123', 'manager@gmail.com', 'MANAGER');

INSERT INTO user (login, password, balance)
VALUES ('Юзер', '123',1000);

INSERT INTO user (login, password, balance)
VALUES ('User', '123', 1000);

INSERT INTO user (login, password, role)
VALUES ('Master Alexander', '123', 'MASTER');

INSERT INTO user (login, password, role)
VALUES ('Master', '123', 'MASTER');

INSERT INTO user (login, password, balance)
VALUES ('юзер2', '123',100);

select * from user;

----------------------------------------------------------
-- Table structure for table `receipt`
----------------------------------------------------------
DROP TABLE IF EXISTS `receipt`;

CREATE TABLE `receipt` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `item` varchar(120) NOT NULL,
                           `description` varchar(120) DEFAULT NULL,
                           `price` decimal(9,2) DEFAULT '0.00',
                           `feedback` varchar(500) DEFAULT NULL,
                           `user_id` int NOT NULL,
                           `master_id` int DEFAULT 2,
                           `status` varchar(50) DEFAULT 'Waiting for payment',
                           `update_time` datetime DEFAULT NOW(),
                           `create_time` datetime DEFAULT NOW(),
                           PRIMARY KEY (`id`),
                           KEY `fk_requests_receipt_idx` (`user_id`),
                           KEY `fk_receipt_user1_idx` (`master_id`),
                           CONSTRAINT `fk_receipt_user1` FOREIGN KEY (`master_id`) REFERENCES `user` (`id`),
                           CONSTRAINT `fk_requests_receipt` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

select * from user;

INSERT INTO receipt (item, description, user_id)
VALUES ('phone', 'new glass', 3);

INSERT INTO receipt (item, user_id)
VALUES ('phoneDefault', 5);

INSERT INTO receipt (item, description, user_id)
VALUES ('pc', 'motherboard', 6);

INSERT INTO receipt (item, description, user_id)
VALUES ('клавиатура', 'гравировка русского алфавита', 5);

INSERT INTO receipt (item, description, user_id)
VALUES ('Микрофон', 'конденсатор, поправить низкие частоты', 3);