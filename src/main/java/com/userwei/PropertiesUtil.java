package com.userwei;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class PropertiesUtil {
       
    public static String getValue(String key){
        Properties prop = new Properties();
        String value = null;

        try(FileInputStream fis = new FileInputStream("config.properties")){
            prop.load(fis);
            value = prop.getProperty(key, null); 
        }catch(Exception e){
            e.printStackTrace();
        }
        return value;
    }

    public static void setValue(String key, String value){
        Properties prop = new Properties();
        String cs = getValue("Custom_Size");
        String csx = getValue("Custom_Size_X");
        String csy = getValue("Custom_Size_Y");
        String cm = getValue("Config_Mode");
        String cax = getValue("Custom_Add_X");
        String cay = getValue("Custom_Add_Y");
        String clax = getValue("Custom_Last_Add_X");
        String clay = getValue("Custom_Last_Add_Y");
        String ibx = getValue("Init_Border_X");
        String iby = getValue("Init_Border_Y");
        
        try(FileOutputStream fos = new FileOutputStream("config.properties")){
            prop.setProperty("Custom_Size", cs);
            prop.setProperty("Custom_Size_X", csx);
            prop.setProperty("Custom_Size_Y", csy);
            prop.setProperty("Config_Mode", cm);
            prop.setProperty("Custom_Add_X", cax);
            prop.setProperty("Custom_Add_Y", cay);
            prop.setProperty("Custom_Last_Add_X", clax);
            prop.setProperty("Custom_Last_Add_Y", clay);
            prop.setProperty("Init_Border_X", ibx);
            prop.setProperty("Init_Border_Y", iby);

            prop.setProperty(key, value);
            prop.store(fos, null);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void resetValue(){
        Properties prop = new Properties();

        try(FileOutputStream fos = new FileOutputStream("config.properties")){
            prop.setProperty("Custom_Size", "Off");
            prop.setProperty("Custom_Size_X", "1280");
            prop.setProperty("Custom_Size_Y", "720");
            prop.setProperty("Config_Mode", "Off");
            prop.setProperty("Custom_Add_X", "0");
            prop.setProperty("Custom_Add_Y", "0");
            prop.setProperty("Custom_Last_Add_X", "0");
            prop.setProperty("Custom_Last_Add_Y", "0");
            prop.setProperty("Init_Border_X", "1280");
            prop.setProperty("Init_Border_Y", "720");
            prop.store(fos, null);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
