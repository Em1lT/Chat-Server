import java.net.ServerSocket
import kotlin.concurrent.thread

class Server(){



    fun start() {

        // server is started in port 9000
        var server = ServerSocket(9000)
        println("Server us runnin on port ${server.localPort}") //server.localport = portnumber

        while (true){
            var client = server.accept()//waits till client is connected to the server
            println("Client connected: ${client.inetAddress.hostAddress}")
            thread { CommandInterpreter(client).run()}//threaded commandInterpreter that handles the terminal commands
    }
    }

}