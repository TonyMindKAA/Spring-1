-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.5.47 - MySQL Community Server (GPL)
-- Операционная система:         Win64
-- HeidiSQL Версия:              9.3.0.5033
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- Дамп данных таблицы spting_db.event: ~5 rows (приблизительно)
DELETE FROM `event`;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` (`id`, `title`, `date`, `ticket_price`) VALUES
	(1, 'star wars', '2015-12-22', 150),
	(2, 'incognito', '2015-12-13', 80),
	(3, 'underwood', '2016-01-21', 70),
	(4, 'big city', '2016-01-21', 60),
	(5, 'English extra tipic', '2016-01-04', 240);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;

-- Дамп данных таблицы spting_db.ticket: ~5 rows (приблизительно)
DELETE FROM `ticket`;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` (`id`, `eventId`, `userId`, `category`, `place`) VALUES
	(1, 1, 1, 'STANDARD', 22),
	(2, 2, 2, 'PREMIUM', 3),
	(3, 3, 3, 'BAR', 4),
	(4, 4, 4, 'BAR', 5),
	(5, 5, 5, 'BAR', 6);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;

-- Дамп данных таблицы spting_db.user: ~5 rows (приблизительно)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `name`, `email`) VALUES
	(1, 'Bob', 'bob@gmail.com'),
	(2, 'Sem', 'sem@gmail.com'),
	(3, 'Vova', 'vova@gmail.com'),
	(4, 'Lina', 'lina@gmail.com'),
	(5, 'Ania', 'ania@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Дамп данных таблицы spting_db.user_account: ~5 rows (приблизительно)
DELETE FROM `user_account`;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` (`id`, `userId`, `prepaid_money`) VALUES
	(1, 1, 66668),
	(2, 2, 2500),
	(3, 3, 9500),
	(4, 4, 23500),
	(5, 5, 90500);
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
