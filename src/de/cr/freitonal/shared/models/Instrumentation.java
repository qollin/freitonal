package de.cr.freitonal.shared.models;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

import de.cr.freitonal.client.utils.StringUtils;

public class Instrumentation extends VolatileInstrumentation implements UID, IsSerializable {
	private static final transient String SecondLevelSeparator = " und ";
	private static final transient String FirstLevelSeparator = ", ";
	private String id;
	private HashMap<Item, Integer> instrumentCounts;

	@SuppressWarnings("unused")
	private Instrumentation() {
		//needed because of GWT serialization
	}

	public Instrumentation(String id, String nickname, Item... instruments) {
		super(nickname, instruments);
		this.id = id;
		instrumentCounts = new HashMap<Item, Integer>();
	}

	public Instrumentation(String id, String nickname, ArrayList<Item> instruments) {
		super(nickname, instruments);
		this.id = id;
		instrumentCounts = new HashMap<Item, Integer>();
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
		if (StringUtils.isNotEmpty(nickname)) {
			return nickname;
		} else {
			return renderInstruments();
		}
	}

	private String renderInstruments() {
		if (instruments.size() == 1) {
			return renderInstrument(instruments.get(0));
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

	private String renderInstrument(Item instrument) {
		String name = instrument.getValue();

		if (instrumentCounts.containsKey(instrument)) {
			return instrumentCounts.get(instrument) + " " + name;
		} else {
			return name;
		}
	}

	private ArrayList<String> createValueList(ArrayList<Item> items, int from, int to) {
		ArrayList<String> strings = new ArrayList<String>();
		for (Item item : items.subList(from, to)) {
			strings.add(renderInstrument(item));
		}

		return strings;
	}

	public void setInstrumentCount(Item instrument, int count) {
		instrumentCounts.put(instrument, count);
	}

	public int getInstrumentCount(Item instrument) {
		if (instrumentCounts.containsKey(instrument)) {
			return instrumentCounts.get(instrument);
		} else {
			return 1;
		}
	}
}
