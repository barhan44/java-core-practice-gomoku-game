package io.barhan.gomoku_game;

public interface GameTable {
	CellValue getValue(int row, int col);
	
	void setValue(int row, int col, CellValue cellValue);
	
	void reset();
	
	int getSize();
	
	boolean isCellFree(int row, int col);
	
	boolean emptyCellExists();
}
