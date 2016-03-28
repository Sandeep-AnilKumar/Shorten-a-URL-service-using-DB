CREATE DATABASE  IF NOT EXISTS `shorten_url`;
USE `shorten_url`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: shorten_url
-- ------------------------------------------------------
-- Server version	5.6.27
--
-- Table structure for table `url_data_table`
--

DROP TABLE IF EXISTS `url_data_table`;

CREATE TABLE `url_data_table` (
  `checksum_value` bigint(19) NOT NULL,
  `short_url` varchar(100) NOT NULL,
  `long_url` varchar(200) NOT NULL,
  PRIMARY KEY (`checksum_value`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `url_data_table`
--

LOCK TABLES `url_data_table` WRITE;

INSERT INTO `url_data_table` VALUES (71965875,'http://san.dy/EGHEFIAG','quora.com/Why-is-the-movie-Batman-v-Superman-Dawn-of-Justice-getting-panned-by-critics'),(169116528,'http://san.dy/BEFkIFa','vogella.com/tutorials/MySQLJava/article.html'),(1086151461,'http://san.dy/AFnoFHj','stackoverflow.com/questions/tagged/multithreading'),(1194199205,'http://san.dy/eBIsDIk','developers.google.com/maps/'),(1929636091,'http://san.dy/AI0FCFCs','google.com/fonts'),(2062352405,'http://san.dy/exIBFt','stackoverflow.com/math/problem1AB/'),(2466771344,'http://san.dy/RmGGFFx','stackoverflow.com/questions/1085709/what-does-synchronized-mean'),(2466771345,'http://san.dy/SmGGFFx','quora.com/questions/1085709/what-does-synchronized-mean'),(3528628588,'http://san.dy/HHEHBFBI','stackoverflow.com/questions/tagged/xcode'),(3528628589,'http://san.dy/IHEHBFBI','quora.com/questions/tagged/xcode'),(3528628590,'http://san.dy/0IEHBFBI','vogella.com/questions/tagged/xcode'),(3763379233,'http://san.dy/GBIKCFK','quora.com/What-are-some-of-the-strangest-facts-about-famous-movies'),(3975085729,'http://san.dy/CGEhEGM','stackoverflow.com/questions/2232759/what-is-the-purpose-of-serialization-in-java'),(4031015759,'http://san.dy/IEGEaEN','quora.com/Whats-the-coolest-feature-youve-seen-in-a-car'),(4157960525,'http://san.dy/yeFIGEO','stackoverflow.com/questions/tagged/thread-safety'),(4157960526,'http://san.dy/zeFIGEO','http://vogella.com/questions/tagged/thread-safety');

UNLOCK TABLES;
-- Dump completed on 2016-03-28  5:07:40
