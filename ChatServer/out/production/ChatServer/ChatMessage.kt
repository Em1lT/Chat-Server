
class ChatMessage(var msg: String,var  name: String, var num: Int){
    override fun toString(): String {
        return "[$name ] $msg "
    }

}
