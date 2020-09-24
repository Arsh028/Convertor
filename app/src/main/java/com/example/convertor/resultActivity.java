package com.example.convertor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.text.BreakIterator;

public class resultActivity extends AppCompatActivity
{
    TextInputEditText result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        String ans = bundle.getString("textResult");
        //String ans = intent.getStringExtra("textObtained");
        Log.i("textob=",""+ans);
        result=findViewById(R.id.result);
        result.setText(ans);
    }
}