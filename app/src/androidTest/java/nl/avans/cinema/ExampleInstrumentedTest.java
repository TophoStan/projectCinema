package nl.avans.cinema;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import nl.avans.cinema.ui.LoginActivity;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginAsGuestEnsuresToastLoggedInAsGuest() {
        onView(withId(R.id.Login_guest)).perform(click());
        onView(withText(R.string.guest_login_message)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
    @Test
    public void loginAsUserEnsuresToastLoggedInAsUser(){
        onView(withId(R.id.Login_login)).perform(click());
        try {
            Thread.sleep(500);
        } catch (Exception e){

        }
        onWebView(withId(R.id.webview_login))
                .withTimeout(10, TimeUnit.SECONDS)
                .withElement(findElement(Locator.XPATH,"//*[@id=\"main\"]/section/div/div/form/input[3]"))
                .perform(webClick());
        onView(withText(R.string.user_login_message)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}