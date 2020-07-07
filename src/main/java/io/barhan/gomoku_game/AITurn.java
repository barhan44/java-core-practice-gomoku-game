package io.barhan.gomoku_game;

public interface AITurn {
	
	void setGameTable(GameTable gameTable);
	
	Cell makeTurn();
	
	Cell makeFirstTurn();
	
}
