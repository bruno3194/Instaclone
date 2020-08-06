package com.bruno.naveen.instaclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

private EditText et1,et2,et3;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSUSignUp:
                if (et1.getText().toString().equals("") || et2.getText().toString().equals("") || et3.getText().toString().equals(""))
                    Toasty.error(MainActivity.this, "FILL ALL THE FIELDS", Toasty.LENGTH_LONG).show();
                else {
                    final ParseUser user = new ParseUser();
                    user.setUsername(et1.getText().toString());
                    user.setEmail(et2.getText().toString());
                    user.setPassword(et3.getText().toString());
                    final ProgressDialog pd = new ProgressDialog(MainActivity.this);
                    pd.show();
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toasty.success(MainActivity.this, user.getUsername() + " Is Registered Successfully", Toasty.LENGTH_LONG).show();
                                transitToSocialActivity();

                            } else {
                                Toasty.error(MainActivity.this, "Error : " + e.getMessage(), Toasty.LENGTH_LONG).show();
                            }
                            pd.cancel();
                        }
                    });
                }
                break;
            case R.id.btnSULogIn:
                Intent in=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(in);
                break;

        }
    }
    private Button b1,b2;

    public void rootTapped(View view){
        InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        // Toasty.Config.getInstance().tintIcon(true);
        //Toasty.Config.getInstance().setToastTypeface(@NonNull Typeface typeface);
        Toasty.Config.getInstance().setTextSize(25);
       // Toasty.Config.getInstance().allowQueue(true);
        Toasty.Config.getInstance().apply();
        et1=findViewById(R.id.edtSignUpName);
        et2=findViewById(R.id.edtSignUpEmail);
        et3=findViewById(R.id.edtSignUpPswd);
        b1=findViewById(R.id.btnSUSignUp);
        b2=findViewById(R.id.btnSULogIn);
        //TO MAKE SURE NO CURRENT USER IS LOGGED IN (TO AVOID CONFLICTS)(TEMPROARY)
        if(ParseUser.getCurrentUser()!=null)
        {
            //ParseUser.getCurrentUser().logOut();
            transitToSocialActivity();
        }

        b1.setOnClickListener(this);


        b2.setOnClickListener(this);
        et3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==keyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN)
                    onClick(b1);
                return false;
            }
        });

    }
        private void transitToSocialActivity()
        {
            Intent imp=new Intent(MainActivity.this,SocialMediaActivity.class);
            startActivity(imp);
            finish();
        }
}