package com.company;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Main {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);

            JSONObject json;
            json = new JSONObject(jsonText.trim());
            return json;
        }
    }

    public static String readAPIfromFile(String filename){

        String apiKey = "";
        try {
              File myFile = new File(filename);
              Scanner myApi = new Scanner(myFile);
              apiKey = myApi.nextLine();

              myApi.close();

            } catch (FileNotFoundException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
            }
            return apiKey;
    }

    public static void main(String[] args) throws IOException, JSONException {

        String apiKey = "";

        /***
         * read file with API Key
         */
        apiKey = readAPIfromFile("apiKey");


        JSONObject json = readJsonFromUrl("http://api.openweathermap.org/data/2.5/weather?id=2891742&lang=de&units=metric&appid=" + apiKey);

        System.out.println(json.toString());
        System.out.println("*****************************");

        JSONArray weather = json.getJSONArray("weather");
        JSONObject main = json.getJSONObject("main");
        JSONObject Station = json.getJSONObject("sys");

        System.out.println("Ort:              " + json.get("name"));
        System.out.println("Temperatur:       " + main.get("temp") + "°C");
        System.out.println("Gefühlt:          " + main.get("feels_like") + "°C");
        System.out.println("Luftfeuchtigkeit: " + main.get("humidity") + "%");

        System.out.println("Wetter:           " + weather.getJSONObject(0).getString("description"));

    }
}