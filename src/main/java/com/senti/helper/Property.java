package com.senti.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {
    private static Properties prop=null;



    public  String getValue(String Property) {
        if (prop!=null) return
                prop.getProperty(Property);

        String result = null;

        try (InputStream input = Thread.currentThread().getContextClassLoader().
                getResourceAsStream("config.properties")) {

            prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");

            }
            prop.load(input);
            result = prop.getProperty(Property);

        } catch (IOException ex) {
            System.out.println("shabi");
            ex.printStackTrace();
        }


        return result;
    }
}
