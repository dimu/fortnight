<h2>RMI: Java Remote Method Invocation

##编写服务端接口，必须继承java.rmi.Remote接口
```
//远程服务接口
public interface IRmiInterface extends Remote {

//远程服务暴露的方法
public int add(int a, int b) throws RemoteException; ;
}
```
##远程服务实现类
```
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
```
##客户端实现类
```
public class RmiClient {

public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
  IRmiInterface obj = (IRmiInterface) Naming.lookup("//127.0.0.1:1322/HelloServer");
  int message = obj.add(1, 2);
  System.out.println(message);
}

}
```
##运行jdk的rmiregistry服务，在cmd命令行先添加server的classpath，在运行rmiregistry
```
set CLASSPATH = "e:\dwxws\tech\java\basic\bin"
start rmiregistry port
```
##然后注册服务端对象,注册服务对象时一定要跟上java的策略文件xx.policy
```
grant {
        permission java.security.AllPermission; //开放所有的端口
};

```
##命令行运行服务端代码，绑定成功会输出成功信息
```
  java -Djava.security.policy=./rmi/xx.policy xx.xx.RMIServer
  或者 java -cp ./ -Djava.rmi.server.useCodebaseOnly=false -Djava.security.policy=./rmi/xx.policy xx.xxRmiServer
```
##客户端运行
```
  java xx.RmiClient
```
