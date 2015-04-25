/*
 * PropietarioDAO.java
 *
 * Created on 11 de enero de 2008, 20:31
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
import java.util.Iterator;
import java.util.List;
import model.Propietario;
import model.Provincia;
import model.Unidad;

/**
 *
 * @author Javier
 */
public class PropietarioDAO {
    
    /** Creates a new instance of PropietarioDAO */
    public PropietarioDAO() {
        this.setCon(cs.getConection());
    }
     private ConnectionSing cs;
    /** Creates a new instance of UnidadDAO */
    
    private Connection con;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    public Propietario guardar(Propietario prop){
        String mysql_var="insert into propietario (prop_nombre,prop_domicilio,prop_telefono,prop_localidad,prop_cp,prop_cuit,prop_prov_codigo) values(?,?,?,?,?,?,?)";
        PreparedStatement st;
        try {
            con.setAutoCommit(false);
            st = con.prepareStatement(mysql_var);
            st.setString(1,prop.getNombre());
            st.setString(2,prop.getDomicilio());
            st.setString(3,prop.getTelefono());
            st.setString(4,prop.getLocalidad());
            st.setString(5,prop.getCp());
            st.setLong(6,prop.getCuit());
            st.setInt(7,prop.getProvincia().getCodigo());
            st.executeUpdate();
            ResultSet rs= st.getGeneratedKeys();
            while (rs.next()){
                prop.setCodigo(rs.getInt(1));
            }
            Iterator it;
            it=prop.getUnidades().iterator();
            while (it.hasNext()){
                 Unidad unid=new Unidad();
                 unid = (Unidad)it.next();
                 mysql_var="UPDATE unidad SET uni_prop_codigo=? WHERE uni_codigo=?";
                 PreparedStatement st1;
                 st = con.prepareStatement(mysql_var);
                 st.setLong(1,prop.getCodigo());
                 st.setInt(2,unid.getCodigo());
                 st.executeUpdate();

            }
            con.commit();
            con.setAutoCommit(true);
            return prop;
        }
        catch (SQLException ex) {
              ex.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex2) {
                ex2.printStackTrace();
            }
              return null;
        }   
    }
    
    public Propietario buscarxcodigo(long codigo){
        String mysql_var="select * from propietario where prop_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,codigo);
            ResultSet rs= st.executeQuery();
            Propietario prop=new Propietario();
            while (rs.next()){
                prop.setCodigo(rs.getLong("prop_codigo"));
                prop.setCp(rs.getString("prop_cp"));
                prop.setCuit(rs.getLong("prop_cuit"));
                prop.setDomicilio(rs.getString("prop_domicilio"));
                prop.setLocalidad(rs.getString("prop_localidad"));
                prop.setTelefono(rs.getString("prop_telefono"));
                prop.setNombre(rs.getString("prop_nombre"));
            }
            return prop;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public List buscarxCriteria(String nombre,String cuit,long con_codigo,long uni_codigo){
        List resultados=new ArrayList();
        String mysql_var="SELECT DISTINCT(prop_codigo),prop.* FROM propietario prop, unidad uni,consorcio con WHERE uni.uni_prop_codigo=prop.prop_codigo AND uni.uni_con_codigo=con.con_codigo AND prop.prop_nombre like ? AND prop.prop_cuit like ?";
        if (con_codigo!=0){
            mysql_var=mysql_var+" AND uni.uni_con_codigo=?";
        }
        if (uni_codigo!=0){
            mysql_var=mysql_var+" AND uni.uni_codigo=?";
        }
        
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,"%"+nombre+"%");
            st.setString(2,"%"+cuit+"%");
            if (con_codigo!=0){
                st.setLong(3,con_codigo);
            }
            if (uni_codigo!=0){
                st.setLong(4,uni_codigo);
            }
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Propietario prop=new Propietario();
                prop.setCodigo(rs.getLong("prop_codigo"));
                prop.setCp(rs.getString("prop_cp"));
                prop.setCuit(rs.getLong("prop_cuit"));
                prop.setDomicilio(rs.getString("prop_domicilio"));
                prop.setLocalidad(rs.getString("prop_localidad"));
                prop.setTelefono(rs.getString("prop_telefono"));
                prop.setNombre(rs.getString("prop_nombre"));
                Unidad uni = new Unidad();
                prop.setUnidades(uni.buscarxPropietario(prop));
                Provincia prov=new Provincia();
                prop.setProvincia((Provincia)prov.buscar().get(0));
                resultados.add(prop);
            }
            return resultados;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public int modificar(Propietario prop){
        String mysql_var="UPDATE propietario SET prop_nombre=?,prop_domicilio=?,prop_telefono=?,prop_localidad=?,prop_cp=?,prop_cuit=?,prop_prov_codigo=? WHERE prop_codigo=?";
        PreparedStatement st;
        boolean success;
        try {
            this.getCon().setAutoCommit(false);
            st = con.prepareStatement(mysql_var);
            st.setString(1,prop.getNombre());
            st.setString(2,prop.getDomicilio());
            st.setString(3,prop.getTelefono());
            st.setString(4,prop.getLocalidad());
            st.setString(5,prop.getCp());
            st.setLong(6,prop.getCuit());
            st.setInt(7,prop.getProvincia().getCodigo());
            st.setLong(8,prop.getCodigo());
            st.executeUpdate();
            mysql_var="UPDATE unidad SET uni_prop_codigo=NULL WHERE uni_prop_codigo=?";
            st = con.prepareStatement(mysql_var);
            st.setLong(1,prop.getCodigo());
            st.executeUpdate();
            StringBuffer mysql_var_buffer=new StringBuffer("UPDATE unidad SET uni_prop_codigo=? WHERE uni_codigo IN (");
            Iterator it=prop.getUnidades().iterator();
            while(it.hasNext()){
                Unidad uni = (Unidad)it.next();
                mysql_var_buffer.append(uni.getCodigo());
                if(it.hasNext()){
                    mysql_var_buffer.append(",");
                }
                else{
                    mysql_var_buffer.append(")");
                }
            }
            System.out.print(mysql_var_buffer.toString());
            st = con.prepareStatement(mysql_var_buffer.toString());
            st.setLong(1,prop.getCodigo());
            st.executeUpdate();
            this.getCon().commit();
            this.getCon().setAutoCommit(true);
            return 1;
            
        }
        catch (SQLException ex) {
             ex.printStackTrace();
            try {
                this.getCon().rollback();
            } catch (SQLException ex2) {
                ex.printStackTrace();
            }
             return 0;
        }
    }
    public int eliminar(Propietario prop){
        String mysql_var="UPDATE unidad SET uni_prop_codigo=NULL WHERE uni_prop_codigo=?";           
        PreparedStatement st;
        boolean success;
        try {
            this.getCon().setAutoCommit(false);
            st = con.prepareStatement(mysql_var);
            st.setLong(1,prop.getCodigo());
            st.executeUpdate();
            mysql_var="DELETE FROM propietario WHERE prop_codigo=?";
            st = con.prepareStatement(mysql_var);
            st.setLong(1,prop.getCodigo());
            st.executeUpdate();
            this.getCon().commit();
            this.getCon().setAutoCommit(true);
            return 1;
            
        }
        catch (SQLException ex) {
             ex.printStackTrace();
            try {
                this.getCon().rollback();
            } catch (SQLException ex2) {
                ex.printStackTrace();
            }
             return 0;
        }
    }
    public int validaExiste(Propietario prop){
        List resultados=new ArrayList();
        String mysql_var="SELECT COUNT(1) FROM propietario WHERE  prop_nombre = ?";
        PreparedStatement st;
        int cantidad=0;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,prop.getNombre());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                cantidad=rs.getInt(1);
            }
            return cantidad;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }


    
}
