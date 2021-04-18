package main;

import io.github.cegredev.josi.OS;

public class HowsItWork {

	public static void likeThis() {
		// The io.github.cegredev.josi.OS enum is the heart of the library. It contains the current operating system:
		OS os = OS.current();
		// ...which can be anything from WIN_95 to WIN_10 to any Mac version to a Linux based system. This is as
		// specific as it gets for this library.

		// The more useful information is the *family* of the operating system, i.e. Windows, Mac, Linux or Other
		// You can get it like this:
		OS.Family family = os.getFamily();

		// ...and the use it to execute code based on it:
		switch (family) {
			case WINDOWS:
				// ...
			case MAC:
				// ...
			case LINUX:
				// ...
			case OTHER:
				// ...
		}

		// This is pretty much all you need to know, but I strongly encourage you to take a look at the other examples
		// to explore some of the utility methods Josi has to offer!
	}

}
