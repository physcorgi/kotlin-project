package servis.chat.repository

import org.springframework.stereotype.Repository
import servis.chat.dto.Form
import servis.chat.dto.Reaction
import servis.chat.dto.User
import servis.chat.dto.UserRegister

@Repository
class UserRepository(
    private val userRepository: MutableList<User> = mutableListOf(),
    private val reactions: MutableMap<Long, MutableList<Reaction>> = mutableMapOf()
) {
    private fun changeGenderToUser(formGender: Form.Gender): User.Gender {
        return when (formGender) {
            Form.Gender.Female -> User.Gender.Female
            Form.Gender.Male -> User.Gender.Male
        }
    }

    fun register(user: UserRegister, token: String): Int {
        if (userRepository.any { it.login == user.login }) {
            return -1
        }
        val id = userRepository.size
        userRepository.add(User(id = id.toLong(), token = token, login = user.login, password = user.password))
        return id
    }

    fun update(form: Form, token: String): Int {
        val user = userRepository.find { it.token == token }
        return if (user != null) {
            user.age = form.age
            user.gender = changeGenderToUser(form.gender)
            user.lastname = form.lastName
            user.firstname = form.firstName
            user.id.toInt()
        } else {
            -1
        }
    }

    fun findById(id: Long): User? {
        return userRepository.find { it.id == id }
    }

    fun addReaction(userId: Long, reaction: Reaction) {
        reactions.computeIfAbsent(userId) { mutableListOf() }
        val existingReaction = reactions[userId]?.find { it.type == reaction.type }
        if (existingReaction != null) {
            reactions[userId]?.remove(existingReaction)
        }
        reactions[userId]?.add(reaction)
    }

    fun getReactions(userId: Long): List<Reaction> {
        return reactions[userId] ?: emptyList()
    }

    fun getAllUsers(): List<User> {
        return userRepository
    }
}