package clients;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Random;

/**
 * Class that contains a moisturemeter
 * 
 * @author thomasmortensen
 * 
 */
public class GetMoistClient {

	private int moist; // Moisture
	private final int MAX_MOIST = 24;
	int generateInt;
	String generateString;
	Random random;
	private MulticastSocket tempClient = new MulticastSocket(8885);
	private InetAddress group = InetAddress.getByName("225.255.255.255");

	/**
	 * Constructor to set up the broadcast and create a random generator
	 * 
	 * @throws SocketException
	 * @throws IOException
	 */
	public GetMoistClient() throws SocketException, IOException {
		tempClient.joinGroup(group);
		random = new Random();
	}

	/**
	 * Method to generate a random moisture measurement within the wanted area
	 */
	public void generateMoist() {
		generateInt = random.nextInt(MAX_MOIST) + 1;

		while (generateInt < 14 || generateInt > MAX_MOIST) {
			generateInt = random.nextInt(MAX_MOIST) + 1;
		}
	}

	/**
	 * Method to make a string of the moisture so it can be transmitted via.
	 * broadcast.
	 * 
	 * @param generateString
	 * @return a string generated from an integer
	 */
	public String getMoist() {
		generateString = Integer.toString(generateInt);
		return generateString;
	}

	/**
	 * A method that makes the broadcast of a moisture measurement possible
	 * 
	 * @return moisture measurement that is uses to check whether the
	 *         temperature is in the wanted area or not.
	 */
	public int publish(String event) {

		String input = null;
		String inputAndUnit = null;

		// while loop to get temperature
		while (true) {
			generateMoist();
			getMoist();
			input = generateString;
			inputAndUnit = input + event;

			System.out.println("Actual moisture: " + input);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			DatagramPacket data = new DatagramPacket(inputAndUnit.getBytes(),
					inputAndUnit.length(), group, 8885);
			try {
				moist = Integer.parseInt(input);
				if (moist == 0) {
					break;
				}
				if (13 < moist && moist < 25) {
					// break;
				} else
					System.out.println("Error. "
							+ "Temperature should be between 14 and 24");
			} catch (NumberFormatException n) {
				System.out.println("Error. Not an integer");
			}
			try {
				tempClient.send(data);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return moist;

	}
}
