package cheema.hardeep.sahibdeep.creditapplication.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cheema.hardeep.sahibdeep.creditapplication.R;

public class Splash extends AppCompatActivity {

    static final int TRANSITION_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Splash.this.startActivity(new Intent(Splash.this, MainActivity.class));
                Splash.this.finish();
            }
        }, TRANSITION_TIME);
    }
}
