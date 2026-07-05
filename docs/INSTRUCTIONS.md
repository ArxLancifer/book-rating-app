# Running the project with Docker

_Note that Docker support is required to containerize and run the project_

To quickly run the database and application altogether just execute the bellow command from the root dir

  ```bash 
  docker compose -f infra/docker-compose.yml up --build --detach
  ```


If you want to run the application without Docker follow the instructions bellow.
Note that you require to have a PostgreSQL database running locally.
- Requirements: Java 21, Maven
- Build: mvn -DskipTests package
- Run (dev): mvn spring-boot:run
- Run (jar): java -jar target/book-rating-api-0.0.1-SNAPSHOT.jar
- Tests: mvn test (integration tests rely on Testcontainers and WireMock)
