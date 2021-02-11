package net.royhome.api.model.builder

class Experience private constructor(
  val title: String?,
  val company: String?,
  val description: ArrayList<String>?,
  val bullets: ArrayList<String>?,
  val techs: ArrayList<String>?,
  val startDate: String?,
  val endDate: String?,
) {
  data class Builder(
    var title: String? = null,
    var company: String? = null,
    var description: ArrayList<String> = ArrayList(),
    var bullets: ArrayList<String> = ArrayList(),
    var techs: ArrayList<String> = ArrayList(),
    var startDate: String? = null,
    var endDate: String? = null,
  ) {
    fun title(title: String) = apply { this.title = title }
    fun company(company: String) = apply { this.company = company }
    fun addDescription(description: String) = apply { this.description.add(description) }
    fun addBullet(bullet: String) = apply { this.bullets.add(bullet) }
    fun addTech(tech: String) = apply { this.techs.add(tech) }
    fun startDate(startDate: String) = apply { this.startDate = startDate }
    fun endDate(endDate: String) = apply { this.endDate = endDate }
    fun build() = Experience(title, company, description, bullets, techs, startDate, endDate)
  }
}

