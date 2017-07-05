package edu.gatech.seclass.sdpcryptogram;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by chaiyixiao on 04/07/2017.
 */
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        mDatabase = FirebaseDatabase.getInstance().getReference();

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
