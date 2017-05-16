package com.example.adrian.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    private Database dbHelper = DatabaseObject.getDbHelper();
    private SQLiteDatabase db = DatabaseObject.getDb();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        setActionListeners();
    }

    public void onSignInClick (){
        String username, password;
        username = ((EditText)findViewById(R.id.usernameText)).getText().toString();
        password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        String[] strArgs = {username, password};

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? and password = ?", strArgs );
        if (cursor.moveToFirst()) {
            Toast.makeText(getApplicationContext(), "Connected!" + cursor.getInt(0), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getBaseContext(), TodoActivity.class);
            intent.putExtra(Extras.SESSION_ID, cursor.getInt(0));
            cursor.close();
            startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), "Error! User does not exist. Please sign up first.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setActionListeners(){

        Button signInBtn = (Button)findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInClick();
            }
        });

        Button signUpBtn = (Button)findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DatabaseObject.closeDatabase();
        finish();
        System.exit(0);
    }

}
