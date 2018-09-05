import java.io.InputStream
import java.io.PrintStream
import java.net.Socket
import java.nio.charset.Charset
import java.util.*

class CommandInterpreter(var client: Socket):Runnable, ChatHistoryObserver{

    override fun newMessage(message: ChatMessage) {
       writer.println(message)
    }
    var chatHistory = ChatHistory
    var users = Users
    val reader: Scanner = Scanner(client.getInputStream())
    val writer = PrintStream(client.getOutputStream(), true)

    override fun run() {
        chatHistory.registerObserver(CommandInterpreter(client))
        writer.println("Welcome!, press :help for all of the commands")
        var on = true
    var name = ""

        while (on == true) {

            var com = reader.nextLine()
            if (com != null) {
                if(com.equals("") || com.equals(" ")){
                    writer.println("error. nothing is written")
                }
                else if (com.contains(":user ")) {
                    name = com.replaceBefore(" ", "")
                    writer.println(users.insertUser(name))

                } else if (com == ":messages") {
                    for (items in chatHistory.myMutableList){
                        writer.println(items)
                    }

                } else if (com == ":users") {
                   for (names in users.nameList){
                       writer.println(names)
                   }

                } else if (com == ":quit") {

                    writer.println("Goodbye")
                    users.deleteUser(name)
                    chatHistory.deregisterObserver(CommandInterpreter(client))
                    on = false

                }else if(com == ":help"){

                    listCommands()
                } else if (com.contains(":")) {

                    writer.println("Did not get command $com")
                }else {
                    if(users.user){
                        chatHistory.addMsg(ChatMessage(com, name))
                    }
                    else{
                        writer.println("Login to send message")
                    }

                }
            }
        }

        }
    fun listCommands(){

        writer.println("Commands:")
        writer.println("\":user yourName\"... creates a user")
        writer.println("\":users\"... List users in the server")
        writer.println("\":messages\"... to see messages")
        writer.println("\":quit\"... to quit")
        writer.println("if you are logged in you can type your message freely")
    }
    }




