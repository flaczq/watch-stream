package com.flaq.apps.watchsteam.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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

    public GameStreamsAdapter(Context context, ArrayList<HashMap<String, Object>> streamsList) {
        super(context, -1, streamsList);
        this.context = context;
        this.streamsList = streamsList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HashMap<String, Object> stream = streamsList.get(position);
        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_game_streams_item, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.channel = (TextView) convertView.findViewById(R.id.channel);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status);
            viewHolder.viewers = (TextView) convertView.findViewById(R.id.viewers);
            viewHolder.updated = (TextView) convertView.findViewById(R.id.updated);
            viewHolder.logo = (ImageView) convertView.findViewById(R.id.logo);
            viewHolder.previewButton = (ImageView) convertView.findViewById(R.id.previewButton);
            viewHolder.preview = (ImageView) convertView.findViewById(R.id.preview);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.channel.setText((String) stream.get("name"));
        viewHolder.status.setText((String) stream.get("status"));
        viewHolder.viewers.setText((String) stream.get("viewers"));
        viewHolder.updated.setText((String) stream.get("updatedAt"));
        viewHolder.logo.setImageBitmap((Bitmap) stream.get("logo"));

        viewHolder.previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewHolder.preview.getDrawable() == null) {
                    String previewURL = (String) streamsList.get(position).get("previewURL");

                    new DownBitmap().execute(viewHolder.preview, previewURL);
                }

                if (viewHolder.preview.getVisibility() == View.INVISIBLE) {
                    viewHolder.preview.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.preview.setVisibility(View.INVISIBLE);
                }
            }
        });

        viewHolder.preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getVisibility() == View.INVISIBLE) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder {

        private TextView channel, status, viewers, updated;
        private ImageView logo, previewButton, preview;

    }

    class DownBitmap extends AsyncTask<Object, Void, Bitmap> {

        private String previewUrl;
        private Bitmap previewBitmap;
        private ImageView imageView;

        @Override
        protected Bitmap doInBackground(Object... params) {
            imageView = (ImageView) params[0];
            previewUrl = (String) params[1];
            previewBitmap = URLUtils.downloadBitmap(previewUrl);
            return previewBitmap;
        }

        protected void onPostExecute(Bitmap previewBitmap) {
            if (imageView != null && previewBitmap != null) {
                imageView.setImageBitmap(previewBitmap);
            }
        }

    }

}
