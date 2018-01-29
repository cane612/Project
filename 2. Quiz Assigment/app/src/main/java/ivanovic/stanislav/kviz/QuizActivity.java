package ivanovic.stanislav.kviz;
import android.app.AlertDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import ivanovic.stanislav.kviz.R;


public class QuizActivity extends Activity {

    Button bt1,bt2,bt3;

    TextView textViewQuestion;

    int progress = 20;
    int rightAnswer = 0;
    int counter = 0;

    QuestionLibrary ql = new QuestionLibrary();

    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Intent intent = getIntent();
        setUpTheQuestion();
    }


    public void setUpTheQuestion(){

        QuestionLibrary ql = new QuestionLibrary();

        textViewQuestion = findViewById(R.id.text_view_question);

        bt1 = findViewById(R.id.buttonOption1);
        bt2 = findViewById(R.id.buttonOption2);
        bt3 = findViewById(R.id.buttonOption3);

        textViewQuestion.setText(ql.getQuestion(counter));

        bt1.setText(ql.getChoice1(counter));
        bt2.setText(ql.getChoice2(counter));
        bt3.setText(ql.getChoice3(counter));
    }

    public void quit_quiz(View view){
        finish();
    }

    public void answerchoice(View view) {

        Button bt = (Button) view;
        int ansnerButton = Integer.parseInt(bt.getTag().toString());
        if(ql.CorrectAnswers[counter] == ansnerButton){
            rightAnswer++;
        }
        counter++;

        pb = findViewById(R.id.progressBar);
        pb.setProgress(progress);
        progress = progress + 20;

        if(counter == 5){

            isClickboolean(false);

                messageAlertDialog("The Final Score is: "+rightAnswer+"/5","SCORE OF THE QUIZ !!");
                return;
        }

        setUpTheQuestion();
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
               counter = 0;
               progress = 20;
               rightAnswer = 0;
               pb = findViewById(R.id.progressBar);
               pb.setProgress(0);
               setUpTheQuestion();
                isClickboolean(true);


            }
        });

        AlertDialog alertdialog = alertDialogB.create();
        alertdialog.show();
    }

    public void isClickboolean(boolean clickable) {
        bt1 = findViewById(R.id.buttonOption1);
        bt2 = findViewById(R.id.buttonOption2);
        bt3 = findViewById(R.id.buttonOption3);

        bt1.setClickable(clickable);
        bt2.setClickable(clickable);
        bt3.setClickable(clickable);
    }
}
