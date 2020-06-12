CREATE TABLE `family_name` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `name` varchar(32) DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `family_names_name_uindex` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `family_kids` (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `kid_id` int(11) DEFAULT NULL,
                               `family_id` int(11) DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `family_kids_pk` (`family_id`,`kid_id`),
                               KEY `family_kids_kid_id_fk` (`kid_id`),
                               CONSTRAINT `family_kids_family_names_id_fk` FOREIGN KEY (`family_id`) REFERENCES family_name (`id`),
                               CONSTRAINT `family_kids_kid_id_fk` FOREIGN KEY (`kid_id`) REFERENCES `kid` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `family`;