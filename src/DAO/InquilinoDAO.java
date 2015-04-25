/*
 * InquilinoDAO.java
 *
 * Created on 12 de enero de 2008, 9:53
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
import model.Inquilino;
import model.Provincia;
import model.Unidad;

/**
 *
 * @author Javier
 */
public class InquilinoDAO {
    
    /** Creates a new instance of InquilinoDAO */
    public InquilinoDAO() {
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
    
    public Inquilino buscarxCodigo(long codigo)
    {
        Inquilino inq=null;
        String mysql_var="SELECT * FROM inquilino WHERE inq_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,codigo);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                inq=new Inquilino();
                inq.setCodigo(rs.getLong("inq_codigo"));
                inq.setCp(rs.getString("inq_cp"));
                inq.setCuit(rs.getLong("inq_cuit"));
                inq.setDomicilio(rs.getString("inq_domicilio"));
                inq.setLocalidad(rs.getString("inq_localidad"));
                inq.setNombre(rs.getString("inq_nombre"));
                Provincia prov=new Provincia();
                inq.setProvincia((Provincia)prov.buscar().get(0));
            }
            return inq;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public Inquilino guardar(Inquilino inq){
        String mysql_var="insert into inquilino(inq_nombre,inq_cuit,inq_domicilio,inq_localidad,inq_cp,inq_prov_codigo) values	(?, ?, ?,?,?,?)";
        PreparedStatement st;
        try {
            con.setAutoCommit(false);
            st = con.prepareStatement(mysql_var);
            st.setString(1,inq.getNombre());
            st.setLong(2,inq.getCuit());
            st.setString(3,inq.getDomicilio());
            st.setString(4,inq.getLocalidad());
            st.setString(5,inq.getCp());
            st.setInt(6,inq.getProvincia().getCodigo());
            st.executeUpdate();
            ResultSet rs= st.getGeneratedKeys();
            while (rs.next()){
                inq.setCodigo(rs.getInt(1));
            }
            Iterator it;
            it=inq.getUnidades().iterator();
            while (it.hasNext()){
                 Unidad unid=new Unidad();
                 unid = (Unidad)it.next();
                 mysql_var="UPDATE unidad SET uni_inq_codigo=? WHERE uni_codigo=?";
                 PreparedStatement st1;
                 st = con.prepareStatement(mysql_var);
                 st.setLong(1,inq.getCodigo());
                 st.setInt(2,unid.getCodigo());
                 st.executeUpdate();
            }
            con.commit();
            con.setAutoCommit(true);
            return inq;
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
    
    public List buscarxCriteria(String nombre,String cuit,long con_codigo,long uni_codigo){
        List resultados=new ArrayList();
        String mysql_var="SELECT DISTINCT(inq_codigo),inq.* FROM inquilino inq, unidad uni,consorcio con WHERE uni.uni_inq_codigo=inq.inq_codigo AND uni.uni_con_codigo=con.con_codigo AND inq.inq_nombre like ? AND inq.inq_cuit like ?";
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
                Inquilino inq = new Inquilino();
                inq.setNombre(rs.getString("inq_nombre"));
                inq.setCodigo(rs.getLong("inq_codigo"));
                inq.setCp(rs.getString("inq_cp"));
                inq.setCuit(rs.getLong("inq_cuit"));
                inq.setDomicilio(rs.getString("inq_domicilio"));
                inq.setLocalidad(rs.getString("inq_localidad"));
                Provincia prov=new Provincia();
                inq.setProvincia((Provincia)prov.buscar().get(0));
                Unidad uni = new Unidad();
                inq.setUnidades(uni.buscarxInquilino(inq));
                resultados.add(inq);
            }
            return resultados;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public int modificar(Inquilino inq){
        String mysql_var="UPDATE inquilino SET inq_nombre=?,inq_domicilio=?,inq_cuit=?,inq_localidad=?,inq_cp=?,inq_prov_codigo=? WHERE inq_codigo=?";
        PreparedStatement st;
        boolean success;
        try {
            this.getCon().setAutoCommit(false);
            st = con.prepareStatement(mysql_var);
            st.setString(1,inq.getNombre());
            st.setString(2,inq.getDomicilio());
            st.setLong(3,inq.getCuit());
            st.setString(4,inq.getLocalidad());
            st.setString(5,inq.getCp());
            st.setInt(6,inq.getProvincia().getCodigo());
            st.setLong(7,inq.getCodigo());
            st.executeUpdate();
            mysql_var="UPDATE unidad SET uni_inq_codigo=NULL WHERE uni_inq_codigo=?";
            st = con.prepareStatement(mysql_var);
            st.setLong(1,inq.getCodigo());
            st.executeUpdate();
            StringBuffer mysql_var_buffer=new StringBuffer("UPDATE unidad SET uni_inq_codigo=? WHERE uni_codigo IN (");
            Iterator it=inq.getUnidades().iterator();
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
            st.setLong(1,inq.getCodigo());
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

    public int eliminar(Inquilino inq){
        String mysql_var="UPDATE unidad SET uni_inq_codigo=NULL WHERE uni_inq_codigo=?";        
        PreparedStatement st;
        boolean success;
        try {
            this.getCon().setAutoCommit(false);
            st = con.prepareStatement(mysql_var);
            st.setLong(1,inq.getCodigo());
            st.executeUpdate();
            mysql_var="DELETE FROM inquilino WHERE inq_codigo=?";
            st = con.prepareStatement(mysql_var);
            st.setLong(1,inq.getCodigo());
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

    public int validaExiste(Inquilino inq){
        List resultados=new ArrayList();
        String mysql_var="SELECT COUNT(1) FROM inquilino inq WHERE  inq.inq_nombre = ?";
        PreparedStatement st;
        int cantidad=0;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,inq.getNombre());
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
