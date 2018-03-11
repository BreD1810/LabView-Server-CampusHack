import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class provides an interface to find the clients.
 */
@WebServlet(name = "/clientlist", urlPatterns = "/clientlist", loadOnStartup = 1, description = "/clientlist")
public class WebClient extends HttpServlet {
	//public static ArrayList<Client> clientList = new ArrayList<Client>();
	public static HashMap<String, ArrayList<Client>> clientList = new HashMap<>();
	private static final long serialVersionUID = 1L;
	private static String labName;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebClient.writeToTestFile("Started init\n");
		BufferedReader br, br2;
		try {
			//Read in the existing clients
			//br = new BufferedReader(new FileReader("/tmp/store/clientlist.txt"));
			//String line = br.readLine();
			
			//Get the list of labs
			ArrayList<String> labs = new ArrayList<String>();
			br = new BufferedReader(new FileReader("/tmp/store/labs.txt"));
			WebClient.writeToTestFile("Read file labs.txt, Content : \n");
			String line = br.readLine();
			while(line != null) {
				WebClient.writeToTestFile(line+"\n");
				labs.add(line);
				line = br.readLine();
			}
			
			for(String lab:labs) {
				br2 = new BufferedReader(new FileReader("/tmp/store/" + lab + ".txt"));
				WebClient.writeToTestFile("Looping through labs\n");
				line = br2.readLine();
				while(line != null) {
					String[] words = line.split(",");
					WebClient.writeToTestFile(line + "\n");
					if(words.length>2) {
						Client cl = new Client(Integer.valueOf(words[0]), words[1]);
						cl.setStatus(Integer.valueOf(words[2]));
						long t = Long.valueOf(words[3]);
						cl.setLastOn(t);
						//Add a client to the lab/Create new lab
						if(WebClient.clientList.containsKey(lab))
						{
							ArrayList<Client> tempClientList = WebClient.clientList.get(lab);
							tempClientList.add(cl);
							WebClient.clientList.put(lab, tempClientList);
						}else {
							ArrayList<Client> tempClientList = new ArrayList<Client>();
							tempClientList.add(cl);
							WebClient.clientList.put(lab, tempClientList);
						}
					}
					line = br2.readLine();
				}
				br2.close();
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			WebClient.writeToTestFile(e + "\n");
			WebClient.writeToErrorLog(e);
		}
		WebClient.writeToTestFile("INit done\n");
	}

	//Not used??
//	public static String logArrayList() {
//		String s = "";
//		for (Client cl : WebClient.clientList) {
//			s += cl.toString() + "\n";
//		}
//		return s;
//	}

	public static Client getClientByName(String machineName) {
		for (Client cl : WebClient.clientList.get(WebClient.labName)) {
			if (cl.getMachineName().equals(machineName)) {
				return cl;
			}
		}
		return null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = "";
		WebClient.labName = request.getParameter("labNumber");
		if (!WebClient.clientList.get(WebClient.labName).isEmpty()) {
			for (Client cl : WebClient.clientList.get(WebClient.labName)) {
				s += cl.toString() + System.lineSeparator();
			}
		}
		response.setContentType("text/plain;charset=UTF-8");

		//Send a list of clients
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
		//Instantly create a response
		doGet(request, response);
	}

	public static void writeToTestFile(String text) {
		// Print to text file
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

	private static void writeToErrorLog(Exception e) {
		File file = new File("/tmp/store/error.log");
		PrintStream ps;
		try {
			ps = new PrintStream(file);
			ps.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}