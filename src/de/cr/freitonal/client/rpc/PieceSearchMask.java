package de.cr.freitonal.client.rpc;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.ComposerSet;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.models.PieceTypeSet;

public class PieceSearchMask {
	private ComposerSet composers;
	private CatalogSet catalogs;
	private PieceTypeSet pieceTypeSet;
	private InstrumentationSet instrumentationSet;

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

	public void copyItemSelectionTo(PieceSearchMask target) {
		composers.copyItemSelectionTo(target.composers);
		catalogs.copyItemSelectionTo(target.catalogs);
		pieceTypeSet.copyItemSelectionTo(target.pieceTypeSet);
		instrumentationSet.copyItemSelectionTo(target.instrumentationSet);
	}
}
