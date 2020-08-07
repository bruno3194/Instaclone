package com.bruno.naveen.instaclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UsersPost extends AppCompatActivity {

    private  LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);
        Intent ri=getIntent();
        String ru=ri.getStringExtra("username");

        setTitle(ru+"'s Posts");

        ParseQuery<ParseObject> parseQuery=new ParseQuery<ParseObject>("Photos");
        parseQuery.whereEqualTo("username",ru);
        parseQuery.orderByDescending("createdAt");

        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Loading posts..");
        pd.show();

        ll=findViewById(R.id.linearLayout);
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size()>0 && e==null)
                {
                    for(ParseObject post : objects)
                    {
                        final TextView imgDesc=new TextView(UsersPost.this);
                        String str=post.get("description")+"";
                        imgDesc.setText(str);
                        ParseFile pf=(ParseFile)post.get("picture");
                        pf.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data!=null && e==null)
                                {
                                    Bitmap bm= BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView im=new ImageView(UsersPost.this);

                                    LinearLayout.LayoutParams imgParams=new LinearLayout.LayoutParams(600,600);
                                    imgParams.setMargins(5,5,5,5);
                                    im.setLayoutParams(imgParams);

                                    im.setScaleType(ImageView.ScaleType.CENTER);
                                    im.setImageBitmap(bm);

                                    LinearLayout.LayoutParams desParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    desParams.setMargins(5,5,5,5);
                                    imgDesc.setLayoutParams(desParams);

                                    imgDesc.setGravity(Gravity.CENTER);
                                    imgDesc.setBackgroundColor(Color.BLUE);
                                    imgDesc.setTextColor(Color.WHITE);
                                    imgDesc.setTextSize(27f);

                                    ll.addView(im);
                                    ll.addView(imgDesc);
                                }
                                pd.dismiss();
                            }
                        });
                    }
                }
            }
        });

    }
}