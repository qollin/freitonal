package de.cr.freitonal.unittests.shared.models;

import static de.cr.freitonal.usertests.client.test.data.TestData.AMajor;
import static de.cr.freitonal.usertests.client.test.data.TestData.Beethoven;
import static de.cr.freitonal.usertests.client.test.data.TestData.InstrumentationPiano;
import static de.cr.freitonal.usertests.client.test.data.TestData.Opus27_1;
import static de.cr.freitonal.usertests.client.test.data.TestData.Sonata;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.shared.models.Item;
import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.models.VolatilePiece;

public class APieceShould {
	private VolatilePiece vPiece;

	@Before
	public void setupVolatilePiece() {
		vPiece = new VolatilePiece(Beethoven, InstrumentationPiano, Sonata, Opus27_1, AMajor);
		vPiece.setOrdinal(new Item("1", "1"));
		vPiece.setParent(new Piece("2", vPiece));
		vPiece.setPublicationDate(new Item("1899", "1899"));
		vPiece.setSubtitle(new Item("The Tempest", "The Tempest"));
	}

	@Test
	public void InitializeAllFieldsFromAGivenVolatilePiece() {
		Piece piece = new Piece("1", vPiece);
		assertEquals(vPiece.getComposer(), piece.getComposer());
		assertEquals(vPiece.getInstrumentation(), piece.getInstrumentation());
		assertEquals(vPiece.getPieceType(), piece.getPieceType());
		assertEquals(vPiece.getCatalog(), piece.getCatalog());
		assertEquals(vPiece.getMusicKey(), piece.getMusicKey());
		assertEquals(vPiece.getOrdinal(), piece.getOrdinal());
		assertEquals(vPiece.getParent(), piece.getParent());
		assertEquals(vPiece.getPublicationDate(), piece.getPublicationDate());
		assertEquals(vPiece.getSubtitle(), piece.getSubtitle());
	}
}
