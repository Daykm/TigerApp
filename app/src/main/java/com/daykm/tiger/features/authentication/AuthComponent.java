package com.daykm.tiger.features.authentication;

import dagger.Subcomponent;

@Subcomponent public interface AuthComponent {
	void inject(AuthActivity authActivity);
}
