package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author thomasmortensen
 * 
 */
public interface RmiServerInterface extends Remote {

	/**
	 * Method to get the average temp. from the mainserver
	 * 
	 * @return average temperature
	 * @throws RemoteException
	 */
	public double getTemp() throws RemoteException;
}
