package com.example.animelist.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.animelist.LoginActivity;
import com.example.animelist.R;
import com.parse.ParseUser;


public class SettingsFragment extends Fragment {

    Button logoutBtn;
    Switch modeSwitch;
    SharedPreferences sharedPreferences;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logoutBtn = view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        modeSwitch = view.findViewById(R.id.swModeToggle);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
        modeSwitch.setChecked(sharedPref.getBoolean("value",true));

        /*
        modeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modeSwitch.isChecked()){
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("save",Context.MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    modeSwitch.setChecked(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("save",Context.MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    modeSwitch.setChecked(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

            }
        });*/

        modeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("save",Context.MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                }
                else
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("save",Context.MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                }
            }
        });
    }

    private void logoutUser() {
        ParseUser.logOutInBackground();
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }

}