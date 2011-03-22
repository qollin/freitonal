package de.cr.freitonal.client.widgets.piecelist;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.ComposerSet;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.models.MusicKeySet;
import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.models.PublicationDateSet;
import de.cr.freitonal.client.models.SubtitleSet;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.shared.models.Catalog;
import de.cr.freitonal.shared.models.Instrumentation;
import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.models.PieceSkeleton;
import de.cr.freitonal.shared.models.VolatilePiece;

public class PieceSkeletonConverter {

	private final PieceSearchMask pieceSearchMask;

	public PieceSkeletonConverter(PieceSearchMask pieceSearchMask) {
		this.pieceSearchMask = pieceSearchMask;
	}

	public Piece convertPieceSkeleton(PieceSkeleton skeleton) {
		VolatilePiece piece = new VolatilePiece();
		addComposerToPiece(skeleton, piece);
		addCatalogToPiece(skeleton, piece);
		addMusicKeyToPiece(skeleton, piece);
		addPieceTypeToPiece(skeleton, piece);
		addSubtitleToPiece(skeleton, piece);
		addInstrumentationToPiece(skeleton, piece);
		addPublicationDateToPiece(skeleton, piece);

		return new Piece(skeleton.getID(), piece);
	}

	private void addComposerToPiece(PieceSkeleton skeleton, VolatilePiece piece) {
		if (skeleton.getComposerID() != null) {
			ComposerSet composers = pieceSearchMask.getComposers();

			Item composer = findItem(skeleton.getComposerID(), composers);
			piece.setComposer(composer);
		}
	}

	private void addCatalogToPiece(PieceSkeleton skeleton, VolatilePiece piece) {
		if (skeleton.getCatalogID() != null) {
			CatalogSet catalogs = pieceSearchMask.getCatalogs();

			Item number = findItem(skeleton.getCatalogID(), catalogs.getNumbers());
			Item name = findItem(skeleton.getCatalogNameID(), catalogs.getNames());
			piece.setCatalog(new Catalog(name, number));
		}
	}

	private void addMusicKeyToPiece(PieceSkeleton skeleton, VolatilePiece piece) {
		if (skeleton.getMusicKeyID() != null) {
			MusicKeySet musicKeys = pieceSearchMask.getMusicKeys();

			Item musicKey = findItem(skeleton.getMusicKeyID(), musicKeys);
			piece.setMusicKey(musicKey);
		}
	}

	private void addPieceTypeToPiece(PieceSkeleton skeleton, VolatilePiece piece) {
		if (skeleton.getTypeID() != null) {
			PieceTypeSet pieceTypes = pieceSearchMask.getPieceTypes();

			Item pieceType = findItem(skeleton.getTypeID(), pieceTypes.getPieceTypes());
			piece.setPieceType(pieceType);
		}
	}

	private void addSubtitleToPiece(PieceSkeleton skeleton, VolatilePiece piece) {
		if (notEmpty(skeleton.getSubtitle())) {
			SubtitleSet subtitles = pieceSearchMask.getSubtitles();

			Item subtitle = findItem(skeleton.getSubtitle(), subtitles);
			piece.setSubtitle(subtitle);
		}
	}

	private void addInstrumentationToPiece(PieceSkeleton skeleton, VolatilePiece piece) {
		if (skeleton.getInstrumentationID() != null) {
			InstrumentationSet instrumentations = pieceSearchMask.getInstrumentations();

			Instrumentation instrumentation = findInstrumentation(skeleton.getInstrumentationID(), instrumentations);
			piece.setInstrumentation(instrumentation);
		}
	}

	private void addPublicationDateToPiece(PieceSkeleton skeleton, VolatilePiece piece) {
		if (notEmpty(skeleton.getPublicationDate())) {
			PublicationDateSet publicationDates = pieceSearchMask.getPublicationDates();

			Item publicationDate = findItem(skeleton.getPublicationDate(), publicationDates);
			piece.setPublicationDate(publicationDate);
		}
	}

	private Instrumentation findInstrumentation(String id, InstrumentationSet instrumentationSet) {
		if (instrumentationSet != null) {
			for (Instrumentation instrumentation : instrumentationSet.getInstrumentations()) {
				if (instrumentation.getID().equals(id)) {
					return instrumentation;
				}
			}
		}
		throw new IllegalStateException("did not find id " + id + " in the instrumentation set");
	}

	private Item findItem(String id, ItemSet itemSet) {
		if (itemSet != null) {
			for (Item item : itemSet.getItems()) {
				if (item.getID().equals(id)) {
					return item;
				}
			}
		}
		throw new IllegalStateException("did not find id " + id + " in the item set");
	}

	private boolean notEmpty(String s) {
		return s != null && !s.equals("");
	}

}
