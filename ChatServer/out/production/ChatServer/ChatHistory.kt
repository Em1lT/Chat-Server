import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ChatHistory: ChatHistoryobservable{

    val myMutableList = mutableListOf<String>()
    val observers = mutableSetOf<ChatHistoryObserver>()
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val chatConsole = ChatConsole()
    var topChatter = TopChatter()

    fun addMsg(message : ChatMessage){
        val time = current.format(formatter)
        myMutableList.add("${colors(message.num)}[$time] $message ${colors(message.num)}")
        notifyObservers(message)

    }
    override fun notifyObservers(message: ChatMessage) {
        for (items in observers){
            items.newMessage(message)
        }
    }

    override fun registerObserver(observer: ChatHistoryObserver) {
        observers.add(chatConsole)
        observers.add(topChatter)
        observers.add(observer)
    }

    override fun deregisterObserver(observer: ChatHistoryObserver) {
        observers.remove(observer)
    }
    fun colors(num : Int = 8): String {

        return when(num){
            1 -> "\u001B[44m" //Blue
            2 -> "\u001B[41m" // Red
            3 -> "\u001B[45m" //Magenta
            4 -> "\u001B[100m" // bright black
            5 -> "\u001B[101m" // bright red
            else -> "\u001B[40m"// Black
        }
        return "\u001B[40m"
    }

}