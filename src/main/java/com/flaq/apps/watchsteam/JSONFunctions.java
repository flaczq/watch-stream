package com.flaq.apps.watchsteam;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by FLAQ on 2015-11-21.
 */
public class JSONFunctions {

    public static String downloadText(String stringUrl) {
        URL url = null;
        String result = "";

        try {
            url = new URL(stringUrl);

            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            byte[] buffer = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int bytesRead = 0;

            while ((bytesRead = bis.read(buffer)) > 0) {
                String text = new String(buffer, 0, bytesRead);
                sb.append(text);
            }

            result = sb.toString();
            bis.close();

        } catch (MalformedURLException e) {
            Log.e("error", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("error", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

}
