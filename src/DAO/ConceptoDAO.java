/*
 * ConceptoDAO.java
 *
 * Created on 12 de enero de 2008, 11:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package DAO;

import com.sun.crypto.provider.RSACipher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Coeficiente;
import model.Concepto;
import model.TipoConcepto;

/**
 *
 * @author Javier
 */
public class ConceptoDAO {
    
    private ConnectionSing cs;
    private Connection con;
    /** Creates a new instance of ConceptoDAO */
    public ConceptoDAO() {
        this.setCon(cs.getConection());
    }
    

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    
    public Concepto guardar(Concepto conc){
        String mysql_var="insert into concepto(conc_nombre,conc_prioridad,conc_origen,conc_coe_codigo,conc_descripcion,conc_ig,conc_iva,conc_tpo_codigo) values(?, ?, ?, ?, ?, ?, ?,?)";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,conc.getNombre());
            st.setInt(2,conc.getPrioridad());
            st.setString(3,conc.getOrigen());
            if (conc.getCoeficiente()!=null){
                st.setLong(4,conc.getCoeficiente().getCodigo());
            }
            else{
                st.setLong(4,0);
            }
            st.setBoolean(5,conc.isDescripcion());
            st.setBoolean(6,conc.isIg());
            st.setBoolean(7,conc.isIva());
            st.setLong(8,conc.getTipoConcepto().getCodigo());
            st.executeUpdate();
            ResultSet rs= st.getGeneratedKeys();
            while (rs.next()){
                conc.setCodigo(rs.getInt(1));
            }
            return conc;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public List buscarVigentesxTipo(boolean distribuible, String origen){
        List concList=new ArrayList();
        String mysql_var=null;
        if(origen.indexOf("Haber")!=-1){
            mysql_var="SELECT con.* FROM concepto con WHERE con.conc_fecha_fin is null AND con.conc_origen=? ORDER BY con.conc_nombre";
        }
        else{
            mysql_var="SELECT con.* FROM concepto con,coeficiente coe WHERE con.conc_coe_codigo=coe.coe_codigo AND con.conc_origen=? AND con.conc_fecha_fin is null AND coe.coe_distribuible=? ORDER BY con.conc_nombre";
        }
        
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,origen);
            if(origen.indexOf("Haber")==-1){
                st.setBoolean(2,distribuible);    
            }
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Concepto concep=new Concepto();
                concep.setCodigo(rs.getLong("conc_codigo"));
                concep.setDescripcion(rs.getBoolean("conc_descripcion"));
                concep.setIg(rs.getBoolean("conc_ig"));
                concep.setIva(rs.getBoolean("conc_iva"));
                concep.setNombre(rs.getString("conc_nombre"));
                concep.setOrigen(rs.getString("conc_origen"));
                concep.setPrioridad(rs.getInt("conc_prioridad"));
                if(origen.indexOf("Haber")==-1){
                    Coeficiente coef=new Coeficiente();
                    concep.setCoeficiente(coef.buscarCodigo(rs.getLong("conc_coe_codigo")));
                
                }
                TipoConcepto tc=new TipoConcepto();
                concep.setTipoConcepto(tc.buscarxId(rs.getLong("conc_tpo_codigo")));
                concList.add(concep);
            }
            return concList;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public List buscarVigentesxTipoSinExpensa(boolean distribuible, String origen){
        List concList=new ArrayList();
        String mysql_var=null;
        if(origen.indexOf("Haber")!=-1){
            mysql_var="SELECT con.* FROM concepto con WHERE con.conc_fecha_fin is null AND con.conc_origen=? ORDER BY con.conc_nombre";
        }
        else{
            mysql_var="SELECT con.* FROM concepto con,coeficiente coe WHERE con.conc_nombre!='Expensas' AND con.conc_coe_codigo=coe.coe_codigo AND con.conc_origen=? AND con.conc_fecha_fin is null AND coe.coe_distribuible=? ORDER BY con.conc_nombre";
        }
        
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,origen);
            if(origen.indexOf("Haber")==-1){
                st.setBoolean(2,distribuible);    
            }
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Concepto concep=new Concepto();
                concep.setCodigo(rs.getLong("conc_codigo"));
                concep.setDescripcion(rs.getBoolean("conc_descripcion"));
                concep.setIg(rs.getBoolean("conc_ig"));
                concep.setIva(rs.getBoolean("conc_iva"));
                concep.setNombre(rs.getString("conc_nombre"));
                concep.setOrigen(rs.getString("conc_origen"));
                concep.setPrioridad(rs.getInt("conc_prioridad"));
                if(origen.indexOf("Haber")==-1){
                    Coeficiente coef=new Coeficiente();
                    concep.setCoeficiente(coef.buscarCodigo(rs.getLong("conc_coe_codigo")));
                
                }
                TipoConcepto tc=new TipoConcepto();
                concep.setTipoConcepto(tc.buscarxId(rs.getLong("conc_tpo_codigo")));
                concList.add(concep);
            }
            return concList;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public List buscarVigentes(String origen){
        List concList=new ArrayList();
        String mysql_var=null;
        if(origen.indexOf("Haber")!=-1){
            mysql_var="SELECT con.* FROM concepto con WHERE con.conc_fecha_fin is null AND con.conc_origen=? ORDER BY con.conc_nombre";
        }
        else{
            mysql_var="SELECT con.* FROM concepto con,coeficiente coe WHERE con.conc_coe_codigo=coe.coe_codigo AND con.conc_origen=? AND con.conc_fecha_fin is null ORDER BY con.conc_nombre";
        }
        
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,origen);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Concepto concep=new Concepto();
                concep.setCodigo(rs.getLong("conc_codigo"));
                concep.setDescripcion(rs.getBoolean("conc_descripcion"));
                concep.setIg(rs.getBoolean("conc_ig"));
                concep.setIva(rs.getBoolean("conc_iva"));
                concep.setNombre(rs.getString("conc_nombre"));
                concep.setOrigen(rs.getString("conc_origen"));
                concep.setPrioridad(rs.getInt("conc_prioridad"));
                if(origen.indexOf("Haber")==-1){
                    Coeficiente coef=new Coeficiente();
                    concep.setCoeficiente(coef.buscarCodigo(rs.getLong("conc_coe_codigo")));
                
                }
                TipoConcepto tc=new TipoConcepto();
                concep.setTipoConcepto(tc.buscarxId(rs.getLong("conc_tpo_codigo")));
                concList.add(concep);
            }
            return concList;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public Concepto buscarxCodigo(long codigo){
        String mysql_var=null;
        Concepto concep=null;
        mysql_var="SELECT con.* FROM concepto con WHERE con.conc_codigo=? ORDER BY con.conc_nombre";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,codigo);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                concep=new Concepto();
                concep.setCodigo(rs.getLong("conc_codigo"));
                concep.setDescripcion(rs.getBoolean("conc_descripcion"));
                concep.setIg(rs.getBoolean("conc_ig"));
                concep.setIva(rs.getBoolean("conc_iva"));
                concep.setNombre(rs.getString("conc_nombre"));
                concep.setOrigen(rs.getString("conc_origen"));
                concep.setPrioridad(rs.getInt("conc_prioridad"));
                if(concep.getOrigen().indexOf("Haber")==-1){
                    Coeficiente coef=new Coeficiente();
                    concep.setCoeficiente(coef.buscarCodigo(rs.getLong("conc_coe_codigo")));
                
                }
                TipoConcepto tc=new TipoConcepto();
                concep.setTipoConcepto(tc.buscarxId(rs.getLong("conc_tpo_codigo")));
            }
            return concep;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
     public Concepto buscarExpensa(){
        String mysql_var=null;
        Concepto concep=null;
        mysql_var="SELECT con.* FROM concepto con WHERE con.conc_nombre=? ORDER BY con.conc_nombre";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,"Expensas");
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                concep=new Concepto();
                concep.setCodigo(rs.getLong("conc_codigo"));
                concep.setDescripcion(rs.getBoolean("conc_descripcion"));
                concep.setIg(rs.getBoolean("conc_ig"));
                concep.setIva(rs.getBoolean("conc_iva"));
                concep.setNombre(rs.getString("conc_nombre"));
                concep.setOrigen(rs.getString("conc_origen"));
                concep.setPrioridad(rs.getInt("conc_prioridad"));
                TipoConcepto tc=new TipoConcepto();
                concep.setTipoConcepto(tc.buscarxId(rs.getLong("conc_tpo_codigo")));
            }
            return concep;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
     }
     
    public List buscarxCriteria(String nombre,String origen,int prioridad, Coeficiente coe){
        List concList=new ArrayList();
        StringBuffer mysql_var=new StringBuffer("SELECT con.* FROM concepto con WHERE con.conc_nombre like ? AND con.conc_origen like ?");
        if (prioridad!=0){
            mysql_var.append(" AND conc_prioridad=?");
        }
        if(coe!=null){
            mysql_var.append(" AND conc_coe_codigo=?");
        }
        mysql_var.append(" ORDER BY con.conc_nombre");      
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var.toString());
            st.setString(1,"%"+nombre+"%");
            st.setString(2,"%"+origen+"%");
            int i=3;
            if (prioridad!=0){
                st.setInt(i,prioridad);
                i++;
            }
            if(coe!=null){
                st.setLong(i,coe.getCodigo());
            }
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Concepto concep=new Concepto();
                concep.setCodigo(rs.getLong("conc_codigo"));
                concep.setDescripcion(rs.getBoolean("conc_descripcion"));
                concep.setIg(rs.getBoolean("conc_ig"));
                concep.setIva(rs.getBoolean("conc_iva"));
                concep.setNombre(rs.getString("conc_nombre"));
                concep.setOrigen(rs.getString("conc_origen"));
                concep.setPrioridad(rs.getInt("conc_prioridad"));
                concep.setFechaBaja(rs.getDate("conc_fecha_fin"));
                if(origen.indexOf("Haber")==-1){
                    Coeficiente coef=new Coeficiente();
                    concep.setCoeficiente(coef.buscarCodigo(rs.getLong("conc_coe_codigo")));
                
                }
                TipoConcepto tc=new TipoConcepto();
                concep.setTipoConcepto(tc.buscarxId(rs.getLong("conc_tpo_codigo")));
                concList.add(concep);
            }
            return concList;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public int modificar(Concepto conc){
        String mysql_var="UPDATE concepto SET conc_nombre=?,conc_prioridad=?,conc_origen=?,conc_coe_codigo=?,conc_descripcion=?,conc_ig=?,conc_iva=?,conc_tpo_codigo=? WHERE conc_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,conc.getNombre());
            st.setInt(2,conc.getPrioridad());
            st.setString(3,conc.getOrigen());
            if (conc.getCoeficiente()==null){
                st.setNull(4,java.sql.Types.INTEGER);
            }
            else{
                st.setLong(4,conc.getCoeficiente().getCodigo());
            }
            st.setBoolean(5,conc.isDescripcion());
            st.setBoolean(6,conc.isIg());
            st.setBoolean(7,conc.isIva());
            st.setLong(8,conc.getTipoConcepto().getCodigo());
            st.setLong(9,conc.getCodigo());
            return st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }
    
    public int eliminar(Concepto conc){
        String mysql_var="UPDATE concepto SET conc_fecha_fin=now() WHERE conc_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,conc.getCodigo());
            return st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }
    
    public int activar(Concepto conc){
        String mysql_var="UPDATE concepto SET conc_fecha_fin=NULL WHERE conc_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,conc.getCodigo());
            return st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }


}
