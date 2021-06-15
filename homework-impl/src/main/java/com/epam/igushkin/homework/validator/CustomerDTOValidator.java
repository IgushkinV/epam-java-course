package com.epam.igushkin.homework.validator;

import com.epam.igushkin.homework.dto.CustomerDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class CustomerDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CustomerDTO customerDTO = (CustomerDTO) o;
        String regexPhone = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{4,20}$";
        boolean mathing = Pattern.matches(regexPhone, customerDTO.getPhone());
        if (!mathing) {
            errors.reject("phone", "Номер телефона в запросе не прошел проверку.");
        }



    }
}
