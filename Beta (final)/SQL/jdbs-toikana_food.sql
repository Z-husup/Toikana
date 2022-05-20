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
-- Table structure for table `food`
--

DROP TABLE IF EXISTS `food`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food` (
  `id` int NOT NULL AUTO_INCREMENT,
  `foodMenuName` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  `price` int NOT NULL,
  `type` varchar(45) NOT NULL,
  `restaurant` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food`
--

LOCK TABLES `food` WRITE;
/*!40000 ALTER TABLE `food` DISABLE KEYS */;
INSERT INTO `food` VALUES (1,'Taikazan:','Kebap',100,'1st Meal','Taikazan'),(2,'Taikazan:','Adana kebabı',150,'1st Meal','Taikazan'),(3,'Taikazan:','Şiş kebab',150,'1st Meal','Taikazan'),(4,'Taikazan:','Köfte',200,'1st Meal','Taikazan'),(5,'Taikazan:','Hamsi',200,'2nd Meal','Taikazan'),(7,'Taikazan:','İmam bayıldı ',100,'2nd Meal','Taikazan'),(8,'Taikazan:','Tea',90,'Drink','Taikazan'),(9,'Taikazan:','Cola',100,'Drink','Taikazan'),(10,'Taikazan:','Green Tea',90,'Drink','Taikazan'),(11,'Taikazan:','Cake',90,'Dessert','Taikazan'),(12,'Taikazan:','Chocolate Biskuit',150,'Dessert','Taikazan'),(13,'Taikazan:','Beshbarmak',100,'1st Meal','Taikazan'),(21,'Taikazan:','Ice Tea',45,'Drink','Taikazan'),(41,'Taikazan:','Grill',120,'2nd Meal','Taikazan'),(43,'Taikazan:','Tequila',300,'Drink','Taikazan'),(45,'Taikazan:','Potato',30,'Dessert','Taikazan'),(46,'Taikazan:','Orange',70,'Drink','Taikazan'),(47,'Taikazan:','Brownie',150,'Dessert','Taikazan'),(52,'Bar@191:','Cola',80,'Drink','Bar@191'),(53,'Bar@191:','Spagetti',160,'1st Meal','Bar@191'),(56,'Bar@191:','Kimchi',120,'2nd Meal','Bar@191'),(57,'Bar@191:','Red',170,'Dessert','Bar@191'),(58,'Taikazan:','Lagman',120,'1st Meal','Taikazan'),(59,'Opera lounge: European:','Pizza',220,'2nd Meal','Opera lounge'),(60,'Opera lounge: European:','Cola',80,'Drink','Opera lounge'),(61,'Opera lounge: European:','Strawberry',100,'Dessert','Opera lounge'),(62,'Opera lounge: European:','Cheese Soup',160,'1st Meal','Opera lounge'),(63,'BUKHARA: Turkish','Lagman',100,'1st Meal','BUKHARA'),(64,'BUKHARA: Turkish','Tea',80,'Drink','BUKHARA'),(65,'BUKHARA: Turkish','Cake',80,'Dessert','BUKHARA');
/*!40000 ALTER TABLE `food` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-21  1:56:41
