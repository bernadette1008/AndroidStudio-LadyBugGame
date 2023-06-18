package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Score extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> scoreList;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);

        Button mainButton = (Button) findViewById(R.id.main);

        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        listView = findViewById(R.id.listView);
        scoreList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoreList);
        listView.setAdapter(adapter);

        dbHelper = new DatabaseHelper(this);

        loadScores();
    }

    private void loadScores() {
        Cursor cursor = dbHelper.getAllScore();
        int scoreIndex = cursor.getColumnIndex("SCORE");
        int userIndex = cursor.getColumnIndex("USER");

        if (scoreIndex >= 0 && userIndex >= 0) {
            if (cursor.moveToFirst()) {
                do {
                    int score = cursor.getInt(scoreIndex);
                    String userName = cursor.getString(userIndex);
                    scoreList.add(userName + ": " + score);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        adapter.notifyDataSetChanged();
    }
}