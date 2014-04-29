package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerInterface extends Remote {
	
	public double getTemp() throws RemoteException;
}
