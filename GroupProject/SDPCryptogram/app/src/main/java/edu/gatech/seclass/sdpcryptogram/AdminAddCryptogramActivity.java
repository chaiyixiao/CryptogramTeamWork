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
import static edu.gatech.seclass.sdpcryptogram.R.layout.add_cryptogram;

public class AdminAddCryptogramActivity extends AppCompatActivity{


    private EditText encodedText;
    private EditText solutionText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(add_cryptogram);
        Button saveBtn = (Button) findViewById(R.id.save_button);
        Button resetBtn = (Button) findViewById(R.id.reset_button);
        Button cancelBtn = (Button) findViewById(R.id.cancel_button);
        encodedText = (EditText) findViewById(R.id.encoded_phrase);
        solutionText = (EditText) findViewById(R.id.solution_phrase);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: check encodedText solutionText length match or not
                // check same encoded letter matches solution?
                // request id && save to db
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
