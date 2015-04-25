/*
 * Utilidades.java
 *
 * Created on 28 de febrero de 2008, 20:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package expensav20;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JFrame;

/**
 *
 * @author Javier
 */
public class Utilidades {
    
    /** Creates a new instance of Utilidades */
    public static void centrarVentana(JFrame frame){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

      // Calcula la nueva localización de la ventana
        int ancho = frame.getSize().width;
        int alto = frame.getSize().height;
        int x = (dim.width - ancho) / 2;
        int y = (dim.height - alto) / 2;

      // Mueve la ventana a la nueva posiciòn
        frame.setLocation(x, y);
    }
    
    public boolean exportarDB(String db_name){
        InputStream in =getClass().getResourceAsStream("/DAO/DBProperties.properties"); 
        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String exc_cmd=new String("mysqldump.exe "+props.getProperty("dbname")+" -u"+props.getProperty("usr")+" -p"+props.getProperty("pass")+" > \""+db_name+".sql\"");
        System.out.print(exc_cmd);
        boolean return_value=false;
        try {
            Process pr = Runtime.getRuntime().exec("cmd /c " + exc_cmd);
            try {
                pr.waitFor();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (pr.exitValue()==0){
                return_value=true;
            }
            else{
                return_value=false;
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
            return_value=false;
        }
        return return_value;
    }
    public boolean importarDB(String db_name){
        InputStream in =getClass().getResourceAsStream("/DAO/DBProperties.properties"); 
        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String exc_cmd=new String("mysql -u"+props.getProperty("usr")+" -p"+props.getProperty("pass")+ " " + props.getProperty("dbname") + " < \""+db_name+"\"");
        System.out.print(exc_cmd);
        boolean return_value=false;
        try {
            Process pr = Runtime.getRuntime().exec("cmd /c " + exc_cmd);
            try {
                pr.waitFor();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if (pr.exitValue()==0){
                return_value=true;
            }
            else{
                return_value=false;
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
            return_value=false;
        }
        return return_value;
    }

}
