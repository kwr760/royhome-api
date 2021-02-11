package net.royhome.api.model.builder

class Education private constructor(
  val degree: String?,
  val school: String?,
  val graduation: String?,
) {
  data class Builder(
    var degree: String? = null,
    var school: String? = null,
    var graduation: String? = null,
  ) {
    fun degree(degree: String) = apply { this.degree = degree }
    fun school(school: String) = apply { this.school = school }
    fun graduation(graduation: String) = apply { this.graduation = graduation }
    fun build() = Education(degree, school, graduation)
  }
}
