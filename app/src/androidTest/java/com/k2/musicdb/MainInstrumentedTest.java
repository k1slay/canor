package com.k2.musicdb;

import android.content.Context;

import com.google.gson.Gson;
import com.k2.musicdb.artist.ArtistFragment;
import com.k2.musicdb.data.models.ApiResponse;
import com.k2.musicdb.data.models.Artist;
import com.k2.musicdb.data.models.SearchResponse;
import com.k2.musicdb.data.models.Song;
import com.k2.musicdb.search.SearchFragment;
import com.k2.musicdb.song.SongFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainInstrumentedTest {

    private Context appContext;
    private MainActivity mainActivity;
    private Gson gson;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void setup() {
        gson = new Gson();
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        mainActivity = mainActivityTestRule.launchActivity(null);
    }

    @Test
    public void searchFragTest() {
        onView(withId(R.id.back_drop)).check(matches(isDisplayed()));

        ApiResponse searchApiResponse = gson.fromJson(MockGeniusService.RESPONSE_SEARCH, ApiResponse.class);
        SearchFragment searchFragment = new SearchFragment();

        searchFragment.replaceAll(mainActivity.getSupportFragmentManager(), R.id.main_container);
        onView(withId(R.id.search_box)).perform(ViewActions.click());
        onView(withId(R.id.search_box)).perform(ViewActions.pressImeActionButton());
        onView(withId(R.id.search_progress)).check(matches(not(isDisplayed())));
        onView(withId(R.id.search_list)).check(matches(not(isDisplayed())));

        mainActivity.runOnUiThread(() -> searchFragment.showData(searchApiResponse.getData(SearchResponse.class, "hits", gson)));
        onView(withId(R.id.search_progress)).check(matches(not(isDisplayed())));
        onView(withId(R.id.search_list)).check(matches(isDisplayed()));

        onView(allOf(withText("Pink Floyd"), isDescendantOfA(withId(R.id.search_list)))).check(matches(isDisplayed()));
    }

    @Test
    public void songFragTest() {
        SongFragment songFragment = new SongFragment();
        songFragment.setArguments(SongFragment.makeBundle("66328", ""));
        ApiResponse songApiResponse = gson.fromJson(MockGeniusService.RESPONSE_SONG, ApiResponse.class);
        songFragment.data = songApiResponse.getData(Song.class, "song", gson);
        songFragment.replaceAll(mainActivity.getSupportFragmentManager(), R.id.main_container);
        onView(withId(R.id.album_title)).check(matches(withText("Shine On")));
        onView(withId(R.id.released_on)).check(matches(not(withText(R.string.not_available_placeholder))));
        onView(withId(R.id.spotify)).check(matches(ViewMatchers.isEnabled()));
        onView(withId(R.id.youtube)).check(matches(not(ViewMatchers.isEnabled())));
        onView(withId(R.id.album_container)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.artist_list)).perform(ViewActions.scrollTo());
        onView(allOf(withText("Pink Floyd"), isDescendantOfA(withId(R.id.artist_list)))).check(matches(isDisplayed()));
    }


    @Test
    public void artistFragTest() {
        ArtistFragment artistFragment = new ArtistFragment();
        artistFragment.setArguments(ArtistFragment.makeBundle("694", ""));
        ApiResponse artistApiResponse = gson.fromJson(MockGeniusService.RESPONSE_ARTIST, ApiResponse.class);
        artistFragment.data = artistApiResponse.getData(Artist.class, "artist", gson);
        artistFragment.replaceAll(mainActivity.getSupportFragmentManager(), R.id.main_container);
        onView(withId(R.id.title)).check(matches(withText("Pink Floyd")));
        onView(withId(R.id.facebook)).check(matches(ViewMatchers.isEnabled()));
        onView(withId(R.id.twitter)).check(matches(ViewMatchers.isEnabled()));
        onView(withId(R.id.instagram)).check(matches(not(ViewMatchers.isEnabled())));
        onView(withId(R.id.artist_data)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.description)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.aka_chips)).check(matches(not(ViewMatchers.isDisplayed())));
    }

}
