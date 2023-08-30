package org.abn.recipe.recipemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String fieldName, String prefix , Long fieldValue) {
        super(String.format("%s not found with %s : %s", fieldName ,prefix, fieldValue));
    }
}
