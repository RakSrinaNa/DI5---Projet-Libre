-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mar 08 Janvier 2019 à 18:32
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `projetlibre`
--

-- --------------------------------------------------------

--
-- Structure de la table `equipe`
--

CREATE TABLE IF NOT EXISTS `equipe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `idGymnase` int(11) NOT NULL,
  `idPoule` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `lien_gymnase` (`idGymnase`),
  KEY `lien_poule` (`idPoule`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1879 ;

--
-- Contenu de la table `equipe`
--

INSERT INTO `equipe` (`id`, `nom`, `idGymnase`, `idPoule`) VALUES
(1869, 'AS Monts', 1697, 260),
(1870, 'TVB', 1698, 260),
(1871, 'PSG', 1699, 260),
(1872, 'CSK Moscou', 1700, 260),
(1873, 'ASVEL', 1701, 261),
(1874, 'UBB', 1702, 261),
(1875, 'Saints NO', 1703, 261),
(1876, 'Rays TB', 1704, 261),
(1877, 'Lions Brisbane', 1705, 261),
(1878, 'THW Kiel', 1706, 261);

-- --------------------------------------------------------

--
-- Structure de la table `gymnase`
--

CREATE TABLE IF NOT EXISTS `gymnase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `capacite` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1707 ;

--
-- Contenu de la table `gymnase`
--

INSERT INTO `gymnase` (`id`, `nom`, `capacite`) VALUES
(1697, 'Gymnase de la fete, Monts', 1),
(1698, 'Palais des sports, Tours', 1),
(1699, 'Le parc, Paris', 1),
(1700, 'Gymnase Vladimir Poutine, Moscou', 1),
(1701, 'Stade William Lambert, Pont-de-ruan', 1),
(1702, 'Stade Chaban Delmas, Bordeaux', 1),
(1703, 'Tide Stadium, New Orleans', 1),
(1704, 'Bonux Stadium, TB', 1),
(1705, 'KFC Park, Brisbane', 1),
(1706, 'KartefelStadion, Kiel', 1);

-- --------------------------------------------------------

--
-- Structure de la table `matchs`
--

CREATE TABLE IF NOT EXISTS `matchs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idEquipeDom` int(11) NOT NULL,
  `idEquipeExt` int(11) NOT NULL,
  `journee` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `lien_equipe_dom` (`idEquipeDom`),
  KEY `lien_equipe_ext` (`idEquipeExt`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2843 ;

--
-- Contenu de la table `matchs`
--

INSERT INTO `matchs` (`id`, `idEquipeDom`, `idEquipeExt`, `journee`) VALUES
(2801, 1870, 1869, 1),
(2802, 1871, 1869, 2),
(2803, 1872, 1869, 3),
(2804, 1869, 1870, 4),
(2805, 1869, 1871, 5),
(2806, 1869, 1872, 6),
(2807, 1872, 1870, 2),
(2808, 1871, 1870, 3),
(2809, 1870, 1872, 5),
(2810, 1870, 1871, 6),
(2811, 1872, 1871, 1),
(2812, 1871, 1872, 4),
(2813, 1874, 1873, 1),
(2814, 1875, 1873, 2),
(2815, 1876, 1873, 3),
(2816, 1877, 1873, 4),
(2817, 1878, 1873, 5),
(2818, 1873, 1874, 6),
(2819, 1873, 1875, 7),
(2820, 1873, 1878, 8),
(2821, 1873, 1877, 9),
(2822, 1873, 1876, 10),
(2823, 1876, 1874, 2),
(2824, 1875, 1874, 3),
(2825, 1878, 1874, 4),
(2826, 1877, 1874, 5),
(2827, 1874, 1877, 7),
(2828, 1874, 1875, 8),
(2829, 1874, 1876, 9),
(2830, 1874, 1878, 10),
(2831, 1877, 1875, 1),
(2832, 1875, 1876, 4),
(2833, 1876, 1875, 5),
(2834, 1875, 1878, 6),
(2835, 1878, 1875, 9),
(2836, 1875, 1877, 10),
(2837, 1876, 1878, 1),
(2838, 1877, 1876, 6),
(2839, 1878, 1876, 7),
(2840, 1876, 1877, 8),
(2841, 1878, 1877, 2),
(2842, 1877, 1878, 3);

-- --------------------------------------------------------

--
-- Structure de la table `poule`
--

CREATE TABLE IF NOT EXISTS `poule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=262 ;

--
-- Contenu de la table `poule`
--

INSERT INTO `poule` (`id`, `nom`) VALUES
(260, 'Poule A'),
(261, 'Poule B');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
