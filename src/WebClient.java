import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloWorld
 */
@WebServlet(name = "/clientlist", urlPatterns = "/clientlist", loadOnStartup = 1, description = "/clientlist")
public class WebClient extends HttpServlet {
	public static ArrayList<Client> clientList = new ArrayList<Client>();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		BufferedReader br;
		String everything;
		try {
			br = new BufferedReader(new FileReader("/tmp/store/clientlist.txt"));
			String line = br.readLine();

			while (line != null) {
				String[] words = line.split(",");
				if (words.length > 2) {
					Client cl = new Client();
					cl.setId(Integer.valueOf(words[0]));
					cl.setMachineName(words[1]);
					long t = Integer.valueOf(words[2]);
					cl.setLastOn(new java.util.Date(t));
					cl.createLogFile();
					clientList.add(cl);
				}
				line = br.readLine();
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Client getClientByName(String machineName) {
		for(Client cl:WebClient.clientList) {
			if(cl.getMachineName()==machineName) {
				return cl;
			}
		}
		return null;
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = "";
		
		for(Client cl:WebClient.clientList) {
			s+= cl.toString() + System.lineSeparator();
		}
			response.setContentType("text/plain;charset=UTF-8");

			ServletOutputStream sout = response.getOutputStream();
			sout.print(s);

		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}