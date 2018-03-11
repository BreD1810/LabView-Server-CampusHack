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
		this.writeToTestFile("Open  connection..."+ "\n");
		//this.writeToTestFile(WebClient.logArrayList());
	}
	
	@OnClose
	public void onClose(){
		this.writeToTestFile("Close connection..." + machineName + "\n");
		//this.writeToTestFile(WebClient.logArrayList());
		
		Client cl = WebClient.getClientByName(machineName);
		if(cl!=null) {
			cl.setStatus(0);
			this.writeToTestFile("Client found \n" + cl.getMachineName() + ", and status=" + cl.getStatus());
			cl.writeLog();
		}
		else {
			this.writeToTestFile("Client machine name not found: " + machineName + "\n");
		}
	}
	
	@OnMessage
	public String onMessage(String machineName) throws IOException{
		this.machineName=machineName;
		if(WebClient.clientList==null||WebClient.clientList.isEmpty()) {
			this.writeToTestFile("EMPTY ARRAY LIST \n");
		}
		Client cl = WebClient.getClientByName(machineName);
		if(cl!=null) {
			cl.setStatus(1);
			this.writeToTestFile("Client found " + cl.getMachineName() + ", and status=" + cl.getStatus() + "\n");
			cl.writeLog();
		}
		else {
			this.writeToTestFile("Client machine name not found: " + machineName + "\n");
		}
		String echoMsg = "Echo from the server : " + machineName;
		return echoMsg;
	}

	@OnError
	public void onError(Throwable e){
		e.printStackTrace();
		this.writeToTestFile("\nError encountered inside onError\n");
		Client cl = WebClient.getClientByName(machineName);
		if(cl!=null) {
			cl.setStatus(0);
			this.writeToTestFile("Client found " + cl.getMachineName() + ", and status=" + cl.getStatus() + "\n");
		}
		else {
			this.writeToTestFile("Client machine name not found: " + machineName + "\n");
		}

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
