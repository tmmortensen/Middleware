package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class SocketServer implements Runnable {

	double average = 0;
	int value;
	double sum = 0;
	int numberOfInputs = 0;

	@Override
	public void run() {

		try {

			while (true) {
				InetAddress group = InetAddress.getByName("225.4.5.6");
				MulticastSocket server = new MulticastSocket(8885);
				server.joinGroup(group);
				byte[] buf = new byte[4];
				DatagramPacket data = new DatagramPacket(buf, buf.length);
				server.receive(data);
				String convertString = new String(data.getData());
				System.out.println(convertString + "\n");

				value = Integer.parseInt(convertString.trim());
				sum += value;
				average = sum / ++numberOfInputs;
				System.out.println("value: " + value + " average: " + average);
				Thread.sleep(200);
			}
		} catch (IOException e) {
			System.out.println(e);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public double avg() {
		return average;
	}

	public void subscribe(String) {
	
		}
	}
}
