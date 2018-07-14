package com.su43berkut17.nanodegree.bakingapp.utils;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class JsonUtils {
    private static final String jsonUrl="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private final String TAG="JsoUtils";

    public JsonUtils(){
    }

    //load the json file
    public static String loadJson() throws IOException {
        Uri uri=Uri.parse(jsonUrl).buildUpon().build();
        URL url=new URL(uri.toString());
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();

        try {
            InputStream recipes = connection.getInputStream();
            /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(recipes));
            String line = "";
            String data="";
            while (line != null) {
                line = bufferedReader.readLine();

                if (line!=null) {
                    data = data + line;
                }
            }

            return data;*/

            Scanner scanner = new Scanner(recipes);
            scanner.useDelimiter("\\A");
            String finalString = "";

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                do {
                    String toAdd = scanner.next();
                    finalString = finalString + toAdd;
                    hasInput = scanner.hasNext();
                } while (hasInput == true);
                Log.i("JsonUtils","We loaded succesfully the json");
                return finalString;
            } else {
                return null;
            }
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            connection.disconnect();
        }
    }
}
