package com.flaq.apps.watchsteam.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flaq.apps.watchsteam.R;

import java.util.ArrayList;
import java.util.HashMap;

public class GameStreamsAdapter extends ArrayAdapter<HashMap<String, Object>> {

    private Context context;
    private ArrayList<HashMap<String, Object>> gamesList;

    public GameStreamsAdapter(Context context, ArrayList<HashMap<String, Object>> gamesList) {
        super(context, -1, gamesList);
        this.context = context;
        this.gamesList = gamesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap<String, Object> stream = gamesList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.content_game_streams_item, parent, false);

        TextView channel = (TextView) itemView.findViewById(R.id.channel);
        TextView status = (TextView) itemView.findViewById(R.id.status);
        TextView viewers = (TextView) itemView.findViewById(R.id.viewers);
        TextView updated = (TextView) itemView.findViewById(R.id.updated);
        ImageView logo = (ImageView) itemView.findViewById(R.id.logo);

        channel.setText((String) stream.get("channel_name"));
        status.setText((String) stream.get("status"));
        viewers.setText((String) stream.get("viewers"));
        updated.setText((String) stream.get("since"));
        logo.setImageBitmap((Bitmap) stream.get("logo"));

        return itemView;
    }

}
