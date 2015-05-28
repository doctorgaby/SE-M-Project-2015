
-- phpMyAdmin SQL Dump
-- version 2.11.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 21, 2015 at 11:04 AM
-- Server version: 5.1.57
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `a8380252_group8`
--

-- --------------------------------------------------------

--
-- Table structure for table `brakeMeasurements`
--

CREATE TABLE `brakeMeasurements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `brake` int(11) NOT NULL,
  `measuredAt` int(11) NOT NULL,
  `uploadedAt` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=295 ;


--
-- Table structure for table `brakePoints`
--

CREATE TABLE `brakePoints` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `brake` int(11) NOT NULL,
  `measuredAt` int(11) NOT NULL,
  `uploadedAt` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1004 ;



--
-- Table structure for table `distractionMeasurements`
--

CREATE TABLE `distractionMeasurements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `distraction` int(11) NOT NULL,
  `measuredAt` int(11) NOT NULL,
  `uploadedAt` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=286 ;


--
-- Table structure for table `distractionPoints`
--

CREATE TABLE `distractionPoints` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `distraction` int(11) NOT NULL,
  `measuredAt` int(11) NOT NULL,
  `uploadedAt` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=352 ;


--
-- Table structure for table `finalmeasurements`
--

CREATE TABLE `finalmeasurements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `speed` int(11) NOT NULL,
  `brake` int(11) NOT NULL,
  `fuel` int(11) NOT NULL,
  `distraction` int(11) NOT NULL,
  `measuredAt` int(11) NOT NULL,
  `uploadedAt` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=89 ;


--
-- Table structure for table `friends`
--

CREATE TABLE `friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `friend` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `created_at` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=41 ;


--
-- Table structure for table `fuelMeasurements`
--

CREATE TABLE `fuelMeasurements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `fuel` int(11) NOT NULL,
  `measuredAt` int(11) NOT NULL,
  `uploadedAt` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=325 ;


--
-- Table structure for table `fuelPoints`
--

CREATE TABLE `fuelPoints` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `fuel` int(11) NOT NULL,
  `measuredAt` int(11) NOT NULL,
  `uploadedAt` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1380 ;


--
-- Table structure for table `speedMeasurements`
--

CREATE TABLE `speedMeasurements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `speed` int(11) NOT NULL,
  `measuredAt` int(11) NOT NULL,
  `uploadedAt` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=267 ;


--
-- Table structure for table `speedPoints`
--

CREATE TABLE `speedPoints` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `speed` int(11) NOT NULL,
  `measuredAt` int(11) NOT NULL,
  `uploadedAt` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=742 ;


--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `encrypted_password` varchar(80) COLLATE latin1_general_ci NOT NULL,
  `salt` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=40 ;

