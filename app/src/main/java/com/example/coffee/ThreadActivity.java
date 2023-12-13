package com.example.coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ThreadActivity extends AppCompatActivity {

    private static final String Tag = "Main Activity";
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        textView = findViewById(R.id.textView);

        buttonClicked(textView);


    }

    public void buttonClicked(View view){

        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i(Tag, "Thread name 1: " + Thread.currentThread().getName());
                synchronized (this){
                    try {
                        wait(5000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ThreadActivity.this, "Download finished..", Toast.LENGTH_SHORT).show();

                    }
                });

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ThreadActivity.this, "2 seconds passed sinse Download was finished..", Toast.LENGTH_SHORT).show();

                    }
                }, 2000);

                Log.i(Tag, "run: downhload finished");
            }
        };
       // runnable.run();
        Log.i(Tag, "Thread name 1: " + Thread.currentThread().getName());
        Thread thread = new Thread(runnable);
        thread.start();
    }

}