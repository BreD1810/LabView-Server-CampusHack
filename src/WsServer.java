import java.io.FileWriter;
import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocketendpoint")
public class WsServer {
	
	@OnOpen
	public void onOpen(){
		System.out.println("Open Connection ...");
	}
	
	@OnClose
	public void onClose(){
		System.out.println("Close Connection ...");
	}
	
	@OnMessage
	public String onMessage(String machineName) throws IOException{
		System.out.println("Message from the client: " + machineName);
		
        //Print to text file
        FileWriter out = null;
		try {
			out = new FileWriter("/tmp/store/test.txt");
			out.write(machineName + ",1");
		} finally {
			if (out != null) {
				out.close();
			}
		}

		String echoMsg = "Echo from the server : " + machineName;
		return echoMsg;
	}

	@OnError
	public void onError(Throwable e){
		e.printStackTrace();
	}

}
