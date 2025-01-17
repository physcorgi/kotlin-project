package servis.chat.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import servis.chat.dto.Form
import servis.chat.dto.Reaction
import servis.chat.dto.UserRegister
import servis.chat.response.FormResponse
import servis.chat.response.UserResponse
import servis.chat.service.UserService

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun register(@RequestBody userRegister: UserRegister): ResponseEntity<UserResponse> {
        return userService.register(userRegister)
    }

    @PutMapping("/{id}")
    fun addInfo(
        @PathVariable id: Long,
        @RequestBody form: Form,
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<FormResponse> {
        return userService.updateInfo(id, form, token)
    }

    @PostMapping("/{id}/reaction")
    fun addReaction(
        @PathVariable id: Long,
        @RequestHeader("Authorization") token: String,
        @RequestBody reaction: Reaction
    ): ResponseEntity<String> {
        return userService.addReaction(id, token, reaction)
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse?>> {
        return userService.getAllUsers()
    }
}