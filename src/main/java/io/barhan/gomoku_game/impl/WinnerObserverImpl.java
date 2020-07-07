package io.barhan.gomoku_game.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.barhan.gomoku_game.Cell;
import io.barhan.gomoku_game.CellValue;
import io.barhan.gomoku_game.GameTable;
import io.barhan.gomoku_game.WinnerObserver;
import io.barhan.gomoku_game.WinnerResult;

public class WinnerObserverImpl implements WinnerObserver {
	private GameTable gameTable;
	private int winCount = Constants.WIN_COUNT;

	@Override
	public void setGameTable(GameTable gameTable) {
		Objects.requireNonNull(gameTable, "Game table cannot be null");
		if (gameTable.getSize() < this.winCount) {
			throw new IllegalArgumentException(
					"Size of gameTable is small: size=" + gameTable.getSize() + ". Required >=" + this.winCount);
		}
		this.gameTable = gameTable;
	}

	@Override
	public WinnerResult isWinnerFound(CellValue cellValue) {
		Objects.requireNonNull(cellValue, "Cell value cannot be null");
		List<Cell> result = this.isWinnerByRow(cellValue);
		if (result != null) {
			return new WinnerResultImpl(result);
		}
		result = this.isWinnerByColumn(cellValue);
		if (result != null) {
			return new WinnerResultImpl(result);
		}
		result = this.isWinnerByMasterDiagonal(cellValue);
		if (result != null) {
			return new WinnerResultImpl(result);
		}
		result = this.isWinnerBySlaveDiagonal(cellValue);
		if (result != null) {
			return new WinnerResultImpl(result);
		}
		return new WinnerResultImpl(null);
	}

	private List<Cell> isWinnerByRow(CellValue cellValue) {
		for (int i = 0; i < this.gameTable.getSize(); i++) {
			List<Cell> cells = new ArrayList<>();
			for (int j = 0; j < this.gameTable.getSize(); j++) {
				if (this.gameTable.getValue(i, j) == cellValue) {
					cells.add(new Cell(i, j));
					if (cells.size() == this.winCount) {
						return cells;
					}
				} else {
					cells.clear();
					if (j > this.gameTable.getSize() - this.winCount) {
						break;
					}
				}
			}
		}
		return null;
	}

	private List<Cell> isWinnerByColumn(CellValue cellValue) {
		for (int i = 0; i < this.gameTable.getSize(); i++) {
			List<Cell> cells = new ArrayList<>();
			for (int j = 0; j < this.gameTable.getSize(); j++) {
				if (this.gameTable.getValue(j, i) == cellValue) {
					cells.add(new Cell(j, i));
					if (cells.size() == this.winCount) {
						return cells;
					}
				} else {
					cells.clear();
					if (j > this.gameTable.getSize() - this.winCount) {
						break;
					}
				}
			}
		}
		return null;
	}

	private List<Cell> isWinnerByMasterDiagonal(CellValue cellValue) {
		int winCountMinusOne = this.winCount - 1;
		for (int i = 0; i < this.gameTable.getSize() - winCountMinusOne; i++) {
			for (int j = 0; j < this.gameTable.getSize() - winCountMinusOne; j++) {
				List<Cell> cells = new ArrayList<>();
				for (int k = 0; k < this.winCount; k++) {
					if (this.gameTable.getValue(i + k, j + k) == cellValue) {
						cells.add(new Cell(i + k, j + k));
						if (cells.size() == this.winCount) {
							return cells;
						}
					} else {
						cells.clear();
						if (j > this.gameTable.getSize() - this.winCount) {
							break;
						}
					}
				}
			}
		}
		return null;
	}

	private List<Cell> isWinnerBySlaveDiagonal(CellValue cellValue) {
		int winCountMinusOne = this.winCount - 1;
		for (int i = 0; i < this.gameTable.getSize() - winCountMinusOne; i++) {
			for (int j = winCountMinusOne; j < this.gameTable.getSize(); j++) {
				List<Cell> cells = new ArrayList<>();
				for (int k = 0; k < this.winCount; k++) {
					if (this.gameTable.getValue(i + k, j - k) == cellValue) {
						cells.add(new Cell(i + k, j - k));
						if (cells.size() == this.winCount) {
							return cells;
						}
					} else {
						cells.clear();
						if (j > this.gameTable.getSize() - this.winCount) {
							break;
						}
					}
				}
			}
		}
		return null;
	}

	private static class WinnerResultImpl implements WinnerResult {
		private final List<Cell> winnerCells;

		public WinnerResultImpl(List<Cell> winnerCells) {
			this.winnerCells = winnerCells != null ? Collections.unmodifiableList(winnerCells)
					: Collections.unmodifiableList(new ArrayList<Cell>());
		}

		@Override
		public boolean isWinnerExists() {
			return this.winnerCells.size() > 0;
		}

		@Override
		public List<Cell> getWinnerCells() {
			return this.winnerCells;
		}
	}
}
