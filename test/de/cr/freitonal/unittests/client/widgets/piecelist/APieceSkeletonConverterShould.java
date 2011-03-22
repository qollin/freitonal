package de.cr.freitonal.unittests.client.widgets.piecelist;

import static de.cr.freitonal.usertests.client.test.data.TestData.AMajor;
import static de.cr.freitonal.usertests.client.test.data.TestData.Eroica;
import static de.cr.freitonal.usertests.client.test.data.TestData.InstrumentationViolinPlusPiano;
import static de.cr.freitonal.usertests.client.test.data.TestData.Mozart;
import static de.cr.freitonal.usertests.client.test.data.TestData.Opus27_1;
import static de.cr.freitonal.usertests.client.test.data.TestData.Sonata;
import static de.cr.freitonal.usertests.client.test.data.TestData.Year1799;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.client.models.CatalogSet;
import de.cr.freitonal.client.models.ComposerSet;
import de.cr.freitonal.client.models.InstrumentationSet;
import de.cr.freitonal.client.models.ItemSet;
import de.cr.freitonal.client.models.MusicKeySet;
import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.models.PublicationDateSet;
import de.cr.freitonal.client.models.SubtitleSet;
import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.client.widgets.piecelist.PieceSkeletonConverter;
import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.models.PieceSkeleton;

public class APieceSkeletonConverterShould {
	private PieceSkeletonConverter converter;
	private PieceSkeleton skeleton;
	private PieceSearchMask pieceSearchMask;

	@Before
	public void setupPieceSkeletonConverter() {
		pieceSearchMask = new PieceSearchMask();
		converter = new PieceSkeletonConverter(pieceSearchMask);

		skeleton = new PieceSkeleton("42");
	}

	@Test
	public void convertAnEmptyPieceSkeleton() {
		Piece piece = converter.convertPieceSkeleton(skeleton);

		assertEquals(piece.getID(), skeleton.getID());
		assertNull(piece.getComposer());
	}

	@Test
	public void convertAPieceSkeletonWithAComposer() {
		skeleton.setComposerID(Mozart.getID());
		pieceSearchMask.setComposers(new ComposerSet(Mozart));

		Piece piece = converter.convertPieceSkeleton(skeleton);
		assertEquals(Mozart, piece.getComposer());
	}

	@Test
	public void ThrowAnExceptioWhenAComposerCannotBeFound() {
		skeleton.setComposerID(Mozart.getID());
		try {
			converter.convertPieceSkeleton(skeleton);
			fail();
		} catch (IllegalStateException e) {
		}
	}

	@Test
	public void convertAPieceSkeletonWithACatalog() {
		skeleton.setCatalogID(Opus27_1.getID());
		skeleton.setCatalogNameID(Opus27_1.getCatalogName().getID());
		pieceSearchMask.setCatalogs(new CatalogSet(new ItemSet(Opus27_1.getCatalogName()), new ItemSet(Opus27_1.getCatalogNumber())));

		Piece piece = converter.convertPieceSkeleton(skeleton);
		assertEquals(Opus27_1, piece.getCatalog());
	}

	@Test
	public void convertAPieceSkeletonWithAMusicKey() {
		skeleton.setMusicKeyID(AMajor.getID());
		pieceSearchMask.setMusicKeys(new MusicKeySet(AMajor));

		Piece piece = converter.convertPieceSkeleton(skeleton);
		assertEquals(AMajor, piece.getMusicKey());
	}

	@Test
	public void convertAPieceSkeletonWithATypeID() {
		skeleton.setTypeID(Sonata.getID());
		pieceSearchMask.setPieceTypes(new PieceTypeSet(new ItemSet(Sonata), new ItemSet()));

		Piece piece = converter.convertPieceSkeleton(skeleton);
		assertEquals(Sonata, piece.getPieceType());
	}

	@Test
	public void convertAPieceSkeletonWithASubtitle() {
		skeleton.setSubtitle(Eroica.getValue());
		pieceSearchMask.setSubtitles(new SubtitleSet(Eroica));

		Piece piece = converter.convertPieceSkeleton(skeleton);
		assertEquals(Eroica, piece.getSubtitle());
	}

	@Test
	public void convertAPieceSkeletonWithAnInstrumentation() {
		skeleton.setInstrumentationID(InstrumentationViolinPlusPiano.getID());
		pieceSearchMask.setInstrumentations(new InstrumentationSet(InstrumentationViolinPlusPiano));

		Piece piece = converter.convertPieceSkeleton(skeleton);
		assertEquals(InstrumentationViolinPlusPiano, piece.getInstrumentation());
	}

	@Test
	public void convertAPieceSkeletonWithAPublicationDate() {
		skeleton.setPublicationDate(Year1799.getValue());
		pieceSearchMask.setPublicationDates(new PublicationDateSet(Year1799));

		Piece piece = converter.convertPieceSkeleton(skeleton);
		assertEquals(Year1799, piece.getPublicationDate());
	}

	@Test
	public void ShouldBeResilientAgainstEmptyPublicationDates() {
		skeleton.setPublicationDate("");
		pieceSearchMask.setPublicationDates(new PublicationDateSet(Year1799));

		Piece piece = converter.convertPieceSkeleton(skeleton);
		assertNull(piece.getPublicationDate());
	}

}
