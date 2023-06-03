package com.example.mathemajs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class accountSettings extends AppCompatActivity {

    private EditText et_username, et_email, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        getSupportActionBar().hide();

        et_username = (EditText) findViewById(R.id.formUsername);
        et_email = (EditText) findViewById(R.id.formEmail);
        et_password = (EditText) findViewById(R.id.formPassword);

        SharedPreferences getPref = getSharedPreferences("user", MODE_PRIVATE);
        String mail = getPref.getString("email", null);
        String password = getPref.getString("password", null);

        UserDatabase admin = new UserDatabase(this, "registrate", null, 1);
        SQLiteDatabase DatabaseConsultUser = admin.getWritableDatabase();
        Cursor data = DatabaseConsultUser.rawQuery("SELECT username FROM users WHERE email ='" + mail + "'", null);

        if (data.moveToFirst())
        {
            et_username.setText(data.getString(0));
            DatabaseConsultUser.close();
        }
        et_email.setText(mail);
        et_password.setText(password);
    }

    public void homeLink(View view)
    {
        Intent home = new Intent(this, homePage.class);
        startActivity(home);
    }

    public void UpdateData(View view)
    {
        UserDatabase admin = new UserDatabase(this, "registrate", null, 1);
        SQLiteDatabase DatabaseConsultUser = admin.getWritableDatabase();

        String camp_username = et_username.getText().toString();
        String camp_email = et_email.getText().toString();
        String camp_password = et_password.getText().toString();

        if (!camp_email.isEmpty() && !camp_password.isEmpty())
        {
            ContentValues register = new ContentValues();
            register.put("username", camp_username);
            register.put("email", camp_email);
            register.put("password", camp_password);

            int cantidad = DatabaseConsultUser.update("users", register, "username='" + camp_username + "'", null);
            DatabaseConsultUser.close();

            if (cantidad == 1)
            {
                Toast.makeText(this, "The information was updated successfully.", Toast.LENGTH_SHORT).show();
                Intent updated = new Intent(this, MainActivity.class);
                startActivity(updated);
            }
        }
        else
        {
            Toast.makeText(this, "Please complete the information and try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void DeleteUser(View view)
    {
        UserDatabase admin = new UserDatabase(this, "registrate", null, 1);
        SQLiteDatabase DatabaseConsultUser = admin.getWritableDatabase();

        String camp_username = et_username.getText().toString();

        int cantidad = DatabaseConsultUser.delete("users", "username='" + camp_username + "'", null);
        DatabaseConsultUser.close();

        if (cantidad == 1)
        {
            Toast.makeText(this, "This account was deleted successfully!", Toast.LENGTH_SHORT).show();
            Intent deleted = new Intent(this, MainActivity.class);
            startActivity(deleted);
        }
        else
        {
            Toast.makeText(this, "Something went wrong, please try again!", Toast.LENGTH_SHORT).show();
        }
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