package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Class that contains a server listening for broadcastsignals and to subscribe
 * correct events.
 * 
 * @author thomasmortensen
 * 
 */
public class MainServer implements Runnable {

	private double average = 0;
	private int value = 0;
	private double sum = 0;
	private int numberOfInputs = 0;
	String event = null;
	String measure = null;
	MulticastSocket server = null;

	/**
	 * Method to start server thread
	 */
	@Override
	public void run() {
		subscribe(event);
	}

	/**
	 * Method that subscribes by listening to a broadcast signal. The
	 * calculation is only done with signal from the right client.
	 */
	public void subscribe(String event) {

		try {
			// while loop to subscribe the data published
			while (true) {
				InetAddress group = InetAddress.getByName("225.255.255.255");
				server = new MulticastSocket(8885);
				server.joinGroup(group);
				byte[] buf = new byte[20];
				DatagramPacket data = new DatagramPacket(buf, buf.length);
				server.receive(data);
				String convertString = new String(data.getData());

				String[] parts = convertString.split("#");
				measure = parts[0]; // Measurement
				event = parts[1].trim(); // Unit

				// temp event
				if (event.equals("thermometer")) {
					avg();
				}

				// moisturemeter event
				if (event.equals("moisturemeter")) {
					System.out.println("moist value: " + value);
				}

				Thread.sleep(200);
			}
		} catch (IOException e) {
			// close server if exception is thrown
			server.close();
			System.out.println(e);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to send calculated avg. temp. to RMI client
	 * 
	 * @return average temperature
	 */
	public double avg() {
		value = Integer.parseInt(measure.trim());
		sum += value;
		average = sum / ++numberOfInputs;
		System.out.println("current temperature: " + value
				+ " average temperature: " + average);
		return average;
	}
}
