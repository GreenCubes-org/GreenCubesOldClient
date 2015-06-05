package org.greencubes.util;

public class UsupportedConfigurationException extends RuntimeException {

	public UsupportedConfigurationException(String cause) {
		super("Ussupported computer configuration: " + cause);
	}
}
