package com.example.mathemajs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class registerPage extends AppCompatActivity {

    private EditText et_username, et_email, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        getSupportActionBar().hide();

        et_username = (EditText) findViewById(R.id.formUsername);
        et_email = (EditText) findViewById(R.id.formEmail);
        et_password = (EditText) findViewById(R.id.formPassword);
    }

    public void RegisterNewUser(View view)
    {
        UserDatabase admin = new UserDatabase(this, "registrate", null, 1);
        SQLiteDatabase DatabaseUser = admin.getWritableDatabase();

        String username = et_username.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty())
        {
            ContentValues register = new ContentValues();
            register.put("username", username);
            register.put("email", email);
            register.put("password", password);

            Cursor dataUsername = DatabaseUser.rawQuery("SELECT username FROM users WHERE username='" + username + "'", null);
            Cursor dataEmail = DatabaseUser.rawQuery("SELECT email FROM users WHERE email='" + email + "'", null);
            if (dataUsername.moveToFirst())
            {
                DatabaseUser.close();
                Toast.makeText(this, "This username already exist.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (dataEmail.moveToFirst())
                {
                    DatabaseUser.close();
                    Toast.makeText(this, "This email already exist.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DatabaseUser.insert("users", null, register);
                    DatabaseUser.close();

                    et_username.setText("");
                    et_email.setText("");
                    et_password.setText("");
                    Toast.makeText(this, "The account has been created successfully!", Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(this, MainActivity.class);
                    startActivity(login);
                }
            }
        }
        else
        {
            Toast.makeText(this, "Something is wrong, please check the information and try again!", Toast.LENGTH_LONG).show();
        }
    }

    public void linkLogin(View view)
    {
        Intent login = new Intent(this, MainActivity.class);
        startActivity(login);
    }

    public void Logout(View view)
    {
        Intent logout = new Intent(this, MainActivity.class);
        startActivity(logout);
    }
}