

import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ClientsAdmin
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
		WebClient.writeToTestFile(request.getParameter("id")+"\n");
		WebClient.writeToTestFile(request.getParameter("name")+"\n");
		Client cl = new Client();
		cl.setId(Integer.valueOf(request.getParameter("id")));
		cl.setMachineName(request.getParameter("name"));
		cl.setLastOn(System.currentTimeMillis());
		cl.setStatus(0);
		cl.createLogFile();
		writeToClientList(cl);
		WebClient.clientList.add(cl);
		WebClient.writeToTestFile("At end of serve, " + cl.toString() +"\n");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void writeToClientList(Client cl) {
        FileWriter out = null;
		try {
			out = new FileWriter("/tmp/store/clientlist.txt", true);
			out.write(cl.toMediumString()+"\n");
			if (out != null) {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
