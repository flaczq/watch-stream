package com.flaq.apps.watchsteam.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.flaq.apps.watchsteam.R;
import com.flaq.apps.watchsteam.adapters.GamesAdapter;
import com.flaq.apps.watchsteam.utilities.URLUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GamesActivity extends AppCompatActivity {

    private GridView gridView;
    private GamesAdapter gamesAdapter;
    private ProgressDialog gamesPreloader;
    private ArrayList<HashMap<String, Object>> gamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView = (GridView) findViewById(R.id.gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String game = (String) gamesList.get(position).get("name");
                Context context = GamesActivity.this;

                Intent nextIntent = new Intent(context, GameStreamsActivity.class);
                nextIntent.putExtra("game", game);
                context.startActivity(nextIntent);
            }
        });

        new ProcessGamesJSON().execute();
    }

    private class ProcessGamesJSON extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            gamesPreloader = new ProgressDialog(GamesActivity.this);
            gamesPreloader.setTitle("Please wait");
            gamesPreloader.setMessage("Games are loading...");
            gamesPreloader.setIndeterminate(false);
            gamesPreloader.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String gamesString = URLUtils.downloadText(getString(R.string.url_games_top));
            gamesList = new ArrayList<>();

            try {
                JSONObject gamesObj = new JSONObject(gamesString);
                JSONArray gamesArr = gamesObj.getJSONArray("top");
                JSONObject gameObj, imageObj;

                for(int i = 0; i < gamesArr.length(); i++) {
                    HashMap<String, Object> gamesMap = new HashMap<>();

                    gamesObj = gamesArr.getJSONObject(i);
                    gameObj = gamesObj.getJSONObject("game");
                    imageObj = gameObj.getJSONObject("box");

                    gamesMap.put("name", gameObj.getString("name"));

                    Bitmap image = URLUtils.downloadBitmap(imageObj.getString("large"));
                    gamesMap.put("image", image);

                    gamesList.add(gamesMap);
                }
            } catch (JSONException e) {
                Log.e("games json", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            gamesAdapter = new GamesAdapter(GamesActivity.this, gamesList);
            gridView.setAdapter(gamesAdapter);

            gamesPreloader.dismiss();
        }
    }

}
