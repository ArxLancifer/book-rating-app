## Exception handling

I believe a centralized place for handling exceptions is the right approach.
Using a GlobalExceptionHandler lets the application catch errors thrown anywhere
and delegate their handling to that single component.

## Fail early

In BookReviewService the method findDetailedBookReview before attempting to request the external book service
first it checks if the given book id has any related reviews and if not we throw an NotFoundException early
in order prevent unnecessary calls to the external API.

## Caching
For simple use cases, I prefer using Spring Cache because it provides a clean, convenient, and
efficient way to manage frequently accessed data. In this case, I chose to apply caching at the external
API service layer using dynamic cache keys such as title, id, and page to prevent redundant network calls.
I limited caching to the external API responses to avoid serving stale data for book reviews, as
I expect users to access and update reviews more frequently.

## Invalidate Caching Mechanism
To prevent having cached data for a long time I added a scheduled cron job that invalidate all cached books
at a configurable time (see application.yml, property: clean-book-cache-cron).

## Testing
I added e2e tests for controllers using TestContainers and WireMock to avoid external API calls in 
test environment.

## Migration
I included Flyway migration to support future database migrations and keep consistency within the schemas. 

## Architecture
I chose to implement a relatively simple but well-organized architecture and project structure, given the
simplicity of the requirements. At the same time, I introduced abstraction through interfaces and 
implementations to allow for future extensions and refactoring. However, if the application were to grow in a
less manageable way, a more robust approach would be preferable, such as package-by-feature organization
or a hexagonal architecture with clearly defined core, domain, ports, and adapters. 