package servis.chat.dto
data class User(
    val id:Int,
    val login: String,
    val password: String,
    var gender: Gender?=null,
    var age: Int?=null,
    var lastname: String?=null,
    var firstname: String?=null,
    val token:String?=null
)
{
    enum class Gender {
        Male, Female
    }
}