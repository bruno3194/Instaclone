package com.bruno.naveen.instaclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et1,et2;
    private Button b1,b2;

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnLILogIn:

                ParseUser.logInInBackground(et1.getText().toString(), et2.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null&&e==null)
                        {
                            Toasty.success(LoginActivity.this,"Successfully Logged In",Toasty.LENGTH_LONG).show();
                            transitToSocialActivity();
                        }
                        else
                        {
                            Toasty.error(LoginActivity.this,"Error : "+e.getMessage(),Toasty.LENGTH_LONG).show();
                        }

                    }
                });


            break;
            case R.id.btnLISignUp:
                finish();
                break;
        }


    }
    public void root(View view){
        InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log In");

        et1=findViewById(R.id.edLogInEmail);
        et2=findViewById(R.id.edLogInPswd);
        et2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(b1);
                }
                return false;
            }
        });
        b1=findViewById(R.id.btnLILogIn);
        b2=findViewById(R.id.btnLISignUp);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        //TO MAKE SURE NO CURRENT USER IS LOGGED IN (TO AVOID CONFLICTS)(TEMPROARY)
        if(ParseUser.getCurrentUser()!=null)
        {
          //  ParseUser.getCurrentUser().logOut();
            transitToSocialActivity();
        }
    }
    private void transitToSocialActivity()
    {
        Intent imp=new Intent(LoginActivity.this,SocialMediaActivity.class);
        startActivity(imp);
    }
}