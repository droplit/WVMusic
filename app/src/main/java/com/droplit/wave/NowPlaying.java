package com.droplit.wave;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;


public class NowPlaying extends ActionBarActivity {


    private SupportAnimator mAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

    }

    /*@Override
    protected void onStart() {
        if(mAnimator != null && !mAnimator.isRunning()){
            //mAnimator = mAnimator.reverse();
            mAnimator.addListener(new SupportAnimator.AnimatorListener() {
                @Override
                public void onAnimationStart() {

                }

                @Override
                public void onAnimationEnd() {
                    mAnimator = null;
                }

                @Override
                public void onAnimationCancel() {

                }

                @Override
                public void onAnimationRepeat() {

                }
            });
        }else if(mAnimator != null){
            //mAnimator.cancel();
            return;
        }else {

            final View myView =findViewById(R.id.card_view);

            // get the center for the clipping circle
            //int cx = (myView.getLeft() + myView.getRight()) / 2;
            //int cy = (myView.getTop() + myView.getBottom()) / 2;
            int cx = myView.getRight();
            int cy = myView.getBottom();

            // get the final radius for the clipping circle
            float finalRadius = hypo(myView.getWidth(), myView.getHeight());

            mAnimator = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);
            mAnimator.addListener(new SupportAnimator.AnimatorListener() {
                @Override
                public void onAnimationStart() {
                }

                @Override
                public void onAnimationEnd() {
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onAnimationCancel() {

                }

                @Override
                public void onAnimationRepeat() {

                }
            });
        }

        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setDuration(1500);
        mAnimator.start();
    }*/

    static float hypo(int a, int b){
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_now_playing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
