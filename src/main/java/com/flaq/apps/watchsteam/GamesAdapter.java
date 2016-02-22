package com.flaq.apps.watchsteam;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FLAQ on 2015-11-21.
 */
public class GamesAdapter extends BaseAdapter {

    private Context context;
    private Intent intent;
    private ArrayList<HashMap<String, Object>> gamesList;

    public GamesAdapter(Context context, ArrayList<HashMap<String, Object>> gamesList) {
        this.context = context;
        this.gamesList = gamesList;
    }

    @Override
    public int getCount() {
        return gamesList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HashMap<String, Object> singleGame = gamesList.get(position);
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setImageResource(R.mipmap.blank);
        } else {
            imageView = (ImageView) convertView;
        }

        Bitmap bitmap = (Bitmap) singleGame.get("image");
        imageView.setImageBitmap(bitmap);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context, GameStreamsActivity.class);
                intent.putExtra("name", (String) singleGame.get("name"));
                context.startActivity(intent);
            }
        });

        return imageView;
    }

}
