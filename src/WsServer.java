import java.io.FileWriter;
import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocketendpoint")
public class WsServer {
	
	public String machineName="";
	
	@OnOpen
	public void onOpen(){
		System.out.println("Open Connection ...");
		this.writeToTestFile("Open  connection...");
	}
	
	@OnClose
	public void onClose(){
		System.out.println("Close Connection ... "+machineName);
		this.writeToTestFile("Close connection..." + machineName);
	}
	
	@OnMessage
	public String onMessage(String machineName) throws IOException{
		this.machineName=machineName;
		System.out.println("Message from the client: " + machineName);
		Client cl = WebClient.getClientByName(machineName);
		cl.setStatus(1);
		String echoMsg = "Echo from the server : " + machineName;
		return echoMsg;
	}

	@OnError
	public void onError(Throwable e){
		e.printStackTrace();
	}
	
	private void writeToTestFile(String text) {
		/*BufferedReader br = new BufferedReader(new FileReader("/tmp/store/test.txt"));
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
		}*/
		
        //Print to text file
        FileWriter out = null;
		try {
			out = new FileWriter("/tmp/store/test.txt", true);
			out.write(text);
			if (out != null) {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
