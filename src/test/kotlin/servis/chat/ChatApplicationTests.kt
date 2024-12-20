package servis.chat

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.junit.jupiter.api.Assertions.*
import servis.chat.dto.Form
import servis.chat.dto.UserRegister
import servis.chat.response.FormResponse
import servis.chat.response.UserResponse

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatApplicationTests {
    
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun testRegister() {
        val request = UserRegister(
            login = "test",
            password = "123"
        )

        val response = restTemplate.postForEntity(
            "/users",
            request,
            UserResponse::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(0, response.body?.id)
        assertEquals("test", response.body?.login)
        assertNotNull(response.body?.token)
    }

    @Test
    fun testRegisterBadRequest() {
        val request = UserRegister(
            login = "",
            password = "123"
        )

        val response = restTemplate.postForEntity(
            "/users",
            request,
            UserResponse::class.java
        )

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    fun testUpdate() {
        // Сначала регистрируем пользователя
        val registerRequest = UserRegister(
            login = "updateTest",
            password = "123"
        )
        
        val registerResponse = restTemplate.postForEntity(
            "/users",
            registerRequest,
            UserResponse::class.java
        )
        
        val token = registerResponse.body?.token
        
        // Создаем заголовки
        val headers = HttpHeaders()
        headers.add("Authorization", token)
        
        // Создаем запрос на обновление
        val updateRequest = Form(
            gender = Form.Gender.Male,
            age = 57,
            firstName = "bro1",
            lastName = "bro2"
        )

        val requestEntity = HttpEntity(updateRequest, headers)

        val response = restTemplate.exchange(
            "/users",
            HttpMethod.PUT,
            requestEntity,
            FormResponse::class.java
        )

        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(response.body)
        assertEquals(0, response.body?.id)
    }

    @Test
    fun testUpdateUnauthorized() {
        val headers = HttpHeaders()
        headers.add("Authorization", "invalid_token")
        
        val request = Form(
            gender = Form.Gender.Male,
            age = 57,
            firstName = "bro1",
            lastName = "bro2"
        )

        val requestEntity = HttpEntity(request, headers)

        val response = restTemplate.exchange(
            "/users",
            HttpMethod.PUT,
            requestEntity,
            FormResponse::class.java
        )

        assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
    }
}

