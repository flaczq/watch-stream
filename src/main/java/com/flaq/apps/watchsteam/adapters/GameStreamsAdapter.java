package com.flaq.apps.watchsteam.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.flaq.apps.watchsteam.R;
import com.flaq.apps.watchsteam.utilities.URLUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class GameStreamsAdapter extends ArrayAdapter<HashMap<String, Object>> {

    private Context context;
    private ArrayList<HashMap<String, Object>> streamsList;
    private ImageView preview;

    public GameStreamsAdapter(Context context, ArrayList<HashMap<String, Object>> streamsList) {
        super(context, -1, streamsList);
        this.context = context;
        this.streamsList = streamsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HashMap<String, Object> stream = streamsList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.content_game_streams_item, parent, false);

        TextView channel = (TextView) itemView.findViewById(R.id.channel);
        TextView status = (TextView) itemView.findViewById(R.id.status);
        TextView viewers = (TextView) itemView.findViewById(R.id.viewers);
        TextView updated = (TextView) itemView.findViewById(R.id.updated);
        ImageView logo = (ImageView) itemView.findViewById(R.id.logo);
        ImageView previewButton = (ImageView) itemView.findViewById(R.id.previewButton);
        preview = (ImageView) itemView.findViewById(R.id.preview);

        channel.setText((String) stream.get("channel"));
        status.setText((String) stream.get("status"));
        viewers.setText((String) stream.get("viewers"));
        updated.setText((String) stream.get("since"));
        logo.setImageBitmap((Bitmap) stream.get("logo"));
        final String previewURL = (String) stream.get("previewURL");
        final String pos = String.valueOf(position);

        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("*<GameStreamsAdapter>", "Position: " + pos);
                new DownBitmap().execute(previewURL);
            }
        });

        return itemView;
    }

    class DownBitmap extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... bitmapURL) {
            Bitmap previewBitmap = URLUtils.downloadBitmap(bitmapURL[0]);
            return previewBitmap;
        }

        protected void onPostExecute(Bitmap previewBitmap) {
            preview.setImageBitmap(previewBitmap);
            preview.setVisibility(View.VISIBLE);
        }

    }

}
