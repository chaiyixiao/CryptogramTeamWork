package edu.gatech.seclass.sdpcryptogram;

import android.content.Intent;
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

import java.util.ArrayList;

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

    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.available_cryptograms_fragment, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
//        String username = getArguments().getString("username");
//        Log.v("**********",getArguments().getString("username"));
//        mDatabase.child("players").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Player currentPlayer = dataSnapshot.getValue(Player.class);
//                Log.v("========", currentPlayer.username);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        mCryptogramList = new ArrayList<>();
        solved = (TextView) v.findViewById(R.id.solved_num);
        started = (TextView) v.findViewById(R.id.started_num);
        totalIncorrect = (TextView) v.findViewById(R.id.incorrect_num);

        availableCryptogramRecyclerView = (RecyclerView) v.findViewById(R.id.available_cryptograms_recycler_view);
        acLayoutManager = new LinearLayoutManager(getActivity());
        availableCryptogramRecyclerView.setLayoutManager(acLayoutManager);

        mAdapter = new AvailableCryptogramsAdapter(mCryptogramList);
        availableCryptogramRecyclerView.setAdapter(mAdapter);
        // TODO: fetch data from database

        solved.setText("20");
        started.setText("15");
        totalIncorrect.setText("100");


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
    public void onStart() {
        super.onStart();

        mDatabase.child("cryptograms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.e("Count ", "" + dataSnapshot.getChildrenCount());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Cryptogram crypto = snapshot.getValue(Cryptogram.class);
                    Log.e("Get Data", crypto.solutionPhrase);
                    mCryptogramList.add(crypto);
                }
                mAdapter.notifyItemInserted(mCryptogramList.size());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private ArrayList<Cryptogram> fetchCryptograms() {
        // TODO: fetch cryptograms from externalWebService and store in database

        return new ArrayList<>();
    }

}