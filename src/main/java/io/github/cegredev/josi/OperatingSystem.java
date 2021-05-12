package io.github.cegredev.josi;

public abstract class OperatingSystem {

	private final CurrentOS.Family family;

	public OperatingSystem(CurrentOS.Family family) {
		this.family = family;
	}

	public CurrentOS.Family getFamily() {
		return family;
	}
}
