package net.royhome.resume

import net.royhome.resume.model.Resume
import net.royhome.resume.repository.ResumeRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ResumeOrderingJpaTest @Autowired constructor(
  private val resumeRepository: ResumeRepository,
) {

  companion object {
    @Container
    private val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:15-alpine")
      .withDatabaseName("royhome")
      // Use 'postgres' superuser so migration ownership statements (OWNER TO postgres) succeed
      .withUsername("postgres")
      .withPassword("postgres")

    @JvmStatic
    @DynamicPropertySource
    fun registerProps(registry: DynamicPropertyRegistry) {
      registry.add("spring.datasource.url") { postgres.jdbcUrl }
      registry.add("spring.datasource.username") { postgres.username }
      registry.add("spring.datasource.password") { postgres.password }
      // Reuse flyway & jpa settings from application-test.yml; only datasource changes here
    }
  }

  @Test
  fun orderedCollectionsAreReturnedByPosition() {
    // Precondition: at least one resume inserted via Flyway or test fixture
    val resumes: List<Resume> = resumeRepository.findAll()
    assertTrue(resumes.isNotEmpty(), "Expected seed resumes via migration data (none found)")
    val resume = resumes.first()

    fun <T> assertOrdered(list: List<T>, name: String, pos: (T) -> Int?) {
      if (list.isEmpty()) return
      val positions = list.mapNotNull { pos(it) }
      assertEquals(positions.sorted(), positions, "$name not ordered: $positions")
    }

    assertOrdered(resume.skillGroups, "skillGroups") { it.position }
    assertOrdered(resume.education, "education") { it.position }
    assertOrdered(resume.experience, "experience") { it.position }
    assertOrdered(resume.project, "project") { it.position }

    resume.experience.forEach { exp ->
      assertOrdered(exp.description, "experience.description") { it.position }
      assertOrdered(exp.bullets, "experience.bullets") { it.position }
    }
  }
}
