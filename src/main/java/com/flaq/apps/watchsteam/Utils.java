package com.flaq.apps.watchsteam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by FLAQ on 2015-11-21.
 */
public class Utils {

    public static String encodeUrl(String urlString) {
        String encoded = "";
        String[] split = urlString.split("/");

        try {
            for (int i = 0; i < split.length; i++) {
                if (split[i].contains("%20")) {
                    encoded += URLEncoder.encode(split[i], "UTF-8");
                } else {
                    encoded += split[i];
                }

                if (i != split.length - 1) {
                    encoded += "/";
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encoded;
    }

    public static String downloadUrl(String urlString) {
        String string = "";

        try {
            URL url = new URL(urlString);
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            byte[] buffer = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int bytesRead;
            String text;

            while ((bytesRead = bis.read(buffer)) > 0) {
                text = new String(buffer, 0, bytesRead);
                sb.append(text);
            }

            string = sb.toString();
            bis.close();
        } catch (MalformedURLException e) {
            Log.e("utils.downloadUrl", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("utils.downloadUrl", e.getMessage());
            e.printStackTrace();
        }

        return string;
    }

    public static Bitmap downloadBitmap(String urlString) {
        InputStream is;
        Bitmap bitmap = null;

        try {
            URL url = new URL(urlString);
            is = url.openConnection().getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            Log.e("utils.downloadBitmap", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("utils.downloadBitmap", e.getMessage());
            e.printStackTrace();
        }

        return bitmap;
    }

    public static void copyStream(InputStream is, OutputStream os) {
        final int BUFFER_SIZE = 1024;
        try {
            byte[] bytes = new byte[BUFFER_SIZE];
            int count;

            while ((count = is.read(bytes, 0, BUFFER_SIZE)) != -1) {
                os.write(bytes, 0, count);
            }
        } catch(Exception e) {
            Log.e("utils.copyStream", e.getMessage());
            e.printStackTrace();
        }
    }

}
