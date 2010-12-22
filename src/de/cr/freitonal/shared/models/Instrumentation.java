package de.cr.freitonal.shared.models;

import java.util.ArrayList;

import de.cr.freitonal.client.utils.StringUtils;

public class Instrumentation extends VolatileInstrumentation {
	private static final String SecondLevelSeparator = " und ";
	private static final String FirstLevelSeparator = ", ";
	private final String id;

	public Instrumentation(String id, String nickname, Item... instruments) {
		super(nickname, instruments);
		this.id = id;
	}

	public Instrumentation(String id, String nickname, ArrayList<Item> instruments) {
		super(nickname, instruments);
		this.id = id;
	}

	public Instrumentation(String id, VolatileInstrumentation vol) {
		this(id, vol.getNickname(), vol.getInstruments());
	}

	public String getID() {
		return id;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		return id.equals(((Instrumentation) other).id);
	};

	@Override
	public String toString() {
		String nickname = getNickname();
		if (StringUtils.isNotEmpty(nickname)) {
			return nickname;
		} else {
			return renderInstruments();
		}
	}

	private String renderInstruments() {
		ArrayList<Item> instruments = getInstruments();
		if (instruments.size() == 1) {
			return instruments.get(0).getValue();
		} else {
			int beforeLastPos = instruments.size() - 2;
			String lastTwoInstruments = StringUtils.join(createValueList(instruments, beforeLastPos, instruments.size()), SecondLevelSeparator);
			if (instruments.size() == 2) {
				return lastTwoInstruments;
			} else {
				String firstInstruments = StringUtils.join(createValueList(instruments, 0, beforeLastPos), FirstLevelSeparator);
				return firstInstruments + FirstLevelSeparator + lastTwoInstruments;
			}

		}
	}

	private ArrayList<String> createValueList(ArrayList<Item> items, int from, int to) {
		ArrayList<String> strings = new ArrayList<String>();
		for (Item item : items.subList(from, to)) {
			strings.add(item.getValue());
		}

		return strings;
	}
}
