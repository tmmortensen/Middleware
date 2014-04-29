package server;

import interfaces.RmiServerInterface;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;

public class RmiServer extends UnicastRemoteObject implements RmiServerInterface {

	private static final long serialVersionUID = 1L;

	public RmiServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	static SocketServer ss = new SocketServer();
	
	public static void main(String args[]) throws Exception {

		System.out.println("RMI server startet");

		Thread t = new Thread(ss);
		t.start();
		
		try {
			LocateRegistry.createRegistry(1099);
			System.out.println("RMI registry oprettet");
		} catch (RemoteException e){
			System.out.println("RMI registry findes allerede");
		}

		RmiServer obj = new RmiServer();

		Naming.rebind("//localhost/RmiServer", obj);
		System.out.println("Peerserver forbundet i registry");
	}
	
	public double getTemp(){
		return ss.avg();
	}

}
