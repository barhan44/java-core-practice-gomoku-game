package io.barhan.gomoku_game.impl;

import io.barhan.gomoku_game.CellValue;
import io.barhan.gomoku_game.GameTable;

public class GameTableImpl implements GameTable {
	private final CellValue[][] gameTable;

	public GameTableImpl() {
		this.gameTable = new CellValue[Constants.SIZE][Constants.SIZE];
		this.reset();
	}

	@Override
	public CellValue getValue(int row, int col) {
		if (this.isValidCell(row, col)) {
			return this.gameTable[row][col];
		} else {
			throw new IndexOutOfBoundsException(
					"Invalid row or col indexes: row=" + row + ", col=" + col + "size=" + getSize());
		}
	}

	@Override
	public void setValue(int row, int col, CellValue cellValue) {
		if (this.isValidCell(row, col)) {
			this.gameTable[row][col] = cellValue;
		} else {
			throw new IndexOutOfBoundsException(
					"Invalid row or col indexes: row=" + row + ", col=" + col + "size=" + getSize());
		}

	}

	@Override
	public void reset() {
		for (int i = 0; i < this.getSize(); i++) {
			for (int j = 0; j < this.getSize(); j++) {
				this.setValue(i, j, CellValue.EMPTY);
			}
		}

	}

	@Override
	public int getSize() {
		return this.gameTable.length;
	}

	@Override
	public boolean isCellFree(int row, int col) {
		return this.getValue(row, col) == CellValue.EMPTY;
	}

	@Override
	public boolean emptyCellExists() {
		for (int i = 0; i < this.getSize(); i++) {
			for (int j = 0; j < this.getSize(); j++) {
				if (this.getValue(i, j) == CellValue.EMPTY) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isValidCell(int row, int col) {
		return row >= 0 && row < this.getSize() && col >= 0 && col < this.getSize();
	};
}
