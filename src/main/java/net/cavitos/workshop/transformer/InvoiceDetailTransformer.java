package net.cavitos.workshop.transformer;

import com.google.common.collect.ImmutableList;
import net.cavitos.workshop.domain.model.type.ProductType;
import net.cavitos.workshop.domain.model.web.InvoiceDetail;
import net.cavitos.workshop.domain.model.web.common.CommonProduct;
import net.cavitos.workshop.model.entity.InvoiceDetailEntity;
import net.cavitos.workshop.web.controller.InvoiceDetailController;
import net.cavitos.workshop.web.controller.ProductController;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class InvoiceDetailTransformer {

    private InvoiceDetailTransformer() {
    }

    public static InvoiceDetail toWeb(InvoiceDetailEntity entity) {

        final var workOrderEntity = entity.getWorkOrderEntity();
        final var productEntity = entity.getProductEntity();

        final var productType = ProductType.of(productEntity.getType())
                .name();

        final var product = new CommonProduct();
        product.setCode(productEntity.getCode());
        product.setName(productEntity.getName());
        product.setType(productType);

        final var detail = new InvoiceDetail();
        detail.setQuantity(entity.getQuantity());
        detail.setUnitPrice(entity.getUnitPrice());
        detail.setTotal(entity.getQuantity() * entity.getUnitPrice());
        detail.setProduct(product);

        if (Objects.nonNull(workOrderEntity)) {

            detail.setWorkOrderNumber(workOrderEntity.getNumber());
        }

        detail.add(buildLinks(entity));

        return detail;
    }

    private static List<Link> buildLinks(InvoiceDetailEntity entity) {

        final var id = entity.getId();
        final var invoiceId = entity.getInvoiceEntity()
                .getId();

        final var updateLink = linkTo(methodOn(InvoiceDetailController.class)
                .update(invoiceId, id, null, null))
                .withRel("update")
                .withType("PUT");

        final var deleteLink = linkTo(methodOn(InvoiceDetailController.class)
                .update(invoiceId, id, null, null))
                .withRel("delete")
                .withType("DELETE");

        return ImmutableList.of(
                updateLink,
                deleteLink
        );
    }
}
