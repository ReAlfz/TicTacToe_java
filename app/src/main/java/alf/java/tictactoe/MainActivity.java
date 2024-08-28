package alf.java.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btn_start;
    private TextView txt_score_A, txt_score_B;
    private void initialize() {
        btn_start = findViewById(R.id.button_start);
        txt_score_A = findViewById(R.id.textView_score_A);
        txt_score_B = findViewById(R.id.textView_score_B);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        btn_start.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });

        SharedPreferences preferences = getSharedPreferences("game_preference", MODE_PRIVATE);
        int scoreA = preferences.getInt("score_playerA", 0);
        int scoreB = preferences.getInt("score_playerB", 0);

        txt_score_A.setText(String.valueOf(scoreA));
        txt_score_B.setText(String.valueOf(scoreB));
    }
}