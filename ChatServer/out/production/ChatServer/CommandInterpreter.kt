import java.awt.Color
import java.io.InputStream
import java.io.PrintStream
import java.net.Socket
import java.nio.charset.Charset
import java.util.*

class CommandInterpreter(var client: Socket, var num: Int):Runnable, ChatHistoryObserver{

    var chatHistory = ChatHistory
    var users = Users
    val reader: Scanner = Scanner(client.getInputStream())
    val writer = PrintStream(client.getOutputStream(), true)


    override fun run() {


        writer.println("${chatHistory.colors(num)}Welcome!, press :help for all of the commands")
        var on = true
        var name = ""
        var name2 = ""
        var loggedIn = false

        while (on == true) {

            var com = reader.nextLine()
            if (com != null) {
                if(com.equals("") || com.equals(" ")){
                    writer.println("error. nothing is written")
                }
                else if (com.contains(":user ")) {
                    name = com.replaceBefore(" ", "")

                    if(loggedIn == false){
                        if(Users.insertUser(name)){
                            loggedIn = true
                            chatHistory.registerObserver(CommandInterpreter(client, num))
                            chatHistory.addMsg(ChatMessage("Username created ", name, num))
                        }else{
                            writer.println("Username already in use")
                        }
                    }else{
                        writer.println("Cannot change username")
                    }
                }/* else if(com.contains("@")) {
                    name2 = com.replaceBeforeLast(" ", "")
                    writer.println("Sending private message to$name2")
                    chatHistory.addprivatMsg(ChatMessage(com.replaceAfterLast("@", ""), name, num))

*/
                 else if (com == ":history") {
                    for (items in chatHistory.myMutableList){
                        writer.println("$items")
                    }

                } else if (com == ":users") {
                   for (names in users.nameList){
                       writer.println(names)
                   }

                } else if (com == ":quit") {
                    writer.println("Goodbye")
                    chatHistory.addMsg(ChatMessage("left", name, num))
                    users.deleteUser(name)
                    chatHistory.deregisterObserver(CommandInterpreter(client, num))

                    on = false
                }else if(com == ":help"){

                    listCommands()
                } else if (com.contains(":")) {

                    writer.println("Did not get command $com")
                }else {
                    if(loggedIn){
                        chatHistory.addMsg(ChatMessage(com, name, num))
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
        writer.println("\":history\"... to see messages")
        writer.println("\":quit\"... to quit")
        writer.println("if you are logged in you can type your message freely ${chatHistory.colors(num)}")
    }

    override fun newMessage(message: ChatMessage) {
            writer.println("${chatHistory.colors(message.num)} $message ${chatHistory.colors(num)}")

    }

    }




