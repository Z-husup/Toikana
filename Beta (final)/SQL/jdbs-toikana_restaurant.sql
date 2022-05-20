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
-- Table structure for table `restaurant`
--

DROP TABLE IF EXISTS `restaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurant` (
  `idrestaurant` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `adress` varchar(45) NOT NULL,
  `halls` int NOT NULL,
  `manager` varchar(45) NOT NULL,
  `menuName` varchar(45) NOT NULL,
  `image` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(600) NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`idrestaurant`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurant`
--

LOCK TABLES `restaurant` WRITE;
/*!40000 ALTER TABLE `restaurant` DISABLE KEYS */;
INSERT INTO `restaurant` VALUES (1,'Taikazan','Chui Ave., 187А, Bishkek Kyrgyzstan',4,'','Taikazan:','src/main/resources/images/2016-10-14.jpg','In the best restaurant of our city:\n?300 banquet hall ??\n?Delicious food\n?Chaykana @ taikazan.kg\n?Tel: 0770171919\n\nWhatsApp number?\nwa.me//996770171919','Active'),(5,'Bar@191','Sowjetskaja 68',4,'','Bar@191:','src/main/resources/images/Bar191-venue01.jpg','Our unique Bar@191, will transport you to\nthe roots of the Silk Road. Be inspired by \nthe ancient cultures of Armenia, China, Iran,\nItaly, Kyrgyzstan, Turkey, Turkmenistan and\nUzbekistan. The vibrant lounge invites you to \nenjoy our outstanding selection of drinks, \nexquisite blend of cocktails and tasteful bites. \nBar@191 offers themed nights, happy \nhours and availability for private celebrations. ','Active'),(6,'Opera lounge','Sowetskaya 191',1,'','Opera lounge: European:','src/main/resources/images/21256488_XDpYTNIKXRml5iyqF1V4OcLG3jUka4YAoR4yewlyfQ4.jpg','Opera Lounge offers an inspiring view to one\nof the most emblematic buildings in Bishkek: \nthe State Opera and Ballet Theater. With its floor \nto ceiling windows, it creates the perfect \natmosphere for a quick business meeting while \nenjoying a flavorsome lunch and fresh beverages. ','Active'),(7,'BUKHARA','116a Shopokov',1,'','BUKHARA: Turkish','src/main/resources/images/502_503316086.jpg','Another great shout for Kyrgyz and Central Asian \ncuisine in general, plus a few internationally \ninclined dishes. This place is more upmarket than \nFaiza, with prices to reflect this, although it’s still \nreasonable. It’s an attractive place, with Central \nAsian textiles, tiles and crafts on display and a \nnice outdoor terrace. \n\nPhone: +996 554 55 44 44','Active');
/*!40000 ALTER TABLE `restaurant` ENABLE KEYS */;
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
