package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.web.Contact;
import net.cavitos.workshop.domain.model.web.response.LinkResponse;
import net.cavitos.workshop.domain.model.web.response.ResourceResponse;
import net.cavitos.workshop.model.entity.ContactEntity;

import static net.cavitos.workshop.web.controller.Route.CONTACTS_RESOURCE;

public final class ContactTransformer {

    private ContactTransformer() {
    }

    public static ResourceResponse<Contact> toWeb(final ContactEntity contactEntity) {

        final var links = new LinkResponse();
        links.setSelf(CONTACTS_RESOURCE + "/" + contactEntity.getId());

        final var provider = new Contact();
        provider.setCode(contactEntity.getCode());
        provider.setType(contactEntity.getType());
        provider.setName(contactEntity.getName());
        provider.setDescription(contactEntity.getDescription());
        provider.setContact(contactEntity.getContact());
        provider.setTaxId(contactEntity.getTaxId());
        provider.setActive(contactEntity.getActive());

        final var resource = new ResourceResponse<Contact>();
        resource.setEntity(provider);
        resource.setLinks(links);

        return resource;
    }
}
