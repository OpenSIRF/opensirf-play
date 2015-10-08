package models;

import java.util.ArrayList;

public class Extension {
	public Extension() {
		objectExtensionPairs = new ArrayList<ExtensionPair>();
	}

	public Extension(String objectExtensionOrganization, String objectExtensionDescription) {
		this();
		this.objectExtensionOrganization = objectExtensionOrganization;
		this.objectExtensionDescription = objectExtensionDescription;
	}

	public ArrayList<ExtensionPair> getObjectExtensionPairs() {
		return objectExtensionPairs;
	}

	public void setObjectExtensionPairs(ArrayList<ExtensionPair> objectExtensionPairs) {
		this.objectExtensionPairs = objectExtensionPairs;
	}

	public String getObjectExtensionOrganization() {
		return objectExtensionOrganization;
	}

	public void setObjectExtensionOrganization(String objectExtensionOrganization) {
		this.objectExtensionOrganization = objectExtensionOrganization;
	}

	public String getObjectExtensionDescription() {
		return objectExtensionDescription;
	}

	public void setObjectExtensionDescription(String objectExtensionDescription) {
		this.objectExtensionDescription = objectExtensionDescription;
	}

	private ArrayList<ExtensionPair> objectExtensionPairs;
	private String objectExtensionOrganization;
	private String objectExtensionDescription;

}
