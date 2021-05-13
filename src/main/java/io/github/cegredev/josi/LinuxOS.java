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
package io.github.cegredev.josi;

public class LinuxOS extends OperatingSystem {

	private final Distribution distro;

	public LinuxOS(Distribution distro) {
		super(CurrentOS.Family.LINUX);

		this.distro = distro;
	}

	public Distribution getDistro() {
		return distro;
	}

	public enum Distribution {

		DEBIAN, UBUNTU, GENTOO, LINUX_MINT, RED_HAT_ENTERPRISE_LINUX, CENTOS, FEDORA, ARCH_LINUX,
		/**
		 * Suse/OpenSUSE and any child-distributions.
		 */
		SUSE,
		/**
		 * An unknown or at least unrecognizable Linux based operating system.
		 */
		UNKNOWN;

		public static Distribution fromID(String id) {
			switch (id) {
				// All the OSs up to (inclusive) SUSE can be found in this GitHub repo or its forks:
				// https://gist.github.com/natefoo/814c5bf936922dad97ff
				// Additional links for validation were also added to some

				// https://blog.thewatertower.org/2020/01/07/stash-of-etc-os-release-files/
				case "debian":
					return DEBIAN;
				case "ubuntu":
					return UBUNTU;
				case "centos":
					return CENTOS;
				case "fedora":
					return FEDORA;
				case "arch":
					return ARCH_LINUX;
				// https://gitweb.gentoo.org/proj/baselayout.git/tree/etc.Linux/os-release
				// https://gist.github.com/Wuodan/52d9761a77331ca3b8d044a50b910f52
				case "gentoo":
					return GENTOO;
				// https://en.opensuse.org/SDB:SUSE_and_openSUSE_Products_Version_Outputs
				case "suse":
				case "opensuse":
					return SUSE;

				// https://itsfoss.com/check-linux-mint-version/
				case "linuxmint":
					return LINUX_MINT;
				// https://www.cyberciti.biz/faq/what-version-of-redhat-linux-am-i-running/
				case "rhel":
					return RED_HAT_ENTERPRISE_LINUX;
				default:
					return UNKNOWN;
			}
		}

	}

}
