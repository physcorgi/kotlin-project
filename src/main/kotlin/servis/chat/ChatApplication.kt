package servis.chat
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
@SpringBootApplication
@EnableFeignClients
class ChatApplication

fun main(args: Array<String>) {
	runApplication<ChatApplication>(*args)
}
