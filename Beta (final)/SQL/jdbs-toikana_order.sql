-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: jdbs-toikana
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `client` varchar(45) NOT NULL,
  `restaurant` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `description` varchar(600) NOT NULL,
  `date` date NOT NULL,
  `status` varchar(45) NOT NULL,
  `pay` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,'Darika','Taikazan','Corporate','10 people\n\nWe want:\n8 cola; 10 Kebab; 2 Cakes\n adiitional - Music\n','2022-05-20','Fisnished','374'),(2,'Akyl','Taikazan','Corporate','10 people\n100 hamsi','2022-05-20','Fisnished','520'),(3,'Akyl','Taikazan','Toi','1000 people\n1000 kebab\n\n','2022-05-19','Fisnished','26004'),(4,'Bayas','Taikazan','Wedding','100 people\n\nmany food','2022-05-19','Fisnished','112717'),(5,'Zhu','Taikazan','Birthday','10 people\n25 kebabs\n15 Kofte ','2022-05-15','Fisnished','8594'),(6,'Akyl','Taikazan','Birthday','defaf','2022-05-04','Fisnished','0'),(7,'Rustam','Taikazan','Birthday','Want to order a table for 7 people\nwith kebab and 4 cola\n\nadditionally - music','2022-05-14','Fisnished','0'),(8,'Rustam','Taikazan','Wedding','For 200 people\nBeshbarmak\nand all drinks\nand cake ','2022-05-17','Fisnished','0'),(9,'Akyl','Taikazan','Toi','check','2022-05-21','Active','33021327'),(10,'Darika','Chaikana Navat','Corporate','10 people\n5 Pizza 5 Cola\n2 tables\nAdditional: food','2022-05-20','Fisnished','6595'),(11,'Darika','Bar@191','Birthday','12 people\n12 spagetti; 6 cola; 7 red','2022-05-21','Active','9024'),(12,'Akyl','Bar@191','Corporate','15 people\n15 Kimchi\ncola\nred','2022-06-04','Active','10976'),(13,'Akyl','Taikazan','Individual','15 people\n5Kebab\nTea\nCake 2\n','2022-05-29','Active','6379');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-21  1:56:40
