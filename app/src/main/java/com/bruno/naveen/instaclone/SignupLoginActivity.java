package com.bruno.naveen.instaclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import es.dmoral.toasty.Toasty;

public class SignupLoginActivity extends AppCompatActivity {

    private EditText et1,et2,et3,et4;
    private Button b1,b2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        et1=findViewById(R.id.edtUsignUP);
        et2=findViewById(R.id.edtPsignUP);
        et3=findViewById(R.id.edtUL);
        et4=findViewById(R.id.edtPL);

        b1=findViewById(R.id.btnSignUp);
        b2=findViewById(R.id.btnLogin);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ParseUser appUser=new ParseUser();
                appUser.setUsername(et1.getText().toString());
                appUser.setPassword(et2.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null)
                        {
                            Toasty.success(SignupLoginActivity.this,appUser.getUsername()+" Is Registered Successfully",Toasty.LENGTH_LONG).show();
                            Intent inn=new Intent(SignupLoginActivity.this,WelcomeActivity.class);
                            startActivity(inn);
                        }
                        else
                        {
                            Toasty.error(SignupLoginActivity.this,"Error : "+e.getMessage(),Toasty.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logInInBackground(et3.getText().toString(), et4.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null&&e==null)
                        {
                            Toasty.success(SignupLoginActivity.this,"Successfully Logged In",Toasty.LENGTH_LONG).show();
                            Intent inn=new Intent(SignupLoginActivity.this,WelcomeActivity.class);
                            startActivity(inn);
                        }
                        else
                        {
                         Toasty.error(SignupLoginActivity.this,"Error : "+e.getMessage(),Toasty.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
