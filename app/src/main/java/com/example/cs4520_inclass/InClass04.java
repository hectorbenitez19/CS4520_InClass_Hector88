package com.example.cs4520_inclass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//HECTOR BENITEZ ASSIGNMENT 4

public class InClass04 extends AppCompatActivity {
    private final String TAG = "demo";
    Button generate;
    TextView max,min,ave,complex;
    SeekBar complexity;
    ProgressBar progressBar;
    ExecutorService threadPool;
    public static  Handler handleQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class04);

        generate = findViewById(R.id.generate);
        max = findViewById(R.id.maximumText2);
        min = findViewById(R.id.minimumText);
        ave = findViewById(R.id.averageText);
        complex = findViewById(R.id.complexityText);
        complexity = findViewById(R.id.heavyWorkSeekBar);
        progressBar = findViewById(R.id.progressBar);
        threadPool = Executors.newFixedThreadPool(2);

        complexity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                   complex.setText(progress + " times");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //heavy task
                threadPool.execute(new doGenerateNumberWork(complexity.getProgress(), handleQueue));

            }
        });

        handleQueue = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                switch (msg.what) {
                    case doGenerateNumberWork.STATUS_START:
                        progressBar.setProgress(0);
                        break;

                        case doGenerateNumberWork.STATUS_PROGRESS:
                            Bundle RecievedProgress = msg.getData();
                            int progressSoFar = RecievedProgress.getInt(doGenerateNumberWork.KEY_PROGRESS);
                            progressBar.setProgress(progressSoFar);

                            break;

                        case doGenerateNumberWork.STATUS_END:
                            max.setText("");
                            min.setText("");
                            ave.setText("");
                            Bundle recievedData = msg.getData();
                            double maxData = recievedData.getDouble(doGenerateNumberWork.KEY_MAX);
                            double minData = recievedData.getDouble(doGenerateNumberWork.KEY_MIN);
                            double aveData = recievedData.getDouble(doGenerateNumberWork.KEY_AVERAGE);
                         //   Log.d(TAG, maxData + "max data recieved in main");
                          //  Log.d(TAG, minData + "min data recieved in main");
                          //  Log.d(TAG, aveData + "average data recieved in main");
                            DecimalFormat df = new DecimalFormat("0.00");
                            max.append(df.format(maxData) + "");
                            min.append(df.format(minData) + "");
                            ave.append(df.format(aveData) + "");
                            progressBar.setProgress(0);

                            break;

                }
                return false;
            }
        });

    }


}


