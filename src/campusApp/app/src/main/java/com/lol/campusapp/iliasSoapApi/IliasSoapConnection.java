package com.lol.campusapp.iliasSoapApi;

import com.lol.campusapp.iliasSoapApi.generatedSoupClasses.MLKILIASSoapWebserviceBinding;
import com.lol.campusapp.iliasSoapApi.generatedSoupClasses.MLKilUserData;

public class IliasSoapConnection {
    //these are just some Methods written to test the generated Soap-Classes
    //the result: the generated Soap-Classes are not fully functional
    //most relevant methods are only available with a premium subscribtion
    //this approach was therefor abandoned and not yet continued


    public static final String ILIAS_CLIENT = "mriliastest";
    public static MLKILIASSoapWebserviceBinding service;

    public static String login() {
        service = new MLKILIASSoapWebserviceBinding();
        String loginresult = null;
        try {
            loginresult = service.login(ILIAS_CLIENT,
                                "abcdef",
                                "abcdef!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginresult;
    }

    public static String getSomeData() {
        login();
        MLKilUserData result = null;
        try {
            result = service.getUser("Wurthman", 25);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fristname:" + result.firstname
                + "; orther: " + result.toString();
    }
}
