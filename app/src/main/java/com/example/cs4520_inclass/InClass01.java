package com.example.cs4520_inclass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


//HECTOR BENITEZ IN CLASS ASSIGNMENT #1

public class InClass01 extends AppCompatActivity {
    final String TAG = "demo";
    Button calculateBMI;
    EditText weight, feet, inches;
    TextView scale, status, error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class01);
        setTitle("BMI Calculator");

        calculateBMI = findViewById(R.id.BMI);
        weight = findViewById(R.id.weight);
        feet = findViewById(R.id.feet);
        inches = findViewById(R.id.inches);
        scale = findViewById(R.id.BMIScale);
        status = findViewById(R.id.BMIStatus);
        error = findViewById(R.id.ErrorText);


        calculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String weightInput = weight.getText().toString();
               String heightFeetInput = feet.getText().toString();
               String heightInchesInput = inches.getText().toString();
                error.setText("");
               //do input checking before converting to int
                if(weight.getText().toString().equals("") ||
                        feet.getText().toString().equals("") ||
                        inches.getText().toString().equals("")) {

                    error.append("Error need to input a valid value for each field");
                }


                else {

                    if(android.text.TextUtils.isDigitsOnly(weightInput) &&
                            android.text.TextUtils.isDigitsOnly(heightFeetInput) &&
                            android.text.TextUtils.isDigitsOnly(heightInchesInput)) {

                        double weightInt = Integer.parseInt(weightInput);
                        double heightFeetInt = Integer.parseInt(heightFeetInput);
                        double heightInchesInt = Integer.parseInt(heightInchesInput);

                        if (weightInt < 0 || heightFeetInt < 0 || heightInchesInt < 0) {
                            error.append("Error need to input a Numeric value above 0 for each field");
                        }

                        else {
                            double heightInInches = (heightFeetInt * 12) + heightInchesInt;
                            double BMI = (weightInt / (heightInInches * heightInInches)) * 703;

                            scale.setText("");
                            scale.append("Your BMI: " + String.format("%1$.1f", BMI));

                            if (BMI < 18.5) {
                                status.append("You Are UnderWeight");
                            } else if (BMI >= 18.5 && BMI <= 24.9) {
                                status.setText("");
                                status.append("You Are Normal Weight");
                            } else if (BMI >= 25 && BMI <= 29.9) {
                                status.setText("");
                                status.append("You Are OverWeight");
                            } else if (BMI >= 30) {
                                status.setText("");
                                status.append("You Are Obese");
                            }

                        }
                    }
                    else {
                        error.append("Error need to input a Numeric value for each field");

                    }

                }
            }


        });
    }

}