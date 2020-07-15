package com.example.convertor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class resultActivity extends AppCompatActivity
{
    TextView headerText,resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent=getIntent();
        Bundle bundle = getIntent().getExtras();
        String ans = bundle.getString("textResult");
        //String ans = intent.getStringExtra("textObtained");
        Log.i("textob=",""+ans);
        resultText=findViewById(R.id.resultText);
        resultText.setText(ans);

    }
}