package com.bruno.naveen.instaclone;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SharePictureTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SharePictureTab extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.imgo:
                if(Build.VERSION.SDK_INT>=23 && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }
                else
                {
                    getChosenImage();
                }

                break;
            case R.id.btnShare:
                if(recvd!=null)
                {
                    if(edet.getText().toString().equals(""))
                    {
                        Toasty.error(getContext(),"You Must Enter A Description",Toasty.LENGTH_SHORT).show();

                    }
                    else
                    {
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        recvd.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] pichu=byteArrayOutputStream.toByteArray();
                        //server..
                        ParseFile ps=new ParseFile("bru.png",pichu);
                        ParseObject po=new ParseObject("Photos");
                        po.put("picture",ps);
                        po.put("description",edet.getText().toString());
                        po.put("username", ParseUser.getCurrentUser().getUsername());
                        final ProgressDialog pd=new ProgressDialog(getContext());
                        pd.setMessage("Uploading");
                        pd.show();
                        po.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null)
                                {
                                    Toasty.success(getContext(),"SUCCESSFULLY UPLOADED",Toasty.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toasty.error(getContext(),"Error : "+e.getMessage(),Toasty.LENGTH_LONG).show();
                                }
                                pd.dismiss();

                            }
                        });
                    }

                }
                else
                {
                    Toasty.error(getContext(),"You Must Select An Image",Toasty.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void getChosenImage() {
        Intent in=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(in,2000);
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView im;
    private EditText edet;
    private Button btn;
    private Bitmap recvd;

    public SharePictureTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SharePictureTab.
     */
    // TODO: Rename and change types and number of parameters
    public static SharePictureTab newInstance(String param1, String param2) {
        SharePictureTab fragment = new SharePictureTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vc=inflater.inflate(R.layout.fragment_share_picture_tab, container, false);

        edet=vc.findViewById(R.id.edtDesc);
        im=vc.findViewById(R.id.imgo);
        btn=vc.findViewById(R.id.btnShare);

        im.setOnClickListener(SharePictureTab.this);
        btn.setOnClickListener(SharePictureTab.this);
        return vc;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1000)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                getChosenImage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2000)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try {
                    //method 2 in socialMedia activity(w/o cursor)
                    Uri selected=data.getData();
                    String[] filePath={MediaStore.Images.Media.DATA};
                    Cursor cur=getActivity().getContentResolver().query(selected,filePath,null,null,null);
                    cur.moveToFirst();
                    int ci=cur.getColumnIndex(filePath[0]);
                    String pp=cur.getString(ci);
                    cur.close();
                    recvd= BitmapFactory.decodeFile(pp);
                    im.setImageBitmap(recvd);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            }

        }
    }
