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
package io.github.cegredev.josi.constraints;

import io.github.cegredev.josi.CurrentOS;
import io.github.cegredev.josi.MacOS;
import io.github.cegredev.josi.OS;

public class MacConstraint<T> extends SpecificConstraint<MacOS, T> {

	public MacConstraint(OSConstraint<T> target) {
		super(target);
	}

	public MacConstraint<T> vRange(int minMajor, int minMinor, int maxMajor, int maxMinor) {
		// TODO: Actually implement
		return addToTarget(os -> os.getMajor() >= minMajor && os.getMajor() <= maxMajor
				&& os.getMinor() >= minMinor && os.getMinor() <= maxMinor);
	}

	public MacConstraint<T> vMin(int minMajor, int minMinor) {
		// TODO: Actually implement
		return addToTarget(os -> os.getMajor() >= minMajor && os.getMinor() >= minMinor);
	}

	public MacConstraint<T> vMax(int maxMajor, int maxMinor) {
		// TODO: Actually implement
		return addToTarget(os -> os.getMajor() <= maxMajor && os.getMinor() <= maxMinor);
	}

	@Override
	protected CurrentOS.Family getFamily() {
		return CurrentOS.Family.MAC;
	}

}
