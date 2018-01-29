package ivanovic.stanislav.TicTacToe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends Activity {
    Random rn = new Random();
    boolean ifPlayerIsX = true;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; // dva podrazumeva da nije kliknuto polje
    int counter = 0;
    int winningLocation[][] = {{0, 3, 6}, {1, 4, 7},{2, 5, 8}, {0, 1, 2}, {3, 4, 5},
            {6, 7, 8}, {0, 4, 8},{2, 4, 6}};

    boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    // LOGIKA IGRE


    public void gameLogic(View view) {

        if (ifPlayerIsX) {
            playerPlays(view, R.drawable.iks);
            computerPlays(R.mipmap.oks);
        } else {
            playerPlays(view, R.mipmap.oks);
            computerPlays(R.drawable.iks);
        }
    }


    //LOGIKA IGRACA


    public void playerPlays(View view, int imageResorse) {
        ImageView tappedView = (ImageView) view;
        tappedView.setClickable(false);
        int tappedViewID = Integer.parseInt(view.getTag().toString());

        if (gameState[tappedViewID] == 2 && gameOver == false) {

            tappedView.setImageResource(imageResorse);
            gameState[tappedViewID] = 1;
            counter++;

            for (int[] winningPosition : winningLocation) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    gameOver = true;
                    if (ifPlayerIsX) {
                        ifPlayerIsX = false;
                    } else {
                        ifPlayerIsX = true;
                    }
                    String result = "WIN WIN";
                    String message = "You WON! Congradulation !! Do you want to play again?";
                    messageAlertDialog(message, result);
                    return;
                }
            }

            if (counter == 9) {
                gameOver = true;
                if (ifPlayerIsX) {
                    ifPlayerIsX = false;
                } else {
                    ifPlayerIsX = true;
                }

                String result = "DRAW";
                String message = "You both played smart! Do you want to play again?";
                messageAlertDialog(message, result);
                return;
            }
        }
    }

    //LOGIKA KOMPJUTERA
    public void computerPlays(int imageResorce) {
        if (!gameOver) {
            counter++;
            int computerMove = rn.nextInt(9);
            while (gameState[computerMove] != 2) {
                computerMove = rn.nextInt(9);
            }
            switch (computerMove) {
                case 0: {
                    if (computerMove == 0) {
                        ImageView img_0 = findViewById(R.id.imageView_0);
                        img_0.setImageResource(imageResorce);
                        gameState[0] = 0;
                        img_0.setClickable(false);
                    }

                }

                case 1: {
                    if (computerMove == 1) {
                        ImageView img_1 = findViewById(R.id.imageView_1);
                        img_1.setImageResource(imageResorce);
                        gameState[1] = 0;
                        img_1.setClickable(false);
                    }
                }
                case 2: {
                    if (computerMove == 2) {
                        ImageView img_2 = findViewById(R.id.imageView_2);
                        img_2.setImageResource(imageResorce);
                        gameState[2] = 0;
                        img_2.setClickable(false);
                    }

                }

                case 3: {

                    if (computerMove == 3) {
                        ImageView img_3 = findViewById(R.id.imageView_3);
                        img_3.setImageResource(imageResorce);
                        gameState[3] = 0;
                        img_3.setClickable(false);
                    }
                }

                case 4: {
                    if (computerMove == 4) {
                        ImageView img_4 = findViewById(R.id.imageView_4);
                        img_4.setImageResource(imageResorce);
                        gameState[4] = 0;
                        img_4.setClickable(false);
                    }
                }

                case 5: {
                    if (computerMove == 5) {
                        ImageView img_5 = findViewById(R.id.imageView_5);
                        img_5.setImageResource(imageResorce);
                        gameState[5] = 0;
                        img_5.setClickable(false);
                    }

                }

                case 6: {
                    if (computerMove == 6) {
                        {
                            ImageView img_6 = findViewById(R.id.imageView_6);
                            img_6.setImageResource(imageResorce);
                            gameState[6] = 0;
                            img_6.setClickable(false);
                        }
                    }

                }
                case 7: {

                    if (computerMove == 7) {
                        ImageView img_7 = findViewById(R.id.imageView_7);
                        img_7.setImageResource(imageResorce);
                        gameState[7] = 0;
                        img_7.setClickable(false);
                    }
                }

                case 8: {

                    if (computerMove == 8) {
                        ImageView img_8 = findViewById(R.id.imageView_8);
                        img_8.setImageResource(imageResorce);
                        gameState[8] = 0;
                        img_8.setClickable(false);
                    }
                }

            }

            for (int[] winningPosition : winningLocation) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    gameOver = true;
                    if (ifPlayerIsX) {
                        ifPlayerIsX = false;
                    } else {
                        ifPlayerIsX = true;
                    }
                    String result = "LOSE";
                    String message = "Computer has Won! Do you want to play again?";
                    messageAlertDialog(message, result);
                    return;
                }
            }

        }
    }


    // LOGIKA KADA SE KLIKNE NA POZITIVNI TASTER KADA SE POJAVI ALERTDIALOG

    public void playAgain() {
        gameOver = false;
        counter = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        LinearLayout linearLayout_1 = findViewById(R.id.view_group_row1);
        LinearLayout linearLayout_2 = findViewById(R.id.view_group_row2);
        LinearLayout linearLayout_3 = findViewById(R.id.view_group_row3);

        for (int i = 0; i < 3; i++) {
            ImageView img1 = ((ImageView) linearLayout_1.getChildAt(i));
            img1.setClickable(true);
            img1.setImageResource(0);

            ImageView img2 = ((ImageView) linearLayout_2.getChildAt(i));
            img2.setClickable(true);
            img2.setImageResource(0);

            ImageView img3 = ((ImageView) linearLayout_3.getChildAt(i));
            img3.setClickable(true);
            img3.setImageResource(0);
        }


    }

    public void messageAlertDialog(String message, String result) {
        AlertDialog.Builder alertDialogB = new AlertDialog.Builder(this);
        alertDialogB.setMessage(message);
        alertDialogB.setTitle(result);

        alertDialogB.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        alertDialogB.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                playAgain();
                if (!ifPlayerIsX) {
                    computerPlays(R.drawable.iks);
                    counter++;
                }
            }
        });

        AlertDialog alertdialog = alertDialogB.create();
        alertdialog.show();
    }
}




