/*
 * CoeficienteDAO.java
 *
 * Created on 12 de enero de 2008, 10:37
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
import java.util.List;
import model.Coeficiente;

/**
 *
 * @author Javier
 */
public class CoeficienteDAO {
    
    private ConnectionSing cs;
    private Connection con;
    /** Creates a new instance of CoeficienteDAO */
    public CoeficienteDAO() {
        this.setCon(cs.getConection());
    }
    

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    
    public Coeficiente guardar(Coeficiente coe){
        String mysql_var="insert into coeficiente(coe_denominacion, coe_distribuible) values (?, ?)";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,coe.getDenominacion());
            st.setBoolean(2,coe.isDistribuible());
            st.executeUpdate();
            ResultSet rs= st.getGeneratedKeys();
            while (rs.next()){
                coe.setCodigo(rs.getInt(1));
            }
            return coe;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public List buscarActivos(){
        List coe_list=new ArrayList();
        String mysql_var="SELECT * FROM coeficiente WHERE coe_fecha_baja IS NULL";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Coeficiente coe=new Coeficiente();
                coe.setCodigo(rs.getInt("coe_codigo"));
                coe.setDenominacion(rs.getString("coe_denominacion"));
                coe.setDistribuible(rs.getBoolean("coe_distribuible"));
                coe_list.add(coe); 
            }
            return coe_list;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public List buscarDistribuiblesActivos(){
        List coe_list=new ArrayList();
        String mysql_var="SELECT * FROM coeficiente WHERE coe_fecha_baja IS NULL AND coe_distribuible=1";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Coeficiente coe=new Coeficiente();
                coe.setCodigo(rs.getInt("coe_codigo"));
                coe.setDenominacion(rs.getString("coe_denominacion"));
                coe.setDistribuible(rs.getBoolean("coe_distribuible"));
                coe_list.add(coe); 
            }
            return coe_list;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public Coeficiente buscarCodigo(long codigo){
        String mysql_var="SELECT * FROM coeficiente WHERE coe_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,codigo);
            ResultSet rs= st.executeQuery();
            Coeficiente coe=new Coeficiente();
            while (rs.next()){
                coe.setCodigo(rs.getInt("coe_codigo"));
                coe.setDenominacion(rs.getString("coe_denominacion"));
                coe.setDistribuible(rs.getBoolean("coe_distribuible"));
            }
            return coe;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List buscarxCriteria(String nombre){
        List coe_list=new ArrayList();
        String mysql_var="SELECT * FROM coeficiente WHERE coe_denominacion like ?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,"%"+nombre+"%");
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Coeficiente coe=new Coeficiente();
                coe.setCodigo(rs.getInt("coe_codigo"));
                coe.setDenominacion(rs.getString("coe_denominacion"));
                coe.setDistribuible(rs.getBoolean("coe_distribuible"));
                coe.setFechaBaja(rs.getDate("coe_fecha_baja"));
                coe_list.add(coe); 
            }
            return coe_list;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public int eliminar(Coeficiente coe){
        String mysql_var="UPDATE coeficiente SET coe_fecha_baja=now() WHERE coe_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,coe.getCodigo());
            st.executeUpdate();
            return 1;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }

    public int modificar(Coeficiente coe){
        String mysql_var="UPDATE coeficiente SET coe_denominacion=?,coe_distribuible=? WHERE coe_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,coe.getDenominacion());
            st.setBoolean(2,coe.isDistribuible());
            st.setLong(3,coe.getCodigo());
            return st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }
    public int activar(Coeficiente coe){
        String mysql_var="UPDATE coeficiente SET coe_fecha_baja=NULL WHERE coe_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,coe.getCodigo());
            st.executeUpdate();
            return 1;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }

}
