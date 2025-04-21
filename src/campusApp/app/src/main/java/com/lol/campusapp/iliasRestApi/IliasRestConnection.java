package com.lol.campusapp.iliasRestApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class IliasRestConnection {
    //the methods do work but are currently too simplictic to properly return data from the IliasApi

    public static final String API_BASE_URL =
        "https://ilias-test.hrz.uni-marburg.de/Customizing/global/plugins/Services/UIComponent/UserInterfaceHook/REST/api.php/v2";

    private HttpURLConnection getConnection(String urlExtension) throws IOException {
        String urlStr = API_BASE_URL + "/" + urlExtension;

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            System.out.println("responseCode = " + responseCode);
            System.out.println("responseMessage = " + conn.getResponseMessage());
            throw new IOException(conn.getResponseMessage());
        }
        return conn;
    }

    public String httpGet(String urlExtension) throws IOException {
        StringBuilder sb = null;
        HttpURLConnection conn = null;

        try {
            conn = getConnection(urlExtension);

            // Buffer the result into a string
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return sb.toString();
    }

    public String httpPost(String urlExtension, Map<String, String> paramMap)
                            throws IOException {
        StringBuilder sb = null;
        HttpURLConnection conn = null;

        try {
            conn = getConnection(urlExtension);

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            // Create the form content
            OutputStream out = conn.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            for (String key : paramMap.keySet()) {
                writer.write(key);
                writer.write("=");
                writer.write(URLEncoder.encode(paramMap.get(key), "UTF-8"));
                writer.write("&");
            }
            writer.close();
            out.close();

            // Buffer the result into a string
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }

        return sb.toString();
    }
}
