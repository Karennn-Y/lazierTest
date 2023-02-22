package com.example.laziertest.dto.user;

import java.util.Map;

public class GoogleUserInfo {

	private final Map<String, Object> attributes;
	private String getProviderId;
	private String getProvider;
	private String getUserEmail;

	public GoogleUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getProviderId() {
		return String.valueOf(attributes.get("sub"));
	}

	public String getUserEmail() {
		return String.valueOf(attributes.get("email"));
	}

	public String getProvider() {
		return "google";
	}

	public String getName() {
		return String.valueOf(attributes.get("name"));
	}
}
