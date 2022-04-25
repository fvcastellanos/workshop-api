package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.enumeration.ActiveStatus;
import net.cavitos.workshop.domain.model.web.CarLine;
import net.cavitos.workshop.model.entity.CarLineEntity;
import net.cavitos.workshop.web.controller.CarLineController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class CarLineTransformer {

    private CarLineTransformer() {
    }

    public static CarLine toWeb(final CarLineEntity carLineEntity) {

        var carBrandEntity = carLineEntity.getCarBrand();

        final var selfLink = linkTo(methodOn(CarLineController.class)
                .getById(carBrandEntity.getId(), carLineEntity.getId()))
                .withSelfRel();

        final var active = ActiveStatus.of(carLineEntity.getActive())
                .name();

        final var carLine = new CarLine();
        carLine.setName(carLineEntity.getName());
        carLine.setDescription(carLineEntity.getDescription());
        carLine.setActive(active);

        carLine.add(selfLink);

        return carLine;
    }
}
