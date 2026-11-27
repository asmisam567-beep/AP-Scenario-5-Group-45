# Scenario 5 tool ownership (to be implemented by group members)

The `main` branch is the shared base project. It intentionally contains **no implementations or registrations** for the five conversational tools. Each member should create their assigned controller, service, and tests on their own branch.

| Assignment | Branch name | Suggested controller | Suggested service |
|---|---|---|---|
| Tool 1 – Search Books | `tool-1-search-books` | `BookSearchController` | `BookSearchService` |
| Tool 2 – Suggest Price | `tool-2-suggest-price` | `PriceSuggestionController` | `PriceSuggestionService` |
| Tool 3 – Find Listings | `tool-3-find-listings` | `ListingSearchController` | `ListingSearchService` |
| Tool 4 – Create Listing | `tool-4-create-listing` | `ListingCreationController` | `ListingCreationService` |
| Tool 5 – Express Interest | `tool-5-express-interest` | `InterestController` | `InterestService` |

## Integration rule

Each member should add only their own controller/service/tests and submit a pull request. The integrator coordinates the shared `TextbooksModule.java` change that registers all five controllers in `tools()`. Do not have all members edit that file independently.

Shared domain classes and rules already in `main` include `TextbookService`, `TextbookRules`, `TextbookExceptions`, `Book`, `Listing`, `ListingRequest`, and `Interest`. Reuse these rather than duplicating business rules.
