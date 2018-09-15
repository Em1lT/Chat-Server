import java.net.ServerSocket
import kotlin.concurrent.thread
/*
Emil Toivainen
1706854

This starts the server in port 9000. it takes 2 parameters with the thread. the client and the number. numbers then decide what
color your chat server will be. Originally parameters were the system `in`, System `out` but i put them inside the command interpreter

 */
class Server(){

    var num = 0

    fun start() {

        try {
            // server is started in port 9000
            var server = ServerSocket(9000)
            println("Server us runnin on port ${server.localPort}") //server.localport = portnumber

            while (true) {
                var client = server.accept()//waits till client is connected to the server
                println("Client connected: ${client.inetAddress.hostAddress}")
                num++
                thread { CommandInterpreter(client, num).run() }//threaded commandInterpreter that handles the terminal commands
            }
        }catch (e: Exception){
            println("Got exception: ${e.message}")
        }finally {
            println("all finished")
        }
    }

}