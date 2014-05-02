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

	/**
	 * Method to start server thread
	 */
	@Override
	public void run() {
		subscribe(event);
	}

	/**
	 * Method to send avg. temp. to RMI client
	 * 
	 * @return average temperature
	 */
	public double avg() {
		return average;
	}

	/**
	 * Method that subscribes by listening to a broadcast signal. The
	 * calculation is only done with signal from the right client.
	 */
	public void subscribe(String event) {
		this.event = event;
		MulticastSocket server = null;
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
				String measure = parts[0]; // Measurement
				event = parts[1].trim(); // Unit

				// calculation of the avg. temp.
				if (event.equals("thermometer")) {
					value = Integer.parseInt(measure.trim());
					sum += value;
					average = sum / ++numberOfInputs;
					System.out.println("value: " + value + " average: "
							+ average + " on unit " + event);
				}

				// moisturemeter event
				if (event.equals("moisturemeter")) {
					System.out.println("value: " + value + " average: "
							+ average + " on unit " + event);
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
}
