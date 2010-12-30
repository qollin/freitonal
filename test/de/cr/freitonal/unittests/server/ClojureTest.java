package de.cr.freitonal.unittests.server;

import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Map;

import org.junit.Before;

import clojure.lang.RT;
import clojure.lang.Var;

public class ClojureTest {
	private static HashSet<String> init = new HashSet<String>();
	private final String testPackage;
	private final String packagePrefix = "de.cr.freitonal.unittests.server.";

	public ClojureTest(String testPackage) {
		this.testPackage = packagePrefix + testPackage;
	}

	@Before
	public void setUp() {
		if (init.contains(testPackage)) {
			return;
		}
		try {
			RT.loadResourceScript(testPackage.replace(".", "/") + ".clj");
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		init.add(testPackage);
	}

	public void testAll() {
		Var runTests = RT.var(packagePrefix + "testtools", "runTests");
		try {
			@SuppressWarnings("rawtypes")
			Map map = (Map) runTests.invoke(testPackage);
			if (map == null || (map.get(":fail") != null && (Integer) map.get(":fail") > 0)
					|| (map.get(":error") != null && (Integer) map.get(":error") > 0)) {
				fail();
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("an exception was thrown: " + e);
		}
	}
}
