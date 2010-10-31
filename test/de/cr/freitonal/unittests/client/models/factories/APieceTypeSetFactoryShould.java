package de.cr.freitonal.unittests.client.models.factories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.cr.freitonal.client.models.PieceTypeSet;
import de.cr.freitonal.client.models.factories.PieceTypeSetFactory;
import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.shared.models.Item;

public class APieceTypeSetFactoryShould extends FactoryTest {
	@Test
	public void ReturnACorrectPieceType() {
		DTOObject jsonObject = parse("{'piece-piece_type': [['1', 'Quartett']], 'piece-type+instrumentation': [['1', 'Streichquartett']]}");

		PieceTypeSetFactory factory = new PieceTypeSetFactory();
		PieceTypeSet pieceTypeSet = factory.createPieceTypeSet(jsonObject);

		assertEquals(new Item("1", "Quartett"), pieceTypeSet.getPieceTypes().getItem(0));
		assertEquals(new Item("1", "StreichQuartett"), pieceTypeSet.getPiecePlusInstrumentationTypes().getItem(0));
	}
}
