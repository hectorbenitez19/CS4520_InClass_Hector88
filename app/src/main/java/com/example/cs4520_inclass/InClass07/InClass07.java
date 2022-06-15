package com.example.cs4520_inclass.InClass07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cs4520_inclass.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InClass07 extends AppCompatActivity implements FragmentRegister.IRegisterToNotes, FragmentLogin.ILoginToNotes, FragmentNotes.IMakeNotes {


    public static String ACCESS_TOKEN;
    public static String FRAGMENT_NOTES_TAG = "notes";
    public static String FRAGMENT_REGISTER_TAG = "register";
    public static String FRAGMENT_LOGIN_TAG = "login";
    public static OkHttpClient client;
    public static final String TAG = "demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_class07);


        HttpUrl url;
        client = new OkHttpClient();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.a7_fragContainer, new FragmentLogin(),FRAGMENT_LOGIN_TAG )
                .commit();

    }

    @Override
    public void registerAccount(String name, String email, String password) {

        String baseURL = "http://dev.sakibnm.space:3000/api/auth/register";

        FormBody body = new FormBody.Builder()
                .add("name", name)
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(baseURL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    ACCESS_TOKEN = response.body().string();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.a7_fragContainer, new FragmentNotes(), FRAGMENT_NOTES_TAG )
                            .addToBackStack(null)
                            .commit();

                }
                else {
                    Log.d(InClass07.TAG,  response.body().string());
                }
            }
        });

    }

    @Override
    public void goToNotes(String accessToken) {
        ACCESS_TOKEN = accessToken;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.a7_fragContainer, new FragmentNotes(), FRAGMENT_NOTES_TAG )
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void toRegister() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.a7_fragContainer, new FragmentRegister(), FRAGMENT_REGISTER_TAG )
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void login(String email, String password) {
        HttpUrl url;
        String baseURL = "http://dev.sakibnm.space:3000/api/auth/login";

        FormBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(baseURL)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                   ACCESS_TOKEN = response.body().string();
                    Log.d(TAG, "Login successful going to notes");


                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.a7_fragContainer, new FragmentNotes(), FRAGMENT_NOTES_TAG )
                            .addToBackStack(null)
                            .commit();
                }
            }
        });


    }

    @Override
    public void makeNote(String note) {
        String baseURL = "http://dev.sakibnm.space:3000/api/auth/note/post";

        FormBody body = new FormBody.Builder()
                .add("text", note)
                .build();

        Request request = new Request.Builder()
                .url(baseURL)
                .addHeader("x-access-token", ACCESS_TOKEN)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    assert response.body() != null;

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.a7_fragContainer, new FragmentNotes(), FRAGMENT_NOTES_TAG )
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

    @Override
    public void deleteNote(String id) {
        String baseURL = "http://dev.sakibnm.space:3000/api/auth/note/delete";

        FormBody body = new FormBody.Builder()
                .add("id", id)
                .build();

        Request request = new Request.Builder()
                .url(baseURL)
                .addHeader("x-access-token", ACCESS_TOKEN)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    assert response.body() != null;

                  //  getSupportFragmentManager()
                   //         .beginTransaction()
                    //        .replace(R.id.a7_fragContainer, new FragmentNotes(), FRAGMENT_NOTES_TAG )
                     //       .addToBackStack(null)
                      //      .commit();
                }
            }
        });

    }



    @Override
    public void logout() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void getNotes() {
        String baseURL = "http://dev.sakibnm.space:3000/api/note/getall";


        Request request = new Request.Builder()
                .url(baseURL)
                .addHeader("x-access-token", ACCESS_TOKEN)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    Log.d(TAG, "list of notes: " + response.body().string());
                }

            }
        });
    }
}