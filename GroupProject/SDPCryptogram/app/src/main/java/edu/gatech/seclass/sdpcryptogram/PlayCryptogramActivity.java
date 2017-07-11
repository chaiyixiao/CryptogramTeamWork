package edu.gatech.seclass.sdpcryptogram;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 * Created by chaiyixiao on 04/07/2017.
 */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.gatech.seclass.utilities.ExternalWebService;

import static edu.gatech.seclass.sdpcryptogram.R.layout.play_cryptogram;

public class PlayCryptogramActivity extends AppCompatActivity {

    private ArrayList<String> mEncodedLetters = new ArrayList<>();
    private ArrayList<String> mySolutionLetters = new ArrayList<>();

    private String cryptogramId = "";
    private String solutionStr = "";
    private String username = "";

    private RecyclerView playRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private PlayCryptogramAdapter mAdapter;

    private DatabaseReference mDatabase;
    private PlayCryptogram mPlayCrypt = new PlayCryptogram();
    private Player currentPlayer = new Player();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(play_cryptogram);

        playRecyclerView = (RecyclerView) findViewById(R.id.play_cryptogram_recycler_view);
        playRecyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(this, 10);
        playRecyclerView.setLayoutManager(mGridLayoutManager);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            cryptogramId = (String) b.get("CRYPTOGRAM_ID");
            char[] encodedArr = ((String) b.get("CRYPTOGRAM_ENCODED")).toCharArray();
            for (char c : encodedArr) {
                mEncodedLetters.add(String.valueOf(c));
            }
            solutionStr = (String) b.get("CRYPTOGRAM_SOLUTION");
            username = (String) b.get("USERNAME");
            mDatabase = FirebaseGetInstanceClass.GetFirebaseDatabaseInstance().getReference();

            mDatabase.child("playCryptograms").child(username).child(cryptogramId).addListenerForSingleValueEvent(new ValueEventListener() {

                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue(PlayCryptogram.class) != null) {
                        mPlayCrypt = dataSnapshot.getValue(PlayCryptogram.class);
                        String mySolutionStr = mPlayCrypt.getPriorSolution();
                        char[] mySolutionArr = mySolutionStr.toCharArray();

                        if (!mySolutionStr.isEmpty()) {
                            for (char c : mySolutionArr) {
                                mySolutionLetters.add(String.valueOf(c));
                            }
                        } else {
                            resetEmptyMySolutionLetters();
                        }
                        mAdapter = new PlayCryptogramAdapter(mEncodedLetters, mySolutionLetters, listener);

                    } else {
                        resetEmptyMySolutionLetters();
                        mAdapter = new PlayCryptogramAdapter(mEncodedLetters, mySolutionLetters, listener);
                    }

                    playRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            }); //
        }

        final Button submit = (Button) findViewById(R.id.submit_cryptogram_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer sb = new StringBuffer();

                for (String mySolutionLetter : mySolutionLetters) {
                    if (mySolutionLetter.isEmpty()) {
                        sb.append(" ");
                    } else {
                        sb.append(mySolutionLetter);
                    }
                }

                if (sb.toString().replace(" ", "").length() == solutionStr.replace(" ", "").length()) {

                    int index = ExternalWebService.getInstance().playernameService().indexOf(username);
                    ExternalWebService.PlayerRating rating = ExternalWebService.getInstance().syncRatingService().get(index);
                    currentPlayer = new Player(username, rating);

                    if (sb.toString().equals(solutionStr)) {
                        mPlayCrypt.setProgressComplete();
                        currentPlayer.solvedCount++;

                        LinearLayout layout = (LinearLayout) findViewById(R.id.right_submit_answer);
                        layout.setVisibility(View.VISIBLE);
//                    PlayCryptogramActivity.this.finish();
                    } else {
                        submit.setError("Wrong Answer!");
                        mPlayCrypt.setPriorSolution(sb.toString());
                        mPlayCrypt.setIncorrectSubmit();
                        currentPlayer.totalIncorrect++;
                    }
                    mDatabase.child("playCryptograms").child(username).child(mPlayCrypt.getCryptogramId()).setValue(mPlayCrypt);
                    mDatabase.child("players").child(username).setValue(currentPlayer);
                    mDatabase.keepSynced(true);
                    ExternalWebService.getInstance().updateRatingService(username, currentPlayer.firstname, currentPlayer.lastname, currentPlayer.solvedCount, currentPlayer.started, currentPlayer.totalIncorrect);

                } else {
                    submit.hasFocus();
                    submit.setError("Complete all the letters before submit!");
                }
            }
        });
        // back
        Button back = (Button) findViewById(R.id.back_cryptogram_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayCryptogramActivity.this.finish();
            }
        });

        //reset
        Button reset = (Button) findViewById(R.id.reset_cryptogram_button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
//                resetEmptyMySolutionLetters();
//                mAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void resetEmptyMySolutionLetters() {
        ArrayList<String> emptySol = new ArrayList<>();
        for (String l : mEncodedLetters) {
            if (l.matches("[a-zA-Z]")) {
                emptySol.add(" ");
            } else {
                emptySol.add(l);
            }
        }
        mySolutionLetters = emptySol;
    }

    private PlayCryptogramAdapter.OnRefreshRVListener listener = new PlayCryptogramAdapter.OnRefreshRVListener() {
        @Override
        public void onRefresh(final String encoded, final String replace) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ArrayList<String> mySolutionLettersCopy = new ArrayList<String>(mySolutionLetters);
                    for (int i = 0; i < mEncodedLetters.size(); i++) {
                        if (mEncodedLetters.get(i).equals(encoded)) {
                            mySolutionLetters.set(i, replace);
                        }
                    }
                    for (int i = 0; i < mySolutionLetters.size(); i++) {
                        if (!mySolutionLettersCopy.get(i).equals(mySolutionLetters.get(i))) {
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    };

    private Handler handler = new Handler(Looper.getMainLooper());
}
