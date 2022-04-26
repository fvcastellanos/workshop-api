package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.enumeration.InvoiceStatus;
import net.cavitos.workshop.domain.model.web.Invoice;
import net.cavitos.workshop.domain.model.web.common.CommonContact;
import net.cavitos.workshop.model.entity.ContactEntity;
import net.cavitos.workshop.model.entity.InvoiceEntity;
import net.cavitos.workshop.web.controller.InvoiceController;

import static java.util.Objects.nonNull;
import static net.cavitos.workshop.factory.DateTimeFactory.buildStringFromInstant;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class InvoiceTransformer {

    private InvoiceTransformer() {
    }

    public static Invoice toWeb(final InvoiceEntity entity) {

        final var selfLink = linkTo(methodOn(InvoiceController.class)
                .getById(entity.getId()))
                .withSelfRel();

        final var status = InvoiceStatus.of(entity.getStatus());

        final var invoice = new Invoice();
        invoice.setType(entity.getType());
        invoice.setNumber(entity.getNumber());
        invoice.setStatus(status.name());
        invoice.setImageUrl(entity.getImageUrl());
        invoice.setInvoiceDate(buildStringFromInstant(entity.getInvoiceDate()));
        invoice.setEffectiveDate(buildStringFromInstant(entity.getEffectiveDate()));

        if (nonNull(entity.getContactEntity())) {

            invoice.setContact(buildCommonContact(entity.getContactEntity()));
        }

        invoice.add(selfLink);

        return  invoice;
    }

    // ----------------------------------------------------------------------------------------------------

    private static CommonContact buildCommonContact(final ContactEntity entity) {

        final var contact = new CommonContact();
        contact.setType(entity.getType());
        contact.setCode(entity.getCode());
        contact.setName(entity.getName());

        return contact;
    }
    
}