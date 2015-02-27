package com.example.android.testing.espresso.BasicSample;

import android.app.Activity;

import io.appium.droiddriver.helpers.SingleRun;
import io.appium.droiddriver.util.ActivityUtils;

// Based on BaseDroidDriverTest.java DroidDriversInitializer
public class ActivitySupplierInitializer extends SingleRun {
    private static ActivitySupplierInitializer instance;
    protected final ActivityUtils.Supplier<Activity> activitySupplier;

    protected ActivitySupplierInitializer(ActivityUtils.Supplier<Activity> activitySupplier) {
        this.activitySupplier = activitySupplier;
    }

    @Override
    protected void run() {
        // DroidDriver's instrumentation driver requires an activity supplier.
        ActivityUtils.setRunningActivitySupplier(activitySupplier);
    }

    public static synchronized ActivitySupplierInitializer get(ActivityUtils.Supplier<Activity> activitySupplier) {
        if (instance == null) {
            instance = new ActivitySupplierInitializer(activitySupplier);
        }
        return instance;
    }
}
