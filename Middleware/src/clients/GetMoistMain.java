package clients;

/**
 * 
 * @author thomasmortensen
 * 
 */
public class GetMoistMain {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {

		GetMoistClient mc = new GetMoistClient();

		mc.publish();
	}
}
