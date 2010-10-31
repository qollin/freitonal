package de.cr.freitonal.unittests.client.test.data;

public class JSONStrings {

	public static String getInstrumentationWithTwoInstrumentsString() {
		return "{'id': '42', 'nickname':'nickname', 'instruments': [['1', 'violin'], ['2', 'piano']]}";
	}

	public static String getInstrumentationSetWithTwoInstrumentationsString() {
		return "[" + getInstrumentationWithTwoInstrumentsString() + "," + getInstrumentationWithTwoInstrumentsString() + "]";
	}
}
