package com.lol.campusapp.iliasSoapApi;

import junit.framework.TestCase;

public class IliasSoapConnectionTest extends TestCase {


    public void testLogin() {
        String loginresult = IliasSoapConnection.login();
        System.out.println("loginresult = " + loginresult);
    }


    public void testgetSomeData() {
        String result = IliasSoapConnection.getSomeData();
        System.out.println("result = " + result);
        throw new RuntimeException();
    }
}