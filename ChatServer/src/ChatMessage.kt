
class ChatMessage(var msg: String,var  name: String){
    override fun toString(): String {
        return "[$name ] $msg "
    }

}
