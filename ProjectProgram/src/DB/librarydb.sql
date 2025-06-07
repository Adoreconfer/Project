-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Час створення: Чрв 07 2025 р., 16:23
-- Версія сервера: 10.4.32-MariaDB
-- Версія PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База даних: `librarydb`
--

-- --------------------------------------------------------

--
-- Структура таблиці `book`
--

CREATE TABLE `book` (
  `id_book` int(10) UNSIGNED NOT NULL,
  `title` varchar(50) NOT NULL,
  `author` varchar(50) NOT NULL,
  `isbn` varchar(14) NOT NULL,
  `total_copies` int(11) NOT NULL,
  `category` varchar(100) DEFAULT NULL,
  `available_copies` int(11) NOT NULL,
  `publication_year` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп даних таблиці `book`
--

INSERT INTO `book` (`id_book`, `title`, `author`, `isbn`, `total_copies`, `category`, `available_copies`, `publication_year`) VALUES
(8, 'Folwark zwierzęcy', 'George Orwell', '9788370000011', 8, 'Satira polityczna', 8, 2000),
(9, 'Przedwiośnie', 'Stefan Żeromski', '9788370000012', 4, 'Powieść', 4, 2001),
(13, 'Sklepy cynamonowe', 'Bruno Schulz', '9788370000013', 5, 'Opowiadania', 4, 2002),
(14, 'Pan Tadeusz', 'Adam Mickiewicz', '9788300000004', 3, 'Epika', 4, 2001),
(18, 'Czysty Kod', 'Robert C. Martin', '9788370000014', 3, 'Nauka', 3, 2000),
(21, 'Lalka', 'Prus', '9788370000001', 6, 'Powieść', 6, 2000);

-- --------------------------------------------------------

--
-- Структура таблиці `loan`
--

CREATE TABLE `loan` (
  `id_loan` int(10) UNSIGNED NOT NULL,
  `id_book` int(10) UNSIGNED NOT NULL,
  `id_user` int(10) UNSIGNED NOT NULL,
  `due_date` date NOT NULL,
  `fine` decimal(6,2) DEFAULT 0.00,
  `loan_date` date NOT NULL DEFAULT curdate(),
  `return_date` date DEFAULT NULL,
  `status` enum('loaned','returned') NOT NULL DEFAULT 'loaned'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп даних таблиці `loan`
--

INSERT INTO `loan` (`id_loan`, `id_book`, `id_user`, `due_date`, `fine`, `loan_date`, `return_date`, `status`) VALUES
(25, 14, 2, '2025-09-06', 0.00, '2025-06-06', '2025-06-06', 'returned');

-- --------------------------------------------------------

--
-- Структура таблиці `useraccount`
--

CREATE TABLE `useraccount` (
  `id_user` int(10) UNSIGNED NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `role` enum('reader','librarian') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп даних таблиці `useraccount`
--

INSERT INTO `useraccount` (`id_user`, `username`, `password`, `first_name`, `last_name`, `role`) VALUES
(1, 'admin', 'admin1234', 'Jan', 'Kowalski', 'librarian'),
(2, 'user', 'user1234', 'Ala', 'Kowalska', 'reader'),
(4, 'tom', '1234', 'Tom', 'Nowak', 'reader'),
(5, 'zofia14', 'zofia4321', 'Zofia', 'Wiśniewska', 'reader');

--
-- Індекси збережених таблиць
--

--
-- Індекси таблиці `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id_book`),
  ADD UNIQUE KEY `isbn` (`isbn`);

--
-- Індекси таблиці `loan`
--
ALTER TABLE `loan`
  ADD PRIMARY KEY (`id_loan`),
  ADD KEY `id_ksiazka` (`id_book`),
  ADD KEY `id_uzytkownik` (`id_user`);

--
-- Індекси таблиці `useraccount`
--
ALTER TABLE `useraccount`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT для збережених таблиць
--

--
-- AUTO_INCREMENT для таблиці `book`
--
ALTER TABLE `book`
  MODIFY `id_book` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT для таблиці `loan`
--
ALTER TABLE `loan`
  MODIFY `id_loan` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT для таблиці `useraccount`
--
ALTER TABLE `useraccount`
  MODIFY `id_user` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Обмеження зовнішнього ключа збережених таблиць
--

--
-- Обмеження зовнішнього ключа таблиці `loan`
--
ALTER TABLE `loan`
  ADD CONSTRAINT `loan_ibfk_1` FOREIGN KEY (`id_book`) REFERENCES `book` (`id_book`) ON DELETE CASCADE,
  ADD CONSTRAINT `loan_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `useraccount` (`id_user`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
