import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
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
		//machineStateWrite(machineName, 1);
		WebClient.clientList;
		String echoMsg = "Echo from the server : " + machineName;
		return echoMsg;
	}

	@OnError
	public void onError(Throwable e){
		e.printStackTrace();
	}
	
	private void machineStateWrite(String machineName, int state) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("/tmp/store/test.txt"));
		String contents = "";
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				if(line.contains(machineName)) {
					line=machineName + ","+state;
				}
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			contents = sb.toString();

		} finally {
			br.close();
		}
		
        //Print to text file
        FileWriter out = null;
		try {
			out = new FileWriter("/tmp/store/test.txt");
			
			out.write(contents);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
