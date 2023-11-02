package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.web.InventoryMovement;
import net.cavitos.workshop.domain.model.web.common.CommonProduct;
import net.cavitos.workshop.model.entity.InventoryEntity;
import net.cavitos.workshop.web.controller.InventoryMovementController;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class InventoryMovementTransformer {

    public static InventoryMovement toWeb(final InventoryEntity entity) {

        final var dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
                .withZone(ZoneId.systemDefault());

        final var product = entity.getProductEntity();
        final var invoiceDetail = entity.getInvoiceDetailEntity();

        final var selfLink = linkTo(methodOn(InventoryMovementController.class)
                .getById(entity.getId(), null))
                .withSelfRel();

        final var commonProduct = new CommonProduct();
        commonProduct.setCode(product.getCode());
        commonProduct.setName(product.getName());
        commonProduct.setType(product.getType());

        final var movement = new InventoryMovement();
        movement.setId(entity.getId());
        movement.setQuantity(entity.getQuantity());
        movement.setUnitPrice(entity.getUnitPrice());
        movement.setDiscountAmount(entity.getDiscountAmount());
        movement.setOperationType(entity.getOperationType());
        movement.setInvoiceDetailId(invoiceDetail.getId());
        movement.setOperationDate(dateFormatter.format(entity.getOperationDate()));
        movement.setDescription(entity.getDescription());
        movement.setProduct(commonProduct);

        movement.add(selfLink);

        return movement;
    }
}
