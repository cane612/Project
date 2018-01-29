package ivanovic.stanislav;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class MainActivity extends Activity{
	SolarSystem solarSystem;

    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
        		LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 80);
        
        LinearLayout LL1 = new LinearLayout(this);
        LL1.setOrientation(LinearLayout.VERTICAL);
        
        LinearLayout LL2 = new LinearLayout(this);
        LL2.setOrientation(LinearLayout.HORIZONTAL);

        solarSystem = new SolarSystem(this);
        solarSystem.setLayoutParams(layout);
        
        solarSystem.setBackgroundColor(Color.BLACK);
        LL1.addView(solarSystem);

        LL2.setBackgroundColor(Color.BLACK);
        LL1.addView(LL2);
        setContentView(LL1);
		double zoomScaler = 5;
		solarSystem.setZoom(zoomScaler);
    }
    
    @Override
	public void onPause() {
		super.onPause();
		solarSystem.stopLooper();
    }
    
    @Override
	public void onResume() {
		super.onResume();
		solarSystem.startLooper();
    }


}