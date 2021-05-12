package io.github.cegredev.josi;

public class MacOS extends OperatingSystem {

	private final int major;
	private final int minor;

	public MacOS(int major, int minor) {
		super(CurrentOS.Family.MAC);

		this.major = major;
		this.minor = minor;
	}

	public int getMinor() {
		return minor;
	}

	public int getMajor() {
		return major;
	}

}
