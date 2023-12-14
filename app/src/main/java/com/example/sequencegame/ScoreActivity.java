package com.example.sequencegame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private Button playAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //Find views by their IDs
        scoreTextView = findViewById(R.id.txtView);
        playAgainButton = findViewById(R.id.btnAgain);

        //Apply styles to TextView
        scoreTextView.setTextColor(Color.BLACK); // Set text color
        scoreTextView.setPadding(10, 10, 10, 10); // Set padding
        scoreTextView.setTextSize(18); // Set text size

        //Apply styles to Button
        playAgainButton.setTextColor(Color.WHITE); // Set text color
        playAgainButton.setTextSize(18); // Set text size

        //Set onClickListener for the "Play Again" button
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the MainActivity when the button is clicked
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); //Finish the current activity to prevent the user from returning
            }
        });
    }
}
