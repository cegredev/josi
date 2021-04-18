package io.github.cegredev.josi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// I really just do this for the coverage
public class UnsupportedOSExceptionTests {

	@Test
	public void testIncludesOSName() {
		assertTrue(new UnsupportedOSException(OS.WIN_7).getMessage().contains(OS.WIN_7.toString()));
	}

	@Test
	public void testUsesCurrent() {
		assertTrue(new UnsupportedOSException().getMessage().contains(OS.current().toString()));
	}

}
