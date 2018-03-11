import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Client {
	private int id;
	private String machineName;
	private Date lastOn;
	private int status;

	public Client() {

	}

	public Client(int id, String machineName) {
		this();
		setId(id);
		setMachineName(machineName);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public Date getLastOn() {
		return lastOn;
	}

	public void setLastOn(Date lastOn) {
		this.lastOn = lastOn;
	}

	public void setLastOn(long milliseconds) {
		Date date = new Date(milliseconds);
		this.setLastOn(date);
	}

	public long getLastOnMilliSeconds() {
		return this.getLastOn().getTime();
	}

	public String getCleanLog() {
		String l = getLog();
		String[] lines = l.split("\n");
		String cleanLog = "";
		int j = 0;
		for (int i = lines.length - 1; i > 0; i--) {
			if (!lines[i].isEmpty()) {
				cleanLog += lines[i] + "\n";
			}
			j++;
			if (j >= 5) {
				break;
			}
		}
		return cleanLog;
	}

	public String getLog() {

		BufferedReader br;
		String contents = "";
		try {
			br = new BufferedReader(new FileReader("/tmp/store/logs/" + this.getMachineName() + ".log"));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			contents = sb.toString();
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return contents;
	}

	public void createLogFile() {
		String filePath = "/tmp/store/logs/" + this.getMachineName() + ".log";
		Path path = Paths.get(filePath);
		File f = new File(filePath);
		if(!f.exists() || f.isDirectory()) return;
		List<String> lines = Arrays.asList("ID: " + this.getId() + " | Name: " + this.getMachineName() + "\n");
		try {
			Files.write(path, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			WebClient.writeToTestFile(e.toString());
		}
	}

	public void writeLog() {
		FileWriter out = null;
		try {
			Date date = new Date(System.currentTimeMillis());
			out = new FileWriter("/tmp/store/logs/" + this.getMachineName() + ".log", true);
			out.write("\nStatus at timestamp millis: " + System.currentTimeMillis() + " or date : " + date.toString()
					+ " was : " + this.getStatus() + " ;");
			if (out != null) {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return this.getMachineName() + "," + this.getStatus();
	}

	public String toMediumString() {
		return this.getId() + "," + this.toString() + "," + this.getLastOnMilliSeconds();
	}

	public String toLongString() {
		return this.getId() + "," + this.toString() + "," + this.getLastOn() + ",\n" + this.getCleanLog();
	}
}