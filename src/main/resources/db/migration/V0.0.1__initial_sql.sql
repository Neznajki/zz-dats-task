CREATE TABLE `kid` (
                       `id` int(11) NOT NULL AUTO_INCREMENT,
                       `name` varchar(32) DEFAULT NULL,
                       `last_name` varchar(64) DEFAULT NULL,
                       `personal_code` varchar(12) DEFAULT NULL,
                       PRIMARY KEY (`id`),
                       UNIQUE KEY `u_kid` (`personal_code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `kindergarten` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `name` varchar(64) DEFAULT NULL,
                                `address` varchar(128) DEFAULT NULL,
                                `max_kids` int(11) DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `u_kindergarten` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `family` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `kid_id` int(11) DEFAULT NULL,
                          `second_kid_id` int(11) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `u_family` (`kid_id`,`second_kid_id`),
                          KEY `family_kid_id_fk_2` (`second_kid_id`),
                          CONSTRAINT `family_kid_id_fk` FOREIGN KEY (`kid_id`) REFERENCES kid (`id`),
                          CONSTRAINT `family_kid_id_fk_2` FOREIGN KEY (`second_kid_id`) REFERENCES kid (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `kindergarten_kids` (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `kid_id` int(11) DEFAULT NULL,
                                     `kindergarten_id` int(11) DEFAULT NULL,
                                     `joined` datetime DEFAULT CURRENT_TIMESTAMP,
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `kindergarten_kids_pk` (`kindergarten_id`,`kid_id`),
                                     KEY `kindergarten_kids_kid_id_fk` (`kid_id`),
                                     CONSTRAINT `kindergarten_kids_kid_id_fk` FOREIGN KEY (`kid_id`) REFERENCES kid (`id`),
                                     CONSTRAINT `kindergarten_kids_kindergarten_id_fk` FOREIGN KEY (`kindergarten_id`) REFERENCES kindergarten (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `queue` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `kid_id` int(11) DEFAULT NULL,
                         `kindergarten_id` int(11) DEFAULT NULL,
                         `added` datetime DEFAULT CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `u_kid` (`kid_id`,`kindergarten_id`),
                         KEY `queue_kindergarten_id_fk` (`kindergarten_id`),
                         CONSTRAINT `queue_kids_id_fk` FOREIGN KEY (`kid_id`) REFERENCES kid (`id`),
                         CONSTRAINT `queue_kindergarten_id_fk` FOREIGN KEY (`kindergarten_id`) REFERENCES kindergarten (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;