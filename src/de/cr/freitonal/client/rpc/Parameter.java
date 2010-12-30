package de.cr.freitonal.client.rpc;

public class Parameter {

	private final String key;
	private final String value;

	public Parameter(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		Parameter other = (Parameter) obj;
		return key.equals(other.key) && value.equals(other.value);
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
