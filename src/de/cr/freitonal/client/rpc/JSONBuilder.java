package de.cr.freitonal.client.rpc;

public class JSONBuilder extends AbstractBuilder {
	private String parameters = "";
	private boolean firstParameter = true;
	private final URLEncoder urlEncoder;

	public JSONBuilder(PieceSearchMask searchMask, URLEncoder urlEncoder) {
		super(searchMask);
		this.urlEncoder = urlEncoder;
		addParameters();
	}

	public String getHTTPParameters() {
		return parameters;
	}

	@Override
	protected void addHTTPParameter(String key, String value) {
		addHTTPParameter(urlEncoder.encodeParam(key) + "=" + urlEncoder.encodeParam(value));
	}

	private void addHTTPParameter(String httpParameter) {
		parameters += (firstParameter ? "?" : "&") + httpParameter;
		firstParameter = false;
	}

}
