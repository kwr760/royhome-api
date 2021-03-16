package net.royhome.api.model.builder

@Suppress("LongParameterList")
class Resume private constructor(
  val name: String?,
  val address: String?,
  val phone: String?,
  val displayPhone: Boolean?,
  val email: String?,
  val summary: String?,
  val skills: ArrayList<SkillGroup>?,
  val experience: ArrayList<Experience>?,
  val education: ArrayList<Education>?,
) {
  data class Builder(
    var name: String? = null,
    var address: String? = null,
    var phone: String? = null,
    var displayPhone: Boolean? = false,
    var email: String? = null,
    var summary: String? = null,
    var skills: ArrayList<SkillGroup> = ArrayList(),
    var experience: ArrayList<Experience> = ArrayList(),
    var education: ArrayList<Education> = ArrayList(),
  ) {
    fun name(name: String) = apply { this.name = name }
    fun address(address: String) = apply { this.address = address }
    fun phone(phone: String) = apply { this.phone = phone }
    fun displayPhone(displayPhone: Boolean) = apply { this.displayPhone = displayPhone }
    fun email(email: String) = apply { this.email = email }
    fun summary(summary: String) = apply { this.summary = summary }
    fun addSkill(skillgroup: SkillGroup) = apply { this.skills.add(skillgroup) }
    fun addExperience(experience: Experience) = apply { this.experience.add(experience) }
    fun addEducation(education: Education) = apply { this.education.add(education) }
    fun build() = Resume(name, address, phone, displayPhone, email, summary, skills, experience, education)
  }
}
