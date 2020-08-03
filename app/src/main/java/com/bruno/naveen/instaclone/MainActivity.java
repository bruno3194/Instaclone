package com.bruno.naveen.instaclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt;
    private EditText et1,et2,et3,et4,et5;

    @Override
    public void onClick(View view) {
        try{
        ParseObject details=new ParseObject("Details");
        details.put("Name",et1.getText().toString());
        details.put("Age",Integer.parseInt(et2.getText().toString()));
        details.put("PHONE",Long.parseLong(et3.getText().toString()));
        details.put("ADDRESS",et4.getText().toString());
        details.put("email",et5.getText().toString());
            details.saveInBackground(new SaveCallback() {

                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Toasty.success(MainActivity.this,et1.getText().toString()+"is add successfully",Toasty.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toasty.error(MainActivity.this,"Error"+e.toString(),Toasty.LENGTH_SHORT).show();
                    }
                }
            });
        }
        catch(Exception er)
        {
            Toasty.error(MainActivity.this,"Error"+er.toString(),Toasty.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
       // Toasty.Config.getInstance().tintIcon(true);
        //Toasty.Config.getInstance().setToastTypeface(@NonNull Typeface typeface);
        Toasty.Config.getInstance().setTextSize(25);
        Toasty.Config.getInstance().allowQueue(true);
        Toasty.Config.getInstance().apply();
        bt=findViewById(R.id.button);
        et1=findViewById(R.id.EditText);
        et2=findViewById(R.id.EditText2);
        et3=findViewById(R.id.EditText3);
        et4=findViewById(R.id.EditText4);
        et5=findViewById(R.id.EditText5);
        bt.setOnClickListener(this);
    }
}