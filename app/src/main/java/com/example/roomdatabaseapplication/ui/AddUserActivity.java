package com.example.roomdatabaseapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomdatabaseapplication.R;
import com.example.roomdatabaseapplication.data.db.DatabaseClient;
import com.example.roomdatabaseapplication.data.db.User;

public class AddUserActivity extends AppCompatActivity {
    private EditText editTextName, editTextMobile, editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        editTextName = findViewById(R.id.editTextName);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextEmail = findViewById(R.id.editTextEmail);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });
    }

    private void saveUser() {

        final String sName = editTextName.getText().toString().trim();
        final String sMobile = editTextMobile.getText().toString().trim();
        final String sEmail = editTextEmail.getText().toString().trim();
        if (sName.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }

        if (sMobile.isEmpty()) {
            editTextMobile.setError("Mobile required");
            editTextMobile.requestFocus();
            return;
        }

        if (sEmail.isEmpty()) {
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            return;
        }
        class SaveUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                User user = new User();
                user.setName(sName);
                user.setEmail(sEmail);
                user.setMobile(sMobile);
                user.setFinished(false);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .insert(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }
        SaveUser su = new SaveUser();
        su.execute();

    }
}
