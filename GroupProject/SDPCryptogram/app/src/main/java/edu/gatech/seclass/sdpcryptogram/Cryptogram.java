package edu.gatech.seclass.sdpcryptogram;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by wc on 05/07/2017.
 */
@IgnoreExtraProperties
public class Cryptogram {
    public String cryptoId;
    public String encodedPhrase;
    public String solutionPhrase;

    public Cryptogram() {
        this.cryptoId = "";
        this.encodedPhrase = "";
        this.solutionPhrase = "";
    }

    public Cryptogram(String encodedPhrase, String solutionPhrase, String cryptoId) {
        this.encodedPhrase = encodedPhrase;
        this.solutionPhrase = solutionPhrase;
        this.cryptoId = cryptoId;
    }
}
