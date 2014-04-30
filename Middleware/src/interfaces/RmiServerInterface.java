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
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public double getTemp() throws RemoteException;
}
