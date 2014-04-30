package clients;

import interfaces.RmiServerInterface;

import java.rmi.Naming;
import java.util.Scanner;

/**
 * 
 * @author thomasmortensen
 * 
 */
public class ShowTempAvg {

	public static Scanner scan = new Scanner(System.in);

	/**
	 * 
	 * @param agrs
	 * @throws Exception
	 */
	public static void main(String agrs[]) throws Exception {

		RmiServerInterface obj = (RmiServerInterface) Naming
				.lookup("//localhost/RmiServer");

		while (true) {
			System.out.println("Tast Enter for at se gennemsnitstemperaturen");
			scan.nextLine();
			System.out.println(obj.getTemp());
		}
	}
}
