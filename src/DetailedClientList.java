
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class gives a more detailed version of WebClient.java.
 */
@WebServlet(name = "/detailedclientlist", urlPatterns = "/detailedclientlist", loadOnStartup = 1, description = "/detailedclientlist")
public class DetailedClientList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailedClientList() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = "";
		if (!WebClient.clientList.isEmpty()) {
			for(String key: WebClient.clientList.keySet()) {
				for(Client cl: WebClient.clientList.get(key)) {
					s += key +"," + cl.toLongString() + System.lineSeparator();
				}
			}
		}
		response.setContentType("text/plain;charset=UTF-8");

		ServletOutputStream sout = response.getOutputStream();
		sout.print(s);
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
