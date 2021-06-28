package io.github.cegredev.josi.detailed;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.github.cegredev.josi.detailed.WinOS.Version.*;
import static org.junit.jupiter.api.Assertions.*;

public class OSDetermineTests {

	private static final String MESSAGE = "Did not determine correct operating system!";

	private static final WDT[] WIN_TESTS = {new WDT(WinOS.Version.UNKNOWN, "Windows 1.0"),
			new WDT(WIN_95, "Windows 95"), new WDT(WIN_98, "Windows 98"), new WDT(WIN_XP, "Windows XP"),
			new WDT(WIN_VISTA, "Windows Vista"), new WDT(WIN_7, "Windows 7"), new WDT(WIN_8, "Windows 8"),
			new WDT(WIN_8_1, "Windows 8.1"), new WDT(WIN_10, "Windows 10")};

	@Test
	public void testWinDetermine() {
		for (WDT test : WIN_TESTS)
			assertEquals(new WinOS(test.name, test.version, test.expectedVersion, test.server),
					OS.determine(test.name, test.version, null), MESSAGE);
	}

	private static class ODT {

		protected final String name, version;

		private ODT(String name, String version) {
			this.name = name.toLowerCase(Locale.ROOT).trim();
			this.version = version;
		}
	}

	private static class WDT extends ODT {

		private final boolean server;
		private final WinOS.Version expectedVersion;

		private WDT(WinOS.Version expectedVersion, boolean server, String name, String version) {
			super(name, version);

			this.expectedVersion = expectedVersion;
			this.server = server;
		}

		private WDT(WinOS.Version expectedVersion, boolean server, String name) {
			this(expectedVersion, server, name, "");
		}

		private WDT(WinOS.Version expectedVersion, String name) {
			this(expectedVersion, false, name);
		}

	}

}
