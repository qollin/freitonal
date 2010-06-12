package de.cr.freitonal.unittests.client.util;

import java.util.ArrayList;

import org.easymock.IArgumentMatcher;

public class SizeOfArrayList<T> implements IArgumentMatcher {
	private final int expected;

	public SizeOfArrayList(int expected) {
		this.expected = expected;
	}

	public void appendTo(StringBuffer buffer) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public boolean matches(Object argument) {
		if (argument == null) {
			return false;
		}
		if (!(argument instanceof ArrayList<?>)) {
			return false;
		}

		return ((ArrayList<T>) argument).size() == expected;
	}

}
