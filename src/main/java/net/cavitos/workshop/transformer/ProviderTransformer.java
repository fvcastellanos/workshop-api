package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.web.Contact;
import net.cavitos.workshop.domain.model.web.response.LinkResponse;
import net.cavitos.workshop.domain.model.web.response.ResourceResponse;
import net.cavitos.workshop.model.entity.ProviderEntity;

import static net.cavitos.workshop.web.controller.Route.PROVIDERS_RESOURCE;

public final class ProviderTransformer {

    private ProviderTransformer() {
    }

    public static ResourceResponse<Contact> toWeb(final ProviderEntity providerEntity) {

        final var links = new LinkResponse();
        links.setSelf(PROVIDERS_RESOURCE + "/" + providerEntity.getId());

        final var provider = new Contact();
        provider.setCode(providerEntity.getCode());
        provider.setName(providerEntity.getName());
        provider.setDescription(providerEntity.getDescription());
        provider.setContact(providerEntity.getContact());
        provider.setTaxId(providerEntity.getTaxId());
        provider.setActive(providerEntity.getActive());

        final var resource = new ResourceResponse<Contact>();
        resource.setEntity(provider);
        resource.setLinks(links);

        return resource;
    }
}
