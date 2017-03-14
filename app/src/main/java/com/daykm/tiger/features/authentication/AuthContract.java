package com.daykm.tiger.features.authentication;

import com.daykm.tiger.features.base.Contract;

public interface AuthContract {

  interface View extends Contract.View {

    void loadUrl(String url);

    void finish();

    void addAccount(String userId);

    void setResultOk();

    void setResultCanceled();
  }

  interface Presenter extends Contract.Presenter<View> {

  }
}
