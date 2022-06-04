package com.example.cs4520_inclass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InClass05 extends AppCompatActivity {

    EditText input;
    ImageView image, next, previous;
    Button go;
    ProgressBar progress;
    TextView loading;

    Boolean enableButtons = false;
    int CurrentImageIndex = 0;
    ArrayList<String> keywords;
    ArrayList<String> listOfURLImages;

    OkHttpClient client = new OkHttpClient();
    HttpUrl url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class05);

        input = findViewById(R.id.a5_keywordInput);
        image = findViewById(R.id.a5_image);
        next = findViewById(R.id.a5_next);
        previous = findViewById(R.id.a5_prev);
        go = findViewById(R.id.a5_go);
        progress = findViewById(R.id.a5_progressBar);
        loading = findViewById(R.id.a5_loading);

        getKeywords();

        next.setEnabled(false);
        previous.setEnabled(false);

        progress.setEnabled(false);
        progress.setVisibility(View.INVISIBLE);


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(input.getText().toString().isEmpty() || (!keywords.contains(input.getText().toString()))) {
                    Toast.makeText(InClass05.this, "need to input a valid keyword", Toast.LENGTH_SHORT).show();
                }

                else {
                    progress.setEnabled(true);
                    progress.setVisibility(View.VISIBLE);
                    loading.setText("Loading...");

                    getImages(input.getText().toString());

                }

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setEnabled(false);
                previous.setEnabled(false);


                if(CurrentImageIndex == 0) {
                    CurrentImageIndex = listOfURLImages.size() - 1;
                }
                else {
                    CurrentImageIndex--;
                }

                image.setImageResource(R.drawable.ic_launcher_foreground);
                progress.setEnabled(true);
                progress.setVisibility(View.VISIBLE);
                loading.setText("Loading Previous...");

                loadPreviousImage();

                next.setEnabled(true);
                previous.setEnabled(true);
              //  Log.d("demo", "list size: " + listOfURLImages.size());
              //  Log.d("demo", "previous Current Index: " + CurrentImageIndex);
              //  Log.d("demo", "pervious Current Image: " + listOfURLImages.get(CurrentImageIndex));



            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setEnabled(false);
                previous.setEnabled(false);

                if(CurrentImageIndex == listOfURLImages.size() - 1) {
                    CurrentImageIndex = 0;
                }
                else {
                    CurrentImageIndex++;
                }
                image.setImageResource(R.drawable.ic_launcher_foreground);
                progress.setEnabled(true);
                progress.setVisibility(View.VISIBLE);
                loading.setText("Loading Next...");

                loadNextImage();

                next.setEnabled(true);
                previous.setEnabled(true);
              //  Log.d("demo", "list size: " + listOfURLImages.size());
              //  Log.d("demo", "next Current Index: " + CurrentImageIndex);
              //  Log.d("demo", "next Current Image: " + listOfURLImages.get(CurrentImageIndex));



            }
        });





    }


    public void loadPreviousImage() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setEnabled(true);
                        progress.setVisibility(View.VISIBLE);
                        loading.setText("Loading Previous...");
                        Picasso.get().load(listOfURLImages.get(CurrentImageIndex)).into(image);
                        next.setEnabled(true);
                        previous.setEnabled(true);
                        progress.setEnabled(false);
                        progress.setVisibility(View.INVISIBLE);
                        loading.setText("");
                    }
                });
            }
        }, 2000);

    }

    public void loadNextImage() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setEnabled(true);
                        progress.setVisibility(View.VISIBLE);
                        loading.setText("Loading Next...");
                        Picasso.get().load(listOfURLImages.get(CurrentImageIndex)).into(image);
                        progress.setEnabled(false);
                        progress.setVisibility(View.INVISIBLE);
                        loading.setText("");
                        next.setEnabled(true);
                        previous.setEnabled(true);
                    }
                });
            }
        }, 2000);

    }

    public void getKeywords() {
        url = HttpUrl.parse("http://dev.sakibnm.space/apis/images/keywords").newBuilder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                  //  Log.d("demo","Response Successful: " + response.body().string());
                    String keywordsData = response.body().string();

                    String[] keys = keywordsData.split(",");
                    keywords = new ArrayList<String>(Arrays.asList(keys));
                 //   Log.d("demo","Response Successful: " + keywords.toString());

                }

            }
        });

    }


    public void getImages(String keyword) {
        url = HttpUrl.parse("http://dev.sakibnm.space/apis/images/retrieve").newBuilder()
                .addQueryParameter("keyword", keyword)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    //  Log.d("demo","Response Successful: " + response.body().string());

                    String imageData = response.body().string();

                    String[] images = imageData.split("\n");
                    listOfURLImages = new ArrayList<String>(Arrays.asList(images));
                   // Log.d("demo","Response Successful: " + listOfURLImages.toString());
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Picasso.get().load(listOfURLImages.get(CurrentImageIndex)).into(image);
                                    progress.setEnabled(false);
                                    progress.setVisibility(View.INVISIBLE);
                                    loading.setText("");
                                    next.setEnabled(true);
                                    previous.setEnabled(true);
                                }
                            });
                        }
                    }, 2000);


                }

            }
        });
    }
}