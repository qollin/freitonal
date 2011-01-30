package de.cr.freitonal.unittests.client.rpc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.cr.freitonal.client.models.Set;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.usertests.client.test.data.TestData;

public class APieceSearchMaskShould {
	private static void assertItemSetEquals(Object item, Set set) {
		assertEquals(1, set.size());
		assertTrue(item + " is not present in " + set, set.contains(item));
	}

	@Test
	public void CreateItselfFromAVolatilePiece() {
		Piece piece = TestData.createPiece();
		PieceSearchMask pieceSearchMask = new PieceSearchMask(piece);

		assertItemSetEquals(piece.getComposer(), pieceSearchMask.getComposers());
		assertItemSetEquals(piece.getInstrumentation(), pieceSearchMask.getInstrumentations());
		assertItemSetEquals(piece.getPieceType(), pieceSearchMask.getPieceTypes());
		assertItemSetEquals(piece.getCatalog().getCatalogName(), pieceSearchMask.getCatalogs().getNames());
		assertItemSetEquals(piece.getCatalog().getCatalogNumber(), pieceSearchMask.getCatalogs().getNumbers());
		assertItemSetEquals(piece.getMusicKey(), pieceSearchMask.getMusicKeys());
		assertItemSetEquals(piece.getPublicationDate(), pieceSearchMask.getPublicationDates());
		assertItemSetEquals(piece.getSubtitle(), pieceSearchMask.getSubtitles());
	}

	@Test
	public void NotCreateItemsForNullEntries() {
		Piece piece = TestData.createPiece();
		piece.setSubtitle(null);
		PieceSearchMask pieceSearchMask = new PieceSearchMask(piece);

		assertEquals(0, pieceSearchMask.getSubtitles().size());
	}
}
