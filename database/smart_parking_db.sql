-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 16, 2026 at 06:47 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smart_parking_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `analytics`
--

CREATE TABLE `analytics` (
  `id` int(11) NOT NULL,
  `total_vehicles` int(11) DEFAULT NULL,
  `occupied_slots` int(11) DEFAULT NULL,
  `available_slots` int(11) DEFAULT NULL,
  `revenue` double DEFAULT NULL,
  `report_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `exit_records`
--

CREATE TABLE `exit_records` (
  `id` int(11) NOT NULL,
  `vehicle_number` varchar(50) DEFAULT NULL,
  `owner_name` varchar(100) DEFAULT NULL,
  `vehicle_type` varchar(50) DEFAULT NULL,
  `slot_id` varchar(20) DEFAULT NULL,
  `entry_time` datetime DEFAULT NULL,
  `exit_time` datetime DEFAULT current_timestamp(),
  `parking_fee` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `parking_records`
--

CREATE TABLE `parking_records` (
  `id` int(11) NOT NULL,
  `vehicle_number` varchar(50) DEFAULT NULL,
  `owner_name` varchar(50) DEFAULT NULL,
  `vehicle_type` varchar(50) DEFAULT NULL,
  `entry_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `exit_time` timestamp NULL DEFAULT NULL,
  `fee` double DEFAULT 0,
  `status` varchar(20) DEFAULT 'PARKED',
  `slot_number` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parking_records`
--

INSERT INTO `parking_records` (`id`, `vehicle_number`, `owner_name`, `vehicle_type`, `entry_time`, `exit_time`, `fee`, `status`, `slot_number`) VALUES
(67, '1', 'janith', 'Car', '2026-05-16 03:00:54', '2026-05-16 03:01:48', 500, 'EXITED', NULL),
(68, '1', 'janith', 'Car', '2026-05-16 03:01:29', '2026-05-16 03:01:48', 500, 'EXITED', NULL),
(69, '1', 'janith', 'Car', '2026-05-16 03:01:29', '2026-05-16 03:01:48', 500, 'EXITED', NULL),
(70, '1', 'janith', 'Car', '2026-05-16 03:01:29', '2026-05-16 03:01:48', 500, 'EXITED', NULL),
(71, '1', 'User', 'Car', '2026-05-16 03:12:16', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(72, '1', 'User', 'Car', '2026-05-16 03:12:53', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(73, '1', 'User', 'Car', '2026-05-16 03:12:53', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(74, '1', 'User', 'Car', '2026-05-16 03:12:53', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(75, '1', 'User', 'Car', '2026-05-16 03:13:39', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(76, '1', 'User', 'Car', '2026-05-16 03:13:39', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(77, '1', 'User', 'Car', '2026-05-16 03:13:39', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(78, '1', 'User', 'Car', '2026-05-16 03:13:39', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(79, '1', 'User', 'Car', '2026-05-16 03:13:39', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(80, '1', 'User', 'Car', '2026-05-16 03:13:39', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(81, '1', 'User', 'Car', '2026-05-16 03:13:39', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(82, '1', 'User', 'Car', '2026-05-16 03:13:39', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(83, '1', 'User', 'Car', '2026-05-16 03:13:39', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(84, '1', 'User', 'Car', '2026-05-16 03:13:39', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(85, '1', 'User', 'Car', '2026-05-16 03:13:39', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(86, '1', 'User', 'Car', '2026-05-16 03:13:39', '2026-05-16 03:24:07', 100, 'EXITED', NULL),
(87, '1', 'nisal', 'Van', '2026-05-16 03:25:04', '2026-05-16 03:25:32', 100, 'EXITED', 'S1'),
(88, '1', 'User', 'Car', '2026-05-16 03:26:29', NULL, 0, 'PARKED', 'S1'),
(89, '2', 'kamal', 'Van', '2026-05-16 03:27:45', NULL, 0, 'PARKED', 'S2');

-- --------------------------------------------------------

--
-- Table structure for table `parking_slots`
--

CREATE TABLE `parking_slots` (
  `id` int(11) NOT NULL,
  `slot_id` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `floor_number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parking_slots`
--

INSERT INTO `parking_slots` (`id`, `slot_id`, `status`, `floor_number`) VALUES
(1, 'S1', 'Available', 1),
(2, 'S2', 'Available', 1),
(3, 'S3', 'Available', 1),
(4, 'S4', 'Available', 1),
(5, 'S5', 'Available', 1),
(6, 'S6', 'Available', 1),
(7, 'S7', 'Available', 1),
(8, 'S8', 'Available', 1),
(9, 'S9', 'Available', 1),
(10, 'S10', 'Available', 1),
(11, 'S11', 'Available', 1),
(12, 'S12', 'Available', 1);

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `id` int(11) NOT NULL,
  `vehicle_number` varchar(50) DEFAULT NULL,
  `owner_name` varchar(100) DEFAULT NULL,
  `reserved_slot` varchar(20) DEFAULT NULL,
  `reservation_date` date DEFAULT NULL,
  `reservation_time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `role` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(2, 'admin', '1234', 'ADMIN'),
(3, 'user', '1234', 'USER'),
(4, 'security', '1234', 'SECURITY');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `analytics`
--
ALTER TABLE `analytics`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exit_records`
--
ALTER TABLE `exit_records`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `parking_records`
--
ALTER TABLE `parking_records`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `parking_slots`
--
ALTER TABLE `parking_slots`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `slot_id` (`slot_id`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `analytics`
--
ALTER TABLE `analytics`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `exit_records`
--
ALTER TABLE `exit_records`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `parking_records`
--
ALTER TABLE `parking_records`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=90;

--
-- AUTO_INCREMENT for table `parking_slots`
--
ALTER TABLE `parking_slots`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
