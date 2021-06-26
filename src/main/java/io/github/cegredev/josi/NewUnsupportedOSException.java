package io.github.cegredev.josi;

/**
 * Indicates that something about the given {@link OperatingSystem} was unexpected.
 *
 * @author cegredev
 */
public class NewUnsupportedOSException extends RuntimeException {

	public NewUnsupportedOSException(OperatingSystem operatingSystem) {
		super("Unsupported operating system: " + operatingSystem);
	}

}
