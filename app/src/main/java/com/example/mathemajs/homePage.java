package com.example.mathemajs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

public class homePage extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().hide();
    }

    public void cap1Link(View view)
    {
        Intent cap1 = new Intent(this, jsCap1.class);
        startActivity(cap1);
    }

    public void cap33Link(View view)
    {
        Intent cap33 = new Intent(this, jsCap33.class);
        startActivity(cap33);
    }

    public void cap140Link(View view)
    {
        Intent cap140 = new Intent(this, jsCap140.class);
        startActivity(cap140);
    }

    public void accountLink(View view)
    {
        Intent account = new Intent(this, accountSettings.class);
        startActivity(account);
    }

    public void Logout(View view)
    {
        SharedPreferences RememberMe = getSharedPreferences("remember", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = RememberMe.edit();
        editor.putBoolean("rememberme", false);
        editor.commit();

        Intent logout = new Intent(this, MainActivity.class);
        startActivity(logout);
    }
}