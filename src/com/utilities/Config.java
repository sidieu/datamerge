/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author smuyila
 */
public class Config {
    
       public static String getConfig(String parameter) 
     {
      String message = "";

      Properties props = new Properties();
      FileInputStream fis = null;
        try {
            fis = new FileInputStream(System.getProperty("user.home")+"/config.properties");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            props.load(fis);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        message = props.getProperty(parameter);

         return message;
     }
    
}
