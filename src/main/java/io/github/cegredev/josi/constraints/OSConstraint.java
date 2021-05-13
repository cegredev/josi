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

import io.github.cegredev.josi.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class OSConstraint<T> {

	private final List<ChainDataPair> chainDataPairs = new ArrayList<>(Collections.singletonList(new ChainDataPair()));

	private T fallback;

	public OSConstraint(T fallback) {
		this.fallback = fallback;
	}

	public OSConstraint() {
		this(null);
	}

	protected OSConstraint<T> addCondition(Predicate<OperatingSystem> condition) {
		getLastPair().getChain().add(condition);
		return this;
	}

//	public OSConstraint<T> is(OS... operatingSystems) {
//		return addCondition(os -> os.is(operatingSystems));
//	}
//
//	public OSConstraint<T> isNot(OS... operatingSystems) {
//		return addCondition(os -> !os.is(operatingSystems));
//	}

	public OSConstraint<T> isFamily(CurrentOS.Family... families) {
		return addCondition(os -> os.isFamily(families));
	}

	public OSConstraint<T> isNotFamily(CurrentOS.Family... families) {
		return addCondition(os -> !os.isFamily(families));
	}

	// TODO: Implement again
//	public OSConstraint<T> atLeast(OS operatingSystem) {
//		return addCondition(os -> os.isAtLeast(operatingSystem));
//	}

	public WinConstraint<T> win() {
		return new WinConstraint<>(this);
	}

	public MacConstraint<T> mac() {
		return new MacConstraint<>(this);
	}

	public LinuxConstraint<T> linux() {
		return new LinuxConstraint<>(this);
	}

	public OSConstraint<T> pick(T data) {
		getLastPair().setData(data);

		// Get ready to accept a new condition chain
		or();

		return this;
	}

	public OSConstraint<T> or() {
		getChainDataPairs().add(new ChainDataPair());
		return this;
	}

	public boolean check(OperatingSystem operatingSystem) {
		for (ChainDataPair pair : getChainDataPairs())
			if (!pair.getChain().isTrue(operatingSystem))
				return false;
		return true;
	}

	public boolean check() {
		return check(CurrentOS.get());
	}

	public void enforce(OperatingSystem operatingSystem) throws UnsupportedOSException {
		if (!check(operatingSystem))
			throw new UnsupportedOSException();
	}

	public void enforce() throws UnsupportedOSException {
		enforce(CurrentOS.get());
	}

	public T get(OperatingSystem operatingSystem) {
		for (ChainDataPair pair : getChainDataPairs())
			if (pair.getChain().isTrue(operatingSystem))
				return pair.getData();

		T fallback = getFallback();
		if (fallback == null)
			throw new NewUnsupportedOSException(operatingSystem);
		return fallback;
	}

	public T get() {
		return get(CurrentOS.get());
	}

	protected ChainDataPair getLastPair() {
		List<ChainDataPair> chainDataPairs = getChainDataPairs();
		return chainDataPairs.get(chainDataPairs.size() - 1);
	}

	protected T getFallback() {
		return fallback;
	}

	protected List<ChainDataPair> getChainDataPairs() {
		return chainDataPairs;
	}

	protected class ChainDataPair {

		private final ConditionChain chain;

		private T data;

		private ChainDataPair(ConditionChain chain, T data) {
			this.chain = chain;
			this.data = data;
		}

		private ChainDataPair() {
			this(new ConditionChain(), null);
		}

		public ConditionChain getChain() {
			return chain;
		}

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}
	}

}
