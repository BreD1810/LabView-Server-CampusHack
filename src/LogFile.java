
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogFile
 */
@WebServlet(name = "/logfile", urlPatterns = "/logfile", loadOnStartup = 1, description = "/logfile")
public class LogFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br;
		String machineName = request.getParameter("name");
		String fullContent = "";
		try {
			br = new BufferedReader(new FileReader("/tmp/store/logs/"+machineName+".log"));
			String line = br.readLine();
			while (line != null) {
				fullContent += line;
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			WebClient.writeToTestFile(e + "\n");
		}

		response.setContentType("text/plain;charset=UTF-8");

		ServletOutputStream sout = response.getOutputStream();
		sout.print(fullContent);
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
