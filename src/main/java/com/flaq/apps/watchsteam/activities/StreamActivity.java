package com.flaq.apps.watchsteam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.flaq.apps.watchsteam.R;

public class StreamActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();

        String name = intent.getStringExtra("stream");
        String url = getString(R.string.url_stream_1) + name + getString(R.string.url_stream_2);

        Toast.makeText(StreamActivity.this, name, Toast.LENGTH_LONG).show();
    }

}
