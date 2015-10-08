package models;

public class ExtensionPair {
	public ExtensionPair() {
	}

	public ExtensionPair(String objectExtensionKey, String objectExtensionValue) {
		this.objectExtensionKey = objectExtensionKey;
		this.objectExtensionValue = objectExtensionValue;
	}

	public String getObjectExtensionKey() {
		return objectExtensionKey;
	}

	public void setObjectExtensionKey(String objectExtensionKey) {
		this.objectExtensionKey = objectExtensionKey;
	}

	public String getObjectExtensionValue() {
		return objectExtensionValue;
	}

	public void setObjectExtensionValue(String objectExtensionValue) {
		this.objectExtensionValue = objectExtensionValue;
	}

	private String objectExtensionKey;
	private String objectExtensionValue;
}
