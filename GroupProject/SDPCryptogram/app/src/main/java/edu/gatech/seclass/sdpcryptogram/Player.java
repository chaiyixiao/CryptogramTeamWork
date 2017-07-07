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
    public Integer solvedCount = 0;
    public Integer ranking = 0;
    public Integer started = 0;
    public Integer totalIncorrect = 0;

    public Player() {
    }
    //TODO: set ranking
    public Player(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Player(String username, ExternalWebService.PlayerRating rating) {
        // TODO: why assuming the username list and the rating list are matched by each player in order?
        this.username = username;
        this.firstname = rating.getFirstname();
        this.lastname = rating.getLastname();
        this.started = rating.getStarted();
        this.solvedCount = rating.getSolved();
        this.totalIncorrect = rating.getIncorrect();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getSolvedCount() {
        return solvedCount;
    }

    public void setSolvedCount(Integer solvedCount) {
        this.solvedCount = solvedCount;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getStarted() {
        return started;
    }

    public void setStarted(Integer started) {
        this.started = started;
    }

    public Integer getTotalIncorrect() {
        return totalIncorrect;
    }

    public void setTotalIncorrect(Integer totalIncorrect) {
        this.totalIncorrect = totalIncorrect;
    }
}
