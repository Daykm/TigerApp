package com.daykm.tiger.features.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daykm.tiger.dagger.ServiceComponent;

public class BaseActivity extends AppCompatActivity {

    protected ServiceComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App application = (App) getApplication();
        component = application.getComponent();
    }

    public ServiceComponent getComponent() {
        return component;
    }
}
