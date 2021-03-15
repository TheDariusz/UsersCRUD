CREATE DATABASE workshop
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `email` varchar(255) NOT NULL,
                         `username` varchar(255) NOT NULL,
                         `password` varchar(60) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `user_browser` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `requestDate` datetime DEFAULT NULL,
                        `message_type` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        `message` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

