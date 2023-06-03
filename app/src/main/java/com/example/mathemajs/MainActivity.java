package com.example.mathemajs;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_email, et_password;
    private CheckBox rememberme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        et_email = (EditText) findViewById(R.id.formEmail);
        et_password = (EditText) findViewById(R.id.formPassword);
        rememberme = (CheckBox) findViewById(R.id.remember);

        SharedPreferences getData = getSharedPreferences("user", Context.MODE_PRIVATE);
        et_email.setText(getData.getString("email", ""));
        et_password.setText(getData.getString("password", ""));

        SharedPreferences RememberMe = getSharedPreferences("remember", Context.MODE_PRIVATE);
        rememberme.setChecked(RememberMe.getBoolean("rememberme", false));

        if (rememberme.isChecked()) {
            UserDatabase admin = new UserDatabase(this, "registrate", null, 1);
            SQLiteDatabase DatabaseConsultUser = admin.getWritableDatabase();

            String mail = et_email.getText().toString();
            String password = et_password.getText().toString();

            if (!mail.isEmpty() && !password.isEmpty()) {
                Cursor data = DatabaseConsultUser.rawQuery("SELECT password FROM users WHERE email ='" + mail + "'", null);
                if (data.moveToFirst() && password.equals(data.getString(0))) {
                    DatabaseConsultUser.close();
                    Intent homepage = new Intent(this, homePage.class);
                    startActivity(homepage);
                } else {//
                    DatabaseConsultUser.close();
                }
            }
        }
    }

    public void linkRegister(View view) {
        Intent register = new Intent(this, registerPage.class);
        startActivity(register);
    }

    public void loginFunction(View view) {
        SharedPreferences UpData = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor Objeditor = UpData.edit();
        Objeditor.putString("email", et_email.getText().toString());
        Objeditor.putString("password", et_password.getText().toString());
        Objeditor.commit();

        boolean checked = rememberme.isChecked();

        SharedPreferences RememberMe = getSharedPreferences("remember", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = RememberMe.edit();
        editor.putBoolean("rememberme", checked);
        editor.commit();

        UserDatabase admin = new UserDatabase(this, "registrate", null, 1);
        SQLiteDatabase DatabaseConsultUser = admin.getWritableDatabase();

        String mail = et_email.getText().toString();
        String password = et_password.getText().toString();

        if (!mail.isEmpty() && !password.isEmpty()) {
            Cursor data = DatabaseConsultUser.rawQuery("SELECT password FROM users WHERE email ='" + mail + "'", null);
            if (data.moveToFirst() && password.equals(data.getString(0))) {
                DatabaseConsultUser.close();
                Intent homepage = new Intent(this, homePage.class);
                startActivity(homepage);
            } else {
                Toast.makeText(this, "Wrong Email or Password, please check and try again!", Toast.LENGTH_SHORT).show();
                DatabaseConsultUser.close();
            }
        } else {
            Toast.makeText(this, "Please complete the information.", Toast.LENGTH_LONG).show();
        }
    }

    public void Logout(View view)
    {
        Intent logout = new Intent(this, MainActivity.class);
        startActivity(logout);
    }
}