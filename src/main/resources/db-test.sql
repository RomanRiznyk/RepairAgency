CREATE DATABASE  IF NOT EXISTS `repair` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 */;
USE `repair`;
-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: repair
-- ------------------------------------------------------
-- Server version	8.0.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `login` varchar(20) DEFAULT NULL,
                        `password` varchar(20) DEFAULT NULL,
                        `role` varchar(25) DEFAULT 'USER',
                        `wallet` decimal(20,2) DEFAULT '0.00',
                        `email` varchar(50) DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (25,'Master','123','ENGINEER',0.00,'master@gmail.com'),(26,'admin','1','ADMIN',0.00,'admin@gmail.com'),(27,'user','1','USER',25.55,'user@gmail.com'),(28,'Petrov','1','ENGINEER',0.00,'petrov@gmail.com'),(29,'user2','1','USER',50.00,'user2@gmail.com'),(31,'Misha','1','USER',50.00,'misha@gmail.com'),(32,'User3','1','USER',50.00,'user3@gmail.com'),(33,'User4','1','USER',50.00,'user4@gmail.com'),(35,'User5','1','USER',40.00,'user5@gmail.com'),(39,'Vecheslav','1','ENGINEER',0.00,'vech@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Table structure for table `receipt`
--

DROP TABLE IF EXISTS `receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt` (
                           `invoice_id` int NOT NULL AUTO_INCREMENT,
                           `brand` varchar(120) NOT NULL,
                           `model` varchar(220) NOT NULL,
                           `description` varchar(120) NOT NULL,
                           `price` decimal(20,2) DEFAULT '0.00',
                           `feedback` varchar(500) DEFAULT NULL,
                           `user_id` int NOT NULL,
                           `engineer_id` int DEFAULT '25',
                           `status` varchar(50) DEFAULT 'Payment expected',
                           `date` datetime DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (`invoice_id`),
                           KEY `fk_requests_invoice_idx` (`user_id`),
                           KEY `fk_invoice_user1_idx` (`engineer_id`),
                           CONSTRAINT `fk_invoice_user1` FOREIGN KEY (`engineer_id`) REFERENCES `user` (`id`),
                           CONSTRAINT `fk_requests_invoice` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt`
--

LOCK TABLES `receipt` WRITE;
/*!40000 ALTER TABLE `receipt` DISABLE KEYS */;
INSERT INTO `receipt` VALUES (2,'apple','iphone 6','low battary capacity',0.00,NULL,29,28,'Done','2021-06-05 13:19:02'),(3,'Xiaomi','note8','low battary capacity',25.00,'фів',27,25,'Paid','2021-06-05 13:19:02'),(5,'lenovo','p500','broken screen',100.00,'nice job',27,25,'Payment expected','2021-06-05 13:19:02'),(6,'Nokia','3310','still working',30.00,NULL,29,25,'Paid','2021-06-05 13:19:02'),(7,'Redmi','K20','WI-FI not working',5.00,NULL,27,25,'Paid','2021-06-05 13:19:02'),(8,'Redmi','K10','bluetooth not working',0.00,NULL,27,25,'Payment expected','2021-06-05 13:19:02'),(9,'Redmi','K10','charging stop working',100.00,'It`s not working',32,28,'Done','2021-06-05 13:19:02'),(10,'Samsung','s8','broken glass',80.00,'nice work? thanks to Petrov for helping me',33,28,'Done','2021-06-05 13:19:02'),(11,'Redmi','8','broken',10.00,'good',35,28,'Done','2021-06-05 13:19:02'),(12,'Samsung','s10','empty',0.00,NULL,27,25,'Payment expected','2021-06-05 14:23:18'),(13,'Samsung','s9','empty',0.00,NULL,35,25,'Payment expected','2021-06-05 14:24:17'),(14,'Samsung','s20+','empty',0.00,NULL,27,25,'Payment expected','2021-06-05 14:53:55'),(15,'Redmi','note10','charging stop working',0.00,NULL,27,25,'Payment expected','2021-06-05 15:11:23'),(16,'lenovo','p500','broken screen',0.00,NULL,27,25,'Payment expected','2021-06-05 15:16:44'),(17,'Huawei','p20pro','Camera broken',0.00,NULL,27,25,'Payment expected','2021-06-05 15:30:45');
/*!40000 ALTER TABLE `receipt` ENABLE KEYS */;
UNLOCK TABLES;

/*!50001 DROP VIEW IF EXISTS `allcars`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
# /*!50001 CREATE ALGORITHM=UNDEFINED */
#     /*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
#     /*!50001 VIEW `allcars` AS select `cars`.`id` AS `id`,`cars`.`marque` AS `marque`,`cars`.`car_class` AS `car_class`,`cars`.`model` AS `model`,`cars`.`price` AS `price`,`cars`.`car_status` AS `car_status` from `cars` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
