package de.cr.freitonal.shared.models;

import com.google.gwt.user.client.rpc.IsSerializable;

public class VolatilePiece implements IsSerializable {
	private Item composer;
	private VolatileCatalog volatileCatalog;
	private Item musicKey;
	private Item type;
	private Item subtitle;
	private Item ordinal;
	private Piece parent;
	private VolatileInstrumentation instrumentation;
	private Item publicationDate;

	public VolatilePiece(Item composer, VolatileInstrumentation instrumentation) {
		this.composer = composer;
		this.instrumentation = instrumentation;
	}

	public VolatilePiece(Item composer, VolatileInstrumentation instrumentation, Item pieceType) {
		this(composer, instrumentation);
		this.type = pieceType;
	}

	public VolatilePiece(Item composer, VolatileInstrumentation instrumentation, VolatileCatalog catalog) {
		this(composer, instrumentation);
		this.setCatalog(catalog);
	}

	public VolatilePiece(Item composer, VolatileInstrumentation instrumentation, VolatileCatalog catalog, Piece parent) {
		this(composer, instrumentation, catalog);
		this.parent = parent;
	}

	public VolatilePiece() {
	}

	public VolatilePiece(Item composer, Instrumentation instrumentation, Item pieceType, VolatileCatalog catalog, Item musicKey) {
		this(composer, instrumentation, pieceType);
		this.volatileCatalog = catalog;
		this.setMusicKey(musicKey);
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

	public boolean hasNonVolatileInstrumentation() {
		return instrumentation instanceof Instrumentation;
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

	public boolean hasNonVolatileCatalog() {
		return volatileCatalog instanceof Catalog;
	}

	public void setParent(Piece parent) {
		this.parent = parent;
	}

	public Piece getParent() {
		return parent;
	}

	public void setMusicKey(Item musicKey) {
		this.musicKey = musicKey;
	}

	public Item getMusicKey() {
		return musicKey;
	}

	public void setSubtitle(Item subtitle) {
		this.subtitle = subtitle;
	}

	public Item getSubtitle() {
		return subtitle;
	}

	public void setOrdinal(Item ordinal) {
		this.ordinal = ordinal;
	}

	public Item getOrdinal() {
		return ordinal;
	}

	public void setPublicationDate(Item publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Item getPublicationDate() {
		return publicationDate;
	}
}
