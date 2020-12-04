package com.example.convertor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.BreakIterator;
import java.util.Locale;

public class resultActivity extends AppCompatActivity
{
    TextInputEditText result;
    Button copyClipboardbtn;
    private TextToSpeech mTTS;
    private SeekBar SeekBarPitch;
    private SeekBar SeekBarSpeed;
    private Button ButtonSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        String ans = bundle.getString("textResult");
        //String ans = intent.getStringExtra("textObtained");
        Toast.makeText(getApplicationContext(),"text detected",Toast.LENGTH_SHORT).show();
        result=findViewById(R.id.result);
        result.setText(ans);

        copyClipboardbtn = findViewById(R.id.copyClipboard_btn);
        ButtonSpeak = findViewById(R.id.say_it_btn);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(),"Language not supported",Toast.LENGTH_SHORT).show();
                        Log.e("TTS", "Language not supported");
                    } else {
                        ButtonSpeak.setEnabled(true);
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Initialization failed",Toast.LENGTH_SHORT).show();
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
        //TextInputEditText = findViewById(R.id.result);
        SeekBarPitch = findViewById(R.id.pitch_seekBar);
        SeekBarSpeed = findViewById(R.id.speed_seekBar);

        ButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = result.getText().toString();
                float pitch = (float) SeekBarPitch.getProgress() / 50;
                if (pitch < 0.1) pitch = 0.1f;
                float speed = (float) SeekBarSpeed.getProgress() / 50;
                if (speed < 0.1) speed = 0.1f;
                mTTS.setPitch(pitch);
                mTTS.setSpeechRate(speed);
                mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        copyClipboardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("store result", result.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Text Copied to clipboard", Toast.LENGTH_SHORT).show();

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,result.getText().toString());
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });
    }
}