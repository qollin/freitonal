package de.cr.freitonal.client.rpc;

import com.google.gwt.user.client.rpc.IsSerializable;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.ComposerSet;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.models.MusicKeySet;
import de.cr.freitonal.client.models.OrdinalSet;
import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.models.PublicationDateSet;
import de.cr.freitonal.client.models.SubtitleSet;
import de.cr.freitonal.shared.models.Piece;

public class PieceSearchMask implements IsSerializable {
	private ComposerSet composers;
	private CatalogSet catalogs;
	private PieceTypeSet pieceTypeSet;
	private InstrumentationSet instrumentationSet;
	private SubtitleSet subtitleSet;
	private OrdinalSet ordinalSet;
	private MusicKeySet musicKeySet;
	private PublicationDateSet publicationDateSet;

	public PieceSearchMask(Piece piece) {
		setComposers(new ComposerSet(piece.getComposer()));

		ItemSet catalogNames = new ItemSet(piece.getCatalog().getCatalogName());

		ItemSet catalogNumbers = new ItemSet(piece.getCatalog().getCatalogNumber());
		setCatalogs(new CatalogSet(catalogNames, catalogNumbers));

		ItemSet pieceTypes = new ItemSet(piece.getPieceType());
		setPieceTypes(new PieceTypeSet(pieceTypes, new ItemSet()));

		setInstrumentations(new InstrumentationSet(piece.getInstrumentationAsNonVolatile()));

		setSubtitles(new SubtitleSet(piece.getSubtitle()));
		setOrdinals(new OrdinalSet(piece.getOrdinal()));
		setMusicKeys(new MusicKeySet(piece.getMusicKey()));
		setPublicationDates(new PublicationDateSet(piece.getPublicationDate()));
	}

	public PieceSearchMask() {
	}

	/**
	 * @param composers
	 *            the composers to set
	 */
	public void setComposers(ComposerSet composers) {
		this.composers = composers;
	}

	/**
	 * @return the composers
	 */
	public ComposerSet getComposers() {
		return composers;
	}

	public void setCatalogs(CatalogSet catalogs) {
		this.catalogs = catalogs;
	}

	public CatalogSet getCatalogs() {
		return catalogs;
	}

	public void setPieceTypes(PieceTypeSet pieceTypeSet) {
		this.pieceTypeSet = pieceTypeSet;
	}

	public PieceTypeSet getPieceTypes() {
		return pieceTypeSet;
	}

	public void setInstrumentations(InstrumentationSet instrumentationSet) {
		this.instrumentationSet = instrumentationSet;
	}

	public InstrumentationSet getInstrumentations() {
		return instrumentationSet;
	}

	public SubtitleSet getSubtitles() {
		return subtitleSet;
	}

	public void setSubtitles(SubtitleSet subtitleSet) {
		this.subtitleSet = subtitleSet;
	}

	public OrdinalSet getOrdinals() {
		return ordinalSet;
	}

	public void setOrdinals(OrdinalSet ordinalSet) {
		this.ordinalSet = ordinalSet;
	}

	/**
	 * @param musicKeySet
	 *            the musicKeySet to set
	 */
	public void setMusicKeys(MusicKeySet musicKeySet) {
		this.musicKeySet = musicKeySet;
	}

	/**
	 * @return the musicKeySet
	 */
	public MusicKeySet getMusicKeys() {
		return musicKeySet;
	}

	public void copyItemSelectionTo(PieceSearchMask target) {
		composers.copyItemSelectionTo(target.composers);
		catalogs.copyItemSelectionTo(target.catalogs);
		pieceTypeSet.copyItemSelectionTo(target.pieceTypeSet);
		publicationDateSet.copyItemSelectionTo(target.publicationDateSet);
	}

	public ItemSet getPublicationDates() {
		return publicationDateSet;
	}

	public void setPublicationDates(PublicationDateSet publicationDateSet) {
		this.publicationDateSet = publicationDateSet;
	}

}
