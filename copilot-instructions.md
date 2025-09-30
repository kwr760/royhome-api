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

Kotlin + Spring Boot + PostgreSQL best practices
- Kotlin idioms
	- Prefer constructor injection; avoid field injection and `@Autowired` on fields.
	- Use `val` for immutability; restrict `var` to JPA entity technical needs.
	- Avoid `lateinit` for optionals; use nullable types with safe calls and `requireNotNull` when truly necessary.
	- Do not use `data class` for JPA entities (equals/hashCode/identity pitfalls); use data classes for DTOs/requests/responses.
	- Keep public APIs explicit; mark internals `internal` where appropriate.
- Persistence/JPA
	- Entities: keep small and focused. Map only what you need. Prefer composition over large aggregates.
	- Transactions at service layer using `@Transactional`. Mark read paths `@Transactional(readOnly = true)`.
	- Prevent N+1: prefer fetch joins, `@EntityGraph`, projections, and dedicated read DTO queries.
	- Use optimistic locking with `@Version` on mutable aggregates. Avoid long-running transactions.
	- Favor `Pageable`/`Slice` for pagination; avoid unbounded queries. Stream large reads with appropriate fetch size.
	- Maintain consistent naming: snake_case table/column names; configure Hibernate naming strategy if needed.
	- Avoid cascades that surprise; be explicit (`CascadeType.PERSIST`/`MERGE`) and avoid `REMOVE` on shared references.
- PostgreSQL specifics
	- Store times as UTC `TIMESTAMP WITH TIME ZONE` in Java/Kotlin as `Instant`/`OffsetDateTime`; set `hibernate.jdbc.time_zone=UTC`.
	- Primary keys: prefer `UUID` (v4 or v7) or `BIGSERIAL` identity; avoid natural keys.
	- Use `jsonb` for schemaless data sparingly; index with GIN when queried. Keep core relational.
	- Add indexes for frequent filters/joins; verify with `EXPLAIN ANALYZE` in performance work.
	- Tune HikariCP conservatively: start with `maximumPoolSize = 10-20` per instance, keep connection timeout < request timeout.
	- Avoid `SELECT *`; select only required columns.
- Migrations (Flyway)
	- All schema changes via Flyway under `src/main/resources/db/migration` with versioned scripts (e.g., `V5.1__add_index.sql`).
	- Zero-downtime: additive first (add columns default nullable/backfilled), deploy, then cleanup in a later migration.
	- Reversible operations: avoid destructive changes in the same release. Backup before major drops.
- Testing with Testcontainers
	- Use a shared reusable PostgreSQL container for integration tests; apply Spring dynamic properties to point datasource.
	- Run Flyway migrations against the container on test startup to mirror prod schema.
	- Keep a minimal happy-path integration test per repository/service critical path; mock external systems.
- Configuration & profiles
	- Profiles: use `dev`, `docker`, `prod` as defined in `application-*.yml`. Keep secrets in env vars, not YAML.
	- Datasource: prefer `SPRING_DATASOURCE_URL`, `USERNAME`, `PASSWORD`; include connection properties (sslmode, time zone) as needed.
	- Observability: enable Actuator in non-prod with health, info, metrics; restrict sensitive endpoints in prod.
- Performance & reliability
	- Bound thread pools and timeouts (server, client, DB). Keep request timeouts < DB timeouts.
	- Batch writes with `saveAll` and `hibernate.jdbc.batch_size`; avoid per-row flush.
	- Monitor slow queries; add indexes or rewrite queries. Use `@Query` for read-optimized projections.
	- Prefer idempotent operations in controllers/services where possible.
- Security
	- Use least-privilege DB roles; separate migration and application users if feasible.
	- Validate all external input at boundaries; use Kotlin types and Bean Validation annotations.
	- Avoid logging secrets/PII; use structured logging. Scrub or mask sensitive fields.

Troubleshooting
- If builds fail after plugin or Gradle upgrades, revert the wrapper and upgrade plugins incrementally.
- For JVM incompatibilities, verify `org.gradle.java.home` and `sourceCompatibility` are aligned with CI.

Contact & ownership
- Direct repo-specific questions to the repository owner.

Beast chat mode
- Purpose: a high-intensity, proactive mode for large/ambiguous changes where the assistant takes initiative to implement, test, and iterate with minimal back-and-forth.
- How to invoke: start your request with `beast:` or say “use Beast mode”.
- Behaviors
	- Minimal questions: ask only when blocked by missing info; otherwise proceed with reasonable assumptions.
	- Do the work end-to-end: plan briefly, apply patches, run build/tests, and report concrete results.
	- Quality gates every change: Build -> Lint/Detekt -> Unit/Integration tests -> optional smoke test.
	- Small, verifiable steps: limit each change set to a focused scope; commit/PR per logical unit.
	- Proactive fixes: if a test or linter fails, fix it immediately or isolate and open a follow-up TODO/issue.
	- Safety: never introduce secrets; avoid irreversible deletes; add migrations rather than in-place schema edits.
	- Observability: add basic logs/metrics when adding features that hit I/O or critical paths.
- Output style
	- Start with a one-line goal and a short step plan.
	- After 3–5 actions, provide a compact status update: what ran, key results, next.
	- Include “quality gates” summary: Build/Lint/Tests (PASS/FAIL) and deltas only.
	- Keep it concise and skimmable; avoid repeating unchanged plans.
- Acceptance
	- Changes compile, detekt passes (or baseline updated with justification), tests green, and docs updated when public behavior changes.
	- For DB-affecting changes: Flyway migration present and idempotent; dev/prod configs unaffected.

End of file
