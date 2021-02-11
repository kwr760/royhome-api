package net.royhome.api.model.builder

class SkillGroup private constructor(
    val name: String?,
    var skills: ArrayList<String>?,
) {
    data class Builder(
        var name: String? = null,
        var skills: ArrayList<String> = ArrayList(),
    ) {
        fun name(name: String) = apply { this.name = name }
        fun addSkill(skill: String) = apply { this.skills.add(skill) }
        fun build() = SkillGroup(name, skills)
    }
}
