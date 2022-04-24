package net.cavitos.workshop.domain.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidator implements ConstraintValidator<Date, String> {

    private final String DATE_PATTERN = "^\\d{2}/\\d{2}/\\d{4}$";
    private final Pattern PATTERN = Pattern.compile(DATE_PATTERN);

    @Override
    public void initialize(final Date constraintAnnotation) {

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext constraintValidatorContext) {

        if (Objects.isNull(value)) {

            return false;
        }

        try {

            LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException exception) {

            return false;
        }
    }
}
