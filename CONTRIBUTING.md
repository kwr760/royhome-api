# Contributing to RoyHome API

Thanks for helping improve this project! This document explains how to get the project running locally, how to run tests and linters, and what to include in a good pull request.

## Quickstart (Windows / PowerShell)

1. Install JDK 17 (Eclipse Temurin or Adoptium recommended).
2. Install Docker Desktop and start it (Testcontainers needs Docker).
3. Open a PowerShell terminal in the repository root.

Run the app in development profile:

```powershell
./gradlew --no-daemon -Dspring.profiles.active=dev bootRun
```

## Running tests

- Run the full test suite (includes Testcontainers checks):

```powershell
./gradlew --no-daemon test
```

- Run a single test class (faster):

```powershell
./gradlew --no-daemon test --tests "fully.qualified.TestClassName"
```

Notes:
- Docker must be running for Testcontainers tests.
- If a Spring-context integration test fails due to Flyway or DB dialect issues, try the container-only test or run unit tests only.

## Code style and static analysis

- Detekt is configured for Kotlin static analysis. To run locally:

```powershell
./gradlew detekt
```

- KtLint is also used; run:

```powershell
./gradlew ktlintFormat
```

CI will run these checks on pull requests.

## Making changes & submitting a PR

1. Create a feature branch from `main` (or the appropriate base branch):

```powershell
git checkout -b feat/your-feature
```

2. Keep commits small and focused. Use descriptive commit messages (see below).
3. Run tests and linters locally before pushing.
4. Push and open a PR. In your PR description include:
   - What the change does and why.
   - Testing notes and required environment (e.g., Docker running).
   - Any follow-up tasks or migration steps.

## Commit message guidelines

- Use the conventional format: `type(scope): short description` (examples below).
- Types: feat, fix, docs, chore, refactor, style, test

Examples:
```
feat(api): add OpenAPI endpoint for resume
fix(db): ensure flyway migration is idempotent
chore(deps): bump spring-boot to 3.5.x
```

## PR review checklist

- [ ] Code builds and tests pass locally
- [ ] Linting / Detekt issues addressed
- [ ] Changes documented (README / code comments)
- [ ] No sensitive data or secrets in the PR

## Help and communication

Open an issue for anything unclear or to propose larger changes. For quick questions, mention me in the PR or create a draft PR.

Thank you for contributing!
