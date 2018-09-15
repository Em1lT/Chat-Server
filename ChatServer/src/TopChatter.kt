
class TopChatter:ChatHistoryObserver {

    var mutableMap = mutableMapOf<String, Int>()

    override fun newMessage(message: ChatMessage) {

        if(!mutableMap.containsKey(message.name)){
                mutableMap[message.name] = 0
        }else{
            mutableMap.merge(message.name,1,Int::plus)
             var map = mutableMap.toList().sortedBy { it.second }.reversed().take(4)

            println("Top Chatters: ")
            var i = 1
           for (item in map){
               println("$i $item")
               i++
           }
            }
    }
}