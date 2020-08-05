package com.bruno.naveen.instaclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileTab extends Fragment {
    private EditText ed1,ed2,ed3,ed4,ed5;
    private Button b1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileTab.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileTab newInstance(String param1, String param2) {
        ProfileTab fragment = new ProfileTab();
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

    public void nullEliminate(ParseUser cur,String str, EditText ed)
    {
        if(str.equals("null"))
            ed.setText("");
        else
            ed.setText(cur.get("profileName")+"");
        Toasty.success(getContext(),str,Toasty.LENGTH_LONG).show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ////////////*******************IMP**********CANT INTIALIZE UI COMPONENTS NORMALLY************************?????///////////////////
        View view= inflater.inflate(R.layout.fragment_profile_tab, container, false);
        ed1=view.findViewById(R.id.edtProfName);
        ed2=view.findViewById(R.id.edtBio);
        ed3=view.findViewById(R.id.edtProfesssion);
        ed4=view.findViewById(R.id.edtHobbies);
        ed5=view.findViewById(R.id.edtFasvSport);
        b1=view.findViewById(R.id.btnUpdate);
        final ParseUser cur=ParseUser.getCurrentUser();
        nullEliminate(cur,cur.get("profileName")+"",ed1);
        nullEliminate(cur,cur.get("profileBio")+"",ed2);
        nullEliminate(cur,cur.get("profileProfession")+"",ed3);
        nullEliminate(cur,cur.get("profileHobbies")+"",ed4);
        nullEliminate(cur,cur.get("profileFavSport")+"",ed5);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cur.put("profileName",ed1.getText().toString());
                cur.put("profileBio",ed2.getText().toString());
                cur.put("profileProfession",ed3.getText().toString());
                cur.put("profileHobbies",ed4.getText().toString());
                cur.put("profileFavSport",ed5.getText().toString());
               final  ProgressDialog pd=new ProgressDialog(getContext());
                pd.show();
                cur.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null)
                            Toasty.success(getContext(),"SUCCESSFULLY UPDATED",Toasty.LENGTH_LONG).show();//********************cant use context name in fragments***********
                        else
                            Toasty.error(getContext(),"Error : "+e.getMessage(),Toasty.LENGTH_LONG).show();
                        pd.cancel();
                    }
                });
            }
        });
        return view;
    }

//    public void onBackPressed() {
//        // Here you want to show the user a dialog box
//        new AlertDialog.Builder(getContext())
//                .setTitle("Exiting the App")
//                .setMessage("Are you sure?")
//                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        // The user wants to leave - so dismiss the dialog and exit
//                        ParseUser.logOut();
//                        getActivity().finish();
//                        System.exit(0);
//                        dialog.dismiss();
//                    }
//                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                // The user is not sure, so you can exit or just stay
//                dialog.dismiss();
//            }
//        }).show();
//
//    }
}