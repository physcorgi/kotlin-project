package servis.chat.client

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import servis.chat.dto.Form
import servis.chat.dto.UserRegister
import servis.chat.response.FormResponse
import servis.chat.response.UserResponse
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "userClient", url = "http://localhost:8080/")
interface UserClient {

    @PostMapping(value = ["/users"])
    fun registerUser(@RequestBody user: UserRegister): ResponseEntity<UserResponse>

    @PutMapping(value = ["/users"])
    fun updateUser(@RequestBody form: Form, @RequestHeader("Authorization") token: String): ResponseEntity<FormResponse>
}