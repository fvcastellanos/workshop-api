package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.web.CarBrand;
import net.cavitos.workshop.domain.model.web.response.LinkResponse;
import net.cavitos.workshop.domain.model.web.response.ResourceResponse;
import net.cavitos.workshop.model.entity.CarBrandEntity;

import static net.cavitos.workshop.web.controller.Route.CAR_BRANDS_RESOURCE;

public final class CarBrandTransformer {

    private CarBrandTransformer() {
    }

    public static ResourceResponse<CarBrand> toWeb(final CarBrandEntity carBrandEntity) {

        var link = new LinkResponse();
        link.setSelf(CAR_BRANDS_RESOURCE + "/" + carBrandEntity.getId());

        var carBrand = new CarBrand();
        carBrand.setName(carBrandEntity.getName());
        carBrand.setDescription(carBrandEntity.getDescription());
        carBrand.setActive(carBrandEntity.getActive());

        var response = new ResourceResponse<CarBrand>();
        response.setEntity(carBrand);
        response.setLinks(link);

        return response;
    }

    public static CarBrandEntity toModel(final String tenant, final String carBrandId, final CarBrand carBrand) {

        return CarBrandEntity.builder()
                .id(carBrandId)
                .tenant(tenant)
                .name(carBrand.getName())
                .active(carBrand.getActive())
                .description(carBrand.getDescription())
                .build();
    }
}
