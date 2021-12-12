package com.furkanavcu.catchthefurkan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView scoreText;
    TextView timeText;
    int activeScore, score, activeImage, activeImageForVisible;
    ImageView imageView, imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8;
    ImageView[] imageArray;
    Random random = new Random();
    Runnable runnable;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreText = findViewById(R.id.TextViewScore);
        timeText = findViewById(R.id.TextViewTime);
        imageView = findViewById(R.id.imageView);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageArray = new ImageView[]{imageView, imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8};
        score = 1;
        activeImage = 0;
        activeScore = 0;
        hideAndShowImages();
        CountDownTime();

    }

    public void GetScoreOnClick(View view) {
        activeScore = activeScore + score;
        scoreText.setText("Score: " + activeScore);
    }

    public void hideAndShowImages() {

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image : imageArray) {
                    image.setClickable(false);
                    image.setVisibility(View.INVISIBLE);
                }
                activeImage = random.nextInt(imageArray.length);
                imageArray[activeImage].setVisibility(View.VISIBLE);
                imageArray[activeImage].setClickable(true);
                handler.postDelayed(this, 500);
            }
        };
        handler.post(runnable);
    }

    public void CountDownTime() {
        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timeText.setText("Time Off");
                handler.removeCallbacks(runnable);//runnable'ı durdur anlamına geliyor
                for (ImageView image : imageArray) {
                    image.setClickable(false);
                    image.setVisibility(View.INVISIBLE);
                }
                GameFinishAlert();
            }
        }.start();
    }

    public void GameFinishAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Game Over");
        alert.setMessage(" You wanna play Again ?");
        alert.setPositiveButton("Play Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        alert.setNegativeButton("I want to Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);

            }
        });
        alert.show();
    }
}