package io.barhan.gomoku_game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import io.barhan.gomoku_game.impl.GameTableImpl;

public class GUIClient extends JFrame {
	private static final long serialVersionUID = 1286745218776845696L;
	private final JLabel cells[][];
	private final GameTable gameTable;

	public GUIClient() {
		super("Gomoku-game");
		this.gameTable = new GameTableImpl();
		this.cells = new JLabel[this.gameTable.getSize()][this.gameTable.getSize()];
		this.generateUITable();
	}

	private void generateUITable() {
		setLayout(new GridLayout(this.gameTable.getSize(), this.gameTable.getSize()));
		for (int i = 0; i < this.gameTable.getSize(); i++) {
			for (int j = 0; j < this.gameTable.getSize(); j++) {
				this.cells[i][j] = new JLabel();
				final JLabel cell = this.cells[i][j];
				cell.setPreferredSize(new Dimension(45, 45));
				cell.setHorizontalAlignment(SwingConstants.CENTER);
				cell.setVerticalAlignment(SwingConstants.CENTER);
				cell.setFont(new Font(Font.SERIF, Font.PLAIN, 35));
				cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				add(cell);
				cell.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
					}
				});
			}
		}
	}
}
