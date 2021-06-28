package io.github.cegredev.josi.detailed;

import io.github.cegredev.josi.detailed.LinuxOS;
import io.github.cegredev.josi.detailed.OtherOS;
import io.github.cegredev.josi.detailed.WinOS;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.github.cegredev.josi.detailed.WinOS.Version.*;
import static org.junit.jupiter.api.Assertions.*;

public class OSEqualsTests {

	private static final String IDENTICAL = "Completely identical operating systems did not equal each other!";
	private static final String LOGICALLY_EQUAL = "Two instances representing the same operating system did not" +
			" equal each other!";
	private static final String DIFFERENT = "Different operating systems equaled each other!";

	@Test
	public void winOSEqualsTest() {
		assertEquals(new WinOS("Windows 8.1", "", WIN_8_1, false),
				new WinOS("Windows 8.1", "", WIN_8_1, false), IDENTICAL);

		assertEquals(new WinOS("Windows 7", "", WIN_7, false),
				new WinOS("Win 7", "", WIN_7, false), LOGICALLY_EQUAL);

		assertNotEquals(new WinOS("Windows 10", "", WIN_10, true),
				new WinOS("Windows 10", "", WIN_10, false), DIFFERENT);
	}

	@Test
	public void macOSEqualsTest() {
	}

	@Test
	public void linuxOSEqualsTest() {
		assertEquals(new LinuxOS("Ubuntu", "", new HashMap<>(), LinuxOS.Distribution.UBUNTU),
				new LinuxOS("Ubuntu", "", new HashMap<>(), LinuxOS.Distribution.UBUNTU), IDENTICAL);

		assertNotEquals(new LinuxOS("Debian", "", new HashMap<>(), LinuxOS.Distribution.DEBIAN),
				new LinuxOS("Ubuntu", "", new HashMap<>(), LinuxOS.Distribution.UBUNTU), DIFFERENT);
	}

	@Test
	public void otherOSEqualsTest() {
		assertEquals(new OtherOS("Sunos", "", OtherOS.OS.SOLARIS),
				new OtherOS("Sunos", "", OtherOS.OS.SOLARIS), IDENTICAL);

		assertNotEquals(new OtherOS("Sunos", "", OtherOS.OS.SOLARIS),
				new OtherOS("nope", "", OtherOS.OS.UNKNOWN), DIFFERENT);
	}

}
