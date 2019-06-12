package exception;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CatchOrder {
	public static void main(String[] args) {
		try{
			throw new IOException();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException");
		} catch (IOException e) {
			System.out.println("IOException");
		} catch (Exception e) {
			System.out.println("Exception");
		}
	}
}
