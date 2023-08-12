package net.cavitos.workshop.common.assertion;

import net.cavitos.workshop.domain.exception.BusinessException;
import net.cavitos.workshop.domain.model.web.ProductCategory;
import org.assertj.core.api.ThrowableAssert;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public final class CommonAssertions {

    public static void assertBusinessException(ThrowableAssert.ThrowingCallable shouldRaiseThrowable,
                                               final String message) {

        assertThatThrownBy(shouldRaiseThrowable)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Product Category not found")
                .extracting(throwable -> (BusinessException) throwable)
                .hasFieldOrPropertyWithValue("httpStatus", HttpStatus.NOT_FOUND);

    }

    public static void assertBusinessException(ThrowableAssert.ThrowingCallable shouldRaiseThrowable,
                                               final String message,
                                               final HttpStatus httpStatus) {

        assertThatThrownBy(shouldRaiseThrowable)
                .isInstanceOf(BusinessException.class)
                .hasMessage(message)
                .extracting(throwable -> (BusinessException) throwable)
                .hasFieldOrPropertyWithValue("httpStatus", httpStatus);
    }

    public static void assertBusinessException(ThrowableAssert.ThrowingCallable shouldRaiseThrowable,
                                               final String message,
                                               final HttpStatus httpStatus,
                                               final String fieldName) {

        assertThatThrownBy(shouldRaiseThrowable)
                .isInstanceOf(BusinessException.class)
                .hasMessage(message)
                .extracting(throwable -> (BusinessException) throwable)
                .hasFieldOrPropertyWithValue("httpStatus", httpStatus)
                .hasFieldOrPropertyWithValue("field", fieldName);
    }

    public static void assertHateoasLink(final RepresentationModel<?> representationModel,
                                         final String rel,
                                         final String href) {

        assertThat(representationModel.getLink(rel))
                .isNotEmpty()
                .get()
                .hasFieldOrPropertyWithValue("href", href)
                .extracting(link -> link.getRel().value())
                .isEqualTo(rel);
    }
}
