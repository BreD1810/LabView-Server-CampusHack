import java.util.Date;

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
	
	public String getLog() {
		return "";
	}
	
	public void createLogFile() {
		
	}
	
	public void writeLog(String log) {
		
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return id+","+machineName+","+status+","+lastOn;	
	}
}