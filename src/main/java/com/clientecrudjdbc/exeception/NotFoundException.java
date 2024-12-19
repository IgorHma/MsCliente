package com.clientecrudjdbc.exeception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    private static final long serialVersionUID = 3589936453612375546L;

    @Getter
    private String resourceName;

    public NotFoundException(String resourceName) {
        super(resourceName + " not found");
        this.resourceName = resourceName;
    }

}
