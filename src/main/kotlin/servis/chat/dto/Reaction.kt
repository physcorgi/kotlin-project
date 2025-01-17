package servis.chat.dto

data class Reaction(
    val type: ReactionType
) {
    enum class ReactionType {
        LIKE, DISLIKE
    }
}