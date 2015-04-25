/*
 * PropiedadesDAO.java
 *
 * Created on 24 de enero de 2008, 20:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Propiedades;

/**
 *
 * @author Javier
 */
public class PropiedadesDAO {
    
    /** Creates a new instance of PropiedadesDAO */
    public PropiedadesDAO() {
        this.setCon(cs.getConection());
    }
        /** Creates a new instance of PropietarioDAO */
    private ConnectionSing cs;
    /** Creates a new instance of UnidadDAO */
    
    private Connection con;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    public Map buscarTodas(){
        String mysql_var=null;
        mysql_var="SELECT * FROM propiedades";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            ResultSet rs= st.executeQuery();
            Map propMapa=new HashMap();
            while (rs.next()){
                Propiedades prop=new Propiedades();
                prop.setCodigo(rs.getLong("prop_codigo"));
                prop.setNombre(rs.getString("prop_nombre"));
                prop.setValor(rs.getString("prop_valor"));
                propMapa.put(prop.getNombre(),prop.getValor());
            }
            return propMapa;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public List buscarTodasList(){
        String mysql_var=null;
        mysql_var="SELECT * FROM propiedades";
        PreparedStatement st;
        try {
            con.setAutoCommit(true);
            st = con.prepareStatement(mysql_var);
            ResultSet rs= st.executeQuery();
            List lst=new ArrayList();
            while (rs.next()){
                Propiedades prop=new Propiedades();
                prop.setCodigo(rs.getLong("prop_codigo"));
                prop.setNombre(rs.getString("prop_nombre"));
                prop.setValor(rs.getString("prop_valor"));
                lst.add(prop);
            }
            return lst;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public int modificar(Propiedades prop){
        String mysql_var="UPDATE propiedades SET prop_valor=? WHERE prop_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,prop.getValor());
            st.setLong(2,prop.getCodigo());
            return st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }

}
