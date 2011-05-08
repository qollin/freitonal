package de.cr.freitonal.unittests.server;

import static org.junit.Assert.fail;

import java.sql.DriverManager;

import org.junit.Test;

public class DBSanityCheck {
	@Test
	public void checkIfConnectionToDBIsPossible() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/freitonaltest", "freitonal", "atonalistdoof");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

}
