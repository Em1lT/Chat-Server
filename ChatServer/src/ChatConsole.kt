class ChatConsole(): ChatHistoryObserver{


    override fun newMessage(message: ChatMessage) {
        println(message)
    }


}