package com.userwei;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.configuration.PropertiesConfiguration;


public class PropertiesUtil {

    public static String getValue(String key){
        String value = null;
        try{
            /*
            InputStream input = PropertiesUtil.class.getResourceAsStream("config.properties");
            Properties props = new Properties();
            props.load(input);
            value = props.getProperty(key, null); 
            */
            InputStream input = PropertiesUtil.class.getResourceAsStream("config.properties");
            PropertiesConfiguration config = new PropertiesConfiguration();
            config.load(input);
            value = config.getString(key);

        }catch(Exception e){
            e.printStackTrace();
        }
        return value;
    }

    public static void setValue(String key, String value){
        try{
            InputStream input = PropertiesUtil.class.getResourceAsStream("config.properties");
            PropertiesConfiguration config = new PropertiesConfiguration();
            config.load(input);
            value = config.getString(key);

            String cs = config.getString("Custom_Size");
            String csx = config.getString("Custom_Size_X");
            String csy = config.getString("Custom_Size_Y");
            String cm = config.getString("Config_Mode");
            String cax = config.getString("Custom_Add_X");
            String cay = config.getString("Custom_Add_Y");
            String clax = config.getString("Custom_Last_Add_X");
            String clay = config.getString("Custom_Last_Add_Y");
            String ibx = config.getString("Init_Border_X");
            String iby = config.getString("Init_Border_Y");
            
            config.setProperty("Custom_Size", cs);
            config.setProperty("Custom_Size_X", csx);
            config.setProperty("Custom_Size_Y", csy);
            config.setProperty("Config_Mode", cm);
            config.setProperty("Custom_Add_X", cax);
            config.setProperty("Custom_Add_Y", cay);
            config.setProperty("Custom_Last_Add_X", clax);
            config.setProperty("Custom_Last_Add_Y", clay);
            config.setProperty("Init_Border_X", ibx);
            config.setProperty("Init_Border_Y", iby);

            config.setProperty(key, value);
            config.save();
        }catch(Exception e){
            e.printStackTrace();
        }

        /*
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
        
        try{
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
            // prop.store(fos, null);
        }catch(Exception e){
            e.printStackTrace();
        }
        */
    }

    public static void resetValue(){
        try{
            InputStream input = PropertiesUtil.class.getResourceAsStream("config.properties");
            PropertiesConfiguration config = new PropertiesConfiguration();
            config.load(input);

            config.setProperty("Custom_Size", "Off");
            config.setProperty("Custom_Size_X", "1280");
            config.setProperty("Custom_Size_Y", "720");
            config.setProperty("Config_Mode", "Off");
            config.setProperty("Custom_Add_X", "0");
            config.setProperty("Custom_Add_Y", "0");
            config.setProperty("Custom_Last_Add_X", "0");
            config.setProperty("Custom_Last_Add_Y", "0");
            config.setProperty("Init_Border_X", "1280");
            config.setProperty("Init_Border_Y", "720");

            config.save();
        }catch(Exception e){
            e.printStackTrace();
        }
        /*
        File jarPath = new File(PropertiesUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath = null;
        try{
            propertiesPath = jarPath.getParentFile().getCanonicalPath();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        Properties prop = new Properties();

        try(FileOutputStream fos = new FileOutputStream(propertiesPath + "/classes/com/userwei/config.properties")){
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
            OutputStream os = null;

            prop.store(os, null);
        }catch(Exception e){
            e.printStackTrace();
        }
        */
    }

}
