package cs2114.restaurant;

import android.content.Intent;
import realtimeweb.yelp.exceptions.BusinessSearchException;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import sofia.widget.ImageView;

// -------------------------------------------------------------------------
/**
 * This is the test case for the restaurant Screen.
 *
 * @author Sheng Zhou
 * @version 2013.04.17
 */
public class RestaurantScreenTests
    extends student.AndroidTestCase<RestaurantScreen>
{
    // ~ Fields ................................................................

    // have in your layout.
    private Button    next;
    private Button    previous;
    private Button    viewMap;
    private RatingBar ratingBar;
    private EditText  searchField;
    private TextView  restaurantName;
    private TextView  numRatings;
    private ImageView imageView;


    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Test cases that extend AndroidTestCase must have a parameterless
     * constructor that calls super() and passes it the screen/activity class
     * being tested.
     */
    public RestaurantScreenTests()
    {
        super(RestaurantScreen.class);
    }


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Initializes the text fixtures.
     */
    public void setUp()
    {
        // Set up the test.
    }


    // ----------------------------------------------------------
    /**
     * The method is to test searchFieldEditingDone().
     */
    public void testSearchFieldEditingDone()
    {
        enterText(searchField, "Blacksburg, VA");
        assertTrue(next.isEnabled());
        assertTrue(previous.isEnabled());
        assertTrue(viewMap.isEnabled());

        enterText(searchField, "Richmond, VA");
        assertFalse(next.isEnabled());
        assertFalse(previous.isEnabled());
        assertFalse(viewMap.isEnabled());

    }


    // ----------------------------------------------------------
    /**
     * The method is to test BusinessSearchFailed class.
     */
    public void testBusinessSearchFailed()
    {
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run()
            {
                getScreen().businessSearchFailed(
                    new BusinessSearchException("", "", ""));
            }
        });

        assertFalse(next.isEnabled());
        assertFalse(previous.isEnabled());
        assertFalse(viewMap.isEnabled());
    }


    // ----------------------------------------------------------
    /**
     * The method is to test businessSearchCompleted().
     */
    public void testBusinessSearchCompleted()
    {
        enterText(searchField, "Blacksburg, VA");
        assertEquals(
            "Virginia Polytechnic Institute and State University",
            restaurantName.getText().toString());
        assertEquals(4.5, ratingBar.getRating(), 0.01);
        assertEquals("5 ratings", numRatings.getText());
        assertEquals("http://s3-media3.ak.yelpcdn.com/bphoto" +
        		"/hkDzkCxJZ5arOJDozUjXGg/ms.jpg",
            imageView.getImageURI().toString());
    }


    // ----------------------------------------------------------
    /**
     * The method is to test nextClicked() method.
     */
    public void testNextClicked()
    {
        enterText(searchField, "Blacksburg, VA");
        click(next);
        assertEquals("Sub Station II", restaurantName.getText().toString());
        assertEquals(4.0, ratingBar.getRating(), 0.01);
        assertEquals("10 ratings", numRatings.getText());
        assertEquals("http://s3-media2.ak.yelpcdn.com/"
            + "bphoto/qRVDzYHLJVj8nq2sBPapqw/ms.jpg",
            imageView.getImageURI().toString());
    }


    // ----------------------------------------------------------
    /**
     * The method is to test previousClicked() method.
     */
    public void testPreviousClicked()
    {
        enterText(searchField, "Blacksburg, VA");
        click(previous);
        assertEquals("Lyric Theatre", restaurantName.getText().toString());
        assertEquals(5.0, ratingBar.getRating(), 0.01);
        assertEquals("17 ratings", numRatings.getText());
        assertEquals(null, imageView.getImageURI());
    }


    // ----------------------------------------------------------
    /**
     * The method is to test viewMapClicked().
     */
    public void testViewMapClicked()
    {
        enterText(searchField, "Blacksburg, VA");
        prepareForUpcomingActivity(Intent.ACTION_VIEW);
        click(viewMap);
    }
}
