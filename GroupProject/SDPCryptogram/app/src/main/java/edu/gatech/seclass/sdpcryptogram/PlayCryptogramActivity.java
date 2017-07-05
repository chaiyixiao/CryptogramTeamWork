package edu.gatech.seclass.sdpcryptogram;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by chaiyixiao on 04/07/2017.
 */
import java.util.ArrayList;

import static edu.gatech.seclass.sdpcryptogram.R.layout.play_cryptogram;

public class PlayCryptogramActivity extends AppCompatActivity {


    private ArrayList<Cryptogram> mCryptogramList;

    private RecyclerView playRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private PlayCryptogramAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(play_cryptogram);

        playRecyclerView = (RecyclerView)findViewById(R.id.play_cryptogram_recycler_view);
        mGridLayoutManager = new GridLayoutManager(this, 10);
        playRecyclerView.setLayoutManager(mGridLayoutManager);

        mAdapter = new PlayCryptogramAdapter(mCryptogramList);
        playRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
