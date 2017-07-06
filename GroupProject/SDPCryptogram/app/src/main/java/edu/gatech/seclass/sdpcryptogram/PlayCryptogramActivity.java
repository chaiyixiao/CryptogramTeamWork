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
import java.util.ArrayList;

import static edu.gatech.seclass.sdpcryptogram.R.layout.play_cryptogram;

public class PlayCryptogramActivity extends AppCompatActivity {

    private ArrayList<String> mEncodedLetters = new ArrayList<>();
    private ArrayList<String> mySolutionLetters = new ArrayList<>();

    private String cryptogramId = "";
    private String solutionStr = "";

    private RecyclerView playRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private PlayCryptogramAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(play_cryptogram);


        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            this.cryptogramId = (String) b.get("CRYPTOGRAM_KEY");
            char[] encodedArr = ((String) b.get("CRYPTOGRAM_ENCODED")).toCharArray();
            this.solutionStr = (String) b.get("CRYPTOGRAM_SOLUTION");
            String mySolutionStr = ""; // TODO: this.mySolutionLetters = 从 PlayCryptogram 来
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
                // TODO: match
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
                resetEmptyMySolutionLetters();
                mAdapter.notifyItemChanged(mySolutionLetters.size(), mySolutionLetters);
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
                            Log.v("before===", mySolutionLetters.toString());

                            mySolutionLetters.set(i, replace);
                            Log.v("after===", mySolutionLetters.toString());
                        }
                    }
                    Log.v("loop===", mySolutionLetters.toString());

                    for (int i = 0; i < mySolutionLetters.size(); i++) {
                        if (!mySolutionLettersCopy.get(i).equals(mySolutionLetters.get(i))) {
                            mAdapter.notifyDataSetChanged();
                            Log.v("adapter===", mySolutionLetters.toString());
                        }
                    }

////                    if (!mySolutionLetters.toString().equals(mySolutionLettersCopy.toString())) {
//
//                    }
                }
            });
        }
    };

    private Handler handler = new Handler(Looper.getMainLooper());
}
