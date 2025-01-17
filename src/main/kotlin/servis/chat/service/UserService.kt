package servis.chat.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import servis.chat.dto.Form
import servis.chat.dto.Reaction
import servis.chat.dto.UserRegister
import servis.chat.response.FormResponse
import servis.chat.response.UserResponse
import servis.chat.repository.UserRepository
import kotlin.random.Random

@Service
class UserService(
    private val userRepository: UserRepository
) {
    private fun tokenGeneration(length: Int): String {
        val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length).map { chars.random() }.joinToString("")
    }

    fun register(userRegister: UserRegister): ResponseEntity<UserResponse> {
        if (userRegister.login.isBlank() || userRegister.password.isBlank()) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        val existingUser = userRepository.getAllUsers().find { it.login == userRegister.login }
        if (existingUser != null) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        val token = tokenGeneration(Random.nextInt(8, 16))
        val id = userRepository.register(userRegister, token)
        return if (id != -1) {
            ResponseEntity(UserResponse(id.toLong(), userRegister.login, token), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    fun updateInfo(id: Long, form: Form, token: String): ResponseEntity<FormResponse> {
        val updatedId = userRepository.update(form, token)
        return if (updatedId >= 0) {
            ResponseEntity(FormResponse(updatedId.toLong()), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    fun addReaction(id: Long, token: String, reaction: Reaction): ResponseEntity<String> {
        val user = userRepository.findById(id)
        return if (user != null && user.token == token) {
            userRepository.addReaction(id, reaction)
            ResponseEntity("Reaction '${reaction.type}' added for user ID $id", HttpStatus.OK)
        } else {
            ResponseEntity("User not found or unauthorized", HttpStatus.UNAUTHORIZED)
        }
    }

    fun getAllUsers(): ResponseEntity<List<UserResponse?>> {
        val users = userRepository.getAllUsers().map {
            it.token?.let { it1 -> UserResponse(it.id, it.login, it1) }
        }
        return ResponseEntity(users, HttpStatus.OK)
    }
}