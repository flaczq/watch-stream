package com.flaq.apps.watchsteam;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FLAQ on 2015-11-20.
 */
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DownJSON jsonInstance = new DownJSON();
        jsonInstance.execute();
    }

    private class DownJSON extends AsyncTask<Void, Void, Void> {
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
            String gamesString = Utils.downloadUrl(getString(R.string.json_url_games_top));
            gamesList = new ArrayList<>();

            try {
                JSONObject gamesObj = new JSONObject(gamesString);
                JSONArray gamesArr = gamesObj.getJSONArray("top");
                JSONObject gameObj, imageObj;

                for(int i = 0; i < gamesArr.length(); i++) {
                    HashMap<String, Object> gameMap = new HashMap<>();

                    gamesObj = gamesArr.getJSONObject(i);
                    gameObj = gamesObj.getJSONObject("game");
                    imageObj = gameObj.getJSONObject("box");

                    gameMap.put("name", gameObj.getString("name"));

                    Bitmap image = Utils.downloadBitmap(imageObj.getString("large"));
                    gameMap.put("image", image);

                    gamesList.add(gameMap);
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
