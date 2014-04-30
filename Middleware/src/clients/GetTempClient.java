package clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Scanner;

/**
 * 
 * @author thomasmortensen
 * 
 */
public class GetTempClient {

	private int temp; // Temperature
	private MulticastSocket tempClient = new MulticastSocket(8885);
	private InetAddress group = InetAddress.getByName("225.255.255.255");
	private static Scanner scan = new Scanner(System.in);

	/**
	 * 
	 * @throws SocketException
	 * @throws IOException
	 */
	public GetTempClient() throws SocketException, IOException {
		tempClient.joinGroup(group);
	}

	/**
	 * 
	 * @return
	 */
	public int publish() {

		String indtastning = null;
		String inputAndUnit = null;
		String tempString = "#thermometer";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// while loop to get temperature
		while (true) {
			System.out.println("Enter temp value");
			try {
				indtastning = br.readLine();
				inputAndUnit = indtastning + tempString;
			} catch (IOException e) {
				e.printStackTrace();
			}
			DatagramPacket data = new DatagramPacket(inputAndUnit.getBytes(),
					inputAndUnit.length(), group, 8885);
			try {
				temp = Integer.parseInt(indtastning);
				if (temp == 0) {
					break;
				}
				if (13 < temp && temp < 25) {
					// break;
				} else
					System.out.println("Error. "
							+ "Temperature should be between 14 and 24");
			} catch (NumberFormatException n) {
				System.out.println("Error. Please enter integer ;D");
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
