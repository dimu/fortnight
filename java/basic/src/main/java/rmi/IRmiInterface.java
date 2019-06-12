package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

//远程服务接口
public interface IRmiInterface extends Remote {
	
	//远程服务暴露的方法
	public int add(int a, int b) throws RemoteException; ;
}
