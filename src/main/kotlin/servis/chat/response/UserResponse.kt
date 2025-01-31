package servis.chat.response

data class UserResponse(
    val id: Long,
    val login: String,
    val token: String
)