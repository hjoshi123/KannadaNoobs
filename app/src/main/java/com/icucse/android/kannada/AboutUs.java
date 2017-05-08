package com.icucse.android.kannada;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.icucse.android.kannada.R;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        TextView textView = (TextView) findViewById(R.id.kannada_name);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/kannada.ttf");
        textView.setTypeface(tf);

    }
}
