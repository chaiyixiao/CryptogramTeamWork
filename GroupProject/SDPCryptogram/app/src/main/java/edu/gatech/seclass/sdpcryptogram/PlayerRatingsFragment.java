package edu.gatech.seclass.sdpcryptogram;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;

/**
 * Created by wc on 04/07/2017.
 */

public class PlayerRatingsFragment extends Fragment {


//    private TextView totalIncorrect;
    private RecyclerView playerRatingsRecyclerView;
    private LinearLayoutManager ratingsLayoutManager;
    private PlayerRatingsAdapter mAdapter;

    private ArrayList<Player> mPlayers;
//    private List<ExternalWebService.PlayerRating> playerRatings = new ArrayList<>();
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.player_ratings_fragment, container, false);
        mDatabase = FirebaseGetInstanceClass.GetFirebaseDatabaseInstance().getReference();

        // get the list of player ratings, and the list of player usernames
        List<ExternalWebService.PlayerRating> playerRatings = ExternalWebService.getInstance().syncRatingService();
        List<String> playerNames = ExternalWebService.getInstance().playernameService();

        mPlayers = new ArrayList<>();
        for (int i = 0; i < playerRatings.size(); i++) {
            Player p = new Player(playerNames.get(i), playerRatings.get(i));
            mPlayers.add(p);
        }

        // sort player ratings by solved numbers, then by incorrect numbers, then by started numbers
        Collections.sort(mPlayers, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                // the player who solved more ranks first
                int solvedComp = p2.getSolvedCount().compareTo(p1.getSolvedCount());
                if (solvedComp != 0) {
                    return solvedComp;
                } else {
                    // the player who has less incorrect submission ranks first
                    int incorrectComp = p1.getTotalIncorrect().compareTo(p2.getTotalIncorrect());
                    if (incorrectComp != 0) {
                        return incorrectComp;
                    } else {
                        // the player who starts more ranks first
                        return p2.getStarted().compareTo(p1.getStarted());
                    }
                }
            }
        });

        // assign ranking numbers to players given the sorted player list
        for (int i = 0; i < mPlayers.size(); i++) {
            Player p = mPlayers.get(i);
            p.setRanking(i + 1);
        }

        // create recycler view
        playerRatingsRecyclerView = (RecyclerView) v.findViewById(R.id.player_ratings_recycler_view);
        playerRatingsRecyclerView.setHasFixedSize(true);
        ratingsLayoutManager = new LinearLayoutManager(getActivity());
        playerRatingsRecyclerView.setLayoutManager(ratingsLayoutManager);
        mAdapter = new PlayerRatingsAdapter(mPlayers);
        playerRatingsRecyclerView.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

//        mDatabase.child("players").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                    Player player = snapshot.getValue(Player.class);
//                    Log.e("Get Data", player.username);
//                    mPlayers.add(player);
//                }
//                mAdapter.notifyItemInserted(mPlayers.size());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    public ArrayList<Player> fetchPlayerRatings() {
        // TODO: fetch players from database
        return new ArrayList<>();
    }
}
