package edu.gatech.seclass.sdpcryptogram;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by wc on 05/07/2017.
 */
@IgnoreExtraProperties
public class PlayCryptogram {
    public String username;
    public String cryptogramId;
    public String progress;
    public String priorSolution;
    public int numIncorrectSubmission;

    public PlayCryptogram() {
        this.username = "";
    }

    public PlayCryptogram(String username, String cryptogramId) {
        this.username = username;
        this.cryptogramId = cryptogramId;
    }

    public void savePriorSolution(String solution) {
        this.priorSolution = solution;
    }
    public void startPlaying() {
        this.progress = "In progress";
    }
    public void addIncorrectsubmit() {
        this.numIncorrectSubmission++;
    }
    public void completePlaying() {
        this.progress = "Solved";
    }

}
