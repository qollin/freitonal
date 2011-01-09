package de.cr.freitonal.shared.models;

public class VolatilePiece {
	private Item composer;
	private VolatileCatalog volatileCatalog;
	public Item musicKey;
	private Item type;
	public Item subtitle;
	public Item ordinal;
	private Piece parent;
	private VolatileInstrumentation instrumentation;

	public VolatilePiece(Item composer, VolatileInstrumentation instrumentation) {
		this.composer = composer;
		this.instrumentation = instrumentation;
	}

	public VolatilePiece(Item composer, VolatileInstrumentation instrumentation, Item pieceType) {
		this(composer, instrumentation);
		this.type = pieceType;
	}

	public VolatilePiece(Item composer, VolatileInstrumentation instrumentation, Catalog catalog) {
		this(composer, instrumentation);
		this.setCatalog(catalog);
	}

	public VolatilePiece(Item composer, VolatileInstrumentation instrumentation, Catalog catalog, Piece parent) {
		this(composer, instrumentation, catalog);
		this.parent = parent;
	}

	public VolatilePiece() {
	}

	/**
	 * @param instrumentation
	 *            the instrumentation to set
	 */
	public void setInstrumentation(VolatileInstrumentation instrumentation) {
		this.instrumentation = instrumentation;
	}

	/**
	 * @return the instrumentation
	 */
	public VolatileInstrumentation getInstrumentation() {
		return instrumentation;
	}

	public Instrumentation getInstrumentationAsNonVolatile() {
		if (!(instrumentation instanceof Instrumentation)) {
			throw new IllegalStateException("the current instrumentation is volatile");
		}
		return (Instrumentation) instrumentation;
	}

	/**
	 * @param composer
	 *            the composer to set
	 */
	public void setComposer(Item composer) {
		this.composer = composer;
	}

	/**
	 * @return the composer
	 */
	public Item getComposer() {
		return composer;
	}

	public Item getPieceType() {
		return type;
	}

	public void setPieceType(Item pieceType) {
		this.type = pieceType;
	}

	public void setCatalog(VolatileCatalog volatileCatalog) {
		this.volatileCatalog = volatileCatalog;
	}

	public VolatileCatalog getCatalog() {
		return volatileCatalog;
	}

	public void setParent(Piece parent) {
		this.parent = parent;
	}

	public Piece getParent() {
		return parent;
	}
}
