package de.cr.freitonal.unittests.shared.renderer;

import static de.cr.freitonal.usertests.client.test.data.TestData.Beethoven;
import static de.cr.freitonal.usertests.client.test.data.TestData.Sonata;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.models.VolatilePiece;
import de.cr.freitonal.shared.renderer.InstrumentationRenderer;
import de.cr.freitonal.shared.renderer.PieceRenderer;
import de.cr.freitonal.usertests.client.test.data.TestData;

public class APieceRendererShould {

	private PieceRenderer pieceRenderer;
	private VolatilePiece vPiece;

	@Before
	public void setupPieceRenderer() {
		pieceRenderer = new PieceRenderer();
		vPiece = new VolatilePiece();
	}

	@Test
	public void StartWithTheComposerOfThePiece() {
		vPiece.setComposer(Beethoven);
		String pieceTitle = pieceRenderer.render(new Piece("1", vPiece));
		assertTrue(pieceTitle.startsWith(Beethoven.getValue()));
	}

	@Test
	public void IncludeThePieceTypeAfterADash() {
		vPiece.setPieceType(Sonata);
		String pieceTitle = pieceRenderer.render(new Piece("1", vPiece));
		assertTrue(pieceTitle.contains(" - " + Sonata.getValue()));
	}

	@Test
	public void IncludeTheInstrumentation() {
		vPiece.setPieceType(Sonata);
		vPiece.setInstrumentation(TestData.InstrumentationViolinPlusPiano);
		String pieceTitle = pieceRenderer.render(new Piece("1", vPiece));
		String instrumentationTitle = new InstrumentationRenderer().render(TestData.InstrumentationViolinPlusPiano);
		assertTrue(pieceTitle.contains(" für " + instrumentationTitle));
	}
}
