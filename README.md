# Java OS Independence
...or Josi for short, is a simple and lightweight Java library esigned to make making decisions based on the current operating system at runtime easier.

## Getting Started

Maven:

```
```

Gradle:

```
```

### How's it work?

The [OS](https://github.com/cegredev/josi/blob/main/src/main/java/io/github/cegredev/josi/OS.java) enum is the heart of the library. It contains the current operating system:
```
OS os = OS.current();
```
...which can be anything from `WIN_95` to `WIN_10` to any Mac version to a Linux based system. This is as specific as it gets for this library.

The more useful information is the *family* of the operating system, i.e. Windows, Mac, Linux or Other. You can get it like this:
```
OS.Family family = os.getFamily();
```
...and the use it to execute code based on it:
```
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

This is pretty much all you need to know, but I strongly encourage you to take a look at the other examples to explore some of the utility methods Josi has to offer!

### Examples

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
