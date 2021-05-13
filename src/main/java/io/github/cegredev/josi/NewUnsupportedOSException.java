package io.github.cegredev.josi;

public class NewUnsupportedOSException extends RuntimeException {

	public NewUnsupportedOSException(OperatingSystem operatingSystem) {
		super("Unsupported operating system: " + operatingSystem);
	}

}
