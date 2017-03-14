package com.daykm.tiger.features.base;

public interface Contract<S extends Contract.View> {

	interface View<S> {

	}

	interface Presenter<S> {
		void attach(S view);

		void detach();
	}
}
