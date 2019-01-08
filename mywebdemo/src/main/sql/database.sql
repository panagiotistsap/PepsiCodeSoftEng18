-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: softeng
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
-- Table structure for table `logged`
--

DROP TABLE IF EXISTS `logged`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logged` (
  `token` varchar(255) NOT NULL,
  `admin` int(1) DEFAULT NULL,
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logged`
--

LOCK TABLES `logged` WRITE;
/*!40000 ALTER TABLE `logged` DISABLE KEYS */;
INSERT INTO `logged` VALUES ('i8a7immh0p3102ar42nopvxcc',1),('jgtir7se9l90qq8exd552jlpa',0),('ue8wxcp5tq4h43m6t3kbzbqmr',1);
/*!40000 ALTER TABLE `logged` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (2,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(3,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(6,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(7,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(8,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(9,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(10,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(11,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(12,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(13,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(14,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(15,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(16,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(17,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(18,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(19,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(20,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(21,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(22,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(23,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(24,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(25,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(26,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(27,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(28,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(29,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(30,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(31,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(32,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(33,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(34,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(35,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(36,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(37,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(38,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(39,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(40,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(41,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(42,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(43,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(44,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(45,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(46,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(47,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(48,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(49,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(50,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(51,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(52,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(53,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(54,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(55,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(56,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(57,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(58,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(59,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(60,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(61,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(62,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(63,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(64,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(65,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(66,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(67,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(68,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(69,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(70,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(71,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(72,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(73,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(74,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(75,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(76,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(77,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(78,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(79,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(80,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(81,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(82,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(83,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(84,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(85,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(86,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(87,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(88,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(89,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(90,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(91,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(92,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(93,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(94,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(95,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(96,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(97,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(98,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(99,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(100,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(101,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(102,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(103,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(104,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(105,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(106,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(107,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(108,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(109,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(110,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(111,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(112,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(113,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(114,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(115,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(116,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(117,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(118,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(119,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(120,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(121,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(122,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(123,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(124,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(125,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(126,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(127,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(128,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(129,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(130,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(131,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(132,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(133,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(134,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(135,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(136,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(137,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(138,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(139,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(140,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(141,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(142,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(143,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(144,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(145,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(146,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(147,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(148,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(149,'goo','ahhaha','tou diaolo',_binary '',NULL),(151,'goo','ahhaha','tou diaolo',_binary '\0',NULL),(152,'goo','ahhaha','tou diaolo',_binary '\0',NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller`
--

DROP TABLE IF EXISTS `seller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seller` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `Ing` double(10,5) NOT NULL,
  `Iat` double(10,5) NOT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `withdrawn` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller`
--

LOCK TABLES `seller` WRITE;
/*!40000 ALTER TABLE `seller` DISABLE KEYS */;
/*!40000 ALTER TABLE `seller` ENABLE KEYS */;
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
  `price` varchar(255) NOT NULL,
  KEY `sellerid` (`sellerid`),
  KEY `productid` (`productid`),
  CONSTRAINT `sells_ibfk_1` FOREIGN KEY (`sellerid`) REFERENCES `seller` (`id`),
  CONSTRAINT `sells_ibfk_2` FOREIGN KEY (`productid`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sells`
--

LOCK TABLES `sells` WRITE;
/*!40000 ALTER TABLE `sells` DISABLE KEYS */;
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

-- Dump completed on 2019-01-08 16:25:09
