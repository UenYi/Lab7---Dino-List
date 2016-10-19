package com.example.a1031002.lab7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView nameTV = (TextView)findViewById(R.id.detailName);
        TextView infoTV = (TextView)findViewById(R.id.detailInfo);
        ImageView img = (ImageView) findViewById(R.id.detailPic);

        Bundle extras = getIntent().getExtras();

        nameTV.setText(extras.getString("name"));
        infoTV.setText(extras.getString("info"));
        img.setImageResource(extras.getInt("pic"));
    }
}
