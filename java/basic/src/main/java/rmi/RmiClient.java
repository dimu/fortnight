package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RmiClient {
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		IRmiInterface obj = (IRmiInterface) Naming.lookup("//127.0.0.1:1322/HelloServer");
		int message = obj.add(1, 2);
		System.out.println(message);
	}

}
