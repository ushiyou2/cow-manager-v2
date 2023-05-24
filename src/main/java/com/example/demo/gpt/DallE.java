package com.example.demo.gpt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class DallE {
    public static String getImg(String text) throws Exception {
        String url = "https://api.openai.com/v1/images/generations";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + System.getenv("OPENAI_KEY_API"));
        
        
        JSONObject data = new JSONObject();
        data.put("prompt", text);
        data.put("n", 1);
        data.put("size", "1024x1024");

        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        int status = con.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String output = in.lines().reduce((a, b) -> a + b).get();
            String imgUrl = new JSONObject(output).getJSONArray("data").getJSONObject(0).getString("url");
            
            return imgUrl;
        } else {
            BufferedReader error = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String errorOutput = error.lines().reduce((a, b) -> a + b).get();
            return errorOutput;
        }

    }

    public static void main(String[] args) {
        try {
			System.out.println(getImg("A dragon reading a book in forest under night sky."));
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }

}
