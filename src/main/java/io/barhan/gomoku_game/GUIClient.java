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
				cell.setPreferredSize(new Dimension(Constants.cellWidth, Constants.cellHeight));
				cell.setHorizontalAlignment(SwingConstants.CENTER);
				cell.setVerticalAlignment(SwingConstants.CENTER);
				cell.setFont(new Font(Font.SERIF, Font.PLAIN, Constants.cellFontSize));
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
	
	private void drawCellValue(Cell cell) {
		final int cellRowIndex = cell.getRowIndex();
		final int cellColIndex = cell.getColIndex();
		CellValue cellValue = this.gameTable.getValue(cellRowIndex, cellColIndex);
		final boolean isAITurn = cellValue == CellValue.AI;
		this.cells[cellRowIndex][cellColIndex].setText(cellValue.getValue());
		if (isAITurn) {
			this.cells[cellRowIndex][cellColIndex].setForeground(Color.RED);
		} else {
			this.cells[cellRowIndex][cellColIndex].setForeground(Color.GREEN);
		}
	}
}
