package com.example.cs4520_inclass;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

//HECTOR BENITEZ ASSIGNMENT 4

public class doGenerateNumberWork implements Runnable{
    private final String TAG = "demo";
    private int Complexity;
    private Handler messageQueue;
    public static final int STATUS_START = 0x001;
    public static final int STATUS_PROGRESS = 0x002;
    public static final int STATUS_END = 0x003;
    public static final String KEY_PROGRESS = "KEY PROGRESS";
    public static final String KEY_MAX = "KEY MAX";
    public static final String KEY_MIN = "KEY MIN";
    public static final String KEY_AVERAGE = "KEY AVERAGE";

    public doGenerateNumberWork(int complexity, Handler handle) {
        Complexity = complexity;
        messageQueue = handle;
    }
    @Override
    public void run() {
        Message startMessage = new Message();
        startMessage.what = STATUS_START;
        messageQueue.sendMessage(startMessage);

        ArrayList<Double> numbers  = HeavyWork.getArrayNumbers(Complexity);
        double maxnum = Collections.max(numbers);
        double minnum = Collections.min(numbers);
        double total = 0.0;
        for (Double i: numbers) {
            Log.d(TAG, i + " ");
            total = total + i;
        }

        double average = total / numbers.size();


        Message endMessage = new Message();
        endMessage.what = STATUS_END;
        Bundle bundle = new Bundle();
        bundle.putDouble(doGenerateNumberWork.KEY_MAX, maxnum);
        bundle.putDouble(doGenerateNumberWork.KEY_MIN, minnum);
        bundle.putDouble(doGenerateNumberWork.KEY_AVERAGE, average);
        endMessage.setData(bundle);
        messageQueue.sendMessage(endMessage);

    }
}
