**Recipe Management API**

The Recipe Management API is a tool that allows users to manage their favorite recipes. 
It provides endpoints for adding, updating, removing, and fetching recipes, 
as well as filtering available recipes based on different criteria. 
Additionally, it supports text search within the instructions.

**Tech Stack**

The Recipe Management API is built using the following technologies:


    Spring Boot 3.1.3
    Java 17
    Maven
    H2 for local storage
    Docker
    Postgres docker image for integration tests

**Endpoints**
The postman collections can be found in /src/main/resources/RecipeApi.postman_collection.json
and api docs avaialble at  /src/main/resources/api-docs.json


The following endpoints are available in the Recipe Management API:

        1. **GET /receipes Get all recipes**

         Summary: Get a list of all recipes.

         Response:
         Status code: 200
         Description: List of all recipes
         Content: application/json
         Schema: RecipeDto


       2.  **POST /receipes: Create a new recipe**

         Summary: Create a new recipe.
         Request body: RecipeDto

         Response:
         Status code: 201
         Description: Recipe created successfully

   
       3. **GET /recipes/{recipeId}: Get a recipe by ID**
   
         Summary: Get a recipe by ID.
         Path parameter: recipeId

         Response:
         Status code: 200
         Description: Recipe found
         Content: application/json
         Schema: RecipeDto
         Status code: 404
         Description: Recipe not found
   
       4.  **PUT /recipes/{recipeId}: Update a recipe by ID**
   
         Summary: Update a recipe by ID.
         Path parameter: recipeId
         Request body: RecipeDto

         Response:
         Status code: 200
         Description: Recipe updated successfully
         Content: application/json
         Schema: RecipeDto
         Status code: 404
         Description: Recipe not found
   
   
       5.  **DELETE /recipes/{recipeId}: Delete a recipe by ID**
   
         Summary: Delete a recipe by ID.
         Path parameter: recipeId

         Response:
         Status code: 200
         Description: Recipe deleted successfully
         Status code: 404
         Description: Recipe not found
   
   
         
       6.  **POST /recipes/search: Search recipes by criteria**
   
         Summary: Search recipes by criteria.
         Request body: List<SearchCriteria>

         Response:
         Status code: 200
         Description: List of recipes matching the criteria
         Content: application/json
         Schema: RecipeDto
   
   
       7.  **GET /search/instructions: Search recipes with in instructions**
   
         Summary: Search recipes within instructions.
         Query parameter: q

         Response:
         Status code: 200
         Description: List of recipes matching the criteria
         Content: application/json
         Schema: RecipeDto

**How to Run the Application Locally**

* Clone the repository from github URL https://github.com/mahesh-ml/RecipeApi/.
* Import the project to Intellij or Eclipse or any java based IDE.
* Build the project using Maven by running **mvn clean install.**
* Run the RecipeApplication by running **mvn spring-boot:run**
* Please note that project needs following environment/tools - Java 17.
* The default database at local is H2 which stores the recipe data, Any database can be configured by including relevant dependencies and configurations.
* Since all the integration tests are run via postgres database from test container image, Docker must be installed in
  the machine.

The application will start running on http://localhost:6060.
The default port can be changed from application.properties.

OpenAPI spec Link : 

 http://localhost:6060/swagger-ui/index.html

API docs : 

 http://localhost:6060/v3/api-docs



**Improvements -**

* Deployment related details are not implemented yet such as docker-compose and k8s related files.
* Security for role based access can be implemented .
* Search can be more extensive.