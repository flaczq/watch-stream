package com.flaq.apps.watchsteam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by FLAQ on 2015-11-21.
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, String>> imagesArray;
    //private ImageLoader imageLoader;

    public ImageAdapter(Context context, ArrayList<HashMap<String, String>> imagesArray) {
        this.context = context;
        this.imagesArray = imagesArray;

        //imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return imagesArray.size();
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
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        InputStream is = Utils.downloadStream(imagesArray.get(position).get("image"));
        Bitmap image = BitmapFactory.decodeStream(is);
        imageView.setImageBitmap(image);

        return imageView;
    }

}
