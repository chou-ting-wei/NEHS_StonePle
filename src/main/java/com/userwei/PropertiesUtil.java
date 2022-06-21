package com.userwei;

import java.io.File;

import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertiesUtil {
    public static String getValue(String key){
        String value = null;
        try{
            // PropertiesConfiguration config = new PropertiesConfiguration(PropertiesUtil.class.getResource("config.properties"));
            String outPath = System.getProperty("user.dir") + File.separator + "config" + File.separator;
            // System.out.println(outPath);
            PropertiesConfiguration config = new PropertiesConfiguration(outPath + "config.properties");
            value = config.getString(key);
        }catch(Exception e){
            e.printStackTrace();
        }
        return value;
    }

    public static void setValue(String key, String value){
        try{
            // PropertiesConfiguration config = new PropertiesConfiguration(PropertiesUtil.class.getResource("config.properties"));
            String outPath = System.getProperty("user.dir") + File.separator + "config" + File.separator;
            // System.out.println(outPath);
            PropertiesConfiguration config = new PropertiesConfiguration(outPath + "config.properties");

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
    }

    public static void resetValue(){
        try{
            // PropertiesConfiguration config = new PropertiesConfiguration(PropertiesUtil.class.getResource("config.properties"));
            String outPath = System.getProperty("user.dir") + File.separator + "config" + File.separator;
            // System.out.println(outPath);
            PropertiesConfiguration config = new PropertiesConfiguration(outPath + "config.properties");

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
    }

}
