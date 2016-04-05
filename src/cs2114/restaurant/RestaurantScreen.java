package cs2114.restaurant;

import sofia.widget.ImageView;
import sofia.content.ContentViewer;
import android.net.Uri;
import realtimeweb.yelp.BusinessQuery;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Button;
import realtimeweb.yelp.SearchResponse;
import realtimeweb.yelp.exceptions.BusinessSearchException;
import realtimeweb.yelp.BusinessSearch;
import realtimeweb.yelp.Business;
import realtimeweb.yelp.BusinessSearchListener;
import sofia.app.Screen;

// -------------------------------------------------------------------------
/**
 * This class is to design the screen for the restaurant project and realize
 * several funcions.
 *
 * @author Sheng Zhou
 * @version 2013.04.15
 */
public class RestaurantScreen
    extends Screen
    implements BusinessSearchListener
{
    // ~ Fields ................................................................
    private CircularLinkedList<Business> busiList;
    private BusinessSearch               bSearch;
    private Button                       next;
    private Button                       previous;
    private Button                       viewMap;
    private RatingBar                    ratingBar;
    private EditText                     searchField;
    private TextView                     restaurantName;
    private TextView                     numRatings;
    private ImageView                    imageView;


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Initialize the class.
     */
    public void initialize()
    {
        busiList = new CircularLinkedList<Business>();
        bSearch = BusinessSearch.getInstance();
        next.setEnabled(false);
        previous.setEnabled(false);
        viewMap.setEnabled(false);
    }


    /**
     * The method is called when business Search completed.
     *
     * @param search
     *            searchResponse
     */
    public void businessSearchCompleted(SearchResponse search)
    {
        busiList.clear();

        if (search.getBusinesses().isEmpty())
        {
            next.setEnabled(false);
            previous.setEnabled(false);
            viewMap.setEnabled(false);
        }
        else
        {
            for (Business str : search.getBusinesses())
            {
                busiList.add(str);
            }
            setAllStuff();
            next.setEnabled(true);
            previous.setEnabled(true);
            viewMap.setEnabled(true);
        }
    }


    // ----------------------------------------------------------
    /**
     * The method will be called when typing is done.
     */
    public void searchFieldEditingDone()
    {
        String address = searchField.getText().toString();
        bSearch.searchBusinesses(
            new BusinessQuery(address),
            new BusinessSearchGUIAdapter(this));
    }


    /**
     * The method will be called if the search is failed.
     *
     * @param search
     *            BusinessSearchException
     */
    public void businessSearchFailed(BusinessSearchException search)
    {
        next.setEnabled(false);
        previous.setEnabled(false);
        viewMap.setEnabled(false);
    }


    // ----------------------------------------------------------
    /**
     * The method is to show the next one when next button is clicked.
     */
    public void nextClicked()
    {
        busiList.next();
        setAllStuff();
    }


    // ----------------------------------------------------------
    /**
     * The method is to show the previous one when previous button is clicked.
     */
    public void previousClicked()
    {
        busiList.previous();
        setAllStuff();
    }


    // ----------------------------------------------------------
    /**
     * The method is called when viewMap button is clicked and will show in
     * google map.
     */
    public void viewMapClicked()
    {
        double latitude = busiList.getCurrent().getLocation().getLatitude();
        double longitude = busiList.getCurrent().getLocation().getLongitude();
        String uriString =
            "http://maps.google.com/maps?q=" + latitude + "," + longitude;
        Uri uri = Uri.parse(uriString);
        new ContentViewer(uri).start(this);
    }


    // ----------------------------------------------------------
    /**
     * The method is to change the rating bar, the image, and all the stuff
     * needs to be changed.
     */
    public void setAllStuff()
    {
        ratingBar.setRating(busiList.getCurrent().getRating());
        restaurantName.setText(busiList.getCurrent().getName());
        numRatings.setText(busiList.getCurrent().getReviewCount() + " ratings");
        String uriString = busiList.getCurrent().getImageUrl();
        if (uriString == null)
        {
            imageView.setImageURI(null);
        }
        else
        {
            Uri uri = Uri.parse(uriString);
            imageView.setImageURI(uri);
        }
    }
}
