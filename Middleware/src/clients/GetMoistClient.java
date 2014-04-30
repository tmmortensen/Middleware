package clients;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author thomasmortensen
 * 
 */
public class GetMoistClient {

	private int temp; // Temperature
	private final int MAX_MOIST = 24;
	int generateInt;
	String generateString;
	Random random;
	private MulticastSocket tempClient = new MulticastSocket(8885);
	private InetAddress group = InetAddress.getByName("225.255.255.255");
	private static Scanner scan = new Scanner(System.in);

	/**
	 * 
	 * @throws SocketException
	 * @throws IOException
	 */
	public GetMoistClient() throws SocketException, IOException {
		tempClient.joinGroup(group);
		random = new Random();
	}

	public void generateTemp() {
		generateInt = random.nextInt(MAX_MOIST) + 1;

		while (generateInt < 14 || generateInt > MAX_MOIST) {
			generateInt = random.nextInt(MAX_MOIST) + 1;
		}
	}

	public String getTemp() {
		generateString = Integer.toString(generateInt);
		return generateString;
	}

	/**
	 * 
	 * @return
	 */
	public int publish() {

		String input = null;
		String inputAndUnit = null;
		String moistString = "#moisturemeter";
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));

		// while loop to get temperature
		while (true) {
			generateTemp();
			getTemp();
			input = generateString;
			inputAndUnit = input + moistString;

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
				scan.next();
			}
			try {
				tempClient.send(data);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return temp;

	}
}
