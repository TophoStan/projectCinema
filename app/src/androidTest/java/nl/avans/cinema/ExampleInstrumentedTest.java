package nl.avans.cinema;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.*;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.platform.app.InstrumentationRegistry;
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
    public void loginAsUserEnsuresToastLoggedInAsUser() {
        onView(withId(R.id.Login_login)).perform(click());
        sleep();
        onWebView(withId(R.id.webview_login))
                .withTimeout(10, TimeUnit.SECONDS)
                .withElement(findElement(Locator.XPATH, "//*[@id=\"main\"]/section/div/div/form/input[3]"))
                .perform(webClick());
        onView(withText(R.string.user_login_message)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void creatingListWithoutNameEnsuresToastNameCannotBeEmpty() {
        onView(withId(R.id.Login_login)).perform(click());
        sleep();
        onWebView(withId(R.id.webview_login))
                .withTimeout(10, TimeUnit.SECONDS)
                .withElement(findElement(Locator.XPATH, "//*[@id=\"main\"]/section/div/div/form/input[3]"))
                .perform(webClick());
        sleep();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.lists)).perform(click());
        sleep();
        onView(withId(R.id.makeListButton)).perform(click());
        sleep();
        onView(withId(R.id.make_list_name)).perform(typeText(""));
        onView(withText(R.string.create_list_label)).perform(click());
        onView(withText(R.string.error_name_not_empty)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void clickingTrailerMovieThatHasNoTrailerEnsuresToastNoTrailer() {
        onView(withId(R.id.Login_login)).perform(click());
        sleep();
        onWebView(withId(R.id.webview_login))
                .withTimeout(10, TimeUnit.SECONDS)
                .withElement(findElement(Locator.XPATH, "//*[@id=\"main\"]/section/div/div/form/input[3]"))
                .perform(webClick());
        sleep();
        onView(withId(R.id.filmsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText(R.string.trailer)).perform(click());
        //TODO zoek film die geen trailer heeft
    }

    @Test
    public void loginAsGuestClickingListsEnsuresToastLoginToUseLists() {
        onView(withId(R.id.Login_guest)).perform(click());
        sleep();
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText(R.string.lists)).perform(click());
        sleep();
        onView(withText(R.string.login_guest_to_use_list)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

    public void sleep() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {

        }
    }
}