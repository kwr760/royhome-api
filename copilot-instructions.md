Project coding practices for Copilot and contributors

Purpose
- Provide concise, actionable coding, testing and CI guidelines for this Kotlin + Spring Boot (Gradle) project.

Language & tooling
- Use Kotlin 1.9.x language features conservatively; prefer idiomatic Kotlin (data classes, sealed interfaces, extension functions).
- Keep `sourceCompatibility` at Java 17 unless the repo owner approves an upgrade.
- Use the Gradle Kotlin DSL (`build.gradle.kts`) for builds and keep plugin versions centralized in the build script.

Repository structure
- Keep packages small and leant to feature areas (e.g., `net.royhome.api.controller`, `service`, `model`, `config`).
- Put Spring configuration classes under `config` and beans in `@Configuration` classes with clear names.

Dependency management
- Prefer managed dependencies from Spring Boot BOM. Pin direct dependencies only when required (e.g., springdoc version).
- Keep third-party libraries updated through a scheduled dependency bump PR and test in CI before merging.

Coding style
- Follow Kotlin style: explicit visibility modifiers for public APIs, prefer `val` over `var`, and avoid unnecessary `!!` operators.
- Keep functions small (<= 40 lines) and pure where possible.
- Use functional collection operations (`map`, `filter`, `fold`) when clearer than loops.

Testing
- Write unit tests with JUnit 5 and MockK for mocking. Keep tests fast and isolated.
- Add integration tests for critical flows using `@SpringBootTest` with test profiles and in-memory DBs when possible.
- Run `./gradlew clean test` locally and ensure CI runs the same tasks.

CI / GitHub Actions
- Use the existing `.github/workflows/pr-verification.yml` to run `./gradlew clean build` on PR branches.
- Upload test reports and code coverage artifacts (actions/upload-artifact@v4 is used here).
- Make sure CI uses Java 17 in `actions/setup-java@v3`.

API documentation
- Use `springdoc-openapi` for OpenAPI generation. Provide a single `OpenAPI` bean in `config` for global info.
- Keep old Swagger/Springfox config removed; do not mix generators.

Security & secrets
- Do not store secrets in `gradle.properties` or repo. Use GitHub Secrets and CI environment variables.

Gradle wrapper
- Keep the Gradle wrapper checked in. Upgrade Gradle on a dedicated branch and coordinate plugin updates (Kotlin Gradle plugin, SpotBugs, etc.).

Commit messages and PRs
- Use imperative commit messages with a short prefix: `feat:`, `fix:`, `chore:`, `refactor:`, `test:`.
- PRs should include a short summary, change list, test/CI status, and migration notes if applicable.

Troubleshooting
- If builds fail after plugin or Gradle upgrades, revert the wrapper and upgrade plugins incrementally.
- For JVM incompatibilities, verify `org.gradle.java.home` and `sourceCompatibility` are aligned with CI.

Contact & ownership
- Direct repo-specific questions to the repository owner.

End of file
