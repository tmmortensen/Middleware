package clients;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Random;

/**
 * Class containing a temperature client, which generates temperature data.
 * 
 * @author thomasmortensen
 * 
 */
public class GetTempClient {

	private int temp; // Temperature
	private final int MAX_TEMP = 24;
	String generateString;
	int generateInt;
	Random random;
	private MulticastSocket tempClient = new MulticastSocket(8885);
	private InetAddress group = InetAddress.getByName("225.255.255.255");

	/**
	 * Constructor to set up the broadcast and create a random generator
	 * 
	 * @throws SocketException
	 * @throws IOException
	 */
	public GetTempClient() throws SocketException, IOException {
		tempClient.joinGroup(group);
		random = new Random();
	}

	/**
	 * Method to generate a random temperature within the wanted area
	 */
	public void generateTemp() {
		generateInt = random.nextInt(MAX_TEMP) + 1;

		while (generateInt < 14 || generateInt > MAX_TEMP) {
			generateInt = random.nextInt(MAX_TEMP) + 1;
		}
	}

	/**
	 * Method to make a string of the temperature so it can be transmitted via.
	 * broadcast.
	 * 
	 * @param generateString
	 * @return a string generated from an integer
	 */
	public String getTemp() {
		generateString = Integer.toString(generateInt);
		return generateString;
	}

	/**
	 * A method that makes the broadcast of a temperature possible
	 * 
	 * @return temperature that is used to check whether the temperature is in
	 *         the wanted area or not.
	 */
	public int publish(String event) {

		// event = eventTypes.event[1];
		String input = null;
		String inputAndUnit = null;

		// while loop to get temperature
		while (true) {
			generateTemp();
			getTemp();
			input = generateString;
			inputAndUnit = input + event;

			System.out.println("Actual temperature: " + input);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			DatagramPacket data = new DatagramPacket(inputAndUnit.getBytes(),
					inputAndUnit.length(), group, 8885);
			try {
				temp = Integer.parseInt(input);
				if (temp == 0) {
					break;
				}
				if (13 < temp && temp < 25) {
					// break;
				} else
					System.out.println("Error. "
							+ "Temperature should be between 14 and 24");
			} catch (NumberFormatException n) {
				System.out.println("Error. Not an integer");
			}
			try {
				// broadcast the data
				tempClient.send(data);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return temp;
	}
}
