package com.example.visibleandgoneviewwithdatabinding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.visibleandgoneviewwithdatabinding.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        // trường hợp không dùng Observable Field
//        UserViewModel userViewModel = new UserViewModel(false);
        // trường hợp dùng Observable Field
        UserViewModel userViewModel = new UserViewModel();

        mActivityMainBinding.setUserViewModel(userViewModel);

        setContentView(mActivityMainBinding.getRoot());
    }
}