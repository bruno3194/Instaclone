package com.bruno.naveen.instaclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersTab extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent inb=new Intent(getContext(),UsersPost.class);
        inb.putExtra("username",arr.get(i).toString());
        startActivity(inb);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tv;
    private ListView lst;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList arr;
    private ArrayAdapter ad;
    public UsersTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsersTab.
     */
    // TODO: Rename and change types and number of parameters
    public static UsersTab newInstance(String param1, String param2) {
        UsersTab fragment = new UsersTab();
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
        View vv=inflater.inflate(R.layout.fragment_users_tab, container, false);
        tv=vv.findViewById(R.id.txtUser);
        lst=vv.findViewById(R.id.lst);
        arr=new ArrayList();
        ad=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arr);

        lst.setOnItemClickListener(UsersTab.this);
        lst.setOnItemLongClickListener(UsersTab.this);

        ParseQuery<ParseUser> pq= ParseUser.getQuery();
        pq.whereNotEqualTo("username",ParseUser.getCurrentUser().get("username"));
        pq.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null) {
                    if(objects.size()>0) {
                        for (ParseUser cur : objects) {
                            arr.add(cur.getUsername());
                        }
                    }
                }
                lst.setAdapter(ad);
                tv.animate().alpha(0).setDuration(2000);
                lst.setVisibility(View.VISIBLE);
                }
        });
return vv;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        ParseQuery<ParseUser> pe=ParseUser.getQuery();
        pe.whereEqualTo("username",arr.get(i).toString());
        pe.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if(object!=null && e==null)
                {
                   Toasty.custom(getContext(),"BIO:\n"+object.get("profileBio")+"\nProfession : "+object.get("profileProfession")+"\nHobbies:\n"+object.get("profileHobbies"),getContext().getDrawable(R.drawable.smile), Toasty.LENGTH_LONG,true).show();
//                             *********************BUGGY*******************************
//                    new IonAlert(getContext(), IonAlert.WARNING_TYPE)
//                            .setTitleText((object.get("username")+"").toUpperCase())
//                            .setContentText("BIO:\n"+object.get("profileBio")+"\nProfession : "+object.get("profileProfession")+"\nHobbies:\n"+object.get("profileHobbies"))
//                            .setConfirmText("OK")
//                            .setCustomImage(R.drawable.smile)
//                            .setConfirmClickListener(new IonAlert.ClickListener() {
//                                @Override
//                                public void onClick(IonAlert sDialog) {
//                                    sDialog.dismissWithAnimation();
//                                }
//                            })
//                            .show();
                }
            }
        });
        return true;
    }
}