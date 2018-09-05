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

}