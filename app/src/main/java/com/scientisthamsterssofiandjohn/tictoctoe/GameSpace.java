package com.scientisthamsterssofiandjohn.tictoctoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class GameSpace extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneName, playerTwoName, playerOneScore, playerTwoScore;
    private ImageButton settings;
    private Button[] buttons = new Button[9];

    private int playerOneCount, playerTwoCount, roundCount;
    private boolean currentPlayer; // X

    // player1 -> 1 - x
    // player2 -> 2 - o
    // empty -> 0

    private int[] gameCells = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // строки
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},// столбцы
            {0, 4, 8}, {2, 4, 6} // диагонали
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_space);
        init();
    }

    private void init() {
        playerOneName = findViewById(R.id.firstPlayerName);
        playerTwoName = findViewById(R.id.secondPlayerName);
        playerOneScore = findViewById(R.id.firstPlayerCount);
        playerTwoScore = findViewById(R.id.secondPlayerCount);
        settings = findViewById(R.id.settingsButton);

        for (int i = 0; i < buttons.length; i++){
            String buttonsID = "button_" + i;
            int resourseID = getResources().getIdentifier(buttonsID,"id",getPackageName());
            buttons[i] = findViewById(resourseID);
            buttons[i].setBackgroundResource(0);
            buttons[i].setOnClickListener(this);
        }

        currentPlayer = true;
        playerOneCount = 0;
        playerTwoCount = 0;
        roundCount = 0;
    }

    @Override
    public void onClick(View v) {

        if (!((Button)v).getText().toString().equals("")){
            return;
        }

        String buttonsID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonsID.
                substring(buttonsID.length() - 1, buttonsID.length()));

        if (currentPlayer){
            ((Button)v).setBackgroundResource(R.drawable.xplayer);
            ((Button)v).setText("x");
            gameCells[gameStatePointer] = 1;
        } else {
            ((Button)v).setBackgroundResource(R.drawable.oplayer);
            ((Button)v).setText("o");
            gameCells[gameStatePointer] = 2;
        }
        roundCount++;
        if (checkWinner()){

            if (currentPlayer) {
                playerOneCount++;
                updatePlayerScore();
                Toast.makeText(this,
                        "Победил первый игрок!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                playerTwoCount++;
                updatePlayerScore();
                Toast.makeText(this,
                        "Победил второй игрок!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        } else if (roundCount == 9) {
            playAgain();
            Toast.makeText(this,
                    "Ничья!", Toast.LENGTH_SHORT).show();
        } else {
            currentPlayer = !currentPlayer;
        }
    }

    private void playAgain() {
        roundCount = 0;
        currentPlayer = true;
        for (int i = 0; i < buttons.length; i++){
            gameCells[i] = 0;
            buttons[i].setText("");
            buttons[i].setBackgroundResource(0);
        }
    }

    private void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneCount));
        playerTwoScore.setText(Integer.toString(playerTwoCount));
    }

    private boolean checkWinner() {
        boolean winnerResalt = false;

        for (int [] winningPosition : winningPositions){
            if (gameCells[winningPosition[0]] == gameCells[winningPosition[1]] 
            && gameCells[winningPosition[1]] == gameCells[winningPosition[2]]
            && gameCells[winningPosition[0]] != 0){
                winnerResalt = true;
            }
            
        }
        return winnerResalt;
    }
}