package com.flaq.apps.watchsteam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GameStreamsActivity extends AppCompatActivity {

    private Intent intent;
    private ListView listView;
    private GamesAdapter streamsAdapter;
    private ProgressDialog streamsPreloader;
    private ArrayList<HashMap<String, Object>> streamsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_streams);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        intent = getIntent();

        DownJSON jsonInstance = new DownJSON();
        jsonInstance.execute();
    }

    private class DownJSON extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            streamsPreloader = new ProgressDialog(GameStreamsActivity.this);
            streamsPreloader.setTitle("Please wait");
            streamsPreloader.setMessage("Streams are loading...");
            streamsPreloader.setIndeterminate(false);
            streamsPreloader.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String name = intent.getStringExtra("name");
            String gamesString = Utils.downloadUrl(getString(R.string.json_url_game_streams) + name);
            streamsList = new ArrayList<>();

            try {
                JSONObject streamsObj = new JSONObject(gamesString);
                JSONArray streamsArr = streamsObj.getJSONArray("streams");
                JSONObject channelObj;

                for (int i = 0; i < streamsArr.length(); i++) {
                    HashMap<String, Object> gameMap = new HashMap<>();

                    streamsObj = streamsArr.getJSONObject(i);
                    channelObj = streamsObj.getJSONObject("channel");

                    gameMap.put("name", channelObj.getString("display_name"));
                    gameMap.put("status", channelObj.getString("status"));
                    gameMap.put("viewers", channelObj.getString("viewers"));
                    gameMap.put("updatedAt", channelObj.getString("updated_at"));
                    Bitmap logo = Utils.downloadBitmap(channelObj.getString("logo"));
                    gameMap.put("logo", logo);

                    streamsList.add(gameMap);
                }
            } catch (JSONException e) {
                Log.e("game streams json", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            streamsAdapter = new GamesAdapter(GameStreamsActivity.this, streamsList);
            listView.setAdapter(streamsAdapter);

            streamsPreloader.dismiss();
        }
    }

}
