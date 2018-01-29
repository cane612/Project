package ivanovic.stanislav.TicTacToe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.nio.file.Files;

public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClickIntent(View view) {
        Intent gameIntent = new Intent(this,GameActivity.class);
        startActivity(gameIntent);
    }

    public void onClickAboutGame(View view) {
        Intent aboutGameIntent = new Intent(this,AboutActivity.class);
        startActivity(aboutGameIntent);
    }
}
