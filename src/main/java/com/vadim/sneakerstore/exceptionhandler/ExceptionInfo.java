package com.vadim.sneakerstore.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExceptionInfo {

    @JsonProperty
    private String message;

    @JsonProperty
    private int code;

}
