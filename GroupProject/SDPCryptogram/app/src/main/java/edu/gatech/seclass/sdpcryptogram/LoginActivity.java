package edu.gatech.seclass.sdpcryptogram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.gatech.seclass.utilities.ExternalWebService;

import static edu.gatech.seclass.sdpcryptogram.R.layout.login;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(login);

        mDatabase = FirebaseGetInstanceClass.GetFirebaseDatabaseInstance().getReference();
        mDatabase.child("players").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> extPlayers = ExternalWebService.getInstance().playernameService();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Player p = snapshot.getValue(Player.class);
                    if (!extPlayers.contains(p.getUsername())) {
                        ExternalWebService.getInstance().updateRatingService(p.getUsername(), p.getFirstname(),p.getLastname(), p.getSolvedCount(), p.getStarted(), p.getTotalIncorrect());
                    }
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
        RadioGroup loginRadios = (RadioGroup) findViewById(R.id.radioGroup);
        final RadioButton admin = (RadioButton) findViewById(R.id.admin_radio);
        final RadioButton player = (RadioButton) findViewById(R.id.player_radio);

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

        loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (admin.isChecked()) {
                    // log in as the administrator
                    Intent login = new Intent(LoginActivity.this, AdminMenuActivity.class);
                    startActivity(login);

                } else if (player.isChecked()) {
                    // log in as a player
                    final EditText username = (EditText) findViewById(R.id.username);
                    final String usernameStr = username.getText().toString();

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
                } else {
                    // ask the user to choose one radio button
                    player.setError("Choose your account type.");
                }
            }
        });

    }
}
