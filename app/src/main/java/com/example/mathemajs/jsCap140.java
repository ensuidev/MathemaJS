package com.example.mathemajs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class jsCap140 extends AppCompatActivity {
    private VideoView chapter140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_cap140);
        getSupportActionBar().hide();

        chapter140 = (VideoView) findViewById(R.id.chapter140);
        chapter140.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.c140apirest));
        MediaController mc = new MediaController(this);
        mc.setAnchorView(chapter140);
        chapter140.setMediaController(mc);

    }

    public void homeLink(View view)
    {
        Intent home = new Intent(this, homePage.class);
        startActivity(home);
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