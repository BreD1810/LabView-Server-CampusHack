import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerThread extends Thread
{

    private Socket clientSocket;
    private String name;

    public ServerThread(Socket clientSocket)
    {
        System.out.println("New response");
        //Store the socket the client is running on
        this.clientSocket = clientSocket;
    }

    /**
     * The initial response when the connection is made.
     */
    private void initialResponse() throws IOException
    {
        System.out.println("Begin initial response");
        //Get the name from the client
        InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8);
        Scanner scr = new Scanner(isr);
        Boolean test = true;
        while(test)
        {
            if(scr.hasNext())
            {
                String line = scr.nextLine();

                if(line.contains("<MachineName>"))
                {
                    name = line.replace("<MachineName>", "");
                }
                System.out.println(line);
            }
            test = false;
        }

        //Send the receipt to the client
        System.out.println("Sending initial response");
        sendMessage("Received");
        System.out.println("sent");
    }

    /**
     * Send a message to the client, in UTF format.
     * @param message The message to be sent
     */
    private void sendMessage(String message)
    {
        try
        {
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
            outputStream.writeBytes(message);
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
            
            //Print to text file
            FileWriter out = null;
    		try {
    			out = new FileWriter("/tmp/store/test.txt");
    			out.write(name + ",1");
    		} finally {
    			if (out != null) {
    				out.close();
    			}
    		}

            //Create the stream readers for the client
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            //TODO: Figure out scanners

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
        catch (SocketException se)
        {
            //TODO: Add functionality for when the socket is ended.
            System.out.println("Client " + name + " has terminated.");
        }
        catch(IOException ioe)
        {
            System.err.println("IOException.");
            System.out.println("Crashed");
        }
    }

}
