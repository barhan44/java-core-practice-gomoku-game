package io.barhan.gomoku_game.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import io.barhan.gomoku_game.AITurn;
import io.barhan.gomoku_game.Cell;
import io.barhan.gomoku_game.CellValue;
import io.barhan.gomoku_game.GameTable;

public class AITurnImpl implements AITurn {

	private GameTable gameTable;
	private int winCount = Constants.WIN_COUNT;

	@Override
	public void setGameTable(GameTable gameTable) {
		Objects.requireNonNull(gameTable, "Game table cannot be null!");
		if (gameTable.getSize() < this.winCount) {
			throw new IllegalArgumentException(
					"Size of gametable is too small: size=" + gameTable.getSize() + ". Required >=" + this.winCount);
		}
		this.gameTable = gameTable;
	}

	@Override
	public Cell makeTurn() {
		CellValue[] badges = { CellValue.AI, CellValue.PLAYER };
		for (int i = this.winCount - 1; i > 0; i--) {
			for (CellValue cellValue : badges) {
				Cell cell = this.tryMakeTurn(cellValue, i);
				if (cell != null) {
					return cell;
				}
			}
		}
		return this.makeRandomTurn();
	}

	@Override
	public Cell makeFirstTurn() {
		Cell cell = new Cell(this.gameTable.getSize() / 2, this.gameTable.getSize() / 2);
		this.gameTable.setValue(cell.getRowIndex(), cell.getColIndex(), CellValue.AI);
		return cell;
	}

	private Cell makeRandomTurn() {
		List<Cell> emptyCells = this.getAllEmptyCells();
		if (emptyCells.size() > 0) {
			Cell randomCell = emptyCells.get(new Random().nextInt(emptyCells.size()));
			this.gameTable.setValue(randomCell.getRowIndex(), randomCell.getColIndex(), CellValue.AI);
			return randomCell;
		} else {
			throw new AICantMakeTurnException("All cells are filled! Maybe you missed draw message? :)");
		}
	}

	private List<Cell> getAllEmptyCells() {
		List<Cell> emptyCells = new ArrayList<>();
		for (int i = 0; i < this.gameTable.getSize(); i++) {
			for (int j = 0; j < this.gameTable.getSize(); i++) {
				if (this.gameTable.isCellFree(i, j)) {
					emptyCells.add(new Cell(i, j));
				}
			}
		}
		return emptyCells;
	}

	private Cell tryMakeTurn(CellValue cellValue, int notBlankCount) {
		Cell cell = this.tryMakeTurnByRow(cellValue, notBlankCount);
		if (cell != null) {
			return cell;
		}
		cell = this.tryMakeTurnByColumn(cellValue, notBlankCount);
		if (cell != null) {
			return cell;
		}
		cell = this.tryMakeTurnByMasterDiagonal(cellValue, notBlankCount);
		if (cell != null) {
			return cell;
		}
		cell = this.tryMakeTurnBySlaveDiagonal(cellValue, notBlankCount);
		if (cell != null) {
			return cell;
		}
		return null;
	}

	private Cell tryMakeTurnByRow(CellValue cellValue, int notBlankCount) {
		for (int i = 0; i < this.gameTable.getSize(); i++) {
			for (int j = 0; j < this.gameTable.getSize() - this.winCount - 1; j++) {
				boolean hasEmptyCells = false;
				int count = 0;
				List<Cell> checkedCells = new ArrayList<>();
				for (int k = 0; k < this.winCount; k++) {
					checkedCells.add(new Cell(i, j + k));
					if (this.gameTable.getValue(i, j + k) == cellValue) {
						count++;
					} else if (this.gameTable.getValue(i, j + k) == CellValue.EMPTY) {
						hasEmptyCells = true;
					} else {
						hasEmptyCells = false;
						break;
					}
				}
				if (count == notBlankCount && hasEmptyCells) {
					return this.makeOneCellTurnFromList(checkedCells);
				}
			}
		}
		return null;
	}

