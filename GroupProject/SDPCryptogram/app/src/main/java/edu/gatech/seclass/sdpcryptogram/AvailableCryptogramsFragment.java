package edu.gatech.seclass.sdpcryptogram;

import android.app.Activity;
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

public class AvailableCryptogramsFragment extends Fragment{

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

        mAdapter = new AvailableCryptogramsAdapter(username, mCryptogramList, mPlayCryptograms, this);
        availableCryptogramRecyclerView.setAdapter(mAdapter);

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        username = getActivity().getIntent().getExtras().getString("USERNAME");
        List<String> playerNames = ExternalWebService.getInstance().playernameService();
        List<ExternalWebService.PlayerRating> playerRatings = ExternalWebService.getInstance().syncRatingService();
        int playerIndex = playerNames.indexOf(username);
        ExternalWebService.PlayerRating playerRating = playerRatings.get(playerIndex);
        solved.setText(String.valueOf(playerRating.getSolved()));
        started.setText(String.valueOf(playerRating.getStarted()));
        totalIncorrect.setText(String.valueOf(playerRating.getIncorrect()));

//        mDatabase.child("players").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Player currentPlayer = dataSnapshot.getValue(Player.class);
//                solved.setText(String.valueOf(currentPlayer.solvedCount));
//                started.setText(String.valueOf(currentPlayer.started));
//                totalIncorrect.setText(String.valueOf(currentPlayer.totalIncorrect));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        List<String[]> cryptograms = ExternalWebService.getInstance().syncCryptogramService();

        for (String[] cryptogram : cryptograms) {
            List<String> arr = Arrays.asList(cryptogram);

            Cryptogram crypt = new Cryptogram(arr.get(1), arr.get(2), arr.get(0));
            mCryptogramList.add(crypt);
        }
//        mDatabase.child("cryptograms").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Log.e("Count=!", "" + dataSnapshot.getChildrenCount());
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Cryptogram crypto = snapshot.getValue(Cryptogram.class);
//                    mCryptogramList.add(crypto);
//                }
//                mAdapter.notifyItemInserted(mCryptogramList.size());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        mDatabase.child("playCryptograms").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PlayCryptogram pc = snapshot.getValue(PlayCryptogram.class);
                    mPlayCryptograms.add(pc);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void setCryptogramStarted(PlayCryptogram pc) {
        pc.progress = "In progress";
        Map<String, Object> newPc = new HashMap<>();
        newPc.put(pc.cryptogramId, pc);
        mDatabase.child("playCryptograms").child(username).updateChildren(newPc);
    }

    private ArrayList<Cryptogram> fetchCryptograms() {
        // TODO: fetch cryptograms from externalWebService and store in database

        return new ArrayList<>();
    }

}