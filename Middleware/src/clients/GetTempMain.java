package clients;

/**
 * Main class for the temperature client.
 * 
 * @author thomasmortensen
 * 
 */
public class GetTempMain {

	/**
	 * Main method to start the temperature client.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {

		GetTempClient tc = new GetTempClient();
		EventTypes eventTypes = new EventTypes();
		String event = eventTypes.event[1];

		tc.publish(event);
	}
}
