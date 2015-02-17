/*
 * Copyright 2014, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.espresso.BasicSample;

import android.app.Activity;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;

import com.google.android.droiddriver.UiElement;
import com.google.android.droiddriver.finders.By;
import com.google.android.droiddriver.finders.Finder;
import com.google.android.droiddriver.helpers.BaseDroidDriverTest;
import com.google.android.droiddriver.helpers.DroidDrivers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.android.testing.espresso.BasicSample.CloseSoftKeyboard.closeSoftKeyboard;

// BaseDroidDriverTest extends D2ActivityInstrumentationTestCase2 extends ActivityInstrumentationTestCase2
// test runner (set in build.gradle)
// TestRunner extends InstrumentationTestRunner -- handles droiddriver init
// run tests: ./gradlew clean assemble connectedCheck
// run tests quickly: ./gradlew connectedCheck

/**
 * Basic tests showcasing simple view matchers and actions like {@link ViewMatchers#withId},
 * {@link ViewActions#click} and {@link ViewActions#typeText}.
 * <p>
 * Note that there is no need to tell Espresso that a view is in a different {@link Activity}.
 * </p>
 */
public class ChangeTextBehaviorTest extends BaseDroidDriverTest<MainActivity> {

    public static final String STRING_TO_BE_TYPED = "Espresso";

    public ChangeTextBehaviorTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // For each test method invocation, the Activity will not actually be created
        // until the first time this method is called.
        getActivity();
    }

    /**
     * Initializes test fixture once for all tests extending this class. Typically
     * you call {@link com.google.android.droiddriver.helpers.DroidDrivers#init} with an appropriate instance. If an
     * InstrumentationDriver is used, this is a good place to call
     * {@link com.google.android.droiddriver.instrumentation.ViewElement#overrideClassName}
     */
    @Override
    protected void classSetUp() {
        // DroidDriver init
        // DroidDrivers.init(DroidDrivers.newInstrumentationDriver(getInstrumentation())); // fails on API 21
        // API >= 18 use uiautomator driver
        // API <= 17 use instrumentation driver (not well tested, breaks on API 21)
        DroidDrivers.init(DroidDrivers.newDriver(getInstrumentation()));
    }

    String resourceId(int rID) {
        return getTargetContext().getResources().getResourceName(rID);
    }

    UiElement find(Finder finder) {
        // Note that we use on to ensure refreshUiElementTree() is called
        // the 'find' method will not refresh the uielementtree
        return DroidDrivers.get().on(finder);
    }

    UiElement id(int rID) {
        return find(By.resourceId(resourceId(rID)));
    }

    // DroidDriver can't hide the keyboard to use Espresso for now.
    void hideKeyboard(int viewId) {
        onView(withId(viewId)).perform(closeSoftKeyboard());
    }

    public void testChangeText_sameActivity() {
        // Type text and then press the button.
        id(R.id.editTextUserInput).setText(STRING_TO_BE_TYPED);
        hideKeyboard(R.id.editTextUserInput);
        id(R.id.changeTextBt).click();

        // Check that the text was changed.
        String expected = STRING_TO_BE_TYPED;
        String actual = id(R.id.textToBeChanged).getText();

        // Often flakes out with: junit.framework.ComparisonFailure: expected:<[E]spresso> but was:<[]spresso>
        assertEquals(expected, actual);
    }

    public void testChangeText_newActivity() {
        // Type text and then press the button.
        id(R.id.editTextUserInput).setText(STRING_TO_BE_TYPED);
        hideKeyboard(R.id.editTextUserInput);
        id(R.id.activityChangeTextBtn).click();

        // This view is in a different Activity, no need to tell Espresso.
        String expected = STRING_TO_BE_TYPED;
        String actual = id(R.id.show_text_view).getText();

        assertEquals(expected, actual);
    }
}

/*
android.widget.TextView (0)
  text: Hello Espresso!
  id: com.example.android.testing.espresso.BasicSample:id/textToBeChanged
  strings.xml: hello_world

android.widget.EditText (0)
  text: type something?
  id: com.example.android.testing.espresso.BasicSample:id/editTextUserInput

android.widget.Button (0)
  text: Change text
  id: com.example.android.testing.espresso.BasicSample:id/changeTextBt
  strings.xml: change_text

android.widget.Button (1)
  text: Open activity and change text
  id: com.example.android.testing.espresso.BasicSample:id/activityChangeTextBtn
  strings.xml: open_activity_and_change_text
*/
