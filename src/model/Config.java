package model;

public final class Config {

	static Config instance;

	public static Config getInstance() {
		if (instance == null)
			instance = new Config();

		return instance;
	}

	private int borderX = 20;

	private int borderY = 20;

	private int casesize = 50;

	private int pieceR = 40;

	private Config() {
	}

	public int getBorderX() {
		return borderX;
	}

	public int getBorderY() {
		return borderY;
	}

	public int getCaseSize() {
		return casesize;
	}

	public int getPieceR() {
		return pieceR;
	}

}
