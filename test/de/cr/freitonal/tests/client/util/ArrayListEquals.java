package de.cr.freitonal.tests.client.util;

import java.util.ArrayList;

import org.easymock.IArgumentMatcher;

public class ArrayListEquals<T> implements IArgumentMatcher {
	private final ArrayList<T> expected;
	private int differingIndex = -1;
	private T differingValue = null;

	public ArrayListEquals(ArrayList<T> expected) {
		this.expected = expected;
	}

	@SuppressWarnings("unchecked")
	public boolean matches(Object actual) {
		if (expected == null) {
			return actual == null;
		}

		if (!(actual instanceof ArrayList<?>)) {
			return false;
		}
		ArrayList<T> actualArrayList = (ArrayList<T>) actual;

		if (expected.size() != actualArrayList.size()) {
			return false;
		}

		for (int i = 0; i < expected.size(); i++) {
			if (!expected.get(i).equals(actualArrayList.get(i))) {
				differingIndex = i;
				differingValue = actualArrayList.get(i);
				return false;
			}
		}

		return true;
	}

	public void appendTo(StringBuffer buffer) {
		buffer.append("eqArrayList: index ");
		buffer.append(differingIndex);
		if (differingIndex >= 0) {
			buffer.append(": " + expected.get(differingIndex) + " <=> " + differingValue);
		}
	}
}
