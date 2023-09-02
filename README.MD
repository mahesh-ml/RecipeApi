**Recipe Management API**
allows users to manage their favourite recipes. It  allows adding, updating, removing and fetching recipes.

Additionally users should be able to filter available recipes based on one or more of the following criteria:

1. Whether or not the dish is vegetarian
2. The number of servings
3. Specific ingredients (either include or exclude)
4. Text search within the instructions.
   For example, the API should be able to handle the following search requests:
   • All vegetarian recipes
   • Recipes that can serve 4 persons and have “potatoes” as an ingredient
   • Recipes without “salmon” as an ingredient that has “oven” in the instructions.


**Tech Stack**

    Spring Boot 3.1.3
    Java 17
    Maven
    Docker
    Postgres docker image

**Endpoints**

Create Recipe

    URL: /recipes
    
    Method: POST
    Request Body: RecipeDto
    Response Body: RecipeDto
    Response Status: 201 CREATED

Get All Recipes

    URL: /recipes
    
    Method: GET
    Response Body: List<RecipeDto>
    Response Status: 200 OK

Get Recipe by ID

    URL: /recipes/{recipeId}
    Method: GET
    Path Variable: recipeId
    Response Body: RecipeDto
    Response Status: 200 OK
    Error Response Status: 404 NOT FOUND

Update Recipe

    URL: /recipes/{recipeId}
    Method: PUT
    Path Variable: recipeId
    Request Body: RecipeDto
    Response Body: RecipeDto
    Response Status: 200 OK
    Error Response Status: 404 NOT FOUND

Delete Recipe

    URL: /recipes/{recipeId}
    
    Method: DELETE
    Path Variable: recipeId
    Response Body: String
    Response Status: 200 OK
    Error Response Status: 404 NOT FOUND

Search Recipes

    URL: /recipes/search

    Method: POST
    Request Body: List<SearchCriteria>
    Response Body: List<RecipeDto>
    Response Status: 200 OK


Search Recipes

    URL: /recipes/search/instructions

    Method: GET
    Request Param : String searchQuery
    Response Body: List<RecipeDto>
    Response Status: 200 OK

**How to Run the Application**

Clone the repository.
Open the project in an IDE.
Build the project using Maven.
Run the RecipeApplication class.

The application will start running on http://localhost:6060.



