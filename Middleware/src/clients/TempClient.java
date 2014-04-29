package clients;

public class TempClient {

	public static void main(String args[]) throws Exception {
		TempClientThread tc = new TempClientThread();
		tc.publish(null);
	}
}
