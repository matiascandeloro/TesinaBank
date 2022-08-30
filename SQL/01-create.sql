


  CREATE TABLE `users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `active` TINYINT(4)  NULL,
  `password` VARCHAR(45) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `roles` VARCHAR(45) NULL,
  `name` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  `mail` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `proyects` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(150) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `description` VARCHAR(3000) NULL,
  `name` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `contact_mail` VARCHAR(150) NOT NULL,
  `date_generation` DATE NOT NULL,
  `overdue_proposal` TINYINT(4) NOT NULL,
  `deleted` TINYINT(4) NOT NULL,
  `last_date_actualization` DATE NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_proyects_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `tesinabank`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `applicantstudent` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `proyect_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `description` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  INDEX `proyect_id_idx` (`proyect_id` ASC),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `fk_applicantstudent_proyects`
    FOREIGN KEY (`proyect_id`)
    REFERENCES `tesinabank`.`proyects` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_applicantstudent_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `tesinabank`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);

 CREATE TABLE `proyectdoc` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `proyectid` INT(11) NOT NULL,
  `path` VARCHAR(255) NOT NULL,
  `filename` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `proyect_id_idx` (`proyectid` ASC),
  CONSTRAINT `fk_proyectdoc_proyects`
    FOREIGN KEY (`proyectid`)
    REFERENCES `tesinabank`.`proyects` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `tags` (
  `idtags` INT(11) NOT NULL AUTO_INCREMENT,
  `tagname` VARCHAR(45) NULL,
  PRIMARY KEY (`idtags`));

 CREATE TABLE `proyecttags` (
  `idproyecttags` INT(11) NOT NULL AUTO_INCREMENT,
  `proyectid` INT(11) NOT NULL,
  `idtags` INT(11) NOT NULL,
  PRIMARY KEY (`idproyecttags`),
  INDEX `fk_proyecttags_proyect_idx` (`proyectid` ASC),
  INDEX `fk_proyecttags_tags_idx` (`idtags` ASC),
  CONSTRAINT `fk_proyecttags_proyects`
    FOREIGN KEY (`proyectid`)
    REFERENCES `tesinabank`.`proyects` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_proyecttags_tags`
    FOREIGN KEY (`idtags`)
    REFERENCES `tesinabank`.`tags` (`idtags`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
  
CREATE TABLE `config` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `view_time_proposal` BIGINT(20) NOT NULL,
  `delete_time_proposal` BIGINT(20) NOT NULL,
  `max_amoung_applicants` INT(11) NOT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `mails` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `proyect_id` INT(11) NOT NULL,
  `to` VARCHAR(150) NOT NULL,
  `from` VARCHAR(150) NOT NULL,
  `cc` VARCHAR(150) NOT NULL,
  `header` VARCHAR(200) NOT NULL,
  `text`  MEDIUMTEXT NOT NULL,
  `attach` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));