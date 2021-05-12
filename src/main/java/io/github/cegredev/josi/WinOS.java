package io.github.cegredev.josi;

public class WinOS extends OperatingSystem {

	private final boolean server;
	private final Version version;

	public WinOS(Version version, boolean server) {
		super(CurrentOS.Family.WINDOWS);

		this.version = version;
		this.server = server;
	}

	public Version getVersion() {
		return version;
	}

	public boolean isServer() {
		return server;
	}

	public enum Version {

		WIN_95(), WIN_98(), WIN_XP(), WIN_VISTA(), WIN_7(), WIN_8(), WIN_8_1(), WIN_10(), UNKNOWN

	}

}
