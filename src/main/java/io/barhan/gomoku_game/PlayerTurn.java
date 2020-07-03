package io.barhan.gomoku_game;

public interface PlayerTurn {
	void setGameTable(GameTable gameTable);
	
	Cell makeTurn(int row, int col);
}
