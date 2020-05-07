CREATE DATABASE  IF NOT EXISTS `epap` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `epap`;
-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: epap
-- ------------------------------------------------------
-- Server version	5.7.25-log

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
-- Table structure for table `account_locations`
--

DROP TABLE IF EXISTS `account_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `account_locations` (
  `location_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `account_id` int(11) unsigned NOT NULL,
  `location_name` varchar(50) NOT NULL,
  `latitude` double(9,6) NOT NULL,
  `longitude` double(9,6) NOT NULL,
  `status` smallint(5) unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`location_id`),
  UNIQUE KEY `location_id_UNIQUE` (`location_id`),
  KEY `fk_account_locations_account_id_idx` (`account_id`),
  CONSTRAINT `fk_account_locations_account_id` FOREIGN KEY (`account_id`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_locations`
--

LOCK TABLES `account_locations` WRITE;
/*!40000 ALTER TABLE `account_locations` DISABLE KEYS */;
INSERT INTO `account_locations` VALUES (1,6,'شمال الرياض - حي النفل',25.083111,48.039436,1),(2,8,' كيلو 6، جدة',25.155229,46.649323,1),(3,10,'المجمعة',24.146127,47.309189,1),(4,12,'الحزم - الخرج',23.910164,46.941833,1),(5,14,'الشماسية - القصيم',25.083111,48.039436,1),(6,16,'حوطة بني تميم',25.124887,49.302564,1),(7,18,'الرياض وضواحيها',25.155229,46.649323,1),(8,18,'سدير',25.155229,46.649323,1),(9,18,' الدوادمي وعفيف',25.155229,46.649323,1),(10,19,'جدة والطائف',25.124887,49.302564,1),(11,20,'المنطقة الجنوبية',25.124887,49.302564,1),(12,21,'شرق الرياض',25.124887,49.302564,1),(13,22,'حائل',25.124887,49.302564,1),(14,22,'أبها وخميس مشيط',25.124887,49.302564,1),(15,23,'حفر الباطن القيصومة',25.124887,49.302564,1),(16,29,'الرياض',25.124887,49.302564,1),(17,30,'الرياض',25.124887,49.302564,1),(18,31,'الرياض',25.124887,49.302564,1),(19,32,'الرياض',25.124887,49.302564,1),(20,33,'الرياض',25.124887,49.302564,1),(21,34,'الرياض',25.124887,49.302564,1),(22,35,'الرياض',25.124887,49.302564,1),(23,36,'الرياض',25.124887,49.302564,1),(24,37,'الرياض',25.124887,49.302564,1),(25,38,'الرياض',25.124887,49.302564,1),(26,39,'الرياض',25.124887,49.302564,1),(27,40,'الرياض',25.124887,49.302564,1),(28,41,'الرياض',25.124887,49.302564,1),(29,42,'الرياض',25.124887,49.302564,1),(30,43,'الرياض',25.124887,49.302564,1),(31,44,'الرياض',25.124887,49.302564,1),(32,45,'الرياض',25.124887,49.302564,1),(33,46,'الرياض',25.124887,49.302564,1),(34,47,'الرياض',25.124887,49.302564,1),(35,48,'الرياض',25.124887,49.302564,1),(36,49,'الرياض',25.124887,49.302564,1),(37,50,'الرياض',25.124887,49.302564,1),(38,51,'الرياض',25.124887,49.302564,1),(39,52,'الرياض',25.124887,49.302564,1),(40,53,'الرياض',25.124887,49.302564,1),(41,54,'الرياض',25.124887,49.302564,1),(42,55,'الرياض',25.124887,49.302564,1),(43,56,'الرياض',25.124887,49.302564,1),(44,57,'الرياض',25.124887,49.302564,1),(45,58,'الرياض',25.124887,49.302564,1),(46,59,'الرياض',25.124887,49.302564,1),(47,60,'الرياض',25.124887,49.302564,1),(48,61,'الرياض',25.124887,49.302564,1),(49,62,'الرياض',25.124887,49.302564,1),(50,63,'الرياض',25.124887,49.302564,1),(51,64,'الرياض',25.124887,49.302564,1),(52,65,'الرياض',25.124887,49.302564,1),(53,67,'الرياض',25.124887,49.302564,1),(54,68,'الرياض',25.124887,49.302564,1),(55,69,'الرياض',25.124887,49.302564,1),(56,70,'الرياض',25.124887,49.302564,1),(57,71,'الرياض',25.124887,49.302564,1),(58,72,'الرياض',25.124887,49.302564,1),(59,73,'الرياض',25.124887,49.302564,1),(60,74,'الرياض',25.124887,49.302564,1),(61,75,'الرياض',25.124887,49.302564,1),(62,76,'الرياض',25.124887,49.302564,1),(63,77,'الرياض',25.124887,49.302564,1),(64,78,'الرياض',25.124887,49.302564,1),(65,79,'الرياض',25.124887,49.302564,1),(66,80,'الرياض',25.124887,49.302564,1),(67,81,'الرياض',25.124887,49.302564,1),(68,82,'الرياض',25.124887,49.302564,1),(69,83,'الرياض',25.124887,49.302564,1),(70,84,'الرياض',25.124887,49.302564,1),(71,85,'الرياض',25.124887,49.302564,1),(72,86,'الرياض',25.124887,49.302564,1),(73,87,'الرياض',25.124887,49.302564,1),(74,88,'الرياض',25.124887,49.302564,1),(75,89,'الرياض',25.124887,49.302564,1),(76,90,'الرياض',25.124887,49.302564,1),(77,91,'الرياض',25.124887,49.302564,1),(78,92,'الرياض',25.124887,49.302564,1),(79,93,'الرياض',25.124887,49.302564,1),(80,94,'الرياض',25.124887,49.302564,1),(81,66,'الرياض',25.124887,49.302564,1),(82,95,'الرياض',25.124887,49.302564,1);
/*!40000 ALTER TABLE `account_locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_profile`
--

DROP TABLE IF EXISTS `account_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `account_profile` (
  `account_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `public_name` varchar(20) NOT NULL,
  `government_id` varchar(10) NOT NULL,
  `f_name` varchar(20) NOT NULL,
  `m_name` varchar(20) DEFAULT NULL,
  `l_name` varchar(20) NOT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `business_name` varchar(50) NOT NULL,
  `email_address2` varchar(40) DEFAULT NULL,
  `postal_address` varchar(50) DEFAULT NULL,
  `phone_number1` varchar(16) NOT NULL,
  `phone_number2` varchar(16) DEFAULT NULL,
  `notification_code` smallint(3) unsigned DEFAULT NULL,
  `royalty_code` smallint(5) unsigned NOT NULL,
  `rating` float(3,2) unsigned NOT NULL DEFAULT '5.00',
  `num_of_ratings` int(11) unsigned NOT NULL DEFAULT '0',
  `rating_total` int(11) unsigned NOT NULL DEFAULT '0',
  `landing_page_id` smallint(5) unsigned DEFAULT NULL,
  `preferred_language` char(3) NOT NULL,
  `no_of_cancellations` int(11) unsigned NOT NULL DEFAULT '0',
  `offer_or_bid_count` int(11) unsigned NOT NULL DEFAULT '0',
  `executed_offer_or_bid_count` int(11) unsigned NOT NULL DEFAULT '0',
  `contact_us` varchar(100) DEFAULT NULL,
  `reserved1` int(3) DEFAULT NULL,
  `reserved2` varchar(30) DEFAULT NULL,
  `contents` longblob,
  `otp` int(11) DEFAULT NULL,
  `otp_expired_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `otp_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_id_UNIQUE` (`account_id`),
  KEY `fk_account_profile_notification_code_idx` (`notification_code`),
  KEY `fk_account_profile_royalty_code_idx` (`royalty_code`),
  KEY `fk_account_profile_landing_page_id_idx` (`landing_page_id`),
  CONSTRAINT `fk_account_profile_landing_page_id` FOREIGN KEY (`landing_page_id`) REFERENCES `landing_pages` (`landing_page_id`),
  CONSTRAINT `fk_account_profile_notification_code` FOREIGN KEY (`notification_code`) REFERENCES `notification_codes` (`notification_code`),
  CONSTRAINT `fk_account_profile_royalty_code` FOREIGN KEY (`royalty_code`) REFERENCES `royalty_codes` (`royalty_code`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_profile`
--

LOCK TABLES `account_profile` WRITE;
/*!40000 ALTER TABLE `account_profile` DISABLE KEYS */;
INSERT INTO `account_profile` VALUES (1,'مشرف النظام','7894561320','فهد','عبدالله','الحويماني','','منصة إباب','','','0504416182','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(2,'مشرف التشغيل','7894561320','إبراهيم','عمر','المهنا','','منصة إباب','','','1234567890','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(3,'مشرف المالية','7894561320','حسن','محمود','عبدالغني','','منصة إباب','','','1234567890','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(4,'مشرف العلاقات','7894561320','هاني','علي','أصفهاني','','منصة إباب','','','1234567890','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(5,'مشرف النقل','1234567890','حامد','خالد','الإبراهيمي','','منصة إباب','','','1234567890','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(6,'عبدالله أحمد للشراء','1234567890','عبدالله','أحمد','العريفي','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(7,'مندوب عبدالله أحمد ','1234567890','علي','حاج','الجيلاني','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(8,'مها للمشتريات ','1234567890','مها','عبدالله','الأيوبي','','شركة الأيوبي وإخوانه','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(9,'مندوب مها للمشتريات','1234567890','مصطفى','سعد','السعداني','','شركة الأيوبي وإخوانه','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(10,'المتحدون للتصدير','1234567890','عبدالرحمن','عبدالله','الهاجري','','مؤسسة المتحدون','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(11,'مندوب المتحدون ','1234567890','قاسم','صلاح','المريشد','','مؤسسة المتحدون','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(12,'مزارع الشمس الساطعة ','1234567890','إبراهيم','عبده','خفاجي','','مؤسسة بحر جدة','','','9898989898','',3,2,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(13,'مندوب مزارع الشمس','1234567890','جرير','سالم','محياني','','مؤسسة بحر جدة','','','9898989898','',3,2,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(14,'مزرعة حصة الميموني ','1234567890','حصة','عاطف','الأسمر','','مؤسسة حصة الغنامي للتجارة','','','9898989898','',3,2,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(15,'مندوب مزرعة حصة','1234567890','خالد','أحمد','سفياني','','مؤسسة حصة الغنامي للتجارة','','','9898989898','',3,2,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(16,'مزارع الوفيرة','1234567890','سعدون','حمد','السعدون','','السعدون القابضة','','','9898989898','',3,2,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(17,'مندوب مزارع الوفيرة','1234567890','أسعد','جبلي','الحازمي','','السعدون القابضة','','','9898989898','',3,2,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(18,'نقليات الرياض','7894561320','أحمد','عصام','الدباغ','','موسسة الرياض للنقليات','','','1234567890','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(19,'مندوب نقليات الرياض','7894561320','فؤاد','أحمد','مرغني','','موسسة الرياض للنقليات','','','1234567890','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(20,'التوصيل السريع','7894561320','ثامر','يحي','العلياني','','مؤسسة التوصيل السريع للنقليات','','','1234567890','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(21,'مندوب التوصيل السريع','7894561320','بدرية','أحمد','البدراني','','مؤسسة التوصيل السريع للنقليات','','','1234567890','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(22,'جميع الاتجاهات','7894561320','صالح','ظافر','الشهري','','شركة الشهري وإخوانه','','','1234567890','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(23,'مندوب جميع الاتجاهات','7894561320','فيروز','سعد','الفيروزي','','شركة الشهري وإخوانه','','','1234567890','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(24,'عبدالله أحمد','9087654567','عبدالله','علي','أحمد','','',NULL,NULL,'',NULL,3,1,5.00,0,0,NULL,'ar',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25,'مهند أيوب','9087654567','مهند','محمود','شهاب','','',NULL,NULL,'',NULL,3,1,5.00,0,0,NULL,'ar',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(26,'سعيد بخاري','4456323456','سعيد','أحمد','عبد ربه','','',NULL,NULL,'',NULL,3,1,5.00,0,0,NULL,'ar',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,'مشرف الضريبة','1234567890','محمـد','أحمد','العبدالله',NULL,'مصلحة الزكاة',NULL,NULL,'9898989898',NULL,3,1,5.00,0,0,NULL,'ar',0,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(29,'المشتري4','1234567890','4','4','4','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(30,'المشتري5','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(31,'المشتري6','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(32,'المشتري7','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(33,'المشتري8','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(34,'المشتري9','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(35,'المشتري10','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(36,'المشتري11','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(37,'المشتري12','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(38,'المشتري13','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(39,'المشتري14','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(40,'المشتري15','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(41,'المشتري16','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(42,'المشتري17','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(43,'المشتري18','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(44,'المشتري19','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(45,'المشتري20','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(46,'المشتري21','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(47,'المشتري22','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(48,'المشتري23','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(49,'المشتري24','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(50,'المشتري25','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(51,'المشتري26','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(52,'المشتري27','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(53,'المشتري28','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(54,'المشتري29','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(55,'المشتري30','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(56,'المشتري31','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(57,'المشتري32','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(58,'المشتري33','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(59,'المشتري34','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(60,'المشتري35','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(61,'المشتري36','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(62,'المشتري37','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(63,'المشتري38','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(64,'المشتري39','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(65,'المشتري40','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(66,'المشتري41','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(67,'المشتري42','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(68,'المشتري43','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(69,'المشتري44','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(70,'المشتري45','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(71,'المشتري46','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(72,'المشتري47','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(73,'المشتري48','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(74,'المشتري49','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(75,'المشتري50','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(76,'المشتري51','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(77,'المشتري52','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(78,'المشتري53','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(79,'المشتري54','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(80,'المشتري55','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(81,'المشتري56','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(82,'المشتري57','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(83,'المشتري58','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(84,'المشتري59','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(85,'المشتري60','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(86,'المشتري61','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(87,'المشتري62','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(88,'المشتري63','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(89,'المشتري64','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(90,'المشتري65','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(91,'المشتري66','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(92,'المشتري67','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(93,'المشتري68','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(94,'المشتري69','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL),(95,'المشتري70','1234567890','مشتري','مشتري','مشتري','','شركة النهضة القابضة للتجارة والزراعة','','','9898989898','',3,1,5.00,0,0,NULL,'ar',0,0,0,'',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `account_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_status_codes`
--

DROP TABLE IF EXISTS `account_status_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `account_status_codes` (
  `account_status_code` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `account_status_code_meaning` varchar(30) NOT NULL,
  PRIMARY KEY (`account_status_code`),
  UNIQUE KEY `account_status_code_UNIQUE` (`account_status_code`),
  UNIQUE KEY `account_status_code_meaning_UNIQUE` (`account_status_code_meaning`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_status_codes`
--

LOCK TABLES `account_status_codes` WRITE;
/*!40000 ALTER TABLE `account_status_codes` DISABLE KEYS */;
INSERT INTO `account_status_codes` VALUES (9,'Active'),(10,'Approved'),(5,'Blocked'),(8,'Deleted'),(4,'Pending Admin Approval'),(1,'Pending Email Verification'),(3,'Pending Owner Approval'),(2,'Pending Phase 2'),(11,'Pending Phone Number'),(7,'Suspended By Admin'),(6,'Suspended By Owner');
/*!40000 ALTER TABLE `account_status_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_type_codes`
--

DROP TABLE IF EXISTS `account_type_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `account_type_codes` (
  `account_type` char(1) NOT NULL,
  `account_type_meaning` varchar(20) NOT NULL,
  PRIMARY KEY (`account_type`),
  UNIQUE KEY `account_type_UNIQUE` (`account_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_type_codes`
--

LOCK TABLES `account_type_codes` WRITE;
/*!40000 ALTER TABLE `account_type_codes` DISABLE KEYS */;
INSERT INTO `account_type_codes` VALUES ('A','Admin Superuser'),('B','Buyer'),('E','Seller Agent'),('F','Admin Finance'),('H','Shipper'),('I','Shipper Agent'),('N','Admin Shipping'),('O','Admin Operation'),('R','Admin Relations'),('S','Seller'),('U','User'),('V','Visitor'),('W','Admin Vat'),('Y','Buyer Agent');
/*!40000 ALTER TABLE `account_type_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `agent_owner`
--

DROP TABLE IF EXISTS `agent_owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `agent_owner` (
  `agent_owner_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `agent_login_userid` varchar(40) NOT NULL,
  `owner_login_userid` varchar(40) NOT NULL,
  `privileges` smallint(3) unsigned NOT NULL,
  `comments` varchar(100) DEFAULT NULL,
  `purchase_limit` decimal(10,2) unsigned DEFAULT '0.00',
  `limit_spent` decimal(10,2) unsigned DEFAULT '0.00',
  PRIMARY KEY (`agent_owner_id`),
  UNIQUE KEY `agent_owner_UNIQUE` (`agent_owner_id`),
  KEY `fk_agent_owner_owner_id_idx` (`owner_login_userid`),
  KEY `fk_agent_owner_agent_id` (`agent_login_userid`),
  CONSTRAINT `fk_agent_owner_agent_id` FOREIGN KEY (`agent_login_userid`) REFERENCES `login_details` (`login_userid`),
  CONSTRAINT `fk_agent_owner_owner_id` FOREIGN KEY (`owner_login_userid`) REFERENCES `login_details` (`login_userid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agent_owner`
--

LOCK TABLES `agent_owner` WRITE;
/*!40000 ALTER TABLE `agent_owner` DISABLE KEYS */;
INSERT INTO `agent_owner` VALUES (1,'buyer1agent@epap.com','buyer1@epap.com',1,NULL,999999.99,0.00),(2,'buyer2agent@epap.com','buyer2@epap.com',2,NULL,999999.99,0.00),(3,'buyer3agent@epap.com','buyer3@epap.com',1,NULL,999999.99,0.00),(4,'seller1agent@epap.com','seller1@epap.com',1,NULL,999999.99,0.00),(5,'seller2agent@epap.com','seller2@epap.com',2,NULL,999999.99,0.00),(6,'seller3agent@epap.com','seller3@epap.com',1,NULL,999999.99,0.00),(7,'shipper1agent@epap.com','shipper1@epap.com',1,NULL,999999.99,0.00),(8,'shipper2agent@epap.com','shipper2@epap.com',2,NULL,999999.99,0.00),(9,'shipper3agent@epap.com','shipper3@epap.com',1,NULL,999999.99,0.00);
/*!40000 ALTER TABLE `agent_owner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auction_buyers`
--

DROP TABLE IF EXISTS `auction_buyers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `auction_buyers` (
  `auction_buyers_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `auction_sellers_id` int(11) unsigned NOT NULL,
  `daily_auctions_id` int(11) unsigned NOT NULL,
  `buyer_id` int(11) unsigned NOT NULL,
  `partial_allowed` tinyint(1) unsigned NOT NULL,
  `bid_quantity` int(11) unsigned NOT NULL,
  `minimum_quantity` int(11) unsigned NOT NULL DEFAULT '0',
  `executed_quantity` int(11) unsigned NOT NULL,
  `bid_price` float(6,2) unsigned NOT NULL,
  `executed_price` float(6,2) unsigned NOT NULL,
  `royalty_percentage` float(4,2) NOT NULL DEFAULT '0.00' COMMENT 'royalty_value are in percentag.',
  `vat_percentage` float(4,2) unsigned NOT NULL DEFAULT '0.00',
  `shipper` int(11) unsigned DEFAULT NULL,
  `buyer_shipping_charge` float(6,2) unsigned DEFAULT '0.00',
  `buyer_location` int(11) unsigned NOT NULL,
  `buyer_bid_status` smallint(5) NOT NULL DEFAULT '1',
  `reserved1` int(3) DEFAULT NULL,
  `reserved2` varchar(30) DEFAULT NULL,
  `actual_start_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `actual_end_time` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`auction_buyers_id`),
  UNIQUE KEY `auction_buyers_UNIQUE` (`auction_buyers_id`,`buyer_id`),
  UNIQUE KEY `auction_buyers_id_UNIQUE` (`auction_buyers_id`),
  KEY `fk_auction_buyers_auction_buyers_id_idx` (`auction_buyers_id`),
  KEY `fk_auction_buyers_buyer_id_idx` (`buyer_id`),
  KEY `fk_auction_buyers_buyer_location_idx` (`buyer_location`),
  KEY `fk_auction_buyers_daily_auctions_id_idx` (`daily_auctions_id`),
  KEY `fk_auction_buyers_auction_sellers_id_idx` (`auction_sellers_id`),
  KEY `fk_auction_buyers_shipper_idx` (`shipper`),
  CONSTRAINT `fk_auction_buyers_auction_sellers_id` FOREIGN KEY (`auction_sellers_id`) REFERENCES `auction_sellers` (`auction_sellers_id`),
  CONSTRAINT `fk_auction_buyers_buyer_id` FOREIGN KEY (`buyer_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_auction_buyers_buyer_location` FOREIGN KEY (`buyer_location`) REFERENCES `account_locations` (`location_id`),
  CONSTRAINT `fk_auction_buyers_daily_auctions_id` FOREIGN KEY (`daily_auctions_id`) REFERENCES `daily_auctions` (`daily_auctions_id`),
  CONSTRAINT `fk_auction_buyers_shipper` FOREIGN KEY (`shipper`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction_buyers`
--

LOCK TABLES `auction_buyers` WRITE;
/*!40000 ALTER TABLE `auction_buyers` DISABLE KEYS */;
/*!40000 ALTER TABLE `auction_buyers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auction_request`
--

DROP TABLE IF EXISTS `auction_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `auction_request` (
  `request_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `product_id` int(11) unsigned NOT NULL,
  `status` smallint(1) NOT NULL,
  `seller_id` int(11) unsigned NOT NULL,
  `seller_comment` varchar(500) NOT NULL,
  `feedback` varchar(500) DEFAULT NULL,
  `created_on` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `created_by` int(11) unsigned NOT NULL,
  `updated_on` timestamp NULL DEFAULT NULL,
  `updated_by` int(11) unsigned DEFAULT NULL,
  `feedback_on` timestamp(6) NULL DEFAULT NULL,
  `feedback_by` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  UNIQUE KEY `request_id_UNIQUE` (`request_id`),
  KEY `auction_request_product_id_idx` (`product_id`),
  KEY `auction_request_seller_id_idx` (`seller_id`),
  KEY `auction_request_created_by_idx` (`created_by`),
  KEY `auction_request_updated_by_idx` (`updated_by`),
  KEY `auction_request_feedback_by_idx` (`feedback_by`),
  CONSTRAINT `auction_request_created_by` FOREIGN KEY (`created_by`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `auction_request_feedback_by` FOREIGN KEY (`feedback_by`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `auction_request_product_id` FOREIGN KEY (`product_id`) REFERENCES `product_catalog` (`product_id`),
  CONSTRAINT `auction_request_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `auction_request_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction_request`
--

LOCK TABLES `auction_request` WRITE;
/*!40000 ALTER TABLE `auction_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `auction_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auction_sellers`
--

DROP TABLE IF EXISTS `auction_sellers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `auction_sellers` (
  `auction_sellers_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `daily_auctions_id` int(11) unsigned NOT NULL,
  `seller_id` int(11) unsigned NOT NULL,
  `offer_quantity` int(11) unsigned NOT NULL,
  `minimum_quantity` int(11) unsigned NOT NULL DEFAULT '0',
  `available_quantity` int(11) unsigned NOT NULL DEFAULT '0',
  `offer_price` float(6,2) unsigned NOT NULL,
  `royalty_percentage` float(4,2) NOT NULL DEFAULT '0.00' COMMENT 'royalty_value are in percentag.',
  `vat_percentage` float(4,2) unsigned NOT NULL DEFAULT '0.00',
  `seller_location` int(11) unsigned NOT NULL,
  `partial_allowed` tinyint(1) unsigned NOT NULL,
  `shipper` int(11) unsigned DEFAULT NULL,
  `seller_shipping_charge` float(6,2) unsigned DEFAULT '0.00',
  `stn` int(11) NOT NULL,
  `seller_offer_status` smallint(5) NOT NULL DEFAULT '1',
  `actual_start_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `actual_end_time` timestamp(6) NULL DEFAULT NULL,
  `reserved1` int(3) DEFAULT NULL,
  `reserved2` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`auction_sellers_id`),
  UNIQUE KEY `auction_sellers_id_UNIQUE` (`auction_sellers_id`),
  KEY `fk_auction_sellers_seller_id_idx` (`seller_id`),
  KEY `fk_auction_sellers_location_id_idx` (`seller_location`),
  KEY `fk_auction_sellers_daily_auctions_id_idx` (`daily_auctions_id`),
  KEY `fk_auction_sellers_shipper_idx` (`shipper`),
  CONSTRAINT `fk_auction_sellers_daily_auctions_id` FOREIGN KEY (`daily_auctions_id`) REFERENCES `daily_auctions` (`daily_auctions_id`),
  CONSTRAINT `fk_auction_sellers_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_auction_sellers_seller_location` FOREIGN KEY (`seller_location`) REFERENCES `account_locations` (`location_id`),
  CONSTRAINT `fk_auction_sellers_shipper` FOREIGN KEY (`shipper`) REFERENCES `account_profile` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction_sellers`
--

LOCK TABLES `auction_sellers` WRITE;
/*!40000 ALTER TABLE `auction_sellers` DISABLE KEYS */;
/*!40000 ALTER TABLE `auction_sellers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auction_settings`
--

DROP TABLE IF EXISTS `auction_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `auction_settings` (
  `auction_settings_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `currency_code` char(3) NOT NULL,
  `vat_buyers` float(4,2) unsigned NOT NULL,
  `vat_sellers` float(4,2) unsigned NOT NULL,
  PRIMARY KEY (`auction_settings_id`),
  UNIQUE KEY `auction_settings_id_UNIQUE` (`auction_settings_id`),
  KEY `fk_auction_settings_currency_code_idx` (`currency_code`),
  CONSTRAINT `fk_auction_settings_currency_code` FOREIGN KEY (`currency_code`) REFERENCES `currency_codes` (`currency_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction_settings`
--

LOCK TABLES `auction_settings` WRITE;
/*!40000 ALTER TABLE `auction_settings` DISABLE KEYS */;
INSERT INTO `auction_settings` VALUES (1,'SAR',5.00,0.00);
/*!40000 ALTER TABLE `auction_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auction_status_codes`
--

DROP TABLE IF EXISTS `auction_status_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `auction_status_codes` (
  `auction_status_code` smallint(3) unsigned NOT NULL AUTO_INCREMENT,
  `auction_status_code_meaning` varchar(10) NOT NULL,
  PRIMARY KEY (`auction_status_code`),
  UNIQUE KEY `auction_status_code_UNIQUE` (`auction_status_code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction_status_codes`
--

LOCK TABLES `auction_status_codes` WRITE;
/*!40000 ALTER TABLE `auction_status_codes` DISABLE KEYS */;
INSERT INTO `auction_status_codes` VALUES (1,'OPEN'),(2,'RUNNING'),(3,'SETTLING'),(4,'FINISHED'),(5,'CANCELLED'),(6,'EXPIRED');
/*!40000 ALTER TABLE `auction_status_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auction_trades`
--

DROP TABLE IF EXISTS `auction_trades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `auction_trades` (
  `log_timestamp` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `product_id` int(11) unsigned NOT NULL,
  `sold_quantity` int(11) unsigned NOT NULL,
  `sold_price` float(6,2) unsigned NOT NULL,
  UNIQUE KEY `fk_auction_trades_unique` (`product_id`,`log_timestamp`),
  KEY `fk_auction_trades_product_id_idx` (`product_id`),
  CONSTRAINT `fk_auction_trades_product_id` FOREIGN KEY (`product_id`) REFERENCES `product_catalog` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction_trades`
--

LOCK TABLES `auction_trades` WRITE;
/*!40000 ALTER TABLE `auction_trades` DISABLE KEYS */;
/*!40000 ALTER TABLE `auction_trades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_details`
--

DROP TABLE IF EXISTS `bank_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bank_details` (
  `account_id` int(11) unsigned NOT NULL,
  `bank_name` varchar(30) NOT NULL,
  `bank_account_number` varchar(16) NOT NULL,
  `iban` varchar(24) NOT NULL,
  `available_balance` decimal(10,2) NOT NULL DEFAULT '0.00',
  `blocked_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `advance_balance` decimal(10,2) NOT NULL DEFAULT '0.00',
  `cash_position` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_id_UNIQUE` (`account_id`),
  CONSTRAINT `fk_bank_details_account_id` FOREIGN KEY (`account_id`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_details`
--

LOCK TABLES `bank_details` WRITE;
/*!40000 ALTER TABLE `bank_details` DISABLE KEYS */;
INSERT INTO `bank_details` VALUES (1,'مصرف الراجحي','1234567890','123456789012345678901234',500000.00,0.00,0.00,7500000.00),(2,'البنك السعودي البريطاني','9881234810','987456789012345678901234',0.00,0.00,0.00,0.00),(6,'مجموعة سامبا المالية','498498198198','123456789012345678901234',100000.00,0.00,0.00,0.00),(8,'بنك الرياض','98981234890','987456789012345678901234',100000.00,0.00,0.00,0.00),(10,'مجموعة سامبا المالية','498498198198','123456789012345678901234',100000.00,0.00,0.00,0.00),(12,'مصرف الراجحي','123456789120','11498190165110918943231',0.00,0.00,0.00,0.00),(14,'مصرف الراجحي','123456789120','11498190165110918943231',0.00,0.00,0.00,0.00),(16,'مصرف الراجحي','123456789120','11498190165110918943231',0.00,0.00,0.00,0.00),(18,'بنك الرياض','123456789120','11498190165110918943231',0.00,0.00,0.00,0.00),(20,'مجموعة سامبا المالية','123456789120','11498190165110918943231',0.00,0.00,0.00,0.00),(22,'مصرف الإنماء','123456789120','11498190165110918943231',0.00,0.00,0.00,0.00),(27,'مصرف الإنماء','123456789120','11498190165110918943231',0.00,0.00,0.00,0.00),(29,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(30,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(31,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(32,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(33,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(34,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(35,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(36,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(37,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(38,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(39,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(40,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(41,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(42,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(43,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(44,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(45,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(46,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(47,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(48,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(49,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(50,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(51,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(52,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(53,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(54,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(55,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(56,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(57,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(58,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(59,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(60,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(61,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(62,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(63,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(64,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(65,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(66,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(67,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(68,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(69,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(70,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(71,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(72,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(73,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(74,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(75,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(76,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(77,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(78,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(79,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(80,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(81,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(82,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(83,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(84,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(85,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(86,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(87,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(88,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(89,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(90,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(91,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(92,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(93,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(94,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00),(95,'مصرف الإنماء','123456789120','11498190165110918943231',100000.00,0.00,0.00,0.00);
/*!40000 ALTER TABLE `bank_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buyer_balance`
--

DROP TABLE IF EXISTS `buyer_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `buyer_balance` (
  `buyer_id` int(11) unsigned NOT NULL,
  `transaction_date` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `transaction_id` int(11) unsigned DEFAULT NULL,
  `epap_id` int(11) unsigned DEFAULT NULL,
  `transaction_description` varchar(255) NOT NULL,
  `transaction_code` char(1) NOT NULL,
  `debit_credit` decimal(10,2) NOT NULL,
  `advance_balance` decimal(10,2) NOT NULL DEFAULT '0.00',
  `balance` decimal(10,2) unsigned NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `reserved1` int(3) DEFAULT NULL,
  `reserved2` varchar(30) DEFAULT NULL,
  UNIQUE KEY `fk_buyer_balance_primary_key` (`buyer_id`,`transaction_date`),
  KEY `fk_buyer_balance_buyer_id_idx` (`buyer_id`),
  KEY `fk_buyer_balance_transaction_code_idx` (`transaction_code`),
  KEY `fk_buyer_balance_transaction_id_idx` (`transaction_id`),
  KEY `fk_buyer_balance_epap_id` (`epap_id`),
  CONSTRAINT `fk_buyer_balance_epap_id` FOREIGN KEY (`epap_id`) REFERENCES `epap_balance` (`epap_balance_id`),
  CONSTRAINT `fk_buyer_balance_fk_buyer_balance_buyer_id` FOREIGN KEY (`buyer_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_buyer_balance_transaction_code` FOREIGN KEY (`transaction_code`) REFERENCES `transaction_code` (`transaction_code`),
  CONSTRAINT `fk_buyer_balance_transaction_id` FOREIGN KEY (`transaction_id`) REFERENCES `buyer_transactions` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buyer_balance`
--

LOCK TABLES `buyer_balance` WRITE;
/*!40000 ALTER TABLE `buyer_balance` DISABLE KEYS */;
INSERT INTO `buyer_balance` VALUES (6,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(8,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(10,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(29,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(30,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(31,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(32,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(33,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(34,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(35,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(36,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(37,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(38,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(39,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(40,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(41,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(42,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(43,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(44,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(45,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(46,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(47,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(48,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(49,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(50,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(51,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(52,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(53,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(54,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(55,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(56,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(57,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(58,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(59,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(60,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(61,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(62,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(63,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(64,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(65,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(66,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(67,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(68,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(69,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(70,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(71,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(72,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(73,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(74,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(75,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(76,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(77,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(78,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(79,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(80,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(81,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(82,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(83,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(84,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(85,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(86,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(87,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(88,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(89,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(90,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(91,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(92,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(93,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(94,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,''),(95,'2019-12-06 00:09:00.000000',0,NULL,'Opening balance','O',100000.00,0.00,100000.00,'',NULL,'');
/*!40000 ALTER TABLE `buyer_balance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buyer_log`
--

DROP TABLE IF EXISTS `buyer_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `buyer_log` (
  `log_timestamp` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `auction_buyers_id` int(11) unsigned NOT NULL,
  `daily_auction_id` int(11) unsigned NOT NULL,
  `buyer_id` int(11) unsigned NOT NULL,
  `bid_operation` smallint(1) unsigned NOT NULL,
  `bid_quantity` int(11) unsigned NOT NULL,
  `minimum_quantity` int(11) unsigned NOT NULL DEFAULT '0',
  `bid_price` float(6,2) unsigned NOT NULL,
  `shipper` int(11) unsigned DEFAULT NULL,
  `buyer_shipping_charge` float(6,2) unsigned DEFAULT '0.00',
  `executed_quantity` int(11) NOT NULL,
  `executed_price` float(6,2) NOT NULL,
  UNIQUE KEY `fk_buyer_log_primary_key` (`log_timestamp`,`auction_buyers_id`,`bid_operation`),
  KEY `fk_buyer_log_auction_buyers_id_idx` (`auction_buyers_id`),
  KEY `fk_buyer_log_buyer_id_idx` (`buyer_id`),
  KEY `fk_buyer_log_bid_operation_id_idx` (`bid_operation`),
  KEY `fk_buyer_log_daily_auction_id_idx` (`daily_auction_id`),
  KEY `fk_buyer_log_shipper_idx` (`shipper`),
  CONSTRAINT `fk_buyer_log_auction_buyers_id` FOREIGN KEY (`auction_buyers_id`) REFERENCES `auction_buyers` (`auction_buyers_id`),
  CONSTRAINT `fk_buyer_log_buyer_id` FOREIGN KEY (`buyer_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_buyer_log_daily_auction_id` FOREIGN KEY (`daily_auction_id`) REFERENCES `daily_auctions` (`daily_auctions_id`),
  CONSTRAINT `fk_buyer_log_shipper` FOREIGN KEY (`shipper`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buyer_log`
--

LOCK TABLES `buyer_log` WRITE;
/*!40000 ALTER TABLE `buyer_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `buyer_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `buyer_transactions`
--

DROP TABLE IF EXISTS `buyer_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `buyer_transactions` (
  `transaction_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `auction_buyers_id` int(11) unsigned NOT NULL,
  `buyer_id` int(11) unsigned NOT NULL,
  `buy_quantity` int(11) unsigned NOT NULL,
  `buy_price` float(6,2) unsigned NOT NULL,
  `gross_purchase` decimal(10,2) unsigned NOT NULL,
  `royalty_percentage` float(4,2) NOT NULL DEFAULT '0.00' COMMENT 'royalty_value are in percentag.',
  `royalty_amount` decimal(10,2) unsigned NOT NULL,
  `vat_percentage` float(4,2) unsigned NOT NULL DEFAULT '0.00',
  `vat_amount` decimal(10,2) unsigned NOT NULL,
  `buyer_shipping_charge` float(6,2) unsigned DEFAULT '0.00',
  `net_payment` decimal(10,2) unsigned NOT NULL,
  `transaction_creation` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `reserved1` int(3) DEFAULT NULL,
  `reserved2` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  UNIQUE KEY `transaction_id_UNIQUE` (`transaction_id`),
  KEY `fk_buyer_transactions_auction_buyers_id_idx` (`auction_buyers_id`),
  KEY `fk_buyer_transactions_1_idx` (`buyer_id`),
  CONSTRAINT `fk_buyer_transactions_auction_buyers_id` FOREIGN KEY (`auction_buyers_id`) REFERENCES `auction_buyers` (`auction_buyers_id`),
  CONSTRAINT `fk_buyer_transactions_buyer_id` FOREIGN KEY (`buyer_id`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buyer_transactions`
--

LOCK TABLES `buyer_transactions` WRITE;
/*!40000 ALTER TABLE `buyer_transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `buyer_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `comments` (
  `comment_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `to_account_id` int(11) unsigned NOT NULL,
  `comment_text` varchar(255) NOT NULL,
  `created_by` int(11) unsigned NOT NULL,
  `created_timestamp` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `feedback` varchar(255) DEFAULT NULL,
  `updated_by` int(11) unsigned DEFAULT NULL,
  `updated_timestamp` timestamp(6) NULL DEFAULT NULL,
  `comment_status` varchar(15) NOT NULL,
  `reserved` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  UNIQUE KEY `comment_id_UNIQUE` (`comment_id`),
  KEY `fk_comments_against_id_idx` (`to_account_id`),
  KEY `fk_comments_created_by_idx` (`created_by`),
  KEY `fk_comments_updated_by_idx` (`updated_by`),
  CONSTRAINT `fk_comments_account_id` FOREIGN KEY (`to_account_id`) REFERENCES `account_profile` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_comments_created_by` FOREIGN KEY (`created_by`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_comments_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currency_codes`
--

DROP TABLE IF EXISTS `currency_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `currency_codes` (
  `currency_code` char(3) NOT NULL,
  `currency_name` varchar(20) NOT NULL,
  PRIMARY KEY (`currency_code`),
  UNIQUE KEY `currency_code_UNIQUE` (`currency_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currency_codes`
--

LOCK TABLES `currency_codes` WRITE;
/*!40000 ALTER TABLE `currency_codes` DISABLE KEYS */;
INSERT INTO `currency_codes` VALUES ('AED','Arab Emirates Dirham'),('BHD','Bahraini Dinar'),('DZD','Algerian Dinar'),('EGP','Egyptian Pound'),('EUR','Euro'),('GBP','Pound Sterling'),('INR','Indian Rupee'),('IQD','Iraqi Dinar'),('IRR','Iranian Rial'),('JOD','Jordanian Dinar'),('JPY','Japanese Yen'),('KWD','Kuwaiti Dinar'),('LBP','Lebanese Pound'),('MAD','Moroccan Dirham'),('OMR','Omani Rial'),('PHP','Philippine Peso'),('PKR','Pakistan Rupee'),('QAR','Qatari Rial'),('RUB','Russian Ruble'),('SAR','Saudi Riyal'),('SYP','Syrian Pound'),('TND','Tunisian Dollar'),('TRY','Turkish Lira'),('USD','US dollars'),('YER','Yemeni Rial');
/*!40000 ALTER TABLE `currency_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daily_auctions`
--

DROP TABLE IF EXISTS `daily_auctions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `daily_auctions` (
  `daily_auctions_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `product_id` int(11) unsigned NOT NULL,
  `auction_settings_id` int(11) unsigned NOT NULL,
  `begin_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `auction_duration` float(4,2) NOT NULL,
  `auction_status_code` smallint(3) unsigned NOT NULL,
  `createdby_id` int(11) unsigned NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updatedby_id` int(11) unsigned DEFAULT NULL,
  `updated_on` timestamp NULL DEFAULT NULL,
  `cancelledby_id` int(11) unsigned DEFAULT NULL,
  `canceled_on` timestamp NULL DEFAULT NULL,
  `reserved1` int(3) DEFAULT NULL,
  `reserved2` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`daily_auctions_id`),
  UNIQUE KEY `daily_auctions_id_UNIQUE` (`daily_auctions_id`),
  KEY `fk_daily_auctions_auction_status_code_idx` (`auction_status_code`),
  KEY `fk_daily_auctions_createdby_id_idx` (`createdby_id`),
  KEY `fk_daily_auctions_updatedby_id_idx` (`updatedby_id`),
  KEY `fk_daily_auctions_cancelledby_id_idx` (`cancelledby_id`),
  KEY `fk_daily_auctions_product_id_idx` (`product_id`),
  KEY `fk_daily_auctions_auction_settings_id_idx` (`auction_settings_id`),
  CONSTRAINT `fk_daily_auctions_auction_settings_id` FOREIGN KEY (`auction_settings_id`) REFERENCES `auction_settings` (`auction_settings_id`),
  CONSTRAINT `fk_daily_auctions_auction_status_code` FOREIGN KEY (`auction_status_code`) REFERENCES `auction_status_codes` (`auction_status_code`),
  CONSTRAINT `fk_daily_auctions_cancelledby_id` FOREIGN KEY (`cancelledby_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_daily_auctions_createdby_id` FOREIGN KEY (`createdby_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_daily_auctions_product_id` FOREIGN KEY (`product_id`) REFERENCES `product_catalog` (`product_id`),
  CONSTRAINT `fk_daily_auctions_updatedby_id` FOREIGN KEY (`updatedby_id`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_auctions`
--

LOCK TABLES `daily_auctions` WRITE;
/*!40000 ALTER TABLE `daily_auctions` DISABLE KEYS */;
/*!40000 ALTER TABLE `daily_auctions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `epap_balance`
--

DROP TABLE IF EXISTS `epap_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `epap_balance` (
  `epap_balance_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `transaction_date` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `transaction_id` int(11) unsigned DEFAULT NULL,
  `transaction_description` varchar(100) NOT NULL,
  `transaction_code` char(1) NOT NULL,
  `account_id` int(11) unsigned NOT NULL,
  `account_type` char(1) NOT NULL,
  `debit_credit` decimal(10,2) NOT NULL,
  `advance_balance` decimal(10,2) NOT NULL DEFAULT '0.00',
  `balance` decimal(10,2) NOT NULL,
  `cash_position` decimal(10,2) NOT NULL DEFAULT '0.00',
  `comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`epap_balance_id`),
  KEY `fk_epap_balance_account_id_idx` (`account_id`),
  KEY `fk_epap_balance_account_type_idx` (`account_type`),
  KEY `fk_epap_balance_transaction_code_idx` (`transaction_code`),
  CONSTRAINT `fk_epap_balance_account_id` FOREIGN KEY (`account_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_epap_balance_account_type` FOREIGN KEY (`account_type`) REFERENCES `account_type_codes` (`account_type`),
  CONSTRAINT `fk_epap_balance_transaction_code` FOREIGN KEY (`transaction_code`) REFERENCES `transaction_code` (`transaction_code`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `epap_balance`
--

LOCK TABLES `epap_balance` WRITE;
/*!40000 ALTER TABLE `epap_balance` DISABLE KEYS */;
INSERT INTO `epap_balance` VALUES (100,'2019-12-22 00:09:00.000000',1,'Deposit-1','D',1,'A',500000.00,0.00,500000.00,7500000.00,'');
/*!40000 ALTER TABLE `epap_balance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events_meaning`
--

DROP TABLE IF EXISTS `events_meaning`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `events_meaning` (
  `event_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `event_meaning` varchar(255) NOT NULL,
  PRIMARY KEY (`event_id`),
  UNIQUE KEY `event_id_UNIQUE` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events_meaning`
--

LOCK TABLES `events_meaning` WRITE;
/*!40000 ALTER TABLE `events_meaning` DISABLE KEYS */;
/*!40000 ALTER TABLE `events_meaning` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `events_profile`
--

DROP TABLE IF EXISTS `events_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `events_profile` (
  `account_id` int(11) unsigned NOT NULL,
  `event_map` mediumtext,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_id_UNIQUE` (`account_id`),
  KEY `fk_events_profile_account_id_idx` (`account_id`),
  CONSTRAINT `fk_events_profile_account_id` FOREIGN KEY (`account_id`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events_profile`
--

LOCK TABLES `events_profile` WRITE;
/*!40000 ALTER TABLE `events_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `events_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ip_blocking_details`
--

DROP TABLE IF EXISTS `ip_blocking_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ip_blocking_details` (
  `ip` varchar(25) NOT NULL,
  `no_of_blocking` int(11) NOT NULL DEFAULT '0',
  `blocking_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ip`),
  UNIQUE KEY `ip_UNIQUE` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ip_blocking_details`
--

LOCK TABLES `ip_blocking_details` WRITE;
/*!40000 ALTER TABLE `ip_blocking_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_blocking_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `landing_pages`
--

DROP TABLE IF EXISTS `landing_pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `landing_pages` (
  `landing_page_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `landing_page_name` varchar(100) NOT NULL,
  `landing_page_url` varchar(255) NOT NULL,
  PRIMARY KEY (`landing_page_id`),
  UNIQUE KEY `landing_page_id_UNIQUE` (`landing_page_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `landing_pages`
--

LOCK TABLES `landing_pages` WRITE;
/*!40000 ALTER TABLE `landing_pages` DISABLE KEYS */;
INSERT INTO `landing_pages` VALUES (1,'المزادات Auctions','auctions/auctionlist'),(2,'لوحة التحكم Dashboard','setting/dashboard'),(3,'الملف الشخصي EditProfile','setting/syncseditProfile'),(4,'الملاحظات Comments','setting/commentsList');
/*!40000 ALTER TABLE `landing_pages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_details`
--

DROP TABLE IF EXISTS `login_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `login_details` (
  `login_userid` varchar(40) NOT NULL,
  `account_id` int(11) unsigned DEFAULT NULL,
  `account_type` char(1) NOT NULL,
  `account_status` smallint(5) unsigned NOT NULL,
  `public_name` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `password_salt` varchar(255) NOT NULL,
  `email_verified` tinyint(1) DEFAULT '0',
  `failed_login_count` int(3) unsigned DEFAULT '0',
  `login_count` int(3) unsigned DEFAULT '0',
  `creation_timestamp` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `reserved1` int(3) DEFAULT NULL,
  `reserved2` varchar(30) DEFAULT NULL,
  `otp` int(11) DEFAULT NULL,
  `otp_expired_date` timestamp NULL DEFAULT NULL,
  `otp_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`login_userid`),
  UNIQUE KEY `login_userid_UNIQUE` (`login_userid`),
  KEY `fk_login_details_account_type_idx` (`account_type`),
  KEY `fk_login_details_account_status_idx` (`account_status`),
  KEY `fk_login_details_account_id_idx` (`account_id`),
  CONSTRAINT `fk_login_details_account_id` FOREIGN KEY (`account_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_login_details_account_status` FOREIGN KEY (`account_status`) REFERENCES `account_status_codes` (`account_status_code`),
  CONSTRAINT `fk_login_details_account_type` FOREIGN KEY (`account_type`) REFERENCES `account_type_codes` (`account_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_details`
--

LOCK TABLES `login_details` WRITE;
/*!40000 ALTER TABLE `login_details` DISABLE KEYS */;
INSERT INTO `login_details` VALUES ('adminfinance@epap.com',3,'F',9,'مشرف المالية','$2a$10$UICw.Jnw7XmnPjk84Ny/GO21nuoZZlZwAbUwJZTUSBPsYMbDEylAq','$2a$10$UICw.Jnw7XmnPjk84Ny/GO',1,0,0,'2018-05-05 00:09:00.000000',NULL,NULL,NULL,NULL,NULL),('adminoperation@epap.com',2,'O',9,'مشرف التشغيل','$2a$10$UICw.Jnw7XmnPjk84Ny/GO21nuoZZlZwAbUwJZTUSBPsYMbDEylAq','$2a$10$UICw.Jnw7XmnPjk84Ny/GO',1,0,0,'2018-05-05 00:09:00.000000',NULL,NULL,NULL,NULL,NULL),('adminrelations@epap.com',4,'R',9,'مشرف العلاقات','$2a$10$UICw.Jnw7XmnPjk84Ny/GO21nuoZZlZwAbUwJZTUSBPsYMbDEylAq','$2a$10$UICw.Jnw7XmnPjk84Ny/GO',1,0,0,'2018-05-05 00:09:00.000000',NULL,NULL,NULL,NULL,NULL),('adminshipping@epap.com',5,'N',9,'مشرف النقل','$2a$10$UICw.Jnw7XmnPjk84Ny/GO21nuoZZlZwAbUwJZTUSBPsYMbDEylAq','$2a$10$UICw.Jnw7XmnPjk84Ny/GO',1,0,0,'2018-05-05 00:09:00.000000',NULL,NULL,NULL,NULL,NULL),('adminsuper@epap.com',1,'A',9,'مشرف النظام','$2a$10$UICw.Jnw7XmnPjk84Ny/GO21nuoZZlZwAbUwJZTUSBPsYMbDEylAq','$2a$10$UICw.Jnw7XmnPjk84Ny/GO',1,0,0,'2018-05-05 00:09:00.000000',NULL,NULL,NULL,NULL,NULL),('adminvat@epap.com',27,'W',9,'مشرف الضريبة','$2a$10$UICw.Jnw7XmnPjk84Ny/GO21nuoZZlZwAbUwJZTUSBPsYMbDEylAq','$2a$10$UICw.Jnw7XmnPjk84Ny/GO',1,0,0,'2018-05-05 00:09:00.000000',NULL,NULL,NULL,NULL,NULL),('buyer10@epap.com',35,'B',9,'المشتري10','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer11@epap.com',36,'B',9,'المشتري11','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer12@epap.com',37,'B',9,'المشتري12','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer13@epap.com',38,'B',9,'المشتري13','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer14@epap.com',39,'B',9,'المشتري14','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer15@epap.com',40,'B',9,'المشتري15','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer16@epap.com',41,'B',9,'المشتري16','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer17@epap.com',42,'B',9,'المشتري17','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer18@epap.com',43,'B',9,'المشتري18','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer19@epap.com',44,'B',9,'المشتري19','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer1@epap.com',6,'B',9,'عبدالله أحمد للشراء','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2017-12-31 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer1agent@epap.com',7,'Y',9,'مندوب عبدالله أحمد ','$2a$10$hqN9d70umrua/aBE9vIcYei2qHyO/6P8HNBGIfrslurLHsAgsfmVm','$2a$10$hqN9d70umrua/aBE9vIcYe',1,0,0,'2017-12-31 23:57:54.000000',NULL,NULL,NULL,NULL,NULL),('buyer20@epap.com',45,'B',9,'المشتري20','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer21@epap.com',46,'B',9,'المشتري21','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer22@epap.com',47,'B',9,'المشتري22','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer23@epap.com',48,'B',9,'المشتري23','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer24@epap.com',49,'B',9,'المشتري24','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer25@epap.com',50,'B',9,'المشتري25','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer26@epap.com',51,'B',9,'المشتري26','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer27@epap.com',52,'B',9,'المشتري27','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer28@epap.com',53,'B',9,'المشتري28','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer29@epap.com',54,'B',9,'المشتري29','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer2@epap.com',8,'B',9,'مها للمشتريات ','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2017-12-31 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer2agent@epap.com',9,'Y',9,'مندوب مها للمشتريات','$2a$10$HMWzQ9nH6pSw0LMb9l3RfOoJJpPtRwFrLcsrEDA45b0aVktMvL2ny','$2a$10$HMWzQ9nH6pSw0LMb9l3RfO',1,0,0,'2018-01-01 00:06:27.000000',NULL,NULL,NULL,NULL,NULL),('buyer30@epap.com',55,'B',9,'المشتري30','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer31@epap.com',56,'B',9,'المشتري31','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer32@epap.com',57,'B',9,'المشتري32','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer33@epap.com',58,'B',9,'المشتري33','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer34@epap.com',59,'B',9,'المشتري34','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer35@epap.com',60,'B',9,'المشتري35','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer36@epap.com',61,'B',9,'المشتري36','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer37@epap.com',62,'B',9,'المشتري37','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer38@epap.com',63,'B',9,'المشتري38','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer39@epap.com',64,'B',9,'المشتري39','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer3@epap.com',10,'B',9,'المتحدون للتصدير','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2017-12-31 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer3agent@epap.com',11,'Y',9,'مندوب المتحدون ','$2a$10$hqN9d70umrua/aBE9vIcYei2qHyO/6P8HNBGIfrslurLHsAgsfmVm','$2a$10$hqN9d70umrua/aBE9vIcYe',1,0,0,'2017-12-31 23:57:54.000000',NULL,NULL,NULL,NULL,NULL),('buyer40@epap.com',65,'B',9,'المشتري40','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer41@epap.com',66,'B',9,'المشتري41','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer42@epap.com',67,'B',9,'المشتري42','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer43@epap.com',68,'B',9,'المشتري43','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer44@epap.com',69,'B',9,'المشتري44','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer45@epap.com',70,'B',9,'المشتري45','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer46@epap.com',71,'B',9,'المشتري46','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer47@epap.com',72,'B',9,'المشتري47','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer48@epap.com',73,'B',9,'المشتري48','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer49@epap.com',74,'B',9,'المشتري49','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer4@epap.com',29,'B',9,'المشتري4','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer50@epap.com',75,'B',9,'المشتري50','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer51@epap.com',76,'B',9,'المشتري51','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer52@epap.com',77,'B',9,'المشتري52','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer53@epap.com',78,'B',9,'المشتري53','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer54@epap.com',79,'B',9,'المشتري54','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer55@epap.com',80,'B',9,'المشتري55','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer56@epap.com',81,'B',9,'المشتري56','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer57@epap.com',82,'B',9,'المشتري57','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer58@epap.com',83,'B',9,'المشتري58','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer59@epap.com',84,'B',9,'المشتري59','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer5@epap.com',30,'B',9,'المشتري5','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer60@epap.com',85,'B',9,'المشتري60','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer61@epap.com',86,'B',9,'المشتري61','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer62@epap.com',87,'B',9,'المشتري62','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer63@epap.com',88,'B',9,'المشتري63','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer64@epap.com',89,'B',9,'المشتري64','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer65@epap.com',90,'B',9,'المشتري65','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer66@epap.com',91,'B',9,'المشتري66','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer67@epap.com',92,'B',9,'المشتري67','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer68@epap.com',93,'B',9,'المشتري68','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer69@epap.com',94,'B',9,'المشتري69','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer6@epap.com',31,'B',9,'المشتري6','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer70@epap.com',95,'B',9,'المشتري70','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer7@epap.com',32,'B',9,'المشتري7','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer8@epap.com',33,'B',9,'المشتري8','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('buyer9@epap.com',34,'B',9,'المشتري9','$2a$10$cK7/zUJ7.7mw5VugCHwnP.61TY9agVGbscIOOyjDiLGm/C58pVksq','$2a$10$cK7/zUJ7.7mw5VugCHwnP.',1,0,0,'2019-12-05 23:35:48.000000',NULL,NULL,NULL,NULL,NULL),('seller1@epap.com',12,'S',9,'مزارع الشمس الساطعة ','$2a$10$Vg5QAMpMnGzCB3kqASXzq.6yvS38uZ44s5r.r4vxr5h94SmX6d0bC','$2a$10$Vg5QAMpMnGzCB3kqASXzq.',1,0,0,'2017-11-01 00:53:52.000000',NULL,NULL,NULL,NULL,NULL),('seller1agent@epap.com',13,'E',9,'مندوب مزارع الشمس','$2a$10$jV7dj4wiD6GUu3SYOiB.5Oyo0UR3Z1bfrJglIiBbyeSfMQ6Ip6CZ2','$2a$10$jV7dj4wiD6GUu3SYOiB.5O',1,0,0,'2018-01-01 00:08:36.000000',NULL,NULL,NULL,NULL,NULL),('seller2@epap.com',14,'S',9,'مزرعة حصة الميموني ','$2a$10$Vg5QAMpMnGzCB3kqASXzq.6yvS38uZ44s5r.r4vxr5h94SmX6d0bC','$2a$10$Vg5QAMpMnGzCB3kqASXzq.',1,0,0,'2017-11-01 00:53:52.000000',NULL,NULL,NULL,NULL,NULL),('seller2agent@epap.com',15,'E',9,'مندوب مزرعة حصة','$2a$10$jV7dj4wiD6GUu3SYOiB.5Oyo0UR3Z1bfrJglIiBbyeSfMQ6Ip6CZ2','$2a$10$jV7dj4wiD6GUu3SYOiB.5O',1,0,0,'2018-01-01 00:08:36.000000',NULL,NULL,NULL,NULL,NULL),('seller3@epap.com',16,'S',9,'مزارع الوفيرة','$2a$10$Vg5QAMpMnGzCB3kqASXzq.6yvS38uZ44s5r.r4vxr5h94SmX6d0bC','$2a$10$Vg5QAMpMnGzCB3kqASXzq.',1,0,0,'2017-11-01 00:53:52.000000',NULL,NULL,NULL,NULL,NULL),('seller3agent@epap.com',17,'E',9,'مندوب مزارع الوفيرة','$2a$10$jV7dj4wiD6GUu3SYOiB.5Oyo0UR3Z1bfrJglIiBbyeSfMQ6Ip6CZ2','$2a$10$jV7dj4wiD6GUu3SYOiB.5O',1,0,0,'2018-01-01 00:08:36.000000',NULL,NULL,NULL,NULL,NULL),('shipper1@epap.com',18,'H',9,'نقليات الرياض','$2a$10$j0Z7CHlZZaZA2BX69s2eDuFkFMIDRzTk6POf8Sn2k62.L6jVa.FPO','$2a$10$j0Z7CHlZZaZA2BX69s2eDu',1,0,0,'2018-05-15 02:31:51.000000',NULL,NULL,NULL,NULL,NULL),('shipper1agent@epap.com',19,'I',9,'مندوب نقليات الرياض','$2a$10$eTlhUAoCzsfGyNsjAcim.eZxIU5FQ0RshMuEs5vXKdi4U3xu9sJQm','$2a$10$eTlhUAoCzsfGyNsjAcim.e',1,0,0,'2018-05-15 03:26:21.000000',NULL,NULL,NULL,NULL,NULL),('shipper2@epap.com',20,'H',9,'التوصيل السريع','$2a$10$j0Z7CHlZZaZA2BX69s2eDuFkFMIDRzTk6POf8Sn2k62.L6jVa.FPO','$2a$10$j0Z7CHlZZaZA2BX69s2eDu',1,0,0,'2018-05-15 02:31:51.000000',NULL,NULL,NULL,NULL,NULL),('shipper2agent@epap.com',21,'I',9,'مندوب التوصيل السريع','$2a$10$eTlhUAoCzsfGyNsjAcim.eZxIU5FQ0RshMuEs5vXKdi4U3xu9sJQm','$2a$10$eTlhUAoCzsfGyNsjAcim.e',1,0,0,'2018-05-15 03:26:21.000000',NULL,NULL,NULL,NULL,NULL),('shipper3@epap.com',22,'H',9,'جميع الاتجاهات','$2a$10$j0Z7CHlZZaZA2BX69s2eDuFkFMIDRzTk6POf8Sn2k62.L6jVa.FPO','$2a$10$j0Z7CHlZZaZA2BX69s2eDu',1,0,0,'2018-05-15 02:31:51.000000',NULL,NULL,NULL,NULL,NULL),('shipper3agent@epap.com',23,'I',9,'مندوب جميع الاتجاهات','$2a$10$eTlhUAoCzsfGyNsjAcim.eZxIU5FQ0RshMuEs5vXKdi4U3xu9sJQm','$2a$10$eTlhUAoCzsfGyNsjAcim.e',1,0,0,'2018-05-15 03:26:21.000000',NULL,NULL,NULL,NULL,NULL),('user1@epap.com',24,'U',9,'عبدالله أحمد','$2a$10$0eblmY1ZYPfiUOJrfSkPDOLJRICcYQj9oMk52HdebLLpTQcaj2C9e','$2a$10$0eblmY1ZYPfiUOJrfSkPDO',1,0,0,'2018-01-25 21:14:36.000000',NULL,NULL,NULL,NULL,NULL),('user2@epap.com',25,'U',9,'مهند أيوب','$2a$10$0eblmY1ZYPfiUOJrfSkPDOLJRICcYQj9oMk52HdebLLpTQcaj2C9e','$2a$10$0eblmY1ZYPfiUOJrfSkPDO',1,0,0,'2018-01-25 21:14:36.000000',NULL,NULL,NULL,NULL,NULL),('user3@epap.com',26,'U',9,'سعيد بخاري','$2a$10$0eblmY1ZYPfiUOJrfSkPDOLJRICcYQj9oMk52HdebLLpTQcaj2C9e','$2a$10$0eblmY1ZYPfiUOJrfSkPDO',1,0,0,'2018-01-25 21:14:36.000000',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `login_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_logout_log`
--

DROP TABLE IF EXISTS `login_logout_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `login_logout_log` (
  `login_userid` varchar(40) NOT NULL,
  `login_timestamp` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `logout_timestamp` timestamp(6) NULL DEFAULT NULL,
  UNIQUE KEY `fk_login_logout_log_primary_key` (`login_userid`,`login_timestamp`),
  KEY `fk_login_logout_log_login_userid_idx` (`login_userid`),
  CONSTRAINT `fk_login_logout_log_login_userid` FOREIGN KEY (`login_userid`) REFERENCES `login_details` (`login_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_logout_log`
--

LOCK TABLES `login_logout_log` WRITE;
/*!40000 ALTER TABLE `login_logout_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `login_logout_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_center`
--

DROP TABLE IF EXISTS `message_center`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `message_center` (
  `message_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `from_account_id` int(11) unsigned NOT NULL,
  `to_account_id` int(11) unsigned NOT NULL,
  `message` varchar(255) NOT NULL,
  `message_timestamp` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `attachment` longblob,
  `attachment_type` varchar(100) DEFAULT NULL,
  `reserved` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`message_id`),
  UNIQUE KEY `message_id_UNIQUE` (`message_id`),
  KEY `fk_message_center_from_account_id_idx` (`from_account_id`),
  KEY `fk_message_center_to_account_id_idx` (`to_account_id`),
  CONSTRAINT `fk_message_center_from_account_id` FOREIGN KEY (`from_account_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_message_center_to_account_id` FOREIGN KEY (`to_account_id`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_center`
--

LOCK TABLES `message_center` WRITE;
/*!40000 ALTER TABLE `message_center` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_center` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification_codes`
--

DROP TABLE IF EXISTS `notification_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `notification_codes` (
  `notification_code` smallint(3) unsigned NOT NULL AUTO_INCREMENT,
  `notification_meaning` varchar(10) NOT NULL,
  PRIMARY KEY (`notification_code`),
  UNIQUE KEY `notification_code_UNIQUE` (`notification_code`),
  UNIQUE KEY `notification_meaning_UNIQUE` (`notification_meaning`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification_codes`
--

LOCK TABLES `notification_codes` WRITE;
/*!40000 ALTER TABLE `notification_codes` DISABLE KEYS */;
INSERT INTO `notification_codes` VALUES (3,'Both'),(2,'Email Only'),(1,'SMS Only');
/*!40000 ALTER TABLE `notification_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pickup_tickets`
--

DROP TABLE IF EXISTS `pickup_tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `pickup_tickets` (
  `pickup_ticket_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ptn` int(11) unsigned DEFAULT NULL,
  `trade_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `daily_auctions_id` int(11) unsigned NOT NULL,
  `seller_id` int(11) unsigned NOT NULL,
  `seller_name` varchar(50) NOT NULL,
  `seller_owner` int(11) unsigned zerofill NOT NULL,
  `stn` int(11) unsigned NOT NULL,
  `buyer_id` int(11) unsigned NOT NULL,
  `auction_buyers_id` int(11) unsigned NOT NULL,
  `buyer_name` varchar(50) NOT NULL,
  `purchased_quantity` int(11) DEFAULT NULL,
  `seller_location` int(11) unsigned NOT NULL,
  `buyer_location` int(11) unsigned NOT NULL,
  `action` int(3) unsigned DEFAULT NULL,
  `confirmedby_id` int(11) unsigned DEFAULT NULL,
  `confirmed_on` timestamp(6) NULL DEFAULT NULL,
  `cancelledby_id` int(11) unsigned DEFAULT NULL,
  `canceled_on` timestamp(6) NULL DEFAULT NULL,
  `reserved1` int(3) DEFAULT NULL,
  `reserved2` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pickup_ticket_id`),
  KEY `daily_auctions_id_idx` (`daily_auctions_id`),
  KEY `seller_location_idx` (`seller_location`),
  KEY `buyer_location_idx` (`buyer_location`),
  KEY `seller_id_idx` (`seller_id`),
  KEY `buyer_id_idx` (`buyer_id`),
  KEY `pickup_tickets_auction_buyers_id_idx` (`auction_buyers_id`),
  CONSTRAINT `auction_buyers_id` FOREIGN KEY (`auction_buyers_id`) REFERENCES `auction_buyers` (`auction_buyers_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `buyer_id` FOREIGN KEY (`buyer_id`) REFERENCES `account_profile` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `buyer_location` FOREIGN KEY (`buyer_location`) REFERENCES `account_locations` (`location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `daily_auctions_id` FOREIGN KEY (`daily_auctions_id`) REFERENCES `daily_auctions` (`daily_auctions_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `seller_id` FOREIGN KEY (`seller_id`) REFERENCES `account_profile` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `seller_location` FOREIGN KEY (`seller_location`) REFERENCES `account_locations` (`location_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pickup_tickets`
--

LOCK TABLES `pickup_tickets` WRITE;
/*!40000 ALTER TABLE `pickup_tickets` DISABLE KEYS */;
/*!40000 ALTER TABLE `pickup_tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_catalog`
--

DROP TABLE IF EXISTS `product_catalog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_catalog` (
  `product_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `product_group_name` varchar(20) NOT NULL,
  `product_name` varchar(20) NOT NULL,
  `product_type_name` varchar(20) NOT NULL,
  `product_description` varchar(255) NOT NULL,
  `container_specs` varchar(255) NOT NULL,
  `status` smallint(5) unsigned NOT NULL DEFAULT '1',
  `reserved` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_id_UNIQUE` (`product_id`),
  UNIQUE KEY `product_catalog_UNIQUE` (`product_group_name`,`product_name`,`product_type_name`),
  KEY `fk_product_catalog_product_group_id_idx` (`product_group_name`),
  KEY `fk_product_catalog_product_type_id_idx` (`product_type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_catalog`
--

LOCK TABLES `product_catalog` WRITE;
/*!40000 ALTER TABLE `product_catalog` DISABLE KEYS */;
INSERT INTO `product_catalog` VALUES (1,'خضروات','طماطم','نظيف','نظيف أحمر مع التاج وخالي من العيوب','فلين نظيف بعدد 30 حبة، الصف العلوي مع التاج، وزن 4.5 كجم',1,NULL),(2,'خضروات','خيار','نظيف','خيار نظيف عادي، متوسط الطول مع زهرة صفراء','صندوق بلاستيكي قياسي، كامل التعبئة بوزن حوالي 14 كجم',1,NULL),(3,'خضروات','خيار','عويد','خيار كبير الحجم','صندوق بلاستيكي قياسي، كامل التعبئة بوزن حوالي 14 كجم',1,NULL),(4,'خضروات','كوسة','نظيف','كوسة خضراء فاتحة اللون متوسطة الطول أو قصيرة مع الزهرة','صندوق بلاستيكي قياسي، كامل التعبئة بوزن حوالي 14 كجم',1,NULL),(5,'خضروات','طماطم','وسط','طماطم متوسط الحجم - مقلوبة','فلين نظيف بعدد 30 حبة، الصف العلوي مع التاج',1,NULL),(6,'خضروات','طماطم','جامبو','نظيف أحمر مع التاج كبير الحجم وخالي من العيوب','فلين نظيف بعدد 30 حبة، الصف العلوي مع التاج، وزن 5 كجم',1,NULL),(7,'فواكه','برتقال','نظيف','برتقال نظيف لامع اللون حجم عادي','كرتون برتقال وزن 15 كجم',1,NULL),(8,'فواكه','تفاح','نظيف','تفاح قياسي أصفر','كرتون قياسي وزن 15 كجم',1,NULL),(9,'تمور','سكري','القصيم','تمر سكري انتاج القصيم نظيف قياسي الشكل','فلين 5 كجم',1,NULL),(10,'تمور','خلاص','الخرج','تمر خلاص وارد الخرج أو الأحساء داكن اللون خالي من العيوب','فلين قياسي وزن 5 كجم',1,NULL);
/*!40000 ALTER TABLE `product_catalog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_invoice`
--

DROP TABLE IF EXISTS `purchase_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `purchase_invoice` (
  `purchase_invoice_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `invoice_no` varchar(30) DEFAULT NULL,
  `invoice_creation` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `transaction_id` int(11) unsigned NOT NULL,
  `seller_public_name` varchar(20) NOT NULL,
  `buyer_public_name` varchar(20) NOT NULL,
  `buyer_shipper_public_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`purchase_invoice_id`),
  UNIQUE KEY `purchase_invoice_id_UNIQUE` (`purchase_invoice_id`),
  KEY `fk_transaction_id_idx` (`transaction_id`),
  CONSTRAINT `fk_transaction_id_idx` FOREIGN KEY (`transaction_id`) REFERENCES `buyer_transactions` (`transaction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_invoice`
--

LOCK TABLES `purchase_invoice` WRITE;
/*!40000 ALTER TABLE `purchase_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ratings_log`
--

DROP TABLE IF EXISTS `ratings_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ratings_log` (
  `log_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `daily_auctions_id` int(11) unsigned NOT NULL,
  `rator_id` int(11) unsigned NOT NULL,
  `rated_id` int(11) unsigned NOT NULL,
  `rating_value` smallint(1) unsigned DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `rating_timestamp` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`log_id`),
  UNIQUE KEY `log_id_UNIQUE` (`log_id`),
  KEY `fk_ratings_log_rator_id_idx` (`rator_id`),
  KEY `fk_ratings_log_rated_id_idx` (`rated_id`),
  KEY `fk_daily_auctions_id_idx` (`daily_auctions_id`),
  CONSTRAINT `fk_daily_auctions_id` FOREIGN KEY (`daily_auctions_id`) REFERENCES `daily_auctions` (`daily_auctions_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ratings_log_rated_id` FOREIGN KEY (`rated_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_ratings_log_rator_id` FOREIGN KEY (`rator_id`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ratings_log`
--

LOCK TABLES `ratings_log` WRITE;
/*!40000 ALTER TABLE `ratings_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `ratings_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `royalty_codes`
--

DROP TABLE IF EXISTS `royalty_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `royalty_codes` (
  `royalty_code` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `royalty_value` float(4,2) NOT NULL COMMENT 'royalty_value are in percentag.',
  PRIMARY KEY (`royalty_code`),
  UNIQUE KEY `royalty_code_UNIQUE` (`royalty_code`),
  UNIQUE KEY `royalty_value_UNIQUE` (`royalty_value`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `royalty_codes`
--

LOCK TABLES `royalty_codes` WRITE;
/*!40000 ALTER TABLE `royalty_codes` DISABLE KEYS */;
INSERT INTO `royalty_codes` VALUES (1,0.00),(2,5.00),(3,7.50),(4,10.00);
/*!40000 ALTER TABLE `royalty_codes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_notice`
--

DROP TABLE IF EXISTS `sales_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sales_notice` (
  `sales_notice_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `notice_no` varchar(30) DEFAULT NULL,
  `notice_creation` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `transaction_id` int(11) unsigned NOT NULL,
  `seller_public_name` varchar(20) NOT NULL,
  `buyer_public_name` varchar(20) NOT NULL,
  `seller_shipper_public_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`sales_notice_id`),
  UNIQUE KEY `sales_notice_id_UNIQUE` (`sales_notice_id`),
  KEY `fk_sales_transaction_id_idx` (`transaction_id`),
  CONSTRAINT `fk_sales_transaction_id_idx` FOREIGN KEY (`transaction_id`) REFERENCES `seller_transactions` (`transaction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_notice`
--

LOCK TABLES `sales_notice` WRITE;
/*!40000 ALTER TABLE `sales_notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_balance`
--

DROP TABLE IF EXISTS `seller_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `seller_balance` (
  `seller_id` int(11) unsigned NOT NULL,
  `transaction_date` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `transaction_id` int(11) unsigned DEFAULT NULL,
  `epap_id` int(11) unsigned DEFAULT NULL,
  `transaction_description` varchar(255) NOT NULL,
  `transaction_code` char(1) NOT NULL,
  `debit_credit` decimal(10,2) NOT NULL,
  `advance_balance` decimal(10,2) NOT NULL,
  `balance` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `comments` varchar(255) DEFAULT NULL,
  `reserved1` int(3) DEFAULT NULL,
  `reserved2` varchar(30) DEFAULT NULL,
  UNIQUE KEY `fk_seller_balance_primary_key` (`seller_id`,`transaction_date`),
  KEY `fk_seller_balance_seller_id_idx` (`seller_id`),
  KEY `fk_seller_balance_transaction_code_idx` (`transaction_code`),
  KEY `fk_seller_balance_transaction_id_idx` (`transaction_id`),
  KEY `fk_seller_balance_epap_id` (`epap_id`),
  CONSTRAINT `fk_seller_balance_epap_id` FOREIGN KEY (`epap_id`) REFERENCES `epap_balance` (`epap_balance_id`),
  CONSTRAINT `fk_seller_balance_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_seller_balance_transaction_code` FOREIGN KEY (`transaction_code`) REFERENCES `transaction_code` (`transaction_code`),
  CONSTRAINT `fk_seller_balance_transaction_id` FOREIGN KEY (`transaction_id`) REFERENCES `seller_transactions` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_balance`
--

LOCK TABLES `seller_balance` WRITE;
/*!40000 ALTER TABLE `seller_balance` DISABLE KEYS */;
/*!40000 ALTER TABLE `seller_balance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_info`
--

DROP TABLE IF EXISTS `seller_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `seller_info` (
  `seller_id` int(11) unsigned NOT NULL,
  `info_line1` varchar(255) NOT NULL,
  `info_line2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`seller_id`),
  KEY `fk_seller_info_seller_id_idx` (`seller_id`),
  CONSTRAINT `fk_seller_info_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_info`
--

LOCK TABLES `seller_info` WRITE;
/*!40000 ALTER TABLE `seller_info` DISABLE KEYS */;
INSERT INTO `seller_info` VALUES (12,'القصيم من أجمل مناطق المملكة العربية السعودية ، وتقع في الشمال الغربي لمنطقة الرياض ، والجنوب الغربي للمدينة المنورة ، وتشتهر بخصوبة أراضيها إذ معظمها صالحة لزراعة جميع أنواع المحاصيل الزراعية مثل القمح والخضروات والفاكهة والتمور  ','يحتاج الخيار إلى تربة قلوية نوعاً ما برقم هيدروجيني يبلغ 7، ويجب الحرص على المباعدة بين النبات عند زراعة النوع المتسلق مسافة 30 سم تقريباً، كما يمكن رش النبات بمحلول من السكر والماء لجذب النحل، وزيادة إنتاج الثمار'),(14,'القصيم من أجمل مناطق المملكة العربية السعودية ، وتقع في الشمال الغربي لمنطقة الرياض ، والجنوب الغربي للمدينة المنورة ، وتشتهر بخصوبة أراضيها إذ معظمها صالحة لزراعة جميع أنواع المحاصيل الزراعية مثل القمح والخضروات والفاكهة والتمور  ','يحتاج الخيار إلى تربة قلوية نوعاً ما برقم هيدروجيني يبلغ 7، ويجب الحرص على المباعدة بين النبات عند زراعة النوع المتسلق مسافة 30 سم تقريباً، كما يمكن رش النبات بمحلول من السكر والماء لجذب النحل، وزيادة إنتاج الثمار'),(16,'القصيم من أجمل مناطق المملكة العربية السعودية ، وتقع في الشمال الغربي لمنطقة الرياض ، والجنوب الغربي للمدينة المنورة ، وتشتهر بخصوبة أراضيها إذ معظمها صالحة لزراعة جميع أنواع المحاصيل الزراعية مثل القمح والخضروات والفاكهة والتمور  ','يحتاج الخيار إلى تربة قلوية نوعاً ما برقم هيدروجيني يبلغ 7، ويجب الحرص على المباعدة بين النبات عند زراعة النوع المتسلق مسافة 30 سم تقريباً، كما يمكن رش النبات بمحلول من السكر والماء لجذب النحل، وزيادة إنتاج الثمار');
/*!40000 ALTER TABLE `seller_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_log`
--

DROP TABLE IF EXISTS `seller_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `seller_log` (
  `log_timestamp` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `auction_sellers_id` int(11) unsigned NOT NULL,
  `daily_auctions_id` int(11) unsigned NOT NULL,
  `seller_id` int(11) unsigned NOT NULL,
  `offer_operation` smallint(1) unsigned NOT NULL,
  `offer_quantity` int(11) unsigned NOT NULL,
  `minimum_quantity` int(11) unsigned NOT NULL DEFAULT '0',
  `available_quantity` int(11) unsigned NOT NULL DEFAULT '0',
  `offer_price` float(6,2) unsigned NOT NULL,
  `shipper` int(11) unsigned DEFAULT NULL,
  `seller_shipping_charge` float(6,2) unsigned DEFAULT '0.00',
  `stn` int(11) NOT NULL,
  UNIQUE KEY `fk_seller_log_primary_key` (`log_timestamp`,`auction_sellers_id`,`offer_operation`),
  KEY `fk_seller_log_auction_sellers_id_idx` (`auction_sellers_id`),
  KEY `fk_seller_log_seller_id_idx` (`seller_id`),
  KEY `fk_seller_log_offer_operation_id_idx` (`offer_operation`),
  KEY `fk_seller_log_daily_auctions_id_idx` (`daily_auctions_id`),
  KEY `fk_seller_log_shipper_idx` (`shipper`),
  CONSTRAINT `fk_seller_log_auction_sellers_id` FOREIGN KEY (`auction_sellers_id`) REFERENCES `auction_sellers` (`auction_sellers_id`),
  CONSTRAINT `fk_seller_log_daily_auctions_id` FOREIGN KEY (`daily_auctions_id`) REFERENCES `daily_auctions` (`daily_auctions_id`),
  CONSTRAINT `fk_seller_log_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_seller_log_shipper` FOREIGN KEY (`shipper`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_log`
--

LOCK TABLES `seller_log` WRITE;
/*!40000 ALTER TABLE `seller_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `seller_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_offer_info`
--

DROP TABLE IF EXISTS `seller_offer_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `seller_offer_info` (
  `seller_offer_info_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) unsigned NOT NULL,
  `product_id` int(11) unsigned NOT NULL,
  `info_line1` varchar(255) NOT NULL,
  `info_line2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`seller_offer_info_id`),
  UNIQUE KEY `seller_offer_info_id_UNIQUE` (`seller_offer_info_id`),
  KEY `fk_seller_offer_info_seller_id_idx` (`seller_id`),
  KEY `fk_seller_offer_info_product_id_idx` (`product_id`),
  CONSTRAINT `fk_seller_offer_info_product_id` FOREIGN KEY (`product_id`) REFERENCES `product_catalog` (`product_id`),
  CONSTRAINT `fk_seller_offer_info_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_offer_info`
--

LOCK TABLES `seller_offer_info` WRITE;
/*!40000 ALTER TABLE `seller_offer_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `seller_offer_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_offer_pictures`
--

DROP TABLE IF EXISTS `seller_offer_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `seller_offer_pictures` (
  `picture_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) unsigned NOT NULL,
  `product_id` int(11) unsigned NOT NULL,
  `picture_location` varchar(255) NOT NULL,
  `contents` longblob,
  PRIMARY KEY (`picture_id`),
  UNIQUE KEY `picture_id_UNIQUE` (`picture_id`),
  KEY `fk_seller_offer_pictures_seller_id_idx` (`seller_id`),
  KEY `fk_seller_offer_pictures_product_id_idx` (`product_id`),
  CONSTRAINT `fk_seller_offer_pictures_product_id` FOREIGN KEY (`product_id`) REFERENCES `product_catalog` (`product_id`),
  CONSTRAINT `fk_seller_offer_pictures_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_offer_pictures`
--

LOCK TABLES `seller_offer_pictures` WRITE;
/*!40000 ALTER TABLE `seller_offer_pictures` DISABLE KEYS */;
/*!40000 ALTER TABLE `seller_offer_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_pictures`
--

DROP TABLE IF EXISTS `seller_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `seller_pictures` (
  `picture_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) unsigned NOT NULL,
  `picture_location` varchar(100) NOT NULL,
  `contents` longblob,
  PRIMARY KEY (`picture_id`),
  UNIQUE KEY `picture_id_UNIQUE` (`picture_id`),
  KEY `fk_seller_pictures_seller_id_idx` (`seller_id`),
  CONSTRAINT `fk_seller_pictures_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_pictures`
--

LOCK TABLES `seller_pictures` WRITE;
/*!40000 ALTER TABLE `seller_pictures` DISABLE KEYS */;
/*!40000 ALTER TABLE `seller_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_transactions`
--

DROP TABLE IF EXISTS `seller_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `seller_transactions` (
  `transaction_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) unsigned NOT NULL,
  `auction_buyers_id` int(11) unsigned NOT NULL,
  `sell_quantity` int(11) unsigned NOT NULL,
  `sell_price` float(6,2) unsigned NOT NULL,
  `gross_sale` decimal(10,2) unsigned NOT NULL COMMENT '	',
  `royalty_percentage` float(4,2) NOT NULL DEFAULT '0.00' COMMENT 'royalty_value are in percentag.',
  `royalty_amount` decimal(10,2) unsigned NOT NULL,
  `vat_percentage` float(4,2) unsigned NOT NULL DEFAULT '0.00',
  `vat_amount` decimal(10,2) unsigned NOT NULL,
  `seller_shipping_charge` float(6,2) unsigned DEFAULT '0.00',
  `net_sales` decimal(10,2) unsigned NOT NULL,
  `transaction_creation` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `reserved1` int(3) DEFAULT NULL,
  `reserved2` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  UNIQUE KEY `transaction_id_UNIQUE` (`transaction_id`),
  KEY `fk_seller_transactions_seller_id_idx` (`seller_id`),
  KEY `fk_seller_transactions_auction_buyers_id_idx` (`auction_buyers_id`),
  CONSTRAINT `fk_seller_transactions_auction_buyers_id` FOREIGN KEY (`auction_buyers_id`) REFERENCES `auction_buyers` (`auction_buyers_id`),
  CONSTRAINT `fk_seller_transactions_seller_id` FOREIGN KEY (`seller_id`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_transactions`
--

LOCK TABLES `seller_transactions` WRITE;
/*!40000 ALTER TABLE `seller_transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `seller_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipper_balance`
--

DROP TABLE IF EXISTS `shipper_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `shipper_balance` (
  `shipper_id` int(11) unsigned NOT NULL,
  `transaction_date` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `transaction_id` int(11) unsigned DEFAULT NULL,
  `epap_id` int(11) unsigned DEFAULT NULL,
  `transaction_description` varchar(255) NOT NULL,
  `transaction_code` char(1) NOT NULL,
  `debit_credit` decimal(10,2) NOT NULL,
  `advance_balance` decimal(10,2) NOT NULL DEFAULT '0.00',
  `balance` decimal(10,2) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  `createdby` int(11) unsigned NOT NULL,
  `updatedby` int(11) unsigned DEFAULT NULL,
  UNIQUE KEY `fk_shipper_balance_unique` (`shipper_id`,`transaction_date`),
  KEY `fk_shipper_balance_shipper_id_idx` (`shipper_id`),
  KEY `fk_shipper_balance_transaction_id_idx` (`transaction_id`),
  KEY `fk_shipper_balance_transaction_code_idx` (`transaction_code`),
  KEY `fk_shipper_balance_createdby_idx` (`createdby`),
  KEY `fk_shipper_balance_updatedby_idx` (`updatedby`),
  KEY `fk_shipper_balance_epap_id` (`epap_id`),
  CONSTRAINT `fk_shipper_balance_createdby` FOREIGN KEY (`createdby`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_shipper_balance_epap_id` FOREIGN KEY (`epap_id`) REFERENCES `epap_balance` (`epap_balance_id`),
  CONSTRAINT `fk_shipper_balance_shipper_id` FOREIGN KEY (`shipper_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_shipper_balance_transaction_code` FOREIGN KEY (`transaction_code`) REFERENCES `transaction_code` (`transaction_code`),
  CONSTRAINT `fk_shipper_balance_transaction_id` FOREIGN KEY (`transaction_id`) REFERENCES `shipper_transactions` (`transaction_id`),
  CONSTRAINT `fk_shipper_balance_updatedby` FOREIGN KEY (`updatedby`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipper_balance`
--

LOCK TABLES `shipper_balance` WRITE;
/*!40000 ALTER TABLE `shipper_balance` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipper_balance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipper_info`
--

DROP TABLE IF EXISTS `shipper_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `shipper_info` (
  `shipper_id` int(11) unsigned NOT NULL,
  `info_line1` varchar(255) NOT NULL,
  `info_line2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`shipper_id`),
  CONSTRAINT `fk_shipper_info_shipper_id` FOREIGN KEY (`shipper_id`) REFERENCES `account_profile` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipper_info`
--

LOCK TABLES `shipper_info` WRITE;
/*!40000 ALTER TABLE `shipper_info` DISABLE KEYS */;
INSERT INTO `shipper_info` VALUES (18,' تعد شركة زاجل من افضل شركات الشحن بالسعودية، وتقدم لك هذه الشركة خدمات النقل الداخلي بداخل المملكة وبأسعار تبدا من 10 ريال اما إذا اردت الحصول على خدمة سريعة ومميزة يمكنك أن تستفيد من خدمة نقل البضائع السريعة والمهمة ولكن بأسعار تصل إلى 35 ريال سعودي.','ساهم توحيد قياس الحاويات على مستوى العالم في تغيير قياسات السفن والسيارات وعربات القطارات لتناسب أحجام وسعات الحاويات طول 20 و40 قدماً'),(19,' تعد شركة زاجل من افضل شركات الشحن بالسعودية، وتقدم لك هذه الشركة خدمات النقل الداخلي بداخل المملكة وبأسعار تبدا من 10 ريال اما إذا اردت الحصول على خدمة سريعة ومميزة يمكنك أن تستفيد من خدمة نقل البضائع السريعة والمهمة ولكن بأسعار تصل إلى 35 ريال سعودي.','ساهم توحيد قياس الحاويات على مستوى العالم في تغيير قياسات السفن والسيارات وعربات القطارات لتناسب أحجام وسعات الحاويات طول 20 و40 قدماً'),(20,' تعد شركة زاجل من افضل شركات الشحن بالسعودية، وتقدم لك هذه الشركة خدمات النقل الداخلي بداخل المملكة وبأسعار تبدا من 10 ريال اما إذا اردت الحصول على خدمة سريعة ومميزة يمكنك أن تستفيد من خدمة نقل البضائع السريعة والمهمة ولكن بأسعار تصل إلى 35 ريال سعودي.','ساهم توحيد قياس الحاويات على مستوى العالم في تغيير قياسات السفن والسيارات وعربات القطارات لتناسب أحجام وسعات الحاويات طول 20 و40 قدماً'),(21,' تعد شركة زاجل من افضل شركات الشحن بالسعودية، وتقدم لك هذه الشركة خدمات النقل الداخلي بداخل المملكة وبأسعار تبدا من 10 ريال اما إذا اردت الحصول على خدمة سريعة ومميزة يمكنك أن تستفيد من خدمة نقل البضائع السريعة والمهمة ولكن بأسعار تصل إلى 35 ريال سعودي.','ساهم توحيد قياس الحاويات على مستوى العالم في تغيير قياسات السفن والسيارات وعربات القطارات لتناسب أحجام وسعات الحاويات طول 20 و40 قدماً'),(22,' تعد شركة زاجل من افضل شركات الشحن بالسعودية، وتقدم لك هذه الشركة خدمات النقل الداخلي بداخل المملكة وبأسعار تبدا من 10 ريال اما إذا اردت الحصول على خدمة سريعة ومميزة يمكنك أن تستفيد من خدمة نقل البضائع السريعة والمهمة ولكن بأسعار تصل إلى 35 ريال سعودي.','ساهم توحيد قياس الحاويات على مستوى العالم في تغيير قياسات السفن والسيارات وعربات القطارات لتناسب أحجام وسعات الحاويات طول 20 و40 قدماً'),(23,' تعد شركة زاجل من افضل شركات الشحن بالسعودية، وتقدم لك هذه الشركة خدمات النقل الداخلي بداخل المملكة وبأسعار تبدا من 10 ريال اما إذا اردت الحصول على خدمة سريعة ومميزة يمكنك أن تستفيد من خدمة نقل البضائع السريعة والمهمة ولكن بأسعار تصل إلى 35 ريال سعودي.','ساهم توحيد قياس الحاويات على مستوى العالم في تغيير قياسات السفن والسيارات وعربات القطارات لتناسب أحجام وسعات الحاويات طول 20 و40 قدماً');
/*!40000 ALTER TABLE `shipper_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipper_pictures`
--

DROP TABLE IF EXISTS `shipper_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `shipper_pictures` (
  `picture_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `shipper_id` int(11) unsigned NOT NULL,
  `picture_location` varchar(100) NOT NULL,
  `contents` longblob,
  PRIMARY KEY (`picture_id`),
  UNIQUE KEY `picture_id_UNIQUE` (`picture_id`),
  KEY `fk_shipper_pictures_shipper_id_idx` (`shipper_id`),
  CONSTRAINT `fk_shipper_pictures_shipper_id` FOREIGN KEY (`shipper_id`) REFERENCES `account_profile` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipper_pictures`
--

LOCK TABLES `shipper_pictures` WRITE;
/*!40000 ALTER TABLE `shipper_pictures` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipper_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shipper_transactions`
--

DROP TABLE IF EXISTS `shipper_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `shipper_transactions` (
  `transaction_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `shipper_id` int(11) unsigned NOT NULL,
  `auction_buyers_id` int(11) unsigned NOT NULL,
  `shipping_from_location` int(11) unsigned NOT NULL,
  `shipping_to_location` int(11) unsigned NOT NULL,
  `gross_revenue` decimal(10,2) unsigned NOT NULL,
  `royalty_percentage` float(4,2) NOT NULL DEFAULT '0.00' COMMENT 'royalty_value are in percentag.',
  `royalty_amount` decimal(10,2) unsigned NOT NULL,
  `vat_percentage` float(4,2) unsigned NOT NULL DEFAULT '0.00',
  `vat_amount` decimal(10,2) unsigned NOT NULL,
  `net_revenue` decimal(10,2) unsigned NOT NULL,
  `transaction_creation` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`transaction_id`),
  UNIQUE KEY `transaction_id_UNIQUE` (`transaction_id`),
  KEY `fk_shipper_transactions_shipper_id_idx` (`shipper_id`),
  KEY `fk_shipper_transactions_auction_buyers_id_idx` (`auction_buyers_id`),
  KEY `fk_shipper_transactions_shipping_from_location_idx` (`shipping_from_location`),
  KEY `fk_shipper_transactions_shipping_to_location_idx` (`shipping_to_location`),
  CONSTRAINT `fk_shipper_transactions_auction_buyers_id` FOREIGN KEY (`auction_buyers_id`) REFERENCES `auction_buyers` (`auction_buyers_id`),
  CONSTRAINT `fk_shipper_transactions_shipper_id` FOREIGN KEY (`shipper_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_shipper_transactions_shipping_from_location` FOREIGN KEY (`shipping_from_location`) REFERENCES `account_locations` (`location_id`),
  CONSTRAINT `fk_shipper_transactions_shipping_to_location` FOREIGN KEY (`shipping_to_location`) REFERENCES `account_locations` (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shipper_transactions`
--

LOCK TABLES `shipper_transactions` WRITE;
/*!40000 ALTER TABLE `shipper_transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `shipper_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shippers_registration_info`
--

DROP TABLE IF EXISTS `shippers_registration_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `shippers_registration_info` (
  `account_id` int(11) unsigned NOT NULL,
  `driver_license_expiration_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `truck_model_year` int(4) NOT NULL,
  `truck_type` varchar(50) NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `accountId_UNIQUE` (`account_id`),
  CONSTRAINT `fk_shippers_registration_info_account_id` FOREIGN KEY (`account_id`) REFERENCES `account_profile` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shippers_registration_info`
--

LOCK TABLES `shippers_registration_info` WRITE;
/*!40000 ALTER TABLE `shippers_registration_info` DISABLE KEYS */;
INSERT INTO `shippers_registration_info` VALUES (18,'2019-05-05 00:09:00',2011,'دينا عادي'),(19,'2022-07-05 00:09:00',2018,'دينا جامبو'),(20,'2021-11-05 00:09:00',2006,'ونيت أيسوزو'),(21,'2020-02-05 00:09:00',2017,'براد 12م'),(22,'2025-09-05 00:09:00',2017,'دينا جامبو'),(23,'2022-10-05 00:09:00',2014,'براد صغير 3 طن');
/*!40000 ALTER TABLE `shippers_registration_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_code`
--

DROP TABLE IF EXISTS `transaction_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `transaction_code` (
  `transaction_code` char(1) NOT NULL,
  `transaction_code_meaning` varchar(30) NOT NULL,
  PRIMARY KEY (`transaction_code`),
  UNIQUE KEY `transaction_code_UNIQUE` (`transaction_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_code`
--

LOCK TABLES `transaction_code` WRITE;
/*!40000 ALTER TABLE `transaction_code` DISABLE KEYS */;
INSERT INTO `transaction_code` VALUES ('A','Adjustment'),('C','Closing Balance'),('D','Deposit'),('J','Adjustment Minus'),('K','Adjustment Plus'),('M','Commission'),('O','Opening Balance'),('P','Purchase'),('R','Shipping Revenue'),('S','Sales'),('V','Vat'),('W','Withdrawal'),('Y','Advance Payoff');
/*!40000 ALTER TABLE `transaction_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vat_balance`
--

DROP TABLE IF EXISTS `vat_balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vat_balance` (
  `vat_balance_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `transaction_date` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `transaction_id` int(11) unsigned DEFAULT NULL,
  `transaction_description` varchar(100) NOT NULL,
  `transaction_code` char(1) NOT NULL,
  `account_id` int(11) unsigned NOT NULL,
  `account_type` char(1) NOT NULL,
  `debit_credit` decimal(10,2) NOT NULL,
  `balance` decimal(10,2) NOT NULL,
  `comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`vat_balance_id`),
  KEY `fk_vat_balance_account_id_idx` (`account_id`),
  KEY `fk_vat_balance_account_type_idx` (`account_type`),
  KEY `fk_vat_balance_transaction_code_idx` (`transaction_code`),
  CONSTRAINT `fk_vat_balance_account_id` FOREIGN KEY (`account_id`) REFERENCES `account_profile` (`account_id`),
  CONSTRAINT `fk_vat_balance_account_type` FOREIGN KEY (`account_type`) REFERENCES `account_type_codes` (`account_type`),
  CONSTRAINT `fk_vat_balance_transaction_code` FOREIGN KEY (`transaction_code`) REFERENCES `transaction_code` (`transaction_code`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vat_balance`
--

LOCK TABLES `vat_balance` WRITE;
/*!40000 ALTER TABLE `vat_balance` DISABLE KEYS */;
/*!40000 ALTER TABLE `vat_balance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'epap'
--

--
-- Dumping routines for database 'epap'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-07 14:49:35

-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: epap
-- ------------------------------------------------------
-- Server version	5.7.25-log

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
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `announcement` (
  `announcements_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `added_by` int(11) unsigned NOT NULL,
  `modified_by` int(11) unsigned DEFAULT NULL,
  `item` varchar(1024) NOT NULL,
  `added_by _timestamp` timestamp(6) NOT NULL DEFAULT '0000-00-00 00:00:00.000000',
  `modified_by _timestamp` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`announcements_id`),
  UNIQUE KEY `news_id_UNIQUE` (`announcements_id`),
  KEY `fk_news_items_added_by_idx` (`added_by`),
  KEY `fk_news_items_modified_by_idx` (`modified_by`),
  CONSTRAINT `fk_news_items_added_by` FOREIGN KEY (`added_by`) REFERENCES `account_profile` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_news_items_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `account_profile` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

LOCK TABLES `announcement` WRITE;
/*!40000 ALTER TABLE `announcement` DISABLE KEYS */;
INSERT INTO `announcement` VALUES (2,6,NULL,'demo','0000-00-00 00:00:00.000000',NULL);
/*!40000 ALTER TABLE `announcement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'epap'
--

--
-- Dumping routines for database 'epap'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-30 17:09:59

-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: epap
-- ------------------------------------------------------
-- Server version	5.7.25-log

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
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `announcement` (
  `announcements_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `added_by` int(11) unsigned NOT NULL,
  `modified_by` int(11) unsigned DEFAULT NULL,
  `announcement` varchar(1024) NOT NULL,
  `added_by_timestamp` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `modified_by_timestamp` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`announcements_id`),
  UNIQUE KEY `news_id_UNIQUE` (`announcements_id`),
  KEY `fk_news_items_added_by_idx` (`added_by`),
  KEY `fk_news_items_modified_by_idx` (`modified_by`),
  CONSTRAINT `fk_news_items_added_by` FOREIGN KEY (`added_by`) REFERENCES `account_profile` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_news_items_modified_by` FOREIGN KEY (`modified_by`) REFERENCES `account_profile` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

LOCK TABLES `announcement` WRITE;
/*!40000 ALTER TABLE `announcement` DISABLE KEYS */;
/*!40000 ALTER TABLE `announcement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'epap'
--

--
-- Dumping routines for database 'epap'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-31 17:29:56
