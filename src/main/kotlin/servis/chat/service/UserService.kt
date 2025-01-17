package servis.chat.service

import servis.chat.dto.User
import servis.chat.repository.UserRepository


class UserService(private val userRepository: UserRepository) {
    fun response(user: User): User? {
        if( userRepository.findById(user.id) != null ) {
            return user
        }
        else return null
    }

}
