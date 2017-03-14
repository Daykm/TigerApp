package com.daykm.tiger.features.base;

import com.daykm.tiger.BuildConfig;
import javax.inject.Inject;

public class BuildConstants {

  private final String consumerKey = BuildConfig.CONSUMER_KEY;
  private final String secretKey = BuildConfig.SECRET_KEY;

  @Inject public BuildConstants() {
  }

  public String getConsumerKey() {
    return consumerKey;
  }

  public String getSecretKey() {
    return secretKey;
  }
}
