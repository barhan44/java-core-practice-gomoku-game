package io.barhan.gomoku_game;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.WindowConstants;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		GUIClient client = new GUIClient();
		client.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		client.setResizable(false);
		client.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		client.setLocation(screenSize.width / 2 - client.getSize().width / 2,
				screenSize.height / 2 - client.getSize().height / 2);
		client.setVisible(true);
	}
}
