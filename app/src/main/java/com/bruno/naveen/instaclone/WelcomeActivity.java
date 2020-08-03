package com.bruno.naveen.instaclone;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class WelcomeActivity extends AppCompatActivity {

    private Button btt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView txt=findViewById(R.id.txtWel);
        txt.setText("WELCOME!!  "+ ParseUser.getCurrentUser().get("username")+"\n HAVE FUN!!!".toUpperCase());
        txt.setTextColor(Color.BLUE);
        btt=findViewById(R.id.btnLogout);
        btt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                finish();
            }
        });

    }
}