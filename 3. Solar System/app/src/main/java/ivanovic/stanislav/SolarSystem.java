package ivanovic.stanislav;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class SolarSystem extends View {
	static final int DONE = 0;
	static final int RUNNING = 1;
    
	static final int ORBIT_COLOR = Color.argb (255, 66, 66, 66);
	static final int nsteps = 600;
	static final int numpoints = 50;
	static final float dphi = (float) (2* Math.PI/(float)numpoints);
	static final double THIRD = 1.0/3.0;
	static final int planetRadius = 9;
	static final int sunRadius = 20;
	static final float X0 = 0;
	static final float Y0 = 0;
    static final double direction = -1;
    static final double fracWidth = 0.95;

    static final String planetName[] = {"MERKUR", "VENERA", "ZEMLJA"};
    static final double epsilon[] = {0.206, 0.007, 0.017};
    static final double  a[] = {0.387, 0.723, 1.0, 1.524, 5.203, 9.54,
    	                                                19.18, 30.06, 39.53, 2.35, 1.97, 17.83};
    static final double period[] = {0.241, 0.615, 1.0};
    static final double theta0[] = {5.1, 1.4, 1.2};
    static final double orientRad[] = {0.0, 0.0, 0.0};
    static final double retroFac[] = {1,-1,1,};
    
    private int numObjects;
    private AnimationThread animThread;
	private Paint paint;
	private ShapeDrawable planet;
	float X[];
	float Y[];
	float centerX;
	float centerY;
	float R0[];
	double theta[];
	double dTheta[];
	private double pixelScale;
	double c1[];
	double c2[];
	private double dt;
	long delay = 20;
	private double zoomFac = 1.0;
	boolean showLabels = true;
	int mState = DONE;
	boolean isAnimating = true;
	private boolean showOrbits = true;
	private PointF xyPoint;
	private double sinx;
	private double cosx;

	
	public SolarSystem(Context context) {
		super(context);	
		numObjects = 3;
		X = new float[numObjects];
		Y = new float[numObjects];
		theta = new double[numObjects];
		dTheta = new double[numObjects];
		R0 = new float[numObjects];
		c1 = new double[numObjects];
		c2 = new double[numObjects];
		dt = 1/(double)nsteps;
		xyPoint = new PointF();

		
		for(int i=0; i<numObjects; i++){
			theta[i] = -direction*theta0[i];
		}

    	planet = new ShapeDrawable(new OvalShape());
    	planet.getPaint().setColor(Color.WHITE);
    	planet.setBounds(0, 0, 2*planetRadius, 2*planetRadius);	

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(16);
        paint.setStrokeWidth(0);

        animThread = new AnimationThread(handler);
	}

	
   final Handler handler = new Handler() {
       public void handleMessage(Message msg) {
           invalidate();
       }
   };

	@Override
	protected void onSizeChanged  (int w, int h, int oldw, int oldh){

	   	centerX = w/2 + X0;
	   	centerY = h/2 + Y0;

	   	pixelScale= zoomFac*fracWidth* Math.min(centerX, centerY)/a[4];

	   	for(int i=0; i<numObjects; i++){
	   		c1[i] = pixelScale*a[i]*(1-epsilon[i]*epsilon[i]);
	   		c2[i] = direction*2* Math.PI* Math.sqrt(1-epsilon[i]*epsilon[i])
	   					*dt*(pixelScale*a[i])*(pixelScale*a[i])/period[i];
	   		R0[i] = (float) distanceFromFocus(c1[i], epsilon[i], theta[i]);
	   		dTheta[i] = c2[i]/R0[i]/R0[i];
		   	X[i] = centerX - R0[i]*(float) Math.sin(theta[i]) - planetRadius ;
		   	Y[i] = centerY - R0[i]*(float) Math.cos(theta[i]) - planetRadius;
	   	}
	   	animThread.start();
	}

	void setZoom(double scale){
		if(!isAnimating) return;
		zoomFac *= scale;
		pixelScale= zoomFac*fracWidth* Math.min(centerX, centerY)/a[4];
		for(int i=0; i<numObjects; i++){
			c1[i] = pixelScale*a[i]*(1-epsilon[i]*epsilon[i]);
	   		c2[i] = direction*2* Math.PI* Math.sqrt(1-epsilon[i]*epsilon[i])
	   					*dt*(pixelScale*a[i])*(pixelScale*a[i])/period[i];
		}
	}

  @Override
  public void onDraw(Canvas canvas) {
      super.onDraw(canvas);

      drawBackground(paint, canvas);
      paint.setColor(Color.LTGRAY);

      for(int i=0; i<numObjects; i++){

    	   canvas.save();
    	   canvas.translate(centerX, centerY);

    	   rotate2D(orientRad[i], X[i]-centerX+planetRadius, -Y[i]+centerY-planetRadius);
    	   canvas.translate(xyPoint.x -planetRadius, xyPoint.y-planetRadius);
	       planet.draw(canvas);
	       if(showLabels) canvas.drawText(planetName[i], 10, 0, paint);
	       canvas.restore();
      }
  }

  private void drawBackground(Paint paint, Canvas canvas){
	paint.setColor(Color.YELLOW);
	paint.setStyle(Paint.Style.FILL);
	canvas.drawCircle(centerX, centerY, sunRadius, paint);

	if(showOrbits){
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(ORBIT_COLOR);
		double phi=0;

		for(int i=0; i<numObjects; i++){

		   	float lastxx = 0;
		   	float lastyy = (float) (distanceFromFocus(c1[i], epsilon[i], phi)* Math.cos(phi));
		   	rotate2D(orientRad[i], lastxx, lastyy);
		   	lastxx = xyPoint.x+centerX;
		   	lastyy = xyPoint.y+centerY;
		   	phi = 0;

		   	int plotpoints = numpoints;
		   	double delphi = dphi;
		   	if(epsilon[i] > 0.7){
		   		plotpoints *= 3;
		   		delphi *= THIRD;
		   	} 

		   	for(int j=0; j<plotpoints; j++){
		   		phi += delphi;
		   		float rr = (float) distanceFromFocus(c1[i], epsilon[i], phi);
				float xx =  (float)(rr* Math.sin(phi));
				float yy =  (float)(rr* Math.cos(phi));
				rotate2D(orientRad[i], xx, yy);
				xx = xyPoint.x +centerX;
				yy = xyPoint.y +centerY;
				canvas.drawLine(lastxx, lastyy, xx, yy, paint);
				lastxx = xx;
				lastyy = yy;
		   	}
		} 
	}
  }

  private double distanceFromFocus(double c1, double epsilon, double theta){
	  return (c1/(1+epsilon* Math.cos(theta)));
  }

  
  void rotate2D(double phi, float x, float y){
	   sinx = Math.sin(phi);
	   cosx = Math.cos(phi);
	   xyPoint.x = (float)(x*cosx + y*sinx);
	   xyPoint.y = -(float)(-x*sinx + y*cosx);
  }
  

  public void stopLooper(){
	   animThread.setState(DONE);
  }
  public void startLooper(){
	   animThread.setState(RUNNING);
  }

  
   private class AnimationThread extends Thread {
       
       Handler mHandler;
       AnimationThread(Handler h) {
           mHandler = h;
       }

       @Override
       public void run() {
           mState = RUNNING;   
           while (mState == RUNNING) {
           	if(isAnimating) newXY();
               try {
                   Thread.sleep(delay);
               } catch (InterruptedException e) {
               }
               Message msg = mHandler.obtainMessage();
               mHandler.sendMessage(msg);
           }
       }
   	private void newXY(){
   		for(int i=0; i<numObjects; i++){
			dTheta[i] = retroFac[i]*c2[i]/R0[i]/R0[i];
    		theta[i] += dTheta[i];     
    		R0[i] = (float) distanceFromFocus(c1[i], epsilon[i], theta[i]);
    		X[i] =  (float)(R0[i]* Math.sin(theta[i])) + centerX - planetRadius;
    		Y[i] =  centerY - (float)(R0[i]* Math.cos(theta[i])) - planetRadius;
   		}
   	}

       public void setState(int state) {
           mState = state;
       }
   }
}
