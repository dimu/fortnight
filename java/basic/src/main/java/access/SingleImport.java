package access;

import org.apache.commons.math3.analysis.function.Sin;

import java.util.ArrayList;

public class SingleImport {

  /**
   * 要么初始化要么通过构造函数赋值
   */
  public final int COUNT ;

  public static void main(String[] args) {
    ArrayList list = new java.util.ArrayList();
    new SingleImport(12);
  }

  public  SingleImport(int count) {
    /**
     * 通过构造函数初始化final域变量
     */
    this.COUNT = count;
  }

}
