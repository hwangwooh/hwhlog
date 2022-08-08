package com.hawng.hawng.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@Getter
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    private final String code;
    public final String message;
    public final Map<String, String> validation;



    public void addValidation(String field, String defaultMessage) {
     //   ValidationTuple tuple = new ValidationTuple(field, defaultMessage);
        this.validation.put(field, defaultMessage);

    }

    @Builder
    public ErrorResponse(String code, String message,Map<String,String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation != null ? validation : new HashMap<>();
    }
    //    @RequiredArgsConstructor
//    private class ValidationTuple{
//        private final String fieldName;
//        public final String errorMessage;
//    }
}
