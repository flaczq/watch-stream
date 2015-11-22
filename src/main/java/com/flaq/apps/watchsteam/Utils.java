package com.flaq.apps.watchsteam;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by FLAQ on 2015-11-21.
 */
public class Utils {

    public static String encodeString(String urlString) {
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

    public static String downloadString(String urlString) {
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
            Log.e("error", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("error", e.getMessage());
            e.printStackTrace();
        }

        return string;
    }

    public static InputStream downloadStream(String urlString) {
        InputStream stream = null;

        try {
            URL url = new URL(urlString);
            stream = url.openStream();
        } catch (MalformedURLException e) {
            Log.e("error", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("error", e.getMessage());
            e.printStackTrace();
        }

        return stream;
    }

}
