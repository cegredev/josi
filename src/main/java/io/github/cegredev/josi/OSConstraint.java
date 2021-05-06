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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;

public class OSConstraint {

	private final OS target;
	private final List<ChainDataPair> chainDataPairs = new ArrayList<>();

	public OSConstraint(OS target) {
		this.target = target;

		getChainDataPairs().add(new ChainDataPair());
	}

	public OSConstraint() {
		this(OS.current());
	}

	protected ChainDataPair getLastPair() {
		List<ChainDataPair> chainDataPairs = getChainDataPairs();
		return chainDataPairs.get(chainDataPairs.size() - 1);
	}

	public OSConstraint is(OS... operatingSystems) {
		getLastPair().getChain().add(() -> getTarget().is(operatingSystems));
		return this;
	}

	public OSConstraint isNot(OS... operatingSystems) {
		getLastPair().getChain().add(() -> !getTarget().is(operatingSystems));
		return this;
	}

	public OSConstraint isFamily(OS.Family... families) {
		getLastPair().getChain().add(() -> getTarget().isFamily(families));
		return this;
	}

	public OSConstraint isNotFamily(OS.Family... families) {
		getLastPair().getChain().add(() -> !getTarget().isFamily(families));
		return this;
	}

	public OSConstraint atLeast(OS operatingSystem) {
		getLastPair().getChain().add(() -> getTarget().isAtLeast(operatingSystem));
		return this;
	}

	public OSConstraint store(Object data) {
		getLastPair().setData(data);

		// Get ready to accept a new condition chain
		or();

		return this;
	}

	public OSConstraint or() {
		getChainDataPairs().add(new ChainDataPair());
		return this;
	}

	public boolean check() {
		for (ChainDataPair pair : getChainDataPairs())
			if (!pair.getChain().isTrue())
				return false;
		return true;
	}

	public void enforce() throws UnsupportedOSException {
		if (!check())
			throw new UnsupportedOSException();
	}

	@SuppressWarnings("unchecked")
	public <T> T get() {
		for (ChainDataPair pair : getChainDataPairs())
			if (pair.getChain().isTrue())
				return (T) pair.getData();

		throw new UnsupportedOSException();
	}

	protected OS getTarget() {
		return target;
	}

	protected List<ChainDataPair> getChainDataPairs() {
		return chainDataPairs;
	}

	private class ConditionChain {

		private final List<BooleanSupplier> conditions = new ArrayList<>();

		public void add(BooleanSupplier condition) {
			getConditions().add(condition);
		}

		public boolean isTrue() {
			for (BooleanSupplier condition : getConditions())
				if (!condition.getAsBoolean())
					return false;
			return true;
		}

		public List<BooleanSupplier> getConditions() {
			return conditions;
		}
	}

	private class ChainDataPair {

		private final ConditionChain chain;

		private Object data;

		private ChainDataPair(ConditionChain chain, Object data) {
			this.chain = chain;
			this.data = data;
		}

		private ChainDataPair() {
			this(new ConditionChain(), null);
		}

		public ConditionChain getChain() {
			return chain;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}
	}

}
