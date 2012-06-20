



-- -----------------------------------------------------------
-- Entity Designer DDL Script for MySQL Server 4.1 and higher
-- -----------------------------------------------------------
-- Date Created: 06/20/2012 15:39:29
-- Generated from EDMX file: C:\Users\thebigbang\Documents\NetBeansProjects\MyVideoLibrary\vs_db\db\db\model.edmx
-- Target version: 2.0.0.0
-- --------------------------------------------------


-- --------------------------------------------------
-- Dropping existing FOREIGN KEY constraints
-- NOTE: if the constraint does not exist, an ignorable error will be reported.
-- --------------------------------------------------


-- --------------------------------------------------
-- Dropping existing tables
-- --------------------------------------------------
SET foreign_key_checks = 0;
SET foreign_key_checks = 1;

-- --------------------------------------------------
-- Creating all tables
-- --------------------------------------------------

-- Creating table 'GenreSet'

CREATE TABLE `GenreSet` (
    `id` int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `nom` longtext  NOT NULL
);

-- Creating table 'FilmsSet'

CREATE TABLE `FilmsSet` (
    `id` int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `titre` longtext  NOT NULL,
    `description` longtext  NOT NULL,
    `numerotation` longtext  NOT NULL
);

-- Creating table 'RealisateurSet'

CREATE TABLE `RealisateurSet` (
    `id` int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` longtext  NOT NULL,
    `surname` longtext  NOT NULL,
    `biography` longtext  NOT NULL
);

-- Creating table 'FilmsRealisateur'

CREATE TABLE `FilmsRealisateur` (
    `Films_id` int  NOT NULL,
    `Realisateur_id` int  NOT NULL
);

-- Creating table 'FilmsGenre'

CREATE TABLE `FilmsGenre` (
    `Films_id` int  NOT NULL,
    `Genre_id` int  NOT NULL
);



-- --------------------------------------------------
-- Creating all PRIMARY KEY constraints
-- --------------------------------------------------

-- Creating primary key on `Films_id`, `Realisateur_id` in table 'FilmsRealisateur'

ALTER TABLE `FilmsRealisateur`
ADD CONSTRAINT `PK_FilmsRealisateur`
    PRIMARY KEY (`Films_id`, `Realisateur_id` );

-- Creating primary key on `Films_id`, `Genre_id` in table 'FilmsGenre'

ALTER TABLE `FilmsGenre`
ADD CONSTRAINT `PK_FilmsGenre`
    PRIMARY KEY (`Films_id`, `Genre_id` );



-- --------------------------------------------------
-- Creating all FOREIGN KEY constraints
-- --------------------------------------------------

-- Creating foreign key on `Films_id` in table 'FilmsRealisateur'

ALTER TABLE `FilmsRealisateur`
ADD CONSTRAINT `FK_FilmsRealisateur_Films`
    FOREIGN KEY (`Films_id`)
    REFERENCES `FilmsSet`
        (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION;

-- Creating foreign key on `Realisateur_id` in table 'FilmsRealisateur'

ALTER TABLE `FilmsRealisateur`
ADD CONSTRAINT `FK_FilmsRealisateur_Realisateur`
    FOREIGN KEY (`Realisateur_id`)
    REFERENCES `RealisateurSet`
        (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION;

-- Creating non-clustered index for FOREIGN KEY 'FK_FilmsRealisateur_Realisateur'

CREATE INDEX `IX_FK_FilmsRealisateur_Realisateur` 
    ON `FilmsRealisateur`
    (`Realisateur_id`);

-- Creating foreign key on `Films_id` in table 'FilmsGenre'

ALTER TABLE `FilmsGenre`
ADD CONSTRAINT `FK_FilmsGenre_Films`
    FOREIGN KEY (`Films_id`)
    REFERENCES `FilmsSet`
        (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION;

-- Creating foreign key on `Genre_id` in table 'FilmsGenre'

ALTER TABLE `FilmsGenre`
ADD CONSTRAINT `FK_FilmsGenre_Genre`
    FOREIGN KEY (`Genre_id`)
    REFERENCES `GenreSet`
        (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION;

-- Creating non-clustered index for FOREIGN KEY 'FK_FilmsGenre_Genre'

CREATE INDEX `IX_FK_FilmsGenre_Genre` 
    ON `FilmsGenre`
    (`Genre_id`);

-- --------------------------------------------------
-- Script has ended
-- --------------------------------------------------
