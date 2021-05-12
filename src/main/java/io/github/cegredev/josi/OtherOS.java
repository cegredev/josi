package io.github.cegredev.josi;

public class OtherOS extends OperatingSystem {

	private final OS os;

	public OtherOS(OS os) {
		super(CurrentOS.Family.OTHER);

		this.os = os;
	}

	public OS getOS() {
		return os;
	}

	public enum OS {

		/**
		 * The Solaris operating system.
		 */
		SOLARIS(),
		/**
		 * An operating system that cannot be classified.
		 */
		UNKNOWN()

	}

}
