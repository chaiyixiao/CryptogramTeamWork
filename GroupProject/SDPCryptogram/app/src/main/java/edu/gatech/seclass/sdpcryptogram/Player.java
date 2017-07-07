package edu.gatech.seclass.sdpcryptogram;

/**
 * Created by wc on 05/07/2017.
 */
import com.google.firebase.database.IgnoreExtraProperties;

import edu.gatech.seclass.utilities.ExternalWebService;

/**
 * Created by wc on 05/07/2017.
 */
@IgnoreExtraProperties
public class Player {
    public String username= "";
    public String firstname= "";
    public String lastname= "";
    public int solvedCount = 0;
    public int ranking = 0;
    public int started = 0;
    public int totalIncorrect = 0;

    public Player() {
    }
    //TODO: set ranking
    public Player(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Player(String username, ExternalWebService.PlayerRating rating) {
        this.username = username;
        this.firstname = rating.getFirstname();
        this.lastname = rating.getLastname();
        this.started = rating.getStarted();
        this.totalIncorrect = rating.getIncorrect();
    }
}
