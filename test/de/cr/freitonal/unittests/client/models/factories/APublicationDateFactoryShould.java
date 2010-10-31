package de.cr.freitonal.unittests.client.models.factories;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.cr.freitonal.client.models.PublicationDateSet;
import de.cr.freitonal.client.models.factories.PublicationDateSetFactory;
import de.cr.freitonal.client.rpc.dto.DTOObject;
import de.cr.freitonal.shared.models.Item;

public class APublicationDateFactoryShould extends FactoryTest {
	@Test
	public void ReturnACorrectPublicationDateSet() {
		DTOObject jsonObject = parse("{'piece-publication_date': [['1809', '1809']]}");
		PublicationDateSetFactory factory = new PublicationDateSetFactory();
		PublicationDateSet publicationDateSet = factory.createPublicationDateSet(jsonObject);

		assertEquals(new Item("1809", "1809"), publicationDateSet.getItem(0));
	}
}
