package de.cr.freitonal.shared.models;

public class PiecePlusInstrumentationType extends VolatilePiecePlusInstrumentationType {
	private final String id;

	public PiecePlusInstrumentationType(String id, VolatilePiecePlusInstrumentationType volatil) {
		super(volatil.getNickname(), volatil.getPieceType(), volatil.getInstrumentation());
		this.id = id;
	}

	public String getID() {
		return id;
	}
}
