# Group 45 – GitHub branch workflow

## One-time setup by the repository owner

1. Create or open the Group 45 GitHub repository.
2. Upload this main/base project to the `main` branch (including `pom.xml`, `src`, `scenarios`, `config.properties`, `.gitignore`, `.github`, `docs`, and genuine `runtime/traces/*.jsonl` evidence).
3. Invite the other four members under **Settings → Collaborators**. Each member must accept.
4. Ask everyone to clone the same repository after the starter has been uploaded.

## Branch assignment

| Member | Tool | Suggested branch |
|---|---|---|
| Person 1 | Tool 1 – Search Books | `tool-1-search-books` |
| Person 2 | Tool 2 – Suggest Price | `tool-2-suggest-price` |
| Person 3 | Tool 3 – Find Listings | `tool-3-find-listings` |
| Person 4 | Tool 4 – Create Listing | `tool-4-create-listing` |
| Person 5 | Tool 5 – Express Interest | `tool-5-express-interest` |

Replace Person 1–5 with the actual group members in `TOOL_DIVISION.md`.

## Commands for each member

Run these commands once, replacing the branch name with the one assigned to you:

```bash
git clone https://github.com/asmisam567-beep/APAssignment-Scenario-5-Group-45.git
cd APAssignment-Scenario-5-Group-45
git switch -c tool-1-search-books
```

Create your assigned controller and service classes, implement your tool, and add its tests. Do not edit another member’s tool. Then run:

```bash
mvn clean verify
git status
git add <your-changed-files>
git commit -m "Implement Tool 1 - Search Books"
git push -u origin tool-1-search-books
```

Use your own assigned branch name and a commit message describing your actual work. Do not use `git add .` if it would include another member's or unrelated local changes; inspect `git status` first.

## Pull request and integration

1. On GitHub, open **Pull requests → New pull request**.
2. Set the base to `main` and compare to your feature branch.
3. Explain what you changed and which tests you ran.
4. The integrator reviews the diff and confirms the project still builds before merging.
5. After each merge, the integrator updates their local `main` and runs the full test suite:

```bash
git switch main
git pull origin main
mvn clean verify
```

## Avoiding merge conflicts

- Each member creates and owns their controller, service, and corresponding tests.
- `TextbooksModule.java` is shared and currently registers no Scenario 5 tools. The integrator should coordinate adding all five controller registrations after the individual branches are reviewed and merged.
- Do not duplicate or independently rewrite shared classes such as `TextbookService`, `TextbookRules`, `TextbookExceptions`, `Book`, `Listing`, `Interest`, or the privacy guardrail.
- If a shared-file change is necessary, discuss it before editing and include the reason in the pull request.
- Never force-push to `main` or overwrite another member's branch.
