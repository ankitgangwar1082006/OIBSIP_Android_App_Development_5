package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button start ,stop ,pause;
    TextView txtTimer;
    boolean isRunning=false;
    long elapsedTime=0,startTime=0 ;
    Handler handler=new Handler();
    Runnable runnable= new Runnable() {
        @Override
        public void run() {
            long time = System.currentTimeMillis() - startTime;
            long hours = (time / (1000 * 60 * 60)) % 24;
            long minutes = (time / (1000 * 60)) % 60;
            long seconds = (time / 1000) % 60;

            String formattedTime = String.format("%02d : %02d : %02d", hours, minutes, seconds);
            txtTimer.setText(formattedTime);

            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        findId();
        stop.setOnClickListener(v ->stop() );
        start.setOnClickListener(v->start());
        pause.setOnClickListener(v->pause());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void findId()
    {
        start=findViewById(R.id.start);
        pause=findViewById(R.id.pause);
        stop=findViewById(R.id.stop);
        txtTimer=findViewById(R.id.timer);
    }
    private void start()
    {
        if(!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime;
            handler.post(runnable);
            isRunning = true;
        }
    }
    private void stop()
    {
        handler.removeCallbacks(runnable);
        isRunning = false;
        elapsedTime = 0;
        txtTimer.setText("00 : 00 : 00");
    }
    private void pause()
    {
        if(isRunning) {
            isRunning = false;
            handler.removeCallbacks(runnable);
            elapsedTime = System.currentTimeMillis() - startTime;
        }

    }


    @Override
    protected void onResume() {

        super.onResume();
    }
}