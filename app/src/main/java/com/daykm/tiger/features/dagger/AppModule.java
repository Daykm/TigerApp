package com.daykm.tiger.features.dagger;

import android.content.Context;
import com.daykm.tiger.features.base.App;
import dagger.Module;
import dagger.Provides;

@Module(includes = { ServiceModule.class }) public class AppModule {

  App app;

  public AppModule(App app) {
    this.app = app;
  }

  @Provides public Context context() {
    return app;
  }
}
