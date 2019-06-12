package io;

import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * description: getResource在jar包中与在工程中的表现差别，
 * 在工程中getResource是相对于当前的.class文件路径，而在jar包中
 * 是相对于jar的根目录
 *
 * @version 2016年3月30日 上午11:16:16
 * @see
 * modify content------------author------------date
 */
public class ImageReader {
	
	public  ImageReader() {
		System.out.println(getClass().getResource("../interfaces/Honor.class"));
		System.out.println(this.getClass().getClassLoader().getResource("interfaces/Honor.class"));
		System.out.println(this.getClass().getClassLoader().getResource(""));
	}
	
	public static void main(String[] args) {
		new ImageReader();
	}
}
