import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread
{

    Socket clientSocket;

    public ServerThread(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run()
    {
        try
        {
            //ServerSocket server = new ServerSocket(8080);
            //System.out.println("Listening on port 8080.");
            while (true)
            {
                //Socket clientSocket = server.accept();

                InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                String line = reader.readLine();
                while(!line.isEmpty())
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