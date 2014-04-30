package clients;

/**
 * 
 * @author thomasmortensen
 * 
 */
public class GetTempMain {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {

		GetTempClient tc = new GetTempClient();

		tc.publish();
	}
}
