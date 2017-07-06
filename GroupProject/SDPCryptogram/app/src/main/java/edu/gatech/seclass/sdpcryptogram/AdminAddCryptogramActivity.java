package edu.gatech.seclass.sdpcryptogram;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by chaiyixiao on 04/07/2017.
 */
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import edu.gatech.seclass.utilities.ExternalWebService;

import static edu.gatech.seclass.sdpcryptogram.R.layout.add_cryptogram;

public class AdminAddCryptogramActivity extends AppCompatActivity{

    private DatabaseReference mDatabase;

    private EditText encodedText;
    private EditText solutionText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(add_cryptogram);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button saveBtn = (Button) findViewById(R.id.save_button);
        Button resetBtn = (Button) findViewById(R.id.reset_button);
        Button cancelBtn = (Button) findViewById(R.id.cancel_button);
        encodedText = (EditText) findViewById(R.id.encoded_phrase);
        solutionText = (EditText) findViewById(R.id.solution_phrase);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request id && save to db
                String encodedStr = encodedText.getText().toString();
                String solutionStr = solutionText.getText().toString();
                try {
                    // submit the new cryptogram to the external webservice
                    // return id is successful
                    // throws IllegalArgumentException if puzzle duplicates existing puzzle or solution,
                    // or if non-letter characters do not match, if capitalization does not match,
                    // or letter substitutions are inconsistent
                    String idStr = ExternalWebService.getInstance().addCryptogramService(encodedStr, solutionStr);

                    Cryptogram crypto = new Cryptogram(encodedStr, solutionStr, idStr);
                    mDatabase.child("cryptograms").child(idStr).setValue(crypto);
                } catch (IllegalArgumentException e) {
                    System.err.println("IllegalArgumentException: " + e.getMessage());
                }

            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encodedText.setText("");
                solutionText.setText("");
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminAddCryptogramActivity.this.finish();
            }
        });
    }
}