	private Cell tryMakeTurnByColumn(CellValue cellValue, int notBlankCount) {
		for (int i = 0; i < this.gameTable.getSize(); i++) {
			for (int j = 0; j < this.gameTable.getSize() - this.winCount - 1; j++) {
				boolean hasEmptyCells = false;
				int count = 0;
				List<Cell> checkedCells = new ArrayList<>();
				for (int k = 0; k < this.winCount; k++) {
					checkedCells.add(new Cell(j + k, i));
					if (this.gameTable.getValue(j + k, i) == cellValue) {
						count++;
					} else if (this.gameTable.getValue(j + k, i) == CellValue.EMPTY) {
						hasEmptyCells = true;
					} else {
						hasEmptyCells = false;
						break;
					}
				}
				if (count == notBlankCount && hasEmptyCells) {
					return this.makeOneCellTurnFromList(checkedCells);
				}
			}
		}
		return null;
	}

	private Cell tryMakeTurnByMasterDiagonal(CellValue cellValue, int notBlankCount) {
		for (int i = 0; i < this.gameTable.getSize() - this.winCount - 1; i++) {
			for (int j = 0; j < this.gameTable.getSize() - this.winCount - 1; j++) {
				boolean hasEmptyCells = false;
				int count = 0;
				List<Cell> checkedCells = new ArrayList<>();
				for (int k = 0; k < this.winCount; k++) {
					checkedCells.add(new Cell(i + k, j + k));
					if (this.gameTable.getValue(i + k, j + k) == cellValue) {
						count++;
					} else if (this.gameTable.getValue(i + k, j + k) == CellValue.EMPTY) {
						hasEmptyCells = true;
					} else {
						hasEmptyCells = false;
						break;
					}
				}
				if (count == notBlankCount && hasEmptyCells) {
					return this.makeOneCellTurnFromList(checkedCells);
				}
			}
		}
		return null;
	}

	private Cell tryMakeTurnBySlaveDiagonal(CellValue cellValue, int notBlankCount) {
		for (int i = 0; i < this.gameTable.getSize() - this.winCount - 1; i++) {
			for (int j = this.winCount - 1; j < this.gameTable.getSize(); j++) {
				boolean hasEmptyCells = false;
				int count = 0;
				List<Cell> checkedCells = new ArrayList<>();
				for (int k = 0; k < this.winCount; k++) {
					checkedCells.add(new Cell(i + k, j - k));
					if (this.gameTable.getValue(i + k, j - k) == cellValue) {
						count++;
					} else if (this.gameTable.getValue(i + k, j - k) == CellValue.EMPTY) {
						hasEmptyCells = true;
					} else {
						hasEmptyCells = false;
						break;
					}
				}
				if (count == notBlankCount && hasEmptyCells) {
					return this.makeOneCellTurnFromList(checkedCells);
				}
			}
		}
		return null;
	}

	private Cell makeOneCellTurnFromList(List<Cell> inspectedCells) {
		Cell cell = this.findEmptyCell(inspectedCells);
		this.gameTable.setValue(cell.getRowIndex(), cell.getColIndex(), CellValue.AI);
		return cell;
	}

	private Cell findEmptyCell(List<Cell> cells) {
		for (int i = 0; i < cells.size(); i++) {
			Cell currentCell = cells.get(i);
			if (this.gameTable.getValue(currentCell.getRowIndex(), currentCell.getColIndex()) != CellValue.EMPTY) {
				if (i == 0) {
					if (this.isCellEmpty(cells.get(i + 1))) {
						return cells.get(i + 1);
					}
				} else if (i == cells.size() - 1) {
					if (this.isCellEmpty(cells.get(i - 1))) {
						return cells.get(i - 1);
					}
				} else {
					boolean searchDirection = new Random().nextBoolean();
					int first = searchDirection ? i + 1 : i - 1;
					int second = searchDirection ? i - 1 : i + 1;
					if (this.isCellEmpty(cells.get(first))) {
						return cells.get(first);
					} else if (this.isCellEmpty(cells.get(second))) {
						return cells.get(second);
					}
				}
			}
		}
		throw new AICantMakeTurnException("All cells are filled:" + cells);
	}

	private boolean isCellEmpty(Cell cell) {
		return this.gameTable.getValue(cell.getRowIndex(), cell.getColIndex()) == CellValue.EMPTY;
	}

}
