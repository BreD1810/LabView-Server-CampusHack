import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class allows a user to be added through labview.me/admin
 */
@WebServlet(name = "/addclient", urlPatterns = "/addclient", loadOnStartup = 1, description = "/addclient")
public class AddClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddClient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebClient.writeToTestFile("Tryinig to add client \n");
		String labName = request.getParameter("labNumber");
		WebClient.writeToTestFile(request.getParameter("id")+"\n");
		WebClient.writeToTestFile(request.getParameter("name")+"\n");
		WebClient.writeToTestFile(labName+"\n");
		Client cl = new Client(Integer.valueOf(request.getParameter("id")), request.getParameter("name"));
		cl.setStatus(0);
		
		writeNewLab(labName);
		writeToClientList(cl, labName);
		
		if(WebClient.clientList.containsKey(labName))
		{
			WebClient.clientList.get(labName).add(cl);
		}else {
			ArrayList<Client> tempClientList = new ArrayList<Client>();
			tempClientList.add(cl);
			WebClient.clientList.put(labName, tempClientList);
		}	
		
		WebClient.writeToTestFile("At end of serve, " + cl.toString() +"\n");
		response.getWriter().append("Successfully added client!").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void writeToClientList(Client cl, String lab) {
        FileWriter out = null;
		try {
			out = new FileWriter("/tmp/store/"+lab+".txt", true);
			out.write(cl.toMediumString()+"\n");
			if (out != null) {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeNewLab(String labName) {
		String filePath = "/tmp/store/"+labName+".txt";
		File f = new File(filePath);
		if((!f.exists()) || f.isDirectory()) {
			FileWriter out = null;
			try {
				out = new FileWriter(filePath);
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileWriter out2 = null;
			try {
				out2 = new FileWriter("/tmp/store/labs.txt", true);
				out2.write(labName+"\n");
				if (out2 != null) {
					out2.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
