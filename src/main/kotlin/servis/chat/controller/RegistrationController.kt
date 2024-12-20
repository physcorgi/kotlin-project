package servis.chat.controller

import org.springframework.web.bind.annotation.*
import servis.chat.dto.Form
import servis.chat.dto.UserRegister
import servis.chat.service.RegistrationService

@RestController
class RegisterController (
    val registrationService: RegistrationService
) {

    @PostMapping("/users")
    //через равно быстро задаём функцию
    fun register(@RequestBody userRegister: UserRegister) = registrationService.register(userRegister)

    @PutMapping("/users")
    //используем @RequestHeader, чтобы передать заголовок
    fun addInfo(@RequestBody form: Form, @RequestHeader("Authorization") token: String) = registrationService.update(form, token)
}