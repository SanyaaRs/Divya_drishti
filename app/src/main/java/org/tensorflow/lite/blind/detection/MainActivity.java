package org.tensorflow.lite.blind.detection;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static int firstTime = 0;
    private TextView mVoiceInputTv;
    float x1, x2, y1, y2;
    private TextView mSpeakBtn;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static TextToSpeech textToSpeech;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setSpeechRate(1f);
                    textToSpeech.speak("say read for read., calculator for calculator., Weather for weather., Location for location., Battery,Say face detection for detecting face.,Say expression detection for detecting facial expression.,Say currency detection for detecting currency., Time and date. say bank transfer. or, say phone transfer, to transfer the amount. say object detection to detect the object. say exit for closing the application.  Swipe right and say what you want ", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);


    }


    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 > x2) {
                    textToSpeech.stop();
                        startVoiceInput();
        }
        break;
    }
        return false;
}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            a.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                mVoiceInputTv.setText(result.get(0));

                if (mVoiceInputTv.getText().toString().equals("read")) {

                    Intent intent = new Intent(getApplicationContext(), ReadActivity.class);
                    startActivity(intent);
                    mVoiceInputTv.setText(null);
                }
                if (mVoiceInputTv.getText().toString().equals("calculator")) {
                    Intent intent = new Intent(getApplicationContext(), CalculatorActivity.class);
                    startActivity(intent);
                    mVoiceInputTv.setText(null);
                }
                if (mVoiceInputTv.getText().toString().equals("currency detection")) {
                    Intent intent = new Intent(getApplicationContext(), ClassifierActivity.class);
                    startActivity(intent);
                    mVoiceInputTv.setText(null);
                }

                if (mVoiceInputTv.getText().toString().equals("face detection")) {
                    Intent intent = new Intent(getApplicationContext(), RealTimeFaceRecognitionActivity.class);
                    startActivity(intent);
                    mVoiceInputTv.setText(null);
                }

                if (mVoiceInputTv.getText().toString().equals("expression detection")) {
                    Intent intent = new Intent(getApplicationContext(), EmotionDetectionActivity.class);
                    startActivity(intent);
                    mVoiceInputTv.setText(null);
                }

                if (mVoiceInputTv.getText().toString().equals("time and date")) {
                    Intent intent = new Intent(getApplicationContext(), TimeDateActivity.class);
                    startActivity(intent);
                    mVoiceInputTv.setText(null);
                }
                if (mVoiceInputTv.getText().toString().equals("weather")) {
                    Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
                    startActivity(intent);
                    mVoiceInputTv.setText(null);
                }

                if (mVoiceInputTv.getText().toString().equals("battery")) {
                    Intent intent = new Intent(getApplicationContext(), BatteryActivity.class);
                    startActivity(intent);
                    mVoiceInputTv.setText(null);
                }

                if (mVoiceInputTv.getText().toString().equals("location")) {
                    Intent intent = new Intent(getApplicationContext(), LocationActivity.class);
                    startActivity(intent);
                    mVoiceInputTv.setText(null);
                }
                if (mVoiceInputTv.getText().toString().contains("bank transfer")) {
                    Intent i = new Intent(MainActivity.this,Banktransfer.class);
                    startActivity(i);
                }
                else if(mVoiceInputTv.getText().toString().contains("phone transfer")){
                    Intent i = new Intent(MainActivity.this,phonetransfer.class);
                    startActivity(i);
                }

                if (mVoiceInputTv.getText().toString().contains("object detection")) {
                    Intent i = new Intent(MainActivity.this, ObjectDetection.class);
                    startActivity(i);
                }
                if (mVoiceInputTv.getText().toString().equals("exit")) {
                   onPause();
                   finishAffinity();
                }

            }
        }
    }
    public void onDestroy(){
        if (mVoiceInputTv.getText().toString().equals("exit")){
            finish();
        }
        super.onDestroy();
    }

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        super.onPause();

    }
    }

