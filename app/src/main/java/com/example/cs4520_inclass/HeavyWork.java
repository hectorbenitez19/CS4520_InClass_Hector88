package com.example.cs4520_inclass;

import android.os.Bundle;
import android.os.Message;

import java.util.ArrayList;
import java.util.Random;

//HECTOR BENITEZ ASSIGNMENT 4

public class HeavyWork {
    static final int COUNT = 900000;
    static ArrayList<Double> getArrayNumbers(int n){
        ArrayList<Double> returnArray = new ArrayList<>();
        int divisor = 100 / n;
        for (int i=0; i<n; i++){

            returnArray.add(getNumber());

            Message progressMessage = new Message();
            progressMessage.what = doGenerateNumberWork.STATUS_PROGRESS;
            Bundle bundle = new Bundle();
            bundle.putInt(doGenerateNumberWork.KEY_PROGRESS, (i + 1) * divisor);
            progressMessage.setData(bundle);
            InClass04.handleQueue.sendMessage(progressMessage);

        }

        return returnArray;
    }

    static double getNumber(){
        double num = 0;
        Random rand = new Random();
        for(int i=0;i<COUNT; i++){

            num = num + rand.nextDouble();

        }
        return num / ((double) COUNT);
    }
}