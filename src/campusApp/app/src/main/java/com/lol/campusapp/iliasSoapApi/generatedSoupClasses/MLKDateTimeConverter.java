package com.lol.campusapp.iliasSoapApi.generatedSoupClasses;

//----------------------------------------------------
//
// Generated by www.easywsdl.com
// Version: 6.0.1.1
//
// Created by Quasar Development 
//
//----------------------------------------------------

import java.util.Date;


public interface MLKDateTimeConverter
{
    java.util.Date convertDateTime(String strDate);
    java.util.Date convertTime(String strDate);
    java.util.Date convertDate(String strDate);
    String convertDuration(String value);
    String getStringFromDateTime(Date value);
    String getStringFromDate(Date value);
    String getStringFromTime(Date value);
    String getStringFromDuration(String value);
}