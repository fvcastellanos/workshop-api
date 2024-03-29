package net.cavitos.workshop.transformer;

import net.cavitos.workshop.domain.model.status.ActiveStatus;
import net.cavitos.workshop.domain.model.web.CarLine;
import net.cavitos.workshop.model.entity.CarLineEntity;
import net.cavitos.workshop.web.controller.CarBrandController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public final class CarLineTransformer {

    private CarLineTransformer() {
    }

    public static CarLine toWeb(final CarLineEntity carLineEntity) {

        var carBrandEntity = carLineEntity.getCarBrand();

        final var selfLink = linkTo(methodOn(CarBrandController.class)
                .getLineById(carBrandEntity.getId(), carLineEntity.getId(), null))
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
