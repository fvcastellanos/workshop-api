package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.web.brand.CarBrand;
import net.cavitos.workshop.domain.model.web.response.LinkResponse;
import net.cavitos.workshop.domain.model.web.response.ResourceResponse;
import net.cavitos.workshop.model.entity.CarBrandEntity;

import static net.cavitos.workshop.web.controller.Route.CAR_BRANDS_RESOURCE;

public final class CarBrandTransformer {

    private CarBrandTransformer() {
    }

    public static ResourceResponse<CarBrand> toWeb(final String tenant, final CarBrandEntity carBrandEntity) {

        var link = new LinkResponse();
        link.setSelf(CAR_BRANDS_RESOURCE + "/" + tenant + "/" + carBrandEntity.getId());

        var carBrand = new CarBrand();
        carBrand.setName(carBrandEntity.getName());
        carBrand.setDescription(carBrand.getDescription());

        var response = new ResourceResponse<CarBrand>();
        response.setContent(carBrand);
        response.setLinks(link);

        return response;
    }

    public static CarBrandEntity toModel(final String tenant, final String carBrandId, final CarBrand carBrand) {

        return CarBrandEntity.builder()
                .id(carBrandId)
                .tenant(tenant)
                .name(carBrand.getName())
                .description(carBrand.getDescription())
                .build();
    }
}
