package ifsp.doarmario.menu.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import ifsp.doarmario.menu.R;

public class SplashScreenActivity extends AppCompatActivity implements Runnable {
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(this, SPLASH_TIME_OUT);
    }

    @Override
    public void run() {
        Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
