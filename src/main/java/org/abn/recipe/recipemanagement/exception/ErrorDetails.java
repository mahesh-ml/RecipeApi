package org.abn.recipe.recipemanagement.exception;

import java.time.LocalDateTime;


public record ErrorDetails(LocalDateTime timeStamp, String message, String details) {
}
