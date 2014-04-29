package clients;

import interfaces.IPubSub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Scanner;

public class TempClientThread implements IPubSub {
	int temp; // Temperature

	MulticastSocket tempClient = new MulticastSocket(8885);
	InetAddress group = InetAddress.getByName("225.4.5.6");

	public static Scanner scan = new Scanner(System.in);

	public TempClientThread() throws SocketException, IOException {
		// socket.send(packet);
		tempClient.joinGroup(group);
	}

	@Override
	public int publish(String thermostat) {
		String indtastning = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// while loop to get temperature
		while (true) {
			System.out.println("Enter temp value");
			try {
				indtastning = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DatagramPacket data = new DatagramPacket(indtastning.getBytes(),
					indtastning.length(), group, 8885);
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

	@Override
	public void subscribe() {

	}
}
