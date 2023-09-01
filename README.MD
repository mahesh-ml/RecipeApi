**Recipe Management API**

This is a RESTful API built using Spring Boot that provides endpoints for managing recipes.

**Technology Used**

Spring Boot
Java 17
H2 (local)
Maven


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

**How to Run the Application**

Clone the repository.
Open the project in an IDE.
Build the project using Maven.
Run the RecipeApplication class.

The application will start running on http://localhost:6060.



