package net.royhome.api.repository

import net.royhome.api.model.builder.Education
import net.royhome.api.model.builder.Experience
import net.royhome.api.model.builder.Resume
import net.royhome.api.model.builder.SkillGroup

class MockRepository {
  fun getResume(): Resume {
    val resume = Resume.Builder()
      .name("Kevin")
      .address("address")
      .phone("425-111-22222")
      .displayPhone(true)
      .email("name@address.com")
      .summary( "This is the summary" )
      .addSkill(
        SkillGroup.Builder()
          .name("Languages")
          .addSkill("TypeScript")
          .addSkill("Kotlin")
          .build()
      )
      .addSkill(
        SkillGroup.Builder()
          .name("Frameworks")
          .addSkill("Spring")
          .addSkill("React")
          .build(),
      )
      .addExperience(
        Experience.Builder()
          .title("Title")
          .company("Company")
          .addDescription("Description #1")
          .addDescription("Description #2")
          .addBullet("Bullet #1")
          .addBullet("Bullet #2")
          .addTech("Tech #1")
          .addTech("Tech #2")
          .addTech("Tech #3")
          .startDate("2000-01-01")
          .endDate("2010-01-01")
          .build()
      )
      .addEducation(
        Education.Builder()
          .degree("MS")
          .school("UMass")
          .graduation("2000-04-01")
          .build()
      )
      .addEducation(
        Education.Builder()
          .degree("MS")
          .school("UMass")
          .graduation("2000-04-01")
          .build()
      )
      .build()

    return resume;
  }
}