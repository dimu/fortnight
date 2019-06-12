package rmi;

import java.awt.*;
import java.rmi.*;

public class HelloApplet extends java.applet.Applet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int message;

	public void init() {
		try {
			RmiServerImpl obj = (RmiServerImpl) Naming.lookup("//" + getCodeBase().getHost() + "/HelloServer");
			message = obj.add(1, 2);
		} catch (Exception e) {
			System.out.println("HelloApplet exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void paint(Graphics g) {
		g.drawString(message + "", 25, 50);
	}
}
