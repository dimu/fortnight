##design pattern - singleton

单例模式是防止在程序中有多个实例对象，特别是在大型系统中，实例对象中通常伴随着初始化配置，加载资源等。单例能够状态化，例如实现计数功能，或者无状态化提供工具功能。一般web服务的上下文控制，资源加载器都是单例实现。

常见的单例实现代码
-------
```
public class Singleton {

　　private Singleton(){}

　　private static Singleton instance = new Singleton();
　　
　　public static Singleton getInstance() {
　　　　return instance; 　　
　　 }
}


```
或者
---
```
public class Singleton {

　　private static Singleton instance = null;

　　public static synchronized Singleton getInstance() {

　　  if (instance==null) {
　　　　  instance＝new Singleton();
      }

      return instance; 　　
   }
}
此方法为lazy initialization,存在着double-checked locking
```
