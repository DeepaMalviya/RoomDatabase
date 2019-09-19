package com.example.roomdatabaseapplication.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomdatabaseapplication.R;
import com.example.roomdatabaseapplication.data.db.DatabaseClient;
import com.example.roomdatabaseapplication.data.db.User;

public class updateActivity extends AppCompatActivity {

    private EditText editTextNamee, editTextEmaile, editTextMobilee;
    private CheckBox checkBoxFinished;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        editTextNamee = findViewById(R.id.editTextNamee);
        editTextEmaile = findViewById(R.id.editTextEmaile);
        editTextMobilee = findViewById(R.id.editTextMobilee);

        checkBoxFinished = findViewById(R.id.checkBoxFinished);


        final User user = (User) getIntent().getSerializableExtra("user");

        loadTask(user);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateTask(user);
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(updateActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTask(user);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    private void loadTask(User task) {
        editTextNamee.setText(task.getName());
        editTextEmaile.setText(task.getEmail());
        editTextMobilee.setText(task.getMobile());
        checkBoxFinished.setChecked(task.isFinished());
    }

    private void updateTask(final User task) {
        final String sNamee = editTextNamee.getText().toString().trim();
        final String sEmaile = editTextEmaile.getText().toString().trim();
        final String sMobilee = editTextMobilee.getText().toString().trim();

        if (sNamee.isEmpty()) {
            editTextNamee.setError("Name required");
            editTextNamee.requestFocus();
            return;
        }

        if (sEmaile.isEmpty()) {
            editTextEmaile.setError("Email required");
            editTextEmaile.requestFocus();
            return;
        }

        if (sMobilee.isEmpty()) {
            editTextMobilee.setError("Mobile required");
            editTextMobilee.requestFocus();
            return;
        }

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                task.setMobile(sMobilee);
                task.setEmail(sEmaile);
                task.setName(sNamee);
                task.setFinished(checkBoxFinished.isChecked());
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .update(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(updateActivity.this, MainActivity.class));
            }
        }

        UpdateTask ut = new UpdateTask();
        ut.execute();
    }


    private void deleteTask(final User task) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(updateActivity.this, MainActivity.class));
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }

}