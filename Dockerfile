# Base image
FROM openjdk:17-jdk
# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/RecipeManagementApi-0.0.1-SNAPSHOT.jar recipeApi.jar
# Expose the port on which your application will run
EXPOSE 6060
# Set the command to run your application
CMD ["java", "-jar", "recipeApi.jar"]