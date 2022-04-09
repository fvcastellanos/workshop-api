-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema workshop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema workshop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `workshop` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin ;
USE `workshop` ;

-- -----------------------------------------------------
-- Table `workshop`.`discount_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshop`.`discount_type` (
    `id` VARCHAR(50) NOT NULL,
    `name` VARCHAR(150) NOT NULL,
    `description` VARCHAR(300) NULL DEFAULT NULL,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `active` INT NOT NULL DEFAULT '1',
    `tenant` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_discount_type_created` (`created` ASC) VISIBLE,
    INDEX `idx_discount_type_updated` (`updated` ASC) VISIBLE,
    INDEX `idx_discount_type_tenantId` (`tenant` ASC) VISIBLE,
    INDEX `idx_discount_type_active` (`active` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `workshop`.`operation_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshop`.`operation_type` (
    `id` VARCHAR(50) NOT NULL,
    `name` VARCHAR(150) NOT NULL,
    `description` VARCHAR(300) NULL DEFAULT NULL,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `active` INT NOT NULL DEFAULT '1',
    `tenant` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_operation_type_created` (`created` ASC) VISIBLE,
    INDEX `idx_operation_type_updated` (`updated` ASC) VISIBLE,
    INDEX `idx_operation_type_tenant_id` (`tenant` ASC) INVISIBLE,
    INDEX `idx_operation_type_active` (`active` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `workshop`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshop`.`product` (
    `id` VARCHAR(50) NOT NULL,
    `code` VARCHAR(50) NOT NULL,
    `name` VARCHAR(150) NOT NULL,
    `description` VARCHAR(300) NULL DEFAULT NULL,
    `minimal_amount` INT NOT NULL DEFAULT '0',
    `sale_price` DOUBLE NOT NULL,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `active` INT NOT NULL DEFAULT '1',
    `tenant` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uq_product_code` (`code` ASC) VISIBLE,
    INDEX `idx_product_created` (`created` ASC) VISIBLE,
    INDEX `idx_product_updated` (`updated` ASC) VISIBLE,
    INDEX `idx_product_tenant_id` (`tenant` ASC) VISIBLE,
    INDEX `idx_product_active` (`active` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `workshop`.`provider`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshop`.`provider` (
    `id` VARCHAR(50) NOT NULL,
    `code` VARCHAR(50) NOT NULL,
    `name` VARCHAR(150) NOT NULL,
    `contact` VARCHAR(150) NULL DEFAULT NULL,
    `tax_id` VARCHAR(50) NULL DEFAULT NULL,
    `description` VARCHAR(300) NULL DEFAULT NULL,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `active` INT NOT NULL DEFAULT '1',
    `tenant` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uq_provider_code` (`code` ASC) VISIBLE,
    INDEX `idx_provider_created` (`created` ASC) VISIBLE,
    INDEX `idx_provider_tax_id` (`tax_id` ASC) VISIBLE,
    INDEX `idx_provider_updated` (`updated` ASC) VISIBLE,
    INDEX `idx_provider_tenant_id` (`tenant` ASC) VISIBLE,
    INDEX `idx_provider_active` (`active` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `workshop`.`provider_invoice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshop`.`provider_invoice` (
    `id` VARCHAR(50) NOT NULL,
    `provider_id` VARCHAR(50) NULL DEFAULT NULL,
    `suffix` VARCHAR(30) NOT NULL,
    `number` VARCHAR(100) NOT NULL,
    `image_url` VARCHAR(250) NULL DEFAULT NULL,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `tenant` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_provider_invoice_created` (`created` ASC) VISIBLE,
    INDEX `idx_provider_invoice_updated` (`updated` ASC) VISIBLE,
    INDEX `IX_provider_invoice_provider_id` (`provider_id` ASC) VISIBLE,
    INDEX `idx_provider_invoice_number` (`suffix` ASC, `number` ASC) VISIBLE,
    INDEX `idx_provider_invoice_tenant_id` (`tenant` ASC) VISIBLE,
    CONSTRAINT `FK_provider_invoice_provider_provider_id`
    FOREIGN KEY (`provider_id`)
    REFERENCES `workshop`.`provider` (`id`)
    ON DELETE RESTRICT)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `workshop`.`inventory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshop`.`inventory` (
    `id` VARCHAR(50) NOT NULL,
    `product_id` VARCHAR(50) NULL DEFAULT NULL,
    `operation_type_id` VARCHAR(50) NULL DEFAULT NULL,
    `provider_invoice_id` VARCHAR(50) NULL DEFAULT NULL,
    `amount` DOUBLE NOT NULL DEFAULT '1',
    `unit_price` DOUBLE NOT NULL,
    `discount_type_id` VARCHAR(50) NULL DEFAULT NULL,
    `discount_value` DOUBLE NOT NULL,
    `total` DOUBLE NOT NULL,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `tenant` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_inventory_created` (`created` ASC) VISIBLE,
    INDEX `idx_inventory_updated` (`updated` ASC) VISIBLE,
    INDEX `IX_inventory_discount_type_id` (`discount_type_id` ASC) VISIBLE,
    INDEX `IX_inventory_operation_type_id` (`operation_type_id` ASC) VISIBLE,
    INDEX `IX_inventory_product_id` (`product_id` ASC) VISIBLE,
    INDEX `IX_inventory_provider_invoice_id` (`provider_invoice_id` ASC) VISIBLE,
    INDEX `idx_inventory_tenant_id` (`tenant` ASC) VISIBLE,
    CONSTRAINT `FK_inventory_discount_type_discount_type_id`
    FOREIGN KEY (`discount_type_id`)
    REFERENCES `workshop`.`discount_type` (`id`)
    ON DELETE RESTRICT,
    CONSTRAINT `FK_inventory_operation_type_operation_type_id`
    FOREIGN KEY (`operation_type_id`)
    REFERENCES `workshop`.`operation_type` (`id`)
    ON DELETE RESTRICT,
    CONSTRAINT `FK_inventory_product_product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `workshop`.`product` (`id`)
    ON DELETE RESTRICT,
    CONSTRAINT `FK_inventory_provider_invoice_provider_invoice_id`
    FOREIGN KEY (`provider_invoice_id`)
    REFERENCES `workshop`.`provider_invoice` (`id`)
    ON DELETE RESTRICT)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `workshop`.`tenant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshop`.`tenant` (
    `id` VARCHAR(50) NOT NULL,
    `code` VARCHAR(50) NOT NULL,
    `name` VARCHAR(150) NOT NULL,
    `active` INT NOT NULL DEFAULT 1,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_bin;


-- -----------------------------------------------------
-- Table `workshop`.`car_brand`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workshop`.`car_brand` (
    `id` VARCHAR(50) NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `description` VARCHAR(300) NULL,
    `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `active` INT NOT NULL DEFAULT 1,
    `tenant` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_car_brand_name` (`name` ASC) VISIBLE,
    INDEX `idx_car_brand_created` (`created` ASC) VISIBLE,
    INDEX `idx_car_brand_active` (`active` ASC) INVISIBLE,
    INDEX `idx_car_brand_tenant_id` (`tenant` ASC) VISIBLE)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
