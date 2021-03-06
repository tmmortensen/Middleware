package clients;

/**
 * Main class to start the moisturemeter
 * 
 * @author thomasmortensen
 * 
 */
public class GetMoistMain {

	/**
	 * Main method to start the moisturemeter
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {

		GetMoistClient mc = new GetMoistClient();
		EventTypes eventTypes = new EventTypes();
		String event = eventTypes.event[0];

		mc.publish(event);
	}
}
