package com.example.adrian.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private Database dbHelper = DatabaseObject.getDbHelper();
    private SQLiteDatabase db = DatabaseObject.getDb();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setActionListeners();
    }

    private void setActionListeners() {
        Button signUpBtn = (Button) findViewById(R.id.signUpBtnFinal);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpClick();

            }
        });
    }

    private void onSignUpClick (){
        String username, password;
        username = ((EditText)findViewById(R.id.usernameTextSignUp)).getText().toString();
        password = ((EditText)findViewById(R.id.passwordTextSignUp)).getText().toString();
        String[] strArgsForInsert = {username, password};
        String[] strArgs = {username};
        if (checkValidUser (strArgsForInsert)) {

            Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", strArgs);
            if (cursor.moveToFirst()) {
                Toast.makeText(getApplicationContext(), "Username already exists! Choose another.", Toast.LENGTH_SHORT).show();
            } else {
                db.execSQL("INSERT INTO users (username, password) VALUES (?, ?)", strArgsForInsert);
                Toast.makeText(getApplicationContext(), "Account created. You can login now", Toast.LENGTH_SHORT).show();

                cursor.close();
//                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
//                startActivity(intent);
//                getParent().finish();
                finish();
            }
        }
        else{
            Toast.makeText(this, "User must contain at least 6 characters, 1 uppercase letter and 1 digit", Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkValidUser(String[] strArgsForInsert) {
        if (strArgsForInsert[1].length() < 6 )
            return false;
        boolean hasNumber= false, hasUpperCase = false;
        for (int i = 0; i<strArgsForInsert[1].length(); i++){
            char c = strArgsForInsert[1].charAt(i);
            if (c >= '0' && c <= '9')
                hasNumber = true;
            if (c >= 'A' && c<='Z')
                hasUpperCase = true;
        }
        return hasNumber && hasUpperCase;
    }
}
