package edu.gatech.seclass.sdpcryptogram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // initialize the External Web Service and get the list of usernames
        // !!! local version, will not persist data between runs
        // !!! a cloud-synchronizing version may be provided by the instructor later
        final List<String> usernameList =  ExternalWebService.getInstance().playernameService();
        for (String username : usernameList) {
            Log.v("user", username);
        }

//        LinearLayout usernameComp = (LinearLayout) findViewById(R.id.username_container);

        RadioGroup loginRadios = (RadioGroup) findViewById(R.id.radioGroup);
        final RadioButton admin = (RadioButton) findViewById(R.id.admin_radio);
        final RadioButton player = (RadioButton) findViewById(R.id.player_radio);

        loginRadios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.admin_radio) {
                    //Your code
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
                    // startActivity
                    Intent login = new Intent(LoginActivity.this, AdminMenuActivity.class);
                    startActivity(login);
                } else if (player.isChecked()) {
                    EditText username = (EditText) findViewById(R.id.username);
                    String usernameStr = username.getText().toString();

                    // check whether the username is contained in the usernameList
                    if (usernameList.contains(usernameStr)) {
                        // TODO: pop up message "invalid username"
                    } else {

                        mDatabase.child("players").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    Player player = snapshot.getValue(Player.class);
                                    Log.e("Get Data", player.username);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        Intent login = new Intent(LoginActivity.this, PlayerMenuActivity.class);
                        login.putExtra("username", usernameStr);
                        startActivity(login);
                    }
                } else {
                    // Error: no radio checked
                }
            }
        });

    }
}
