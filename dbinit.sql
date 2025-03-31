-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: bookshop
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `books` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `author` varchar(100) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'Hành Trình Về Phương Đông','Baird T. Spalding',110000.00,11,'Cuốn sách khám phá tâm linh và văn hóa phương Đông'),(2,'Nhà Giả Kim','Paulo Coelho',140000.00,3,'Hành trình theo đuổi giấc mơ của một chàng trai chăn cừu'),(3,'Lập Trình Java Cơ Bản','Nguyễn Văn A',120000.00,7,'Cuốn sách hướng dẫn lập trình Java cho người mới bắt đầu'),(4,'Toán Học Cao Cấp','Trần Thị B',180000.00,8,'Sách giáo khoa dành cho sinh viên đại học'),(5,'Harry Potter và Hòn Đá Phù Thủy','J.K. Rowling',250000.00,19,'Phần đầu tiên trong series nổi tiếng của J.K. Rowling'),(6,'Nhật Ký Trong Tù','Hồ Chí Minh',90000.00,24,'Tập thơ nổi tiếng được viết trong thời gian bị giam cầm'),(7,'Dám Nghĩ Lớn','David J. Schwartz',150000.00,12,'Cuốn sách truyền cảm hứng về tư duy tích cực'),(8,'Tắt Đèn','Ngô Tất Tố',85000.00,18,'Tác phẩm kinh điển của văn học Việt Nam'),(9,'Clean Code','Robert C. Martin',300000.00,10,'Hướng dẫn viết mã nguồn sạch và dễ bảo trì'),(10,'Sapiens: Lược Sử Loài Người','Yuval Noah Harari',280000.00,7,'Khám phá lịch sử nhân loại qua góc nhìn khoa học'),(11,'Đắc Nhân Tâm','Dale Carnegie',130000.00,30,'Cuốn sách nổi tiếng về kỹ năng giao tiếp'),(12,'Bí Mật Tư Duy Triệu Phú','T. Harv Eker',160000.00,14,'Hướng dẫn thay đổi tư duy để đạt được thành công tài chính');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE,
  CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (1,1,2,1,140000.00),(2,1,3,1,120000.00),(3,2,2,1,140000.00),(4,3,3,4,120000.00),(5,4,2,1,140000.00),(6,5,3,1,120000.00),(7,6,2,1,140000.00),(8,7,2,1,140000.00),(9,8,3,2,120000.00),(10,8,6,1,90000.00),(11,8,5,1,250000.00),(12,9,2,1,140000.00),(13,9,1,1,110000.00),(14,9,3,1,120000.00);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(20) DEFAULT 'PENDING',
  `total_amount` decimal(10,2) DEFAULT NULL,
  `payment_method` varchar(20) DEFAULT NULL,
  `receiver_name` varchar(45) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `receiver_phone` varchar(45) DEFAULT NULL,
  `orderscol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,3,NULL,'PENDING',260000.00,'COD','Nguyá»n Minh Tiáº¿n','Duong so 22, Hiep Binh Chanh','0817138594',NULL),(2,3,NULL,'PENDING',140000.00,'COD','Nguyá»n Minh Tiáº¿n','Duong so 22, Hiep Binh Chanh','0817138594',NULL),(3,3,NULL,'PENDING',480000.00,'COD','Nguyá»n Minh Tiáº¿n','Duong so 22','0817138594',NULL),(4,3,NULL,'PENDING',140000.00,'COD','Nguyá»n Minh Tiáº¿n','Duong so 22','0817138594',NULL),(5,3,NULL,'PENDING',120000.00,'COD','Nguyá»n Minh Tiáº¿n','Duong so 22, Hiep Binh Chanh','0817138594',NULL),(6,4,NULL,'PENDING',140000.00,'COD','Nguyá»n Minh Tiáº¿n','Duong so 22, Hiep Binh Chanh','0817138594',NULL),(7,3,NULL,'PENDING',140000.00,'COD','Nguyá»n Minh Tiáº¿n','Duong so 22, Hiep Binh Chanh','0817138594',NULL),(8,4,NULL,'PENDING',580000.00,'COD','Nguyen Tien','Duong so 22, Hiep Binh Chanh','0817138594',NULL),(9,4,NULL,'PENDING',370000.00,'COD','Nguyá»n Tiáº¿n','Duong so 22, Hiep Binh Chanh','0817138594',NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` text NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,'$argon2id$v=19$m=15360,t=2,p=1$BP++zjOga8WfQElZYPTJe4CFavyuYNJD1Kth2QbkY+K8n6i+RiOGIN629vyZCijZLapVV2d9YjDOJmouPOReeA$fOjL532mT6MtsMOP3u2I0xUHVj+r0FbtM8p22cjQxIo','Nguyễn Minh Tiến','admin@gmail.com'),(4,'$argon2id$v=19$m=15360,t=2,p=1$BP++zjOga8WfQElZYPTJe4CFavyuYNJD1Kth2QbkY+K8n6i+RiOGIN629vyZCijZLapVV2d9YjDOJmouPOReeA$fOjL532mT6MtsMOP3u2I0xUHVj+r0FbtM8p22cjQxIo','Nguyễn E Mail','minhtien4264@gmail.com');
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

-- Dump completed on 2025-03-31 14:35:22
