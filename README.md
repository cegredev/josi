# Java OS Independence
...or *Josi* for short, is a simple and lightweight Java library esigned to make making decisions based on the current operating system at runtime easier.

## Getting Started

Maven:

```
```

Gradle:

```
```

### How's it work?

The [OS](https://github.com/cegredev/josi/blob/main/src/main/java/io/github/cegredev/josi/OS.java) enum is the heart of the library and contains the current operating system, as well as a list of all that are available:

```java
OS os = OS.current();
```

...which can be anything from `WIN_95` to `WIN_10` to any Mac version to a Linux based system. This is as specific as it gets for this library.

The more useful information is the *family* of the operating system, i.e. `Windows`, `Mac`, `Linux` or `Other`. You can get it like this:

```java
OS.Family family = os.getFamily();
```

...and then use it to execute code based on it:

```java
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
```

This is pretty much all you need to know, but I strongly encourage you to take a look at the other examples below to explore some of the utility methods *Josi* has to offer!

### Examples

Check whether a operating system is a specific one:

```java
// Checks whether the OS is any of the given ones
boolean isHated = OS.current().is(OS.WIN_8, OS.WIN_8_1);

// Checks whether the OS is part of the given families
boolean isUnixBased = OS.current().isFamily(OS.Family.MAC, OS.Family.LINUX);
```

Enforce a specific operating system:

```java
// Throws an exception if the operating system is one of the given ones
OS.current().enforceNot(OS.WIN_95, OS.WIN_98, OS.WIN_VISTA);

// Throws an exception if the operating system family is not part of the given ones
OS.current().enforceFamily(OS.Family.WINDOWS, OS.Family.MAC);

// Only returns the value if the current operating system is Windows, otherwise throws an exception.
// Useful for one-liners where you want to assign a value and check for the OS in the same step.
boolean gamer = OS.current().pickWindows(true);
```

Choose a value based on the current operating systems's family:

```java
// Chooses the correct value based on the current operating system:
// On Windows: "Windows", on Mac: "Mac", on Linux: "Linux", on other: "Other"
String currentOS = OS.current().pick("Windows", "Mac", "Linux", "Other");

// Chooses the correct value between Mac, Linux or anything else, for example:
// On Windows: "other", on Mac: "creative", on Linux: "techy", on other: "other"
String character = OS.current().pickMacLinuxAny("creative", "techy", "other");

// Chooses the correct value between Linux and anything else, for example:
// On Linux: false, on Windows, Mac or anything else: true
boolean filthyCasual = OS.current().pickLinuxAny(false, true);

// There is one of these methods for every single combination, all following the same naming scheme,
// so you can probably even guess their names with looking at the... d o c u m e n t a t i o n .
```

Execute code based on the current operating system:

```java
// There are basically two ways to achieve this. The first and simplest is the following:
// Since we often don't care about which exact version of an operating system we are running, we are
// just using the family of the current OS, which can be one of the below values.
switch (OS.current().getFamily()) {
	case WINDOWS:
		System.out.println("Windows!");
		break;
	case MAC:
		System.out.println("Mac!");
		break;
	case LINUX:
		System.out.println("Linux!");
		break;
	case OTHER:
		System.out.println("Other!");
		break;
}

// If you need more control, you can of course switch on the OS itself as well. However, this is more
// error-prone as there are lots more enum constants for the OS datatype, so you should use the family
// whenever possible.
switch (OS.current()) {
	case WIN_95:
		System.out.println("Upgrade your PC, my god!");
		break;
	case WIN_8:
	case WIN_8_1:
		System.out.println("You are... special!");
		break;
	case WIN_7:
	case WIN_10:
		System.out.println("You are awesome!");
		break;
}
```

## Contributing
Here are the things you can do:

 - Add a new operating system for the OS enum. I sadly can't test and add every operating system there is myself, so I have to rely on the community to grow this aspect further.
 - Add a new utility method to the OS enum, but only if you are convinced that it can be useful in many circumstances, as we do not not want to bloat the libray.
 - Test the code on your machine, i.e. check if it actually returns the correct operating system for you.
 - Make suggestions, i.e. if you want to contribute larger changes to the project, create a pull requets or issue (beforehand) and if it fits, we'll implement it!

## Support

Feel free to open an issue or take a look at some examples!

## License

This project is licensed under the [Apache 2.0 license](https://github.com/cegredev/java-os-independence/blob/main/LICENSE).
