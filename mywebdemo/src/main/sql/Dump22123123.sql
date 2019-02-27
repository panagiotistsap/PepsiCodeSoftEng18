-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: softeng
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `creates`
--

DROP TABLE IF EXISTS `creates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `creates` (
  `userid` int(11) NOT NULL,
  `parking_lot_id` int(11) NOT NULL,
  `timestamp` date NOT NULL,
  KEY `userid` (`userid`),
  KEY `parking_lot_id` (`parking_lot_id`),
  CONSTRAINT `creates_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`id`),
  CONSTRAINT `creates_ibfk_2` FOREIGN KEY (`parking_lot_id`) REFERENCES `parkinglots` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creates`
--

LOCK TABLES `creates` WRITE;
/*!40000 ALTER TABLE `creates` DISABLE KEYS */;
/*!40000 ALTER TABLE `creates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logged`
--

DROP TABLE IF EXISTS `logged`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logged` (
  `token` varchar(255) NOT NULL,
  `admin` int(1) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`token`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `logged_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logged`
--

LOCK TABLES `logged` WRITE;
/*!40000 ALTER TABLE `logged` DISABLE KEYS */;
INSERT INTO `logged` VALUES ('3jwihnh15pokl6wtc073rellt',1,NULL),('aemkflpoq85jx649rke2959xr',1,NULL),('bayva8yginwkqv2uwkucgghuv',1,NULL),('i8a7immh0p3102ar42nopvxcc',1,NULL),('jgtir7se9l90qq8exd552jlpa',0,NULL),('ue8wxcp5tq4h43m6t3kbzbqmr',1,NULL);
/*!40000 ALTER TABLE `logged` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `modifies`
--

DROP TABLE IF EXISTS `modifies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `modifies` (
  `userid` int(11) NOT NULL,
  `productid` int(11) NOT NULL,
  `timestamp` date NOT NULL,
  `price` double(5,2) NOT NULL,
  KEY `userid` (`userid`),
  KEY `productid` (`productid`),
  CONSTRAINT `modifies_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`id`),
  CONSTRAINT `modifies_ibfk_2` FOREIGN KEY (`productid`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `modifies`
--

LOCK TABLES `modifies` WRITE;
/*!40000 ALTER TABLE `modifies` DISABLE KEYS */;
/*!40000 ALTER TABLE `modifies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkinglots`
--

DROP TABLE IF EXISTS `parkinglots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parkinglots` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `Ing` double(10,5) NOT NULL,
  `Iat` double(10,5) NOT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `withdrawn` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkinglots`
--

LOCK TABLES `parkinglots` WRITE;
/*!40000 ALTER TABLE `parkinglots` DISABLE KEYS */;
INSERT INTO `parkinglots` VALUES (1,'Park24','Kolokotroni 14',37.80000,36.40000,'eksoteriko,asfales',0),(2,'park78','kolok78',36.40000,33.50000,'eksoteriko',0),(4,'sakarakes','13',37.60000,13.00000,'',0),(5,'park81','kolok81',36.40000,33.50000,'eksoteriko',1),(6,'park82','kolok82',36.40000,33.50000,'eksoteriko',0),(7,'park83','kolok83',36.40000,33.50000,'eksoteriko',0),(8,'park84','kolok84',36.40000,33.50000,'eksoteriko',0),(9,'park85','kolok85',36.40000,33.50000,'eksoteriko',0),(10,'park86','kolok86',36.40000,33.50000,'eksoteriko',0),(11,'park87','kolok87',36.40000,33.50000,'eksoteriko',0),(12,'park88','kolok88',36.40000,33.50000,'eksoteriko',0),(13,'park89','kolok89',36.40000,33.50000,'eksoteriko',0),(14,'park90','kolok90',36.40000,33.50000,'eksoteriko',0),(15,'park91','kolok91',36.40000,33.50000,'eksoteriko',0),(16,'park92','kolok92',36.40000,33.50000,'eksoteriko',0),(17,'park93','kolok93',36.40000,33.50000,'eksoteriko',0),(18,'park94','kolok94',36.40000,33.50000,'eksoteriko',0),(19,'park95','kolok95',36.40000,33.50000,'eksoteriko',0),(20,'park96','kolok96',36.40000,33.50000,'eksoteriko',0),(21,'park97','kolok97',36.40000,33.50000,'eksoteriko',0),(22,'park98','kolok98',36.40000,33.50000,'eksoteriko',0),(23,'park99','kolok99',36.40000,33.50000,'eksoteriko',0),(24,'newshop','Karpenisiou 13',37.50000,34.00000,NULL,1),(25,'newshop12','Karpenisiou 14',36.00000,34.00000,NULL,1),(26,'newshop12','Karpenisiou 14',36.00000,35.60000,NULL,1);
/*!40000 ALTER TABLE `parkinglots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` mediumtext,
  `category` varchar(128) NOT NULL,
  `withdrawn` bit(1) NOT NULL DEFAULT b'0',
  `tags` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (2,'prointara','ekswteriko','parking',_binary '\0',NULL),(6,'prointara','ekswteriko','parking',_binary '\0',NULL),(7,'prointara','ekswteriko','parking',_binary '\0',NULL),(8,'prointara','ekswteriko','parking',_binary '\0',NULL),(9,'prointara','ekswteriko','parking',_binary '\0',NULL),(10,'prointara','ekswteriko','parking',_binary '\0',NULL),(11,'prointara','ekswteriko','parking',_binary '\0',NULL),(12,'prointara','ekswteriko','parking',_binary '\0',NULL),(13,'prointara','ekswteriko','parking',_binary '\0',NULL),(14,'prointara','ekswteriko','parking',_binary '\0',NULL),(15,'prointara','ekswteriko','parking',_binary '\0',NULL),(16,'prointara','ekswteriko','parking',_binary '\0',NULL),(17,'prointara','ekswteriko','parking',_binary '\0',NULL),(18,'prointara','ekswteriko','parking',_binary '\0',NULL),(19,'prointara','ekswteriko','parking',_binary '\0',NULL),(20,'prointara','ekswteriko','parking',_binary '\0',NULL),(21,'prointara','ekswteriko','parking',_binary '\0',NULL),(22,'prointara','ekswteriko','parking',_binary '\0',NULL),(23,'prointara','ekswteriko','parking',_binary '\0',NULL),(24,'prointara','ekswteriko','parking',_binary '\0',NULL),(25,'prointara','ekswteriko','parking',_binary '\0',NULL),(26,'prointara','ekswteriko','parking',_binary '\0',NULL),(27,'prointara','ekswteriko','parking',_binary '\0',NULL),(28,'prointara','ekswteriko','parking',_binary '\0',NULL),(29,'prointara','ekswteriko','parking',_binary '\0',NULL),(30,'prointara','ekswteriko','parking',_binary '\0',NULL),(31,'prointara','ekswteriko','parking',_binary '\0',NULL),(32,'prointara','ekswteriko','parking',_binary '\0',NULL),(33,'prointara','ekswteriko','parking',_binary '\0',NULL),(34,'prointara','ekswteriko','parking',_binary '\0',NULL),(35,'prointara','ekswteriko','parking',_binary '\0',NULL),(36,'prointara','ekswteriko','parking',_binary '\0',NULL),(37,'prointara','ekswteriko','parking',_binary '\0',NULL),(38,'prointara','ekswteriko','parking',_binary '\0',NULL),(39,'prointara','ekswteriko','parking',_binary '\0',NULL),(40,'prointara','ekswteriko','parking',_binary '\0',NULL),(41,'prointara','ekswteriko','parking',_binary '\0',NULL),(42,'prointara','ekswteriko','parking',_binary '\0',NULL),(43,'prointara','ekswteriko','parking',_binary '\0',NULL),(44,'prointara','ekswteriko','parking',_binary '\0',NULL),(45,'prointara','ekswteriko','parking',_binary '\0',NULL),(46,'prointara','ekswteriko','parking',_binary '\0',NULL),(47,'prointara','ekswteriko','parking',_binary '\0',NULL),(48,'prointara','ekswteriko','parking',_binary '\0',NULL),(49,'prointara','ekswteriko','parking',_binary '\0',NULL),(50,'prointara','ekswteriko','parking',_binary '\0',NULL),(51,'prointara','ekswteriko','parking',_binary '\0',NULL),(52,'prointara','ekswteriko','parking',_binary '\0',NULL),(53,'prointara','ekswteriko','parking',_binary '\0',NULL),(54,'prointara','ekswteriko','parking',_binary '\0',NULL),(55,'prointara','ekswteriko','parking',_binary '\0',NULL),(56,'prointara','ekswteriko','parking',_binary '\0',NULL),(57,'prointara','ekswteriko','parking',_binary '\0',NULL),(58,'prointara','ekswteriko','parking',_binary '\0',NULL),(59,'prointara','ekswteriko','parking',_binary '\0',NULL),(60,'prointara','ekswteriko','parking',_binary '\0',NULL),(61,'prointara','ekswteriko','parking',_binary '\0',NULL),(62,'prointara','ekswteriko','parking',_binary '\0',NULL),(63,'prointara','ekswteriko','parking',_binary '\0',NULL),(64,'prointara','ekswteriko','parking',_binary '\0',NULL),(65,'prointara','ekswteriko','parking',_binary '\0',NULL),(66,'prointara','ekswteriko','parking',_binary '\0',NULL),(67,'prointara','ekswteriko','parking',_binary '\0',NULL),(68,'prointara','ekswteriko','parking',_binary '\0',NULL),(69,'prointara','ekswteriko','parking',_binary '\0',NULL),(70,'prointara','ekswteriko','parking',_binary '\0',NULL),(71,'prointara','ekswteriko','parking',_binary '\0',NULL),(72,'prointara','ekswteriko','parking',_binary '\0',NULL),(73,'prointara','ekswteriko','parking',_binary '\0',NULL),(74,'prointara','ekswteriko','parking',_binary '\0',NULL),(75,'prointara','ekswteriko','parking',_binary '\0',NULL),(76,'prointara','ekswteriko','parking',_binary '\0',NULL),(77,'prointara','ekswteriko','parking',_binary '\0',NULL),(78,'prointara','ekswteriko','parking',_binary '\0',NULL),(79,'prointara','ekswteriko','parking',_binary '\0',NULL),(80,'prointara','ekswteriko','parking',_binary '\0',NULL),(81,'prointara','ekswteriko','parking',_binary '\0',NULL),(82,'prointara','ekswteriko','parking',_binary '\0',NULL),(83,'prointara','ekswteriko','parking',_binary '\0',NULL),(84,'prointara','ekswteriko','parking',_binary '\0',NULL),(85,'prointara','ekswteriko','parking',_binary '\0',NULL),(86,'prointara','ekswteriko','parking',_binary '\0',NULL),(87,'prointara','ekswteriko','parking',_binary '\0',NULL),(88,'prointara','ekswteriko','parking',_binary '\0',NULL),(89,'prointara','ekswteriko','parking',_binary '\0',NULL),(90,'prointara','ekswteriko','parking',_binary '\0',NULL),(91,'prointara','ekswteriko','parking',_binary '\0',NULL),(92,'prointara','ekswteriko','parking',_binary '\0',NULL),(93,'prointara','ekswteriko','parking',_binary '\0',NULL),(94,'prointara','ekswteriko','parking',_binary '\0',NULL),(95,'prointara','ekswteriko','parking',_binary '\0',NULL),(96,'prointara','ekswteriko','parking',_binary '\0',NULL),(97,'prointara','ekswteriko','parking',_binary '\0',NULL),(98,'prointara','ekswteriko','parking',_binary '\0',NULL),(99,'prointara','ekswteriko','parking',_binary '\0',NULL),(100,'prointara','ekswteriko','parking',_binary '\0',NULL),(101,'prointara','ekswteriko','parking',_binary '\0',NULL),(102,'prointara','ekswteriko','parking',_binary '\0',NULL),(103,'prointara','ekswteriko','parking',_binary '\0',NULL),(104,'prointara','ekswteriko','parking',_binary '\0',NULL),(105,'prointara','ekswteriko','parking',_binary '\0',NULL),(106,'prointara','ekswteriko','parking',_binary '\0',NULL),(107,'prointara','ekswteriko','parking',_binary '\0',NULL),(108,'prointara','ekswteriko','parking',_binary '\0',NULL),(109,'prointara','ekswteriko','parking',_binary '\0',NULL),(110,'prointara','ekswteriko','parking',_binary '\0',NULL),(111,'prointara','ekswteriko','parking',_binary '\0',NULL),(112,'prointara','ekswteriko','parking',_binary '\0',NULL),(113,'prointara','ekswteriko','parking',_binary '\0',NULL),(114,'prointara','ekswteriko','parking',_binary '\0',NULL),(115,'prointara','ekswteriko','parking',_binary '\0',NULL),(116,'prointara','ekswteriko','parking',_binary '\0',NULL),(117,'prointara','ekswteriko','parking',_binary '\0',NULL),(118,'prointara','ekswteriko','parking',_binary '\0',NULL),(119,'prointara','ekswteriko','parking',_binary '\0',NULL),(120,'prointara','ekswteriko','parking',_binary '\0',NULL),(121,'prointara','ekswteriko','parking',_binary '\0',NULL),(122,'prointara','ekswteriko','parking',_binary '\0',NULL),(123,'prointara','ekswteriko','parking',_binary '\0',NULL),(124,'prointara','ekswteriko','parking',_binary '\0',NULL),(125,'prointara','ekswteriko','parking',_binary '\0',NULL),(126,'prointara','ekswteriko','parking',_binary '\0',NULL),(127,'prointara','ekswteriko','parking',_binary '\0',NULL),(128,'prointara','ekswteriko','parking',_binary '\0',NULL),(129,'prointara','ekswteriko','parking',_binary '\0',NULL),(130,'prointara','ekswteriko','parking',_binary '\0',NULL),(131,'prointara','ekswteriko','parking',_binary '\0',NULL),(132,'prointara','ekswteriko','parking',_binary '\0',NULL),(133,'prointara','ekswteriko','parking',_binary '\0',NULL),(134,'prointara','ekswteriko','parking',_binary '\0',NULL),(135,'prointara','ekswteriko','parking',_binary '\0',NULL),(136,'prointara','ekswteriko','parking',_binary '\0',NULL),(137,'prointara','ekswteriko','parking',_binary '\0',NULL),(138,'prointara','ekswteriko','parking',_binary '\0',NULL),(139,'prointara','ekswteriko','parking',_binary '\0',NULL),(140,'prointara','ekswteriko','parking',_binary '\0',NULL),(141,'prointara','ekswteriko','parking',_binary '\0',NULL),(142,'prointara','ekswteriko','parking',_binary '\0',NULL),(143,'prointara','ekswteriko','parking',_binary '\0',NULL),(144,'prointara','ekswteriko','parking',_binary '\0',NULL),(145,'prointara','ekswteriko','parking',_binary '\0',NULL),(146,'prointara','ekswteriko','parking',_binary '\0',NULL),(147,'prointara','ekswteriko','parking',_binary '\0',NULL),(148,'prointara','ekswteriko','parking',_binary '\0',NULL),(149,'prointara','ekswteriko','parking',_binary '\0',NULL),(151,'prointara','ekswteriko','parking',_binary '\0',NULL),(152,'prointara','ekswteriko','parking',_binary '\0',NULL),(153,'prointara','ekswteriko','parking',_binary '\0',NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sells`
--

DROP TABLE IF EXISTS `sells`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sells` (
  `sellerid` int(11) NOT NULL,
  `productid` int(11) NOT NULL,
  `price` double(4,2) DEFAULT NULL,
  `datefrom` date DEFAULT NULL,
  `dateto` date DEFAULT NULL,
  KEY `sellerid` (`sellerid`),
  KEY `productid` (`productid`),
  CONSTRAINT `sells_ibfk_1` FOREIGN KEY (`sellerid`) REFERENCES `parkinglots` (`id`),
  CONSTRAINT `sells_ibfk_2` FOREIGN KEY (`productid`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sells`
--

LOCK TABLES `sells` WRITE;
/*!40000 ALTER TABLE `sells` DISABLE KEYS */;
INSERT INTO `sells` VALUES (9,15,19.80,'2019-01-15','2019-02-03'),(10,10,15.60,'2018-03-04','2018-05-15'),(9,15,19.80,'2018-01-02','2020-02-03');
/*!40000 ALTER TABLE `sells` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `admin` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,'pan','tsap',1),(4,'sof','gal',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-27 14:29:31
