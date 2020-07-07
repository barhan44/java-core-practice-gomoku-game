package io.barhan.gomoku_game;

import java.util.List;

public interface WinnerResult {
	boolean isWinnerExists();

	List<Cell> getWinnerCells();
}
