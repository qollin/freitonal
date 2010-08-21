package de.cr.freitonal.shared.models;

public class VolatilePiecePlusInstrumentationType {
	private final Instrumentation instrumentation;
	private final Item pieceType;
	private final String nickname;

	public VolatilePiecePlusInstrumentationType(String nickname, Item pieceType, Instrumentation instrumentation) {
		this.nickname = nickname;
		this.pieceType = pieceType;
		this.instrumentation = instrumentation;
	}

	/**
	 * @return the instrumentation
	 */
	public Instrumentation getInstrumentation() {
		return instrumentation;
	}

	/**
	 * @return the pieceType
	 */
	public Item getPieceType() {
		return pieceType;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
}
