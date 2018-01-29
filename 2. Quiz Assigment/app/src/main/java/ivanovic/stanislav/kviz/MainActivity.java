package ivanovic.stanislav.kviz;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar ab = getActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.WHITE));



    }

    public void beginActivity(View view){
        Intent beginSecondActivity = new Intent(this, QuizActivity.class);
        startActivity(beginSecondActivity);
    }

    public void quitApplication(View view) {

        finish();
        onDestroy();
    }
}
