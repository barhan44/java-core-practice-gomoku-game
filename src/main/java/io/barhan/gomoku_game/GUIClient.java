package io.barhan.gomoku_game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import io.barhan.gomoku_game.impl.AITurnImpl;
import io.barhan.gomoku_game.impl.GameTableImpl;
import io.barhan.gomoku_game.impl.PlayerTurnImpl;

public class GUIClient extends JFrame {
	private static final long serialVersionUID = 1286745218776845696L;
	private final JLabel cells[][];
	private final GameTable gameTable;
	private final PlayerTurn playerTurn;
	private final AITurn aiTurn;
	private final WinnerObserver winnerObserver;
	private boolean playerTurningFirst;

	public GUIClient() {
		super("Gomoku-game");
		this.gameTable = new GameTableImpl();
		this.playerTurn = new PlayerTurnImpl();
		this.aiTurn = new AITurnImpl();
		this.winnerObserver = null;
		this.initGameComponents();
		this.cells = new JLabel[this.gameTable.getSize()][this.gameTable.getSize()];
		this.playerTurningFirst = true;
		this.generateUITable();
	}

	private void generateUITable() {
		setLayout(new GridLayout(this.gameTable.getSize(), this.gameTable.getSize()));
		for (int i = 0; i < this.gameTable.getSize(); i++) {
			for (int j = 0; j < this.gameTable.getSize(); j++) {
				final int row = i;
				final int col = j;
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
						handlePlayerTurn(row, col);
					}
				});
			}
		}
	}

	private void initGameComponents() {
		this.playerTurn.setGameTable(this.gameTable);
		this.aiTurn.setGameTable(this.gameTable);
		this.winnerObserver.setGameTable(this.gameTable);
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

	private void markWinnerCells(List<Cell> winnerCells) {
		for (int i = 0; i < winnerCells.size(); i++) {
			Cell winnerCell = winnerCells.get(i);
			JLabel cellLabel = cells[winnerCell.getRowIndex()][winnerCell.getColIndex()];
			cellLabel.setForeground(Color.PINK);
			cellLabel.setFont(new Font(Font.SERIF, Font.BOLD, Constants.cellFontSize));
		}
	}

	private void startNewGame() {
		this.playerTurningFirst = !this.playerTurningFirst;
		this.gameTable.reset();
		for (int i = 0; i < this.gameTable.getSize(); i++) {
			for (int j = 0; j < this.gameTable.getSize(); j++) {
				JLabel cellLabel = cells[i][j];
				cellLabel.setText(this.gameTable.getValue(i, j).getValue());
				cellLabel.setFont(new Font(Font.SERIF, Font.PLAIN, Constants.cellFontSize));
				cellLabel.setForeground(Color.BLACK);
			}
			if (!this.playerTurningFirst) {
				Cell aiCell = this.aiTurn.makeFirstTurn();
				this.drawCellValue(aiCell);
			}
		}

	}

	private void stopGame() {
		for (int i = 0; i < this.gameTable.getSize(); i++) {
			for (int j = 0; j < this.gameTable.getSize(); j++) {
				this.cells[i][j].removeMouseListener(this.cells[i][j].getMouseListeners()[0]);
			}
		}
	}
	
	private void handleGameOver(String message) {
		if (JOptionPane.showConfirmDialog(this, message) == JOptionPane.YES_OPTION) {
			this.startNewGame();
		} else {
			this.stopGame();
		}
	}

	private void handlePlayerTurn(int row, int col) {
		if (this.gameTable.isCellFree(row, col)) {
			Cell playerCell = this.playerTurn.makeTurn(row, col);
			this.drawCellValue(playerCell);
			WinnerResult winnerResult = this.winnerObserver.isWinnerFound(CellValue.PLAYER);
			if (winnerResult.isWinnerExists()) {
				this.markWinnerCells(winnerResult.getWinnerCells());
				this.handleGameOver("Game over: You win!\nNew game?");
				return;
			}
			if (!this.gameTable.emptyCellExists()) {
				this.handleGameOver("Game over: Draw!\nNew Game?");
				return;
			}

			Cell aiCell = this.aiTurn.makeTurn();
			this.drawCellValue(aiCell);
			winnerResult = this.winnerObserver.isWinnerFound(CellValue.AI);
			if (winnerResult.isWinnerExists()) {
				this.markWinnerCells(winnerResult.getWinnerCells());
				this.handleGameOver("Game over: You lose!\nNew game?");
				return;
			}
			if (!this.gameTable.emptyCellExists()) {
				this.handleGameOver("Game over: Draw!\nNew Game?");
				return;
			}
		} else {
			JOptionPane.showMessageDialog(this, "Cell is not free! Please, choose free cell!");
		}
	}
}
