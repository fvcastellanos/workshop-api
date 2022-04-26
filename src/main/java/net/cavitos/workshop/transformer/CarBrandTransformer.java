package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.status.ActiveStatus;
import net.cavitos.workshop.domain.model.web.CarBrand;
import net.cavitos.workshop.model.entity.CarBrandEntity;
import net.cavitos.workshop.web.controller.CarBrandController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class CarBrandTransformer {

    private CarBrandTransformer() {
    }

    public static CarBrand toWeb(final CarBrandEntity carBrandEntity) {

        final var selfLink = linkTo(methodOn(CarBrandController.class)
                .getById(carBrandEntity.getId()))
                .withSelfRel();

        final var carBrandLines = linkTo(methodOn(CarBrandController.class)
                .getById(carBrandEntity.getId()))
                .slash("lines")
                .withRel("carBrandLines");

        final var active = ActiveStatus.of(carBrandEntity.getActive())
                .name();

        final var carBrand = new CarBrand();
        carBrand.setName(carBrandEntity.getName());
        carBrand.setDescription(carBrandEntity.getDescription());
        carBrand.setActive(active);

        carBrand.add(selfLink, carBrandLines);

        return carBrand;
    }
}
