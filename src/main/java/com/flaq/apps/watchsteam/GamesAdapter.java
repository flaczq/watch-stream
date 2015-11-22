package com.flaq.apps.watchsteam;

import android.content.Context;
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

    private ImageView imageView;
    private Context context;
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

        if (convertView == null) {
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) convertView;
            imageView.setPadding(1, 1, 1, 1);
            imageView.setBackgroundColor(000);
            imageView.setAdjustViewBounds(true);
            imageView.setImageDrawable(context.getDrawable(R.mipmap.blank));
            imageView.setMinimumWidth(272);
        }

        Bitmap bitmap = (Bitmap) game.get("image");
        imageView.setImageBitmap(bitmap);

        return imageView;
    }

}
