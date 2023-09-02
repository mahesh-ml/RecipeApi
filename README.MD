**Recipe Management API**

   The API allows users to manage their favourite recipes. It  allows adding, updating, removing and fetching recipes.
   Additionally users should be able to filter available recipes based on different  criterias:
   It also supports Text search within the instructions.



**Tech Stack**

    Spring Boot 3.1.3
    Java 17
    Maven
    Docker
    Postgres docker image

**Endpoints**

         **GET /receipes Get all recipes**

         Summary: Get a list of all recipes.
         Response:
         Status code: 200
         Description: List of all recipes
         Content: application/json
         Schema: RecipeDto


         **POST /receipes: Create a new recipe**

         Summary: Create a new recipe.
         Request body: RecipeDto
         Response:
         Status code: 201
         Description: Recipe created successfully

   
         **GET /recipes/{recipeId}: Get a recipe by ID**
   
         Summary: Get a recipe by ID.
         Path parameter: recipeId
         Response:
         Status code: 200
         Description: Recipe found
         Content: application/json
         Schema: RecipeDto
         Status code: 404
         Description: Recipe not found
   
         **PUT /recipes/{recipeId}: Update a recipe by ID**
   
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
   
   
         **DELETE /recipes/{recipeId}: Delete a recipe by ID**
   
         Summary: Delete a recipe by ID.
         Path parameter: recipeId
         Response:
         Status code: 200
         Description: Recipe deleted successfully
         Status code: 404
         Description: Recipe not found
   
   
         
         **POST /recipes/search: Search recipes by criteria**
   
         Summary: Search recipes by criteria.
         Request body: List<SearchCriteria>
         Response:
         Status code: 200
         Description: List of recipes matching the criteria
         Content: application/json
         Schema: RecipeDto
   
   
         **GET /search/instructions: Search recipes with in instructions**
   
         Summary: Search recipes within instructions.
         Query parameter: q
         Response:
         Status code: 200
         Description: List of recipes matching the criteria
         Content: application/json
         Schema: RecipeDto

**How to Run the Application Locally**

*    Clone the repository from github URL https://github.com/mahesh-ml/RecipeApi/.
*    Import the project to  Intellij or Eclipse or any java based IDE.
*    Build the project using Maven by running **mvn clean install.**
*    Run the RecipeApplication by running **mvn spring-boot:run**
*    Please note that project needs following environment/tools - Java 17.
*    Since all the integration tests are run via postgres database from test container image,  Docker must be installed in the machine.

The application will start running on http://localhost:6060.
The default port can be changed from application.properties.

OpenAPI Link ->
      http://localhost:6060/swagger-ui/index.html


Improvements - 

*    Deployment related details are not implemented yet such as docker-compose and k8s related files. 
*    Security for role based access can be implemented .
*    Search can be more extensive.