package io.github.cegredev.josi;

/**
 * Indicates that something about the given {@link OperatingSystem} was unexpected.
 *
 * @author cegredev
 */
public class UnsupportedOSException extends RuntimeException {

	public UnsupportedOSException(OperatingSystem operatingSystem) {
		super("Unsupported operating system: " + operatingSystem);
	}

}
