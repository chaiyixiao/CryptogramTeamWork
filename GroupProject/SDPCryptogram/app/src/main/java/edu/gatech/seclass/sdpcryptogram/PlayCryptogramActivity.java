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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(play_cryptogram);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            cryptogramId = (String) b.get("CRYPTOGRAM_ID");
            Log.v("cryptogramId", cryptogramId);
            char[] encodedArr = ((String) b.get("CRYPTOGRAM_ENCODED")).toCharArray();
            solutionStr = (String) b.get("CRYPTOGRAM_SOLUTION");
            username = (String) b.get("USERNAME");

            mDatabase = FirebaseGetInstanceClass.GetFirebaseDatabaseInstance().getReference();
            mDatabase.child("playCryptograms").child(username).addListenerForSingleValueEvent(new ValueEventListener() {

                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot != null) {
                            for (DataSnapshot ss : snapshot.getChildren()) {
                                if (ss.getKey().equals(cryptogramId)) {
                                    mPlayCrypt = ss.getValue(PlayCryptogram.class);
//                                    Log.v("yyyy", mPlayCrypt.priorSolution);
//                                    Log.v("xxxxx", mPlayCrypt.cryptogramId);
//                                    Log.v("zzzzz", mPlayCrypt.progress);

                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            String mySolutionStr = mPlayCrypt.priorSolution;
            char[] mySolutionArr = mySolutionStr.toCharArray();

            for (char c : encodedArr) {
                mEncodedLetters.add(String.valueOf(c));
            }

            if (mySolutionStr.isEmpty()) {
                resetEmptyMySolutionLetters();
            } else {
                for (char c : mySolutionArr) {
                    mySolutionLetters.add(String.valueOf(c));
                }
            }
        }

        playRecyclerView = (RecyclerView) findViewById(R.id.play_cryptogram_recycler_view);
        mGridLayoutManager = new GridLayoutManager(this, 10);
        playRecyclerView.setLayoutManager(mGridLayoutManager);

        mAdapter = new PlayCryptogramAdapter(mEncodedLetters, mySolutionLetters, listener);
        playRecyclerView.setAdapter(mAdapter);

        Button submit = (Button) findViewById(R.id.submit_cryptogram_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = "";
                for (String mySolutionLetter : mySolutionLetters) {
                    res += mySolutionLetter;
                }
                Log.v("res", res);
                Log.v("solution", solutionStr);
                if (res.equals(solutionStr)) {
                    Map<String, Object> newPlayCrypt = new HashMap<>();
                    mPlayCrypt.progress = "Finished";
                    newPlayCrypt.put(mPlayCrypt.cryptogramId, mPlayCrypt);
                    mDatabase.child("playCryptograms").child(username).updateChildren(newPlayCrypt);

                    int index = ExternalWebService.getInstance().playernameService().indexOf(username);
                    ExternalWebService.PlayerRating rating = ExternalWebService.getInstance().syncRatingService().get(index);
                    ExternalWebService.getInstance().updateRatingService(username, rating.getFirstname(), rating.getLastname(), rating.getSolved() + 1, rating.getStarted(), rating.getIncorrect());
//                    PlayCryptogramActivity.this.finish();
                } else {
                    mPlayCrypt.priorSolution = res;
                    mPlayCrypt.numIncorrectSubmission++;

                    Log.v("match error1", res);
                    Log.v("match error2", solutionStr);
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
//                Log.v("resetEmptyMySolutionLetters", mySolutionLetters.toString());
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
                emptySol.add("");
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
