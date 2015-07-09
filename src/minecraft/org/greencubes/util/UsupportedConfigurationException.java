package org.greencubes.util;

public class UsupportedConfigurationException extends RuntimeException {

	private static final long serialVersionUID = -3325269637335676774L;

	public UsupportedConfigurationException(String cause) {
		super("Ussupported computer configuration: " + cause);
	}
}
