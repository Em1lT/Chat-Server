import java.io.PrintStream
import java.net.Socket
import java.util.*

/*
Emil Toivainen
1706854
CommandInterpreter
 */
class CommandInterpreter(var client: Socket, var num: Int):Runnable, ChatHistoryObserver{

    private var chatHistory = ChatHistory
    private val users = Users
    private val reader: Scanner = Scanner(client.getInputStream())
    private val output = PrintStream(client.getOutputStream(), true)


    override fun run() {


        output.println("${chatHistory.colors(num)}Welcome!, press :help for all of the commands")
        var on = true
        var name = ""
        var loggedIn = false

        while (on == true) {
            //this part takes the commands and and decides what they are doing
            var com = reader.nextLine()
            if (com != null) {
                if(com.equals("") || com.equals(" ")){
                    output.println("error. nothing is written")// if enter is pressed without anything it wil print an error
                }
                else if (com.contains(":user ")) {  //this creates the user. You cannot change the user when you are once created it
                    name = com.replaceBefore(" ", "")

                    if(loggedIn == false){
                        if(Users.insertUser(name)){
                            loggedIn = true
                            chatHistory.registerObserver(CommandInterpreter(client, num))
                            chatHistory.addMsg(ChatMessage("Username created ", name, num))
                        }else{
                            output.println("Username already in use")
                        }
                    }else{
                        output.println("Cannot change username")
                    }
                }
                 else if (com == ":history") {
                    for (items in chatHistory.myMutableList){
                        output.println("$items")
                    }

                } else if (com == ":users") {

                   for (names in users.nameList){
                       output.println(names)
                   }

                } else if (com == ":quit" || loggedIn == true) {
                    output.println("Goodbye")
                    chatHistory.addMsg(ChatMessage("left", name, num))
                    users.deleteUser(name)
                    chatHistory.deregisterObserver(CommandInterpreter(client, num))

                    on = false
                } else if(com == ":help"){

                    listCommands()
                } else if (com.contains(":")) {

                    output.println("Did not get command $com")
                }else {
                    if(loggedIn){
                        chatHistory.addMsg(ChatMessage(com, name, num))
                    }
                    else{
                        output.println("Login to send message")
                    }

                }
            }
        }

        }
    fun listCommands(){

        output.println("Commands:")
        output.println("\":user yourName\"... creates a user")
        output.println("\":users\"... List users in the server")
        output.println("\":history\"... to see messages")
        output.println("\":quit\"... to quit")
        output.println("if you are logged in you can type your message freely ${chatHistory.colors(num)}")
    }

    override fun newMessage(message: ChatMessage) {
            output.println("${chatHistory.colors(message.num)} $message ${chatHistory.colors(num)}")

    }

    }




