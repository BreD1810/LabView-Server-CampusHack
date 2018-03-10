import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{

    private ServerSocket serverSocket;
    private Socket connection;

    public static void main(String[] args) throws IOException
    {
        //Establish the server host
        Server serverObj = new Server();
        serverObj.serverSocket = new ServerSocket(1337);

        //Infinite loop to accept multiple clients
        while(true)
        {
            //Wait for a connecting client
            System.out.println("Waiting for client connection.");
            serverObj.connection = serverObj.serverSocket.accept();
            System.out.println("Got client");

            //Start a new thread to listen to the client
            ServerThread thread = new ServerThread(serverObj.connection);
            thread.start();
        }

    }

}
