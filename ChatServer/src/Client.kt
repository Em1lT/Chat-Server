import java.io.OutputStream
import java.net.Socket
import java.nio.charset.Charset
import java.util.*

class Client(client: Socket){


    private val client: Socket = client
    private val reader: Scanner = Scanner(client.getInputStream())
    private val writer: OutputStream = client.getOutputStream()
    
    var online = false

    fun run() {
        write("Welcome")
        online = true


        while (online){
            val text = reader.nextLine()
            if(text == "EXIT"){
                quit()
            }

        }

    }

    private fun write(message: String) {
        writer.write((message + '\n').toByteArray(Charset.defaultCharset()))
    }
    private fun quit(){
        online = false
        client.close()
        println("${client.inetAddress.hostAddress} closed the connection")
    }

}