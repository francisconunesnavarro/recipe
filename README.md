# Recipe

This project uses Spring Boot.

-------------------------

## Architectural Decision

The layer architecture was chosen because it's the most coherent in this context, just a Rest API with a connection to the database.
Also, I tried to use a bit of the DDD concept, so that the infrastructure and domain layers do not have information from the DTOs of the API layer (if in
the future you'd like to have another way of interacting with the application, the domain is assured)

In addition, the following were chosen:

- Spring Boot & Spring Data as framework
- JUnit/Jupiter & Mockito as test libraries
- Liquibase as database schema manager
- Postgres as database
- Test containers for integration tests
- Lombok to decrease verbosity/boilerplate
- OpenApi & Swagger as API documentation

-------------------------
SERVER CONFIGURATION
-------------------------

- Install Java 17
- Install Docker

-------------------------

## Running the app locally

### Preparation

#### Build the images

All images will be built if they don't exist. In order to build the recipe image you have to first build the app. In order to do that you need to have
java and docker installed. Then you need to run

```shell
mvn clean package
```

### Run

To run the app locally using docker-compose use `docker-compose.yml` to launch the application.

You can launch it using IDEA, in this case you can just open the file and use ide controls.

If you don't use IDEA you can launch it using the terminal:

```shell
docker compose up
```

## Liquibase configuration

To run liquibase commands (dropAll, update, etc), fill in the Java Options "Environment Variables" field with:

```
DB_HOST=localhost:5432/recipe;DB_USER=recipedb;DB_PASS=admin
```

### Liquibase commands shortcuts

| Shortcut                          | Command line                                                             |
|-----------------------------------|--------------------------------------------------------------------------|
| liquibase: dropAll                | liquibase:dropAll -f pom.xml                                             |
| liquibase: dropAll & update-local | liquibase:dropAll liquibase:update -Dliquibase.contexts=local -f pom.xml |
| liquibase: update-local           | liquibase:update -Dliquibase.contexts=local -f pom.xml                   |

## Useful URLs

1. **Initial URL** - http://localhost:8080/api
2. Swagger - http://localhost:8080/api/swagger-ui/index.html#/
3. Contracts - http://localhost:8080/api/recipe-contract-specification.yaml

-------------------------

## Examples

- All vegetarian recipe: http://localhost:8080/api/recipes?mealType=VEGETARIAN
- Recipes that can serve 4 persons and have “potatoes” as an
  ingredient: http://localhost:8080/api/recipes?numberOfServings=4&searchIngredientName=Potatoes
- Recipes without “salmon” as an ingredient that has “oven” in the
  instructions: http://localhost:8080/api/recipes?searchIngredientName=salmon&isExcludeIngredient=true&instructions=oven
