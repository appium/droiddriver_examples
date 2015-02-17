package com.example.android.testing.espresso.BasicSample;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.CloseKeyboardAction;
import android.view.View;

import org.hamcrest.Matcher;

// https://github.com/googlesamples/android-testing/issues/5
// https://code.google.com/p/android-test-kit/issues/detail?id=44
// code from:
// https://code.google.com/p/android-test-kit/issues/detail?id=79#c7
public class CloseSoftKeyboard {

    public static ViewAction closeSoftKeyboard() {
        return new ViewAction() {
            private final ViewAction closeKeyboardAction = new CloseKeyboardAction();

            @Override
            public Matcher<View> getConstraints() {
                return closeKeyboardAction.getConstraints();
            }

            @Override
            public String getDescription() {
                return closeKeyboardAction.getDescription();
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                closeKeyboardAction.perform(uiController, view);
                uiController.loopMainThreadForAtLeast(1000L);
            }
        };
    }
}
