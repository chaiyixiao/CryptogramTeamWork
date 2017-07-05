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

import static edu.gatech.seclass.sdpcryptogram.R.layout.login;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(login);

        LinearLayout usernameComp = (LinearLayout) findViewById(R.id.username_container);

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
                    if ( usernameStr == null || usernameStr == "") {
                        // Error
                    } else {
                        // TODO: validate username
                        Log.v("PlayerMenuActivity", usernameStr);
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
