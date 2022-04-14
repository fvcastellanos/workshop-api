package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.web.CarLine;
import net.cavitos.workshop.domain.model.web.response.LinkResponse;
import net.cavitos.workshop.domain.model.web.response.ResourceResponse;
import net.cavitos.workshop.model.entity.CarLineEntity;

import static net.cavitos.workshop.web.controller.Route.CAR_BRANDS_RESOURCE;
import static net.cavitos.workshop.web.controller.Route.CAR_LINES_RESOURCE;

public final class CarLineTransformer {

    private CarLineTransformer() {
    }

    public static ResourceResponse<CarLine> toWeb(final CarLineEntity carLineEntity) {

        final var linkResponse = new LinkResponse();
        linkResponse.setSelf(CAR_BRANDS_RESOURCE + "/" + carLineEntity.getCarBrand().getId() + CAR_LINES_RESOURCE +
                "/" + carLineEntity.getId());

        final var carLine = new CarLine();
        carLine.setName(carLineEntity.getName());
        carLine.setDescription(carLineEntity.getDescription());
        carLine.setActive(carLineEntity.getActive());

        final var resource = new ResourceResponse<CarLine>();
        resource.setEntity(carLine);
        resource.setLinks(linkResponse);

        return resource;
    }
}
