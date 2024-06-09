package com.example.a259317proj.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.a259317proj.Fragments.CallsFragment;
import com.example.a259317proj.Fragments.ChatFragment;
import com.example.a259317proj.Fragments.StatusFragment;

public class MainAdapter extends FragmentPagerAdapter {

    public MainAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ChatFragment();
            case 1: return new CallsFragment();
            case 2: return new StatusFragment();


        }
        return null;
    }



    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;
        if(position==0){
            title = "CHATS";


        }
        return title;
    }
}