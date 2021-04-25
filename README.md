[![Maven Central](https://img.shields.io/maven-central/v/io.github.cegredev/josi.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.cegredev%22%20AND%20a:%22josi%22)
[![javadoc](https://javadoc.io/badge2/io.github.cegredev/josi/javadoc.svg)](https://javadoc.io/doc/io.github.cegredev/josi)
# Java OS Independence
...or *JOSI* for short, is a simple and lightweight Java library designed to make making decisions based on the current operating system easier.

## Getting Started

Maven:

```xml
<dependency>
  <groupId>io.github.cegredev</groupId>
  <artifactId>josi</artifactId>
  <version>0.3.0</version>
</dependency>
```

Gradle:

```gradle
dependencies {
  implementation 'io.github.cegredev:josi:0.3.0'
}
```

Or download the jars directly from [Maven Central](https://search.maven.org/artifact/io.github.cegredev/josi).

### How's it work?

The [OS](https://javadoc.io/page/io.github.cegredev/josi/latest/io/github/cegredev/josi/OS.html) enum is the heart of the library and contains the current operating system:

```java
OS os = OS.current();
```

...which can be anything from `WIN_95` to `WIN_10` to `MAC` to `LINUX` (the goal is to add more detailed Mac and Linux versions with help of the open source community). This is as specific as it gets for this library.

The more useful information is the [Family](https://javadoc.io/page/io.github.cegredev/josi/latest/io/github/cegredev/josi/OS.Family.html) of the operating system, i.e. `WINDOWS`, `MAC`, `LINUX` or `OTHER`. You can get it like this:

```java
OS.Family family = os.getFamily();
```

...and then use it to execute code based on it like this:

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

This is pretty much all you need to know, but I strongly encourage you to take a look at the other examples below and the [documentation](https://javadoc.io/doc/io.github.cegredev/josi) to explore some of the utility methods *JOSI* has to offer!

### Examples

Check whether an operating system is a specific one:

```java
// Checks whether the OS is any of the given ones
boolean isHated = OS.current().is(OS.WIN_8, OS.WIN_8_1);

// Checks whether the OS is part of the given families
boolean isUnixBased = OS.current().isFamily(OS.Family.MAC, OS.Family.LINUX);
```

Enforce a specific operating system:

```java
// Throws an exception if the operating system is one of the given ones
OS.current().enforceNot(OS.WIN_95, OS.WIN_98, OS.WIN_XP);

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

Here are some of the things you could do:

Add a new operating system for the [OS enum](https://github.com/cegredev/josi/blob/main/src/main/java/io/github/cegredev/josi/OS.java): If you happen to be running one not present already, please add it! Since I'm a solo-developer running Windows, I can't possibly test and add every single one there is, so every little bit of help is appreciated! If you want to know more, take a look at [this guide](https://github.com/cegredev/josi/wiki/How-to-add-an-operating-system-to-the-OS-enum) (work in progress!).

Add a new utility method to the OS enum, but only if you are convinced that it can be useful in many circumstances, as the library should be kept lightweight and not be bloated.

Test the code on your machine, i.e. check if it actually returns the correct operating system for you.

Make suggestions, i.e. if you want to contribute larger changes to the project, create a pull requets or issue and if it fits, I'll be glad to implement it!

## Support

Take a look at the [documentation](https://javadoc.io/doc/io.github.cegredev/josi) or feel free to open an issue, I'm happy to help!

## License

This project is licensed under the [MIT License](https://github.com/cegredev/java-os-independence/blob/main/LICENSE), but if you don't credit me in your project, that's fine.

I do not believe this library is a unique or noteworthy accomplishment, but rather just an attempt at standardizing something people did on their own for a long time anyways and therefore don't feel like I deserve any more more credit than owning the repository. On the other hand I ask you to please contribute any changes you make to the code and others may benefit from back to the project, so it can continue to grow.

Note: The first major release `0.1.0` was licensed under the Apache 2.0 license by accident and although the abovementioned philosophy is the same, the legal aspects of course still apply in theory, so keep that in mind.
