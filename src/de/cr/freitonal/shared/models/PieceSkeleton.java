package de.cr.freitonal.shared.models;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PieceSkeleton implements UID, IsSerializable {
	private String id;
	private String composerID;
	private String catalogID;
	private String catalogNameID;
	private String musicKeyID;
	private String typeID;
	private String subtitle;
	private String parentID;
	private String instrumentationID;
	private String publicationDate;

	@SuppressWarnings("unused")
	private PieceSkeleton() {
		//needed because of GWT Serialization
	}

	public PieceSkeleton(String id) {
		this.id = id;
	}

	public String getID() {
		return id;
	}

	public String getComposerID() {
		return composerID;
	}

	public void setComposerID(String composerID) {
		this.composerID = composerID;
	}

	public String getCatalogID() {
		return catalogID;
	}

	public void setCatalogID(String catalogID) {
		this.catalogID = catalogID;
	}

	public String getMusicKeyID() {
		return musicKeyID;
	}

	public void setMusicKeyID(String musicKeyID) {
		this.musicKeyID = musicKeyID;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getInstrumentationID() {
		return instrumentationID;
	}

	public void setInstrumentationID(String instrumentationID) {
		this.instrumentationID = instrumentationID;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public void setCatalogNameID(String catalogNameID) {
		this.catalogNameID = catalogNameID;
	}

	public String getCatalogNameID() {
		return catalogNameID;
	}

}
