-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Час створення: Трв 25 2025 р., 00:48
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
-- Структура таблиці `ksiazka`
--

CREATE TABLE `ksiazka` (
  `id_ksiazka` int(10) UNSIGNED NOT NULL,
  `tytul` varchar(50) NOT NULL,
  `autor` varchar(50) NOT NULL,
  `isbn` varchar(13) NOT NULL,
  `liczba_egzemplarzy` int(11) NOT NULL CHECK (`liczba_egzemplarzy` >= 0),
  `kategoria` varchar(100) DEFAULT NULL,
  `dostepne_egzemplarze` int(11) NOT NULL CHECK (`dostepne_egzemplarze` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Структура таблиці `uzytkownik`
--

CREATE TABLE `uzytkownik` (
  `id_uzytkownik` int(10) UNSIGNED NOT NULL,
  `username` varchar(50) NOT NULL,
  `pin` varchar(50) NOT NULL,
  `imie` varchar(50) NOT NULL,
  `nazwisko` varchar(50) NOT NULL,
  `rola` enum('uzytkownik','bibliotekarz') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Дамп даних таблиці `uzytkownik`
--

INSERT INTO `uzytkownik` (`id_uzytkownik`, `username`, `pin`, `imie`, `nazwisko`, `rola`) VALUES
(1, 'admin', 'admin1234', 'Jan ', 'Kowalski', 'bibliotekarz'),
(2, 'user', 'user1234', 'Ala', 'Kowalska', 'uzytkownik');

-- --------------------------------------------------------

--
-- Структура таблиці `wypozyczenia`
--

CREATE TABLE `wypozyczenia` (
  `id_wypozyczenie` int(10) UNSIGNED NOT NULL,
  `id_ksiazka` int(10) UNSIGNED NOT NULL,
  `id_uzytkownik` int(10) UNSIGNED NOT NULL,
  `data_wypozyczenia` date NOT NULL DEFAULT curdate(),
  `data_zwrotu` date DEFAULT NULL,
  `termin_zwrotu` date NOT NULL,
  `kara` decimal(6,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Індекси збережених таблиць
--

--
-- Індекси таблиці `ksiazka`
--
ALTER TABLE `ksiazka`
  ADD PRIMARY KEY (`id_ksiazka`),
  ADD UNIQUE KEY `isbn` (`isbn`);

--
-- Індекси таблиці `uzytkownik`
--
ALTER TABLE `uzytkownik`
  ADD PRIMARY KEY (`id_uzytkownik`);

--
-- Індекси таблиці `wypozyczenia`
--
ALTER TABLE `wypozyczenia`
  ADD PRIMARY KEY (`id_wypozyczenie`),
  ADD KEY `id_ksiazka` (`id_ksiazka`),
  ADD KEY `id_uzytkownik` (`id_uzytkownik`);

--
-- AUTO_INCREMENT для збережених таблиць
--

--
-- AUTO_INCREMENT для таблиці `ksiazka`
--
ALTER TABLE `ksiazka`
  MODIFY `id_ksiazka` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблиці `uzytkownik`
--
ALTER TABLE `uzytkownik`
  MODIFY `id_uzytkownik` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблиці `wypozyczenia`
--
ALTER TABLE `wypozyczenia`
  MODIFY `id_wypozyczenie` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- Обмеження зовнішнього ключа збережених таблиць
--

--
-- Обмеження зовнішнього ключа таблиці `wypozyczenia`
--
ALTER TABLE `wypozyczenia`
  ADD CONSTRAINT `wypozyczenia_ibfk_1` FOREIGN KEY (`id_ksiazka`) REFERENCES `ksiazka` (`id_ksiazka`) ON DELETE CASCADE,
  ADD CONSTRAINT `wypozyczenia_ibfk_2` FOREIGN KEY (`id_uzytkownik`) REFERENCES `uzytkownik` (`id_uzytkownik`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
