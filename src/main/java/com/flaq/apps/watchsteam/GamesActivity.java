package com.flaq.apps.watchsteam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FLAQ on 2015-11-20.
 */
public class GamesActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> gamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

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

            /*pd_game = new ProgressDialog(GamesActivity.this);
            pd_game.setTitle("Please wait");
            pd_game.setMessage("Games are loading...");
            pd_game.setIndeterminate(false);
            pd_game.show();*/
        }

        @Override
        protected Void doInBackground(Void... params) {
            String gamesString = JSONFunctions.downloadText(getString(R.string.json_url_games_top));
            gamesList = new ArrayList<>();

            try {
                JSONObject gamesObj = new JSONObject(gamesString);
                JSONArray gamesArray = gamesObj.getJSONArray("top");
                HashMap<String, String> gameMap = new HashMap<>();
                JSONObject gameObj, imageObj;

                for(int i = 0; i < gamesArray.length(); i++) {
                    gamesObj = gamesArray.getJSONObject(i);
                    gameObj = gamesObj.getJSONObject("game");
                    imageObj = gameObj.getJSONObject("box");

                    gameMap.put("name", gameObj.getString("name"));
                    gameMap.put("image", imageObj.getString("large"));

                    gamesList.add(gameMap);
                    gameMap.clear();
                }
            } catch (JSONException e) {
                Log.e("games json", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            /*gv_game = (GridView) findViewById(R.id.gv_game);
            adapter = new LVAGames(GamesActivity.this, gamesList);

            gv_game.setAdapter(adapter);

            pd_game.dismiss();*/
        }
    }

}
