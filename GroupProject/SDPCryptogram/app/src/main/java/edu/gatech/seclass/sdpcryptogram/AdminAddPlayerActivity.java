package edu.gatech.seclass.sdpcryptogram;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by chaiyixiao on 04/07/2017.
 */
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.gatech.seclass.utilities.ExternalWebService;

import static edu.gatech.seclass.sdpcryptogram.R.layout.add_player;

public class AdminAddPlayerActivity extends AppCompatActivity {

    private EditText username;
    private EditText firstname;
    private EditText lastname;

    private DatabaseReference mDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(add_player);
        mDatabase = FirebaseGetInstanceClass.GetFirebaseDatabaseInstance().getReference();

        Button saveBtn = (Button) findViewById(R.id.save_player);
        Button cancelBtn = (Button) findViewById(R.id.cancel_add_player);

        username = (EditText) findViewById(R.id.add_username);
        firstname = (EditText) findViewById(R.id.add_first_name);
        lastname = (EditText) findViewById(R.id.add_last_name);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate and storage
                String usernameStr = username.getText().toString();
                String firstnameStr = firstname.getText().toString();
                String lastnameStr = lastname.getText().toString();

                // save the new player info to the external webservice
                // true if successfully updated or added player rating,
                // false if any values are null or empty or cannot add to player ratings
                // TODO： should pop up confirmation message
                if (ExternalWebService.getInstance().updateRatingService(
                        usernameStr, firstnameStr, lastnameStr, 0, 0, 0)) {
                    Log.v("add a new player: ", "successful");
                } else {
                    Log.v("add a new player: ", "unsuccessful");
                }


                Player newPlayer = new Player(usernameStr, firstnameStr, lastnameStr);
                mDatabase.child("players").child(usernameStr).setValue(newPlayer);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAddPlayerActivity.this.finish();
            }
        });
    }
}
