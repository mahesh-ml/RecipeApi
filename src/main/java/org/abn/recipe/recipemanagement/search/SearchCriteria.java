package org.abn.recipe.recipemanagement.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String searchKey;
    private SearchOperation operation;
    private Object value;
}
