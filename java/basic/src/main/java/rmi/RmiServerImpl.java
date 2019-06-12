package rmi;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 * @author dwx 2017年1月3日
 *
 */
public class RmiServerImpl extends UnicastRemoteObject implements IRmiInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数，父类UnicastRemoteObject无参构造函数中默认调用了export对象接口
	 * @throws RemoteException
	 */
	public RmiServerImpl() throws RemoteException {
		super();
	}

	// 暴露的方法具体实现
	public int add(int a, int b) throws RemoteException {
		return a + b;
	}

	public static void main(String args[]) {
		// Create and install a security manager,否则无法将远程对象暴露出来
		if (System.getSecurityManager() == null ) {
			System.setSecurityManager(new RMISecurityManager());
		}
		
		try {
			//由于IRmiImpl对象实现了UnicastRemoteObject对象，在实例化
			//的过程中就将method暴露出来
			RmiServerImpl obj = new RmiServerImpl();
			//default port 1099
			Naming.rebind("//127.0.0.1:1322/HelloServer", obj);
			System.out.println("HelloServer bound in registry");
		} catch (Exception e) {
			System.out.println("HelloImpl err: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
