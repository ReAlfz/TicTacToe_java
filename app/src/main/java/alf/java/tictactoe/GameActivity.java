package alf.java.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity {
    boolean gameActive = false;
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winPosition = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };
    public static int counter = 0;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button exit = findViewById(R.id.exit_button);
        exit.setOnClickListener(v -> {
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }

    private void updateScore(String player) {
        SharedPreferences preferences = getSharedPreferences("game_preference", MODE_PRIVATE);
        int currentScore = preferences.getInt(player, 0);
        currentScore++;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(player, currentScore);
        editor.apply();
    }

    public void playerTap(View view) {
        ImageView img = (ImageView) view;
        TextView status = findViewById(R.id.status);
        int tappedImage = Integer.parseInt(img.getTag().toString());

        if (!gameActive) {
            gameReset();
            counter = 0;
        }

        if (gameState[tappedImage] == 2) {
            counter++;

            if (counter == 9) {
                gameActive = false;
            }

            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f);

            if (activePlayer == 0) {
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                status.setText("O's turn - Tap to play");
            } else {
                img.setImageResource(R.drawable.o);
                activePlayer = 0;
                status.setText("X's turn - Tap to play");
            }

            img.animate().translationYBy(1000f).setDuration(300);
        }

        int flag = 0;
        if (counter > 4) {
            for (int[] winPos : winPosition) {
                if (gameState[winPos[0]] == gameState[winPos[1]]
                        && gameState[winPos[1]] == gameState[winPos[2]]
                        && gameState[winPos[0]] != 2) {

                    flag = 1;
                    String winnerStr;

                    gameActive = false;
                    if (gameState[winPos[0]] == 0) {
                        winnerStr = "X has won";
                        updateScore("score_playerA");
                    } else {
                        winnerStr = "O has won";
                        updateScore("score_playerB");
                    }

                    status.setText(winnerStr);
                }
            }

            if (counter == 9 && flag == 0) {
                status.setText("Match Draw");
            }
        }
    }

    private void gameReset() {
        gameActive = true;
        activePlayer = 0;

        Arrays.fill(gameState, 2);
        ((ImageView) findViewById(R.id.block1)).setImageResource(0);
        ((ImageView) findViewById(R.id.block2)).setImageResource(0);
        ((ImageView) findViewById(R.id.block3)).setImageResource(0);

        ((ImageView) findViewById(R.id.block4)).setImageResource(0);
        ((ImageView) findViewById(R.id.block5)).setImageResource(0);
        ((ImageView) findViewById(R.id.block6)).setImageResource(0);

        ((ImageView) findViewById(R.id.block7)).setImageResource(0);
        ((ImageView) findViewById(R.id.block8)).setImageResource(0);
        ((ImageView) findViewById(R.id.block9)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText("X's turn - Tap to play");
    }
}
