package io.barhan.gomoku_game;

public class Cell {
	private final int rowIndex;
	private final int colIndex;

	public Cell(int rowIndex, int colIndex) {
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
	}

	public int getRowIndex() {
		return this.rowIndex;
	}

	public int getColIndex() {
		return this.colIndex;
	}

	@Override
	public String toString() {
		return this.rowIndex + ":" + this.colIndex;
	}
}
