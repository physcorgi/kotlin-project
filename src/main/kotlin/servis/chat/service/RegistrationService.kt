package servis.chat.service

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import servis.chat.dto.Form
import servis.chat.dto.UserRegister
import servis.chat.repository.UserRepository
import servis.chat.response.FormResponse
import servis.chat.response.UserResponse
import kotlin.random.Random

@Service
class RegistrationService(
    val userRepository: UserRepository
) {
    //генерирование токенов
    private fun tokenGeneration(length: Int): String {
        val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length).map { chars.random() }.joinToString("")
    }

    //для регестрации
    fun register(userRegister: UserRegister): ResponseEntity<UserResponse> {
        if (userRegister.login.isBlank() || userRegister.password.isBlank()) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        
        val token = tokenGeneration(Random.nextInt(8, 16))
        val id = userRepository.register(userRegister, token)
        return ResponseEntity(UserResponse(id, userRegister.login, token), HttpStatus.OK)
    }

    //для обновления
    fun update(form: Form, token: String): ResponseEntity<FormResponse> {
        val id = userRepository.update(form, token)
        return ResponseEntity(FormResponse(id), HttpStatus.OK)
    }
}



//проверка генерации токенов
//fun main() {
//    println("Тест токенов:")
//    repeat(5) {
//        val token = tokenGeneration(10)
//        println(token)
//    }
//}
//
//fun tokenGeneration(length: Int): String {
//    val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
//    return (1..length).map{chars.random()}.joinToString("")
//}