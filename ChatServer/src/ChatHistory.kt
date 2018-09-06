import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ChatHistory: ChatHistoryobservable{

    var myMutableList = mutableListOf<String>()
    var observers = mutableSetOf<ChatHistoryObserver>()



    fun addMsg(message : ChatMessage){
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val time = current.format(formatter)
        myMutableList.add("[$time] $message ")
        notifyObservers(message)

    }
    override fun notifyObservers(message: ChatMessage) {

        for (items in observers){
            items.newMessage(message)
        }
    }

    override fun registerObserver(observer: ChatHistoryObserver) {
        observers.add(observer)
        println("added observer")
    }

    override fun deregisterObserver(observer: ChatHistoryObserver) {
        observers.remove(observer)
        println("removed observer")
    }
    fun colors(num : Int = 8): String {

        when(num){
            1 -> return "\u001B[44m" //Blue
            2 -> return "\u001B[41m" // Red
            3 -> return "\u001B[46m" //Cyan
            4 -> return "\u001B[43m" //Yellow
            5 -> return "\u001B[45m" // Purple
            else -> return "\u001B[40m"// Black
        }
        return "\u001B[40m"
    }

}