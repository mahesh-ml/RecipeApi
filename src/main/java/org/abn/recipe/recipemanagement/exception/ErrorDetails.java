package org.abn.recipe.recipemanagement.exception;

import java.util.Date;


public record ErrorDetails (Date timeStamp, String message,String details ){
}
