package com.flaq.apps.watchsteam.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flaq.apps.watchsteam.R;

import java.util.ArrayList;
import java.util.HashMap;

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
        HashMap<String, Object> game = gamesList.get(position);
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setImageResource(R.mipmap.blank);
        } else {
            imageView = (ImageView) convertView;
        }

        Bitmap bitmap = (Bitmap) game.get("image");
        imageView.setImageBitmap(bitmap);
        imageView.setTag(position);

        if (bitmap.getPixel(0, 0) == Color.argb(255, 103, 68, 168)) {
            TextView name = new TextView(context);
            name.setText((String) game.get("name"));
            name.setVisibility(View.VISIBLE);
            name.setSelected(true);
        }

        return imageView;
    }

}
