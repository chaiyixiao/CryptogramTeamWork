package edu.gatech.seclass.sdpcryptogram;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.gatech.seclass.utilities.ExternalWebService;

import static edu.gatech.seclass.sdpcryptogram.R.layout.login;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private RadioGroup loginRadios;
    private RadioButton admin;
    private RadioButton player;
    private EditText username;
    private DatabaseReference mDatabase;

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(login);
        loginRadios = (RadioGroup) findViewById(R.id.radioGroup);
        admin = (RadioButton) findViewById(R.id.admin_radio);
        player = (RadioButton) findViewById(R.id.player_radio);
        username = (EditText) findViewById(R.id.username);
        loginButton = (Button) findViewById(R.id.login_button);
        loginRadios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.admin_radio) {
                    findViewById(R.id.username_container).setVisibility(View.INVISIBLE);
                } else if (checkedId == R.id.player_radio) {
                    findViewById(R.id.username_container).setVisibility(View.VISIBLE);
                }
            }
        });

        prefs = getSharedPreferences("edu.gatech.seclass.sdpcryptogram", MODE_PRIVATE);

        mDatabase = FirebaseGetInstanceClass.GetFirebaseDatabaseInstance().getReference();
        if (prefs.getBoolean("firstLaunch", true)) {
            prefs.edit().putBoolean("firstLaunch", false).commit();
            firstLaunchInit();
        }
        commonInit();
    }

    private void firstLaunchInit () {
        List<String[]> extCrypts = ExternalWebService.getInstance().syncCryptogramService();
        List<ExternalWebService.PlayerRating> extPlayerRatings = ExternalWebService.getInstance().syncRatingService();
        List<String> extPlayerNames = ExternalWebService.getInstance().playernameService();

        // TODO: players in external service are ones created in other local machines, not necessary to sync
        HashMap<String, Player> playerMap = new HashMap();
        for (int i = 0; i < extPlayerNames.size(); i++) {
            Player p = new Player(extPlayerNames.get(i), extPlayerRatings.get(i));
            playerMap.put(p.getUsername(), p);
        }
        mDatabase.child("players").setValue(playerMap);

        // REQUEST NEW CRYPTOGRAMS
        HashMap<String, Cryptogram> cryptoMap = new HashMap();
        for (String[] extCrypt : extCrypts) {
            List<String> arr = Arrays.asList(extCrypt);
            Cryptogram c = new Cryptogram(arr);
            cryptoMap.put(c.cryptoId, c);
        }
        mDatabase.child("cryptograms").setValue(cryptoMap);

        // TODO: players in external service are ones created in other local machines, not necessary to sync
        for (String extPlayerName : extPlayerNames) {
            HashMap<String, PlayCryptogram> pcMap = new HashMap();
            for (String[] extCrypt : extCrypts) {
                List<String> arr = Arrays.asList(extCrypt);
                PlayCryptogram pc = new PlayCryptogram(extPlayerName, arr.get(0));
                pcMap.put(pc.getCryptogramId(), pc);
            }
            mDatabase.child("playCryptograms").child(extPlayerName).setValue(pcMap);
        }

        // TODO: cryptograms sync for the second time?
        for (String[] extCrypt : extCrypts) {
            List<String> arr = Arrays.asList(extCrypt);
            Cryptogram c = new Cryptogram(arr);
            cryptoMap.put(c.cryptoId, c);
        }
        mDatabase.child("cryptograms").setValue(cryptoMap);
        mDatabase.keepSynced(true);

    }
    private void commonInit () {
        mDatabase.child("players").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> extPlayers = ExternalWebService.getInstance().playernameService();
                if (dataSnapshot.getChildren() != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Player p = snapshot.getValue(Player.class);
                        if (!extPlayers.contains(p.getUsername())) {
                            ExternalWebService.getInstance().updateRatingService(p.getUsername(), p.getFirstname(),p.getLastname(), p.getSolvedCount(), p.getStarted(), p.getTotalIncorrect());
                        }
                    }
                } else {
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mDatabase.child("cryptograms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Cryptogram> mCryptogramList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Cryptogram cr = snapshot.getValue(Cryptogram.class);
                    mCryptogramList.add(cr);
                }
                // upload local cryptograms to external web service
                List<String[]> extCrypts = ExternalWebService.getInstance().syncCryptogramService();
                ArrayList<String> extIds = new ArrayList<>();
                for (String[] extCrypt : extCrypts) {
                    List<String> arr = Arrays.asList(extCrypt);
                    extIds.add(arr.get(0));
                }
                for (Cryptogram cr : mCryptogramList) {
                    if (!extIds.contains(cr.cryptoId)) {
                        ExternalWebService.getInstance().addCryptogramService(cr.encodedPhrase, cr.solutionPhrase);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (admin.isChecked()) {
                    // log in as the administrator
                    Intent login = new Intent(LoginActivity.this, AdminMenuActivity.class);
                    startActivity(login);

                } else if (player.isChecked()) {
                    // log in as a player
                    final String usernameStr = username.getText().toString();
                    if (Objects.equals(usernameStr, "")) {
                        username.setError("please enter a valid username");
                    } else {
                        mDatabase.child("players").child(usernameStr).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Player player = dataSnapshot.getValue(Player.class);
                                if (player != null) {
                                    username.setText("");
                                    Intent login = new Intent(LoginActivity.this, PlayerMenuActivity.class);
                                    login.putExtra("USERNAME", usernameStr);
                                    startActivity(login);
                                } else {
                                    username.setError("please enter a valid username");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                } else {
                    // ask the user to choose one radio button
                    player.setError("Choose your account type.");
                }
            }
        });

    }
}
