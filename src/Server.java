import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{

    ServerSocket serverSocket;
    Socket connection;
    static ArrayList<Thread> threads = new ArrayList<Thread>();

//    public Server(Socket clientSocket) {
//        this.connection = clientSocket;
//    }

    public static void main(String[] args) throws IOException
    {
        //Establish the server host
        //Server serverObj = new Server(new Socket());
        Server serverObj = new Server();
        serverObj.serverSocket = new ServerSocket(8080);

        //Infinite loop to accept multiple clients
        while(true)
        {
            //Wait for a connecting client
            System.out.println("Waiting for client connection.");
            serverObj.connection = serverObj.serverSocket.accept();

            //Start a new thread to listen to the client
            ServerThread thread = new ServerThread(serverObj.connection);
            thread.start();

            //Add the thread to the thread list
            threads.add(thread);
        }

    }

}
