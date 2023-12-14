package com.example.sequencegame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over);

        int userScore = getIntent().getIntExtra("userScore", 0);

        //Update the TextView with the score
        TextView txtViewScore = findViewById(R.id.txtViewScore);
        txtViewScore.setText("Your score was: " + userScore);

        //Assuming you have buttons with ids btnPlayAgain and btnHighScore in your layout
        Button btnPlayAgain = findViewById(R.id.btnPlayAgain);
        Button btnHighScore = findViewById(R.id.btnHighScore);

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToFirstActivity();
            }
        });

        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToScoreActivity();
            }
        });
    }

    private void moveToFirstActivity() {
        Intent intent = new Intent(OverActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); //Finish the current activity to prevent the user from returning
    }

    private void moveToScoreActivity() {
        Intent intent = new Intent(OverActivity.this, ScoreActivity.class);
        startActivity(intent);
        finish(); //Finish the current activity to prevent the user from returning
    }
}
