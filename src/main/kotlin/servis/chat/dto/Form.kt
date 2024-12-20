package servis.chat.dto

data class Form(
    val gender: Gender,
    val age: Int,
    val lastName: String,
    val firstName: String
) {

    enum class Gender {
        Male, Female
    }

}