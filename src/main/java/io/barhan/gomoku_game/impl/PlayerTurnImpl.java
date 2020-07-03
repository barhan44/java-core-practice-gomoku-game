package io.barhan.gomoku_game.impl;

import java.util.Objects;

import io.barhan.gomoku_game.Cell;
import io.barhan.gomoku_game.CellValue;
import io.barhan.gomoku_game.GameTable;
import io.barhan.gomoku_game.PlayerTurn;

public class PlayerTurnImpl implements PlayerTurn {
	
	private GameTable gameTable;

	@Override
	public void setGameTable(GameTable gameTable) {
		Objects.requireNonNull(gameTable, "Game table cannot be null");
		this.gameTable = gameTable;
	}

	@Override
	public Cell makeTurn(int row, int col) {
		this.gameTable.setValue(row, col, CellValue.PLAYER);
		return new Cell(row, col);
	}

}
