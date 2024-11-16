package com.julianjupiter.csm.util;

import com.julianjupiter.csm.dto.ErrorMessageDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Julian Jupiter
 */
public class ValidatorUtil {
    private ValidatorUtil() {
    }

    public static List<ErrorMessageDto> validate(Validator validator, Object target, String objectName, MessageSource messageSource) {
        var dataBinder = new DataBinder(target, objectName);
        dataBinder.addValidators(validator);
        dataBinder.validate();
        var bindingResult = dataBinder.getBindingResult();

        if (!bindingResult.hasErrors()) {
            return List.of();
        }

        return bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> {
                    var fieldErrorCode = fieldError.getCode();
                    var field = fieldError.getField();
                    var resolveMessageCodes = bindingResult.resolveMessageCodes(fieldErrorCode);
                    var code = resolveMessageCodes[0] + "." + field;
                    var message = messageSource.getMessage(code, new Object[]{fieldError.getRejectedValue()}, Locale.ENGLISH);
                    return new ErrorMessageDto(field, message);
                })
                .toList();
    }

    public static List<ErrorMessageDto> validate(ValidatorFactory validatorFactory, Object target, String objectName, String field, MessageSource messageSource) {
        try (validatorFactory) {
            var validator = validatorFactory.getValidator();
            Set<ConstraintViolation<Object>> violations = validator.validateProperty(target, field);
            return violations.stream()
                    .map(constraintViolation -> {
                        var invalidValue = constraintViolation.getInvalidValue();
                        var annotation = constraintViolation.getConstraintDescriptor().getAnnotation()
                                .annotationType()
                                .getSimpleName();
                        var code = annotation + "." + objectName + "." + field;
                        var message = messageSource.getMessage(code, new Object[]{invalidValue}, Locale.ENGLISH);
                        return new ErrorMessageDto(field, message);
                    })
                    .toList();
        }
    }
}
