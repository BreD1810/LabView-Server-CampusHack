
public class LifeChecker implements Runnable {

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(2 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			WebClient.checkAllClients();
		}
	}
}
