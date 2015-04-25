/*
 * ConnectionSing.java
 *
 * Created on 8 de enero de 2008, 18:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Javier
 */
public class ConnectionSing {
    
    /** Creates a new instance of ConnectionSing */
    private static Connection con;
    private static String db_usr;
    private static String db_pass;
    private static String db_name;
    private static String db_port;
    private ConnectionSing() { 
        InputStream in =getClass().getResourceAsStream("/DAO/DBProperties.properties"); 
        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.db_name = props.getProperty("dbname");
        this.db_port = props.getProperty("port");
        this.db_usr = props.getProperty("usr");
        this.db_pass = props.getProperty("pass");
    }
    private static void setProperties()
    {

    }
    public static Connection getConection(){
        ConnectionSing cs=new ConnectionSing();
        if (con==null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://"+db_port+"/"+db_name, db_usr, db_pass);
                return con;
                } catch(SQLException ex) {
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch(Exception e) {
                    System.out.println("Se produjo un error inesperado: "+e.getMessage());
                }
            return null;
        }
        else
        {
            return con;
        }
    }     

}



    