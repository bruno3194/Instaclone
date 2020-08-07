package com.bruno.naveen.instaclone;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import es.dmoral.toasty.Toasty;

public class SocialMediaActivity extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabAdapter tabAdapter;
    private Bitmap recvd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        setTitle("Welcome "+ParseUser.getCurrentUser().getUsername()+" !!!");

        toolbar=findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        viewPager=findViewById(R.id.viewPager);
        tabAdapter=new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager,false);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.postImageItem)
        {
            if(Build.VERSION.SDK_INT>=23 && ActivityCompat.checkSelfPermission(SocialMediaActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},3000);
            }
            else
            {
                captureImage();
            }

        }
        else if(item.getItemId()==R.id.logOutItem)
        {
            ParseUser.getCurrentUser().logOut();
            finish();
            Intent inm=new Intent(SocialMediaActivity.this,MainActivity.class);
            startActivity(inm);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==3000)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                    captureImage();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public void captureImage()
    {
        Intent inn=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(inn,4000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==4000&&resultCode==RESULT_OK&&data!=null)
        {
            try {
                Uri selected=data.getData();
                Bitmap bm=MediaStore.Images.Media.getBitmap(this.getContentResolver(),selected);
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] bytes=byteArrayOutputStream.toByteArray();
                ParseFile ps=new ParseFile("bru.png",bytes);
                ParseObject po=new ParseObject("Photos");
                po.put("picture",ps);
                po.put("username", ParseUser.getCurrentUser().getUsername());
                final ProgressDialog pd=new ProgressDialog(this);
                pd.setMessage("Uploading");
                pd.show();
                po.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null)
                        {
                            Toasty.success(SocialMediaActivity.this,"SUCCESSFULLY UPLOADED",Toasty.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toasty.error(SocialMediaActivity.this,"Error : "+e.getMessage(),Toasty.LENGTH_LONG).show();
                        }
                        pd.dismiss();

                    }
                });


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}