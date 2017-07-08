package edu.gatech.seclass.sdpcryptogram;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.gatech.seclass.utilities.ExternalWebService;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by wc on 04/07/2017.
 */

public class AvailableCryptogramsFragment extends Fragment {

    private TextView solved;
    private TextView started;
    private TextView totalIncorrect;
    private RecyclerView availableCryptogramRecyclerView;
    private LinearLayoutManager acLayoutManager;
    private AvailableCryptogramsAdapter mAdapter;

    private ArrayList<Cryptogram> mCryptogramList;
    private ArrayList<PlayCryptogram> mPlayCryptograms;
    private DatabaseReference mDatabase;

    private String username = "";
    private Player currentPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.available_cryptograms_fragment, container, false);
        mDatabase = FirebaseGetInstanceClass.GetFirebaseDatabaseInstance().getReference();
        mCryptogramList = new ArrayList<>();
        mPlayCryptograms = new ArrayList<>();

        solved = (TextView) v.findViewById(R.id.solved_num);
        started = (TextView) v.findViewById(R.id.started_num);
        totalIncorrect = (TextView) v.findViewById(R.id.incorrect_num);

        availableCryptogramRecyclerView = (RecyclerView) v.findViewById(R.id.available_cryptograms_recycler_view);
        acLayoutManager = new LinearLayoutManager(getActivity());
        availableCryptogramRecyclerView.setLayoutManager(acLayoutManager);

        Button requestButton = (Button) v.findViewById(R.id.request_new_cryptograms);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: request data and sync
                mCryptogramList.addAll(fetchCryptograms());
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDatabase.child("players").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentPlayer = dataSnapshot.getValue(Player.class);
                solved.setText(String.valueOf(currentPlayer.solvedCount));
                started.setText(String.valueOf(currentPlayer.started));
                totalIncorrect.setText(String.valueOf(currentPlayer.totalIncorrect));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mPlayCryptograms = new ArrayList<>();

        mDatabase.child("playCryptograms").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlayCryptogram pc = snapshot.getValue(PlayCryptogram.class);
                    mPlayCryptograms.add(pc);
                }
                mAdapter = new AvailableCryptogramsAdapter(username, mCryptogramList, mPlayCryptograms, AvailableCryptogramsFragment.this);
                availableCryptogramRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        username = getActivity().getIntent().getExtras().getString("USERNAME");

        List<String[]> cryptograms = ExternalWebService.getInstance().syncCryptogramService();

        for (String[] cryptogram : cryptograms) {
            List<String> arr = Arrays.asList(cryptogram);
            Cryptogram crypt = new Cryptogram(arr.get(1), arr.get(2), arr.get(0));
            mCryptogramList.add(crypt);
        }
    }

    public void setCryptogramStarted(PlayCryptogram pc) {

        if (pc.progress.equals("Not started")) {
            currentPlayer.started++;
            mDatabase.child("players").child(username).setValue(currentPlayer);
            pc.progress = "In progress";
            mDatabase.child("playCryptograms").child(username).child(pc.cryptogramId).setValue(pc);
            mDatabase.keepSynced(true);
            ExternalWebService.getInstance().updateRatingService(username, currentPlayer.firstname, currentPlayer.lastname, currentPlayer.solvedCount, currentPlayer.started, currentPlayer.totalIncorrect);
        }
    }


    private ArrayList<Cryptogram> fetchCryptograms() {
        // TODO: fetch cryptograms from externalWebService and store in database

        return new ArrayList<>();
    }

}