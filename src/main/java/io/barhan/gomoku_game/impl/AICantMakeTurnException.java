package io.barhan.gomoku_game.impl;

public class AICantMakeTurnException extends IllegalStateException {
	private static final long serialVersionUID = 2839788104099428479L;

	public AICantMakeTurnException(String message) {
		super(message);
	}

}
