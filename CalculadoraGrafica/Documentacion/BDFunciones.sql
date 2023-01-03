-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-05-2021 a las 09:29:57
-- Versión del servidor: 10.4.18-MariaDB
-- Versión de PHP: 7.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `visualizadorfunciones`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `formulas`
--

CREATE TABLE `formulas` (
  `idFormula` int(3) NOT NULL,
  `nombre` varchar(40) NOT NULL,
  `formula` varchar(50) NOT NULL,
  `tipo` enum('guardada','predeterminada') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `formulas`
--

INSERT INTO `formulas` (`idFormula`, `nombre`, `formula`, `tipo`) VALUES
(1, 'Gravitación universal', '-(G*((m1*m2)/(d)))', 'predeterminada'),
(2, 'e = mc^2', 'm*C^2', 'predeterminada'),
(3, 'Punto en una circunferencia', 'sqrt(100-x^2)', 'predeterminada'),
(10, 'F = m * a', 'm * a', 'predeterminada'),
(13, 'x al cuadrado', 'x^2', 'guardada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tablavalores`
--

CREATE TABLE `tablavalores` (
  `idTabla` int(3) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `idFormula` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tablavalores`
--

INSERT INTO `tablavalores` (`idTabla`, `nombre`, `idFormula`) VALUES
(15, 'x^2', 13);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `valores`
--

CREATE TABLE `valores` (
  `x` double NOT NULL,
  `y` double NOT NULL,
  `idTabla` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `valores`
--

INSERT INTO `valores` (`x`, `y`, `idTabla`) VALUES
(-4, 16, 15),
(-3, 9, 15),
(-2, 4, 15),
(-1, 1, 15),
(0, 0, 15),
(1, 1, 15),
(2, 4, 15),
(3, 9, 15),
(4, 16, 15);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `formulas`
--
ALTER TABLE `formulas`
  ADD PRIMARY KEY (`idFormula`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `tablavalores`
--
ALTER TABLE `tablavalores`
  ADD PRIMARY KEY (`idTabla`),
  ADD UNIQUE KEY `nombre` (`nombre`),
  ADD KEY `fk_tabla_formula` (`idFormula`);

--
-- Indices de la tabla `valores`
--
ALTER TABLE `valores`
  ADD PRIMARY KEY (`x`,`idTabla`),
  ADD KEY `fk_tabla_valor` (`idTabla`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `formulas`
--
ALTER TABLE `formulas`
  MODIFY `idFormula` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `tablavalores`
--
ALTER TABLE `tablavalores`
  MODIFY `idTabla` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tablavalores`
--
ALTER TABLE `tablavalores`
  ADD CONSTRAINT `fk_tabla_formula` FOREIGN KEY (`idFormula`) REFERENCES `formulas` (`idFormula`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Filtros para la tabla `valores`
--
ALTER TABLE `valores`
  ADD CONSTRAINT `fk_tabla_valor` FOREIGN KEY (`idTabla`) REFERENCES `tablavalores` (`idTabla`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
