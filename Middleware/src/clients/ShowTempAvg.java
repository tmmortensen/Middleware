package clients;

import interfaces.RmiServerInterface;

import java.rmi.Naming;
import java.util.Scanner;

/**
 * Class containing an RMI client, which invokes a method on the RMI server and
 * gets the average temperature.
 * 
 * @author thomasmortensen
 * 
 */
public class ShowTempAvg {

	public static Scanner scan = new Scanner(System.in);

	/**
	 * Main method that starts the client.
	 * 
	 * @param agrs
	 * @throws Exception
	 */
	public static void main(String agrs[]) throws Exception {

		RmiServerInterface obj = (RmiServerInterface) Naming
				.lookup("//localhost/RmiServer");

		while (true) {
			System.out.println("Dette er den nuværende gennemsnitstemperatur: "
					+ obj.getTemp());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
	}
}
