package com.lol.campusapp.iliasRestApi;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IliasRestConnectionTest extends TestCase {
    //this Class was created for trying out different configurations and seeing the results
    //the following Methods are no proper Unittests

    IliasRestConnection conn = new IliasRestConnection();

    public void testHttpGet() throws IOException {
        String result = conn.httpGet("routes");
        System.out.println("result = " + result);
    }

    public void testHttpPost() throws IOException {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type", "password");
        paramMap.put("api_key", "mriliastest");
        paramMap.put("username", "");
        paramMap.put("password", "");
        String result = conn.httpPost("oauth2/token", paramMap);
        System.out.println("result = " + result);
    }
}