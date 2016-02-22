package com.flaq.apps.watchsteam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class GameStreamsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_streams);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        Toast toast = Toast.makeText(GameStreamsActivity.this, name, Toast.LENGTH_LONG);
        toast.show();
    }
}
