package com.bruno.naveen.instaclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                ProfileTab pt=new ProfileTab();
                return pt;
            case 1:
                UsersTab ut=new UsersTab();
                return ut;
            case 2:
                return new SharePictureTab();//same like above
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Profile";
            case 1:
                return "Users Tab";
            case 2:
                return "Share Pictures";
            default:
                return null;
        }
    }
}
