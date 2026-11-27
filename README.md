# ITS66704 – Scenario 5: Textbook Exchange Agent (Group 45)

This is the **shared main/base project** for Group 45. It contains the application framework, Scenario 5 data, shared textbook domain rules and services, tests, configuration, memory, guardrails, and available development trace evidence. **The five Scenario 5 conversational tools are intentionally not implemented or registered here.** Each member adds their assigned tool on their own branch.

## Requirements

- Java 21
- Maven
- Ollama running locally with the model configured in `config.properties`

## Build and test

From the project root:

```bash
mvn clean verify
```

## Run the application

```bash
mvn -q exec:java -Dexec.mainClass=my.edu.taylors.ap.App
```

## Five-tool ownership

The five tools are **not included yet**. Each member creates their assigned controller, service, and tests on their feature branch. See [`docs/TOOL_DIVISION.md`](docs/TOOL_DIVISION.md) for the assignment and class names.

| Tool | Assigned function | Suggested controller | Suggested service |
|---|---|---|---|
| Tool 1 | Search Books | `BookSearchController` | `BookSearchService` |
| Tool 2 | Suggest Price | `PriceSuggestionController` | `PriceSuggestionService` |
| Tool 3 | Find Listings | `ListingSearchController` | `ListingSearchService` |
| Tool 4 | Create Listing | `ListingCreationController` | `ListingCreationService` |
| Tool 5 | Express Interest | `InterestController` | `InterestService` |

## GitHub collaboration

1. The repository owner uploads this base project to `main`.
2. Each member clones the repository and creates their assigned feature branch.
3. Each member implements only their assigned tool and tests, committing under their own GitHub account.
4. Each member pushes their branch and opens a pull request into `main`.
5. The integrator reviews and merges each pull request, coordinates registration of all five controllers in `TextbooksModule.java`, and runs `mvn clean verify`.

Branch names and detailed instructions are in [`docs/GITHUB_WORKFLOW.md`](docs/GITHUB_WORKFLOW.md).

## Important shared files

`TextbooksModule.java` is the central registration point. It intentionally returns an empty tool list in this base project. The integrator should coordinate the final registration of the five contributed controllers after reviewing the branches. `TextbookService`, `TextbookRules`, `TextbookExceptions`, the domain records, and guardrails are shared code; reuse them rather than duplicating business rules.

## Contribution integrity

Each member should make a genuine, identifiable contribution to their assigned tool. Preserve real development traces; never fabricate or edit trace evidence to make it appear genuine.
