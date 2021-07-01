/*
 * MIT License

 * Copyright (c) 2021 cegredev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.cegredev.josi.detailed;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.github.cegredev.josi.detailed.WinOS.Release.*;
import static org.junit.jupiter.api.Assertions.*;

public class OSEqualsTests {

	private static final String IDENTICAL = "Completely identical operating systems did not equal each other!";
	private static final String LOGICALLY_EQUAL = "Two instances representing the same operating system did not" +
			" equal each other!";
	private static final String DIFFERENT = "Different operating systems equaled each other!";

	@Test
	public void winOSEqualsTest() {
		assertEquals(new WinOS(WIN_8_1, false),
				new WinOS(WIN_8_1, false), IDENTICAL);

		assertEquals(new WinOS(WIN_7, false),
				new WinOS(WIN_7, false), LOGICALLY_EQUAL);

		assertNotEquals(new WinOS(WIN_10, true),
				new WinOS(WIN_10, false), DIFFERENT);
	}

	@Test
	public void macOSEqualsTest() {
	}

	@Test
	public void linuxOSEqualsTest() {
		assertEquals(new LinuxOS(new HashMap<>(), LinuxOS.Distribution.UBUNTU),
				new LinuxOS(new HashMap<>(), LinuxOS.Distribution.UBUNTU), IDENTICAL);

		assertNotEquals(new LinuxOS(new HashMap<>(), LinuxOS.Distribution.DEBIAN),
				new LinuxOS(new HashMap<>(), LinuxOS.Distribution.UBUNTU), DIFFERENT);
	}

	@Test
	public void otherOSEqualsTest() {
		assertEquals(new OtherOS(OtherOS.Identity.SOLARIS),
				new OtherOS(OtherOS.Identity.SOLARIS), IDENTICAL);

		assertNotEquals(new OtherOS(OtherOS.Identity.SOLARIS),
				new OtherOS(OtherOS.Identity.UNKNOWN), DIFFERENT);
	}

}
