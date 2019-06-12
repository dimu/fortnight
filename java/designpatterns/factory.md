##desgin pattern - factory pattern

工厂模式相当于创建对象实例操作A a = new A(),引入工厂模式会增加额外的代码量，但是会带给系统更大的可扩展性和尽量少的代码修改。特别是如何在对象的构造函数中需要进行初始化配置（例如配置文件加载，配置数据的解析，上下文的配置等），那样在构造函数中就做了过多的工作，更可怕的是如果后续项目中添加很多子类或者集成接口，那就更加麻烦。

##工厂方法
```
public class Factory{

　　public static Sample creator(int which){

　　//getClass 产生Sample 一般可使用动态类装载装入类。
　　if (which==1)
　　　　return new SampleA();
　　else if (which==2)
　　　　return new SampleB();

　　}

}
```
