import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread extends Thread
{

    Socket clientSocket;

    public ServerThread(Socket clientSocket)
    {
        System.out.println("New response");
        //Store the socket the client is running on
        this.clientSocket = clientSocket;
    }

    /**
     * The initial response when the connection is made.
     */
    private void initialResponse()
    {
        System.out.println("Sending initial response");
        sendMessage("Received");
    }

    /**
     * Send a message to the client, in UTF format.
     * @param message The message to be sent
     */
    private void sendMessage(String message)
    {
        try
        {
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            outputStream.writeUTF(message);
        }
        catch(IOException ioe)
        {
            System.err.println("Error responding to client.");
        }
    }

    /**
     * Send the initial connection received message.
     * Keep listen for keep alive message.
     * If no message (or closing message), kill the thread.
     */
    @Override
    public void run()
    {
        try
        {
            //Send the initial response
            initialResponse();

            //Create the stream readers for the client
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(isr);

//            //Loop infinitely to listen for messages.
//            while (true)
//            {
//                String line = reader.readLine();
//                while(!line.isEmpty())
//                {
//                    System.out.println(line);
//                    line = reader.readLine();
//                }
//            }

            //Temp infinite loop until we figure out a way to detect timeout
            while(true)
            {
                String line = reader.readLine();
                while(line != null && !line.isEmpty())
                {
                    System.out.println(line);
                    line = reader.readLine();
                }
            }

        }
        catch(IOException ioe)
        {
            System.err.println("IOException.");
        }
    }

}
