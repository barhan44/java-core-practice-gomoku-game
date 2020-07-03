package io.barhan.gomoku_game;

public enum CellValue {
	EMPTY(' '),
	PLAYER('X'),
	AI('O');
	
	private char value;
	
	private CellValue(char value) {
		this.value = value;
	}
	
	public String getValue() {
		return String.valueOf(this.value);
	}
}
