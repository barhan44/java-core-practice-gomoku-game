package io.barhan.gomoku_game;

public interface WinnerObserver {
	void setGameTable(GameTable gameTable);

	WinnerResult isWinnerFound(CellValue cellValue);
}
