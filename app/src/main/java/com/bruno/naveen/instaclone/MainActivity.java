package com.bruno.naveen.instaclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt,bt2,bt3;
    private EditText et1,et2,et3,et4,et5;
    private TextView tv;
    public static String str="";


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
       // Toasty.Config.getInstance().allowQueue(true);
        Toasty.Config.getInstance().apply();
        bt = findViewById(R.id.button);
        bt2 = findViewById(R.id.btnAll);
        bt3 = findViewById(R.id.btn3);
        et1 = findViewById(R.id.EditText);
        et2 = findViewById(R.id.EditText2);
        et3 = findViewById(R.id.EditText3);
        et4 = findViewById(R.id.EditText4);
        et5 = findViewById(R.id.EditText5);
        tv = findViewById(R.id.txt);
        //GETTING SPECIFIC/SINGLE DATA
        try {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParseQuery<ParseObject> pq = ParseQuery.getQuery("Details");
                    pq.getInBackground("YGJSASylwG", new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null && object != null) {

                                tv.setText("NAME : " + object.get("Name") + "\nAGE : " + object.get("Age"));
                            } else if (e != null) {
                                Toasty.error(MainActivity.this, "Error" + e.toString(), Toasty.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
            });
        } catch (Exception err) {
            Toasty.error(MainActivity.this, "Error" + err.toString(), Toasty.LENGTH_SHORT).show();
        }

        //GETTING ALL DATA
        try {
            bt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParseQuery<ParseObject> pq = ParseQuery.getQuery("Details");
                    pq.whereGreaterThanOrEqualTo("Age",17);
                    pq.setLimit(2);
                    pq.findInBackground(new FindCallback<ParseObject>() {


                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                           MainActivity.str="";
                            if (objects.size() != 0 && e == null) {
                                for (ParseObject ob : objects) {
                                    MainActivity.str = MainActivity.str + "NAME : " + ob.get("Name") + "   AGE : " + ob.get("Age") + "\n";
                                }
                            } else if (e != null) {
                                Toasty.error(MainActivity.this, "Error" + e.toString(), Toasty.LENGTH_SHORT).show();
                                return;
                            }
                            //TO GET IN SINGLE TOAST
                            Toasty.info(MainActivity.this, "" + MainActivity.str, Toasty.LENGTH_LONG).show();
                            //TO GET TOAST USING TIMER :)
//
//                            Timer t = new Timer();
//                            t.scheduleAtFixedRate(new TimerTask() {
//
//
//                                @Override
//                                public void run() {
//                                    MainActivity.this.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            Toasty.info(MainActivity.this, "" + MainActivity.str, Toasty.LENGTH_LONG).show();
//
//                                        }
//                                    });
//                                }
//                            }, 0, 1000);

                        }
                    });
                }
            });

            bt.setOnClickListener(this);
        }
        catch (Exception e)
        {
            Toasty.error(MainActivity.this, "Error" + e.toString(), Toasty.LENGTH_SHORT).show();

        }
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MainActivity.this,SignupLoginActivity.class);
                startActivity(in);
            }
        });
    }
}