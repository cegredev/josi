package io.github.cegredev.josi;

import org.junit.jupiter.api.Test;

import static io.github.cegredev.josi.WinOS.Version.*;
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
					CurrentOS.determine(test.name, test.version, null), MESSAGE);
	}

	private static class WDT {

		private final boolean server;
		private final WinOS.Version expectedVersion;

		private final String name, version;

		private WDT(WinOS.Version expectedVersion, boolean server, String name, String version) {
			this.expectedVersion = expectedVersion;
			this.server = server;
			this.name = name;
			this.version = version;
		}

		private WDT(WinOS.Version expectedVersion, boolean server, String name) {
			this(expectedVersion, server, name, "");
		}

		private WDT(WinOS.Version expectedVersion, String name) {
			this(expectedVersion, false, name);
		}

	}

}
