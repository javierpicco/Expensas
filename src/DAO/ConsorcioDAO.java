/*
 * ConsorcioDAO.java
 *
 * Created on 8 de enero de 2008, 18:26
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.sql.*;
import model.Coeficiente;
import model.Consorcio;
import model.Liquidacion;


/**
 *
 * @author Javier
 */
public class ConsorcioDAO {
    
    private ConnectionSing cs;
    /** Creates a new instance of ConsorcioDAO */
    public ConsorcioDAO() {
        this.setCon(cs.getConection());
        
    }
    
    private Connection con;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    public Consorcio guardar(Consorcio cons){
        String mysql_var="insert into consorcio (con_denominacion, con_domicilio,con_barrio,con_cp,localidad, con_cuit) values(?,?,?,?,?,?)";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,cons.getDenominacion());
            st.setString(2,cons.getDomcilio());
            st.setString(3,cons.getBarrio());
            st.setString(4,cons.getCp());
            st.setString(5,cons.getLocalidad());
            st.setLong(6,cons.getCuit());
            
            st.executeUpdate();
            ResultSet rs= st.getGeneratedKeys();
            while (rs.next()){
                cons.setCodigo(rs.getInt(1));
            }
        mysql_var="INSERT INTO expensaconsorcionumeracion (expcon_con_codigo,expcon_ultimovalor) VALUES (?,?)";
        st = con.prepareStatement(mysql_var);
        st.setInt(1,cons.getCodigo());           
        st.setLong(2,0);
        st.executeUpdate();
        return cons;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public int modificar(Consorcio cons){
        String mysql_var="UPDATE consorcio SET con_denominacion=?,con_domicilio=?,con_barrio=?,con_cp=?,localidad=?,con_cuit=? WHERE con_codigo=?";
        PreparedStatement st;
        System.out.print(mysql_var);
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,cons.getDenominacion());
            st.setString(2,cons.getDomcilio());
            st.setString(3,cons.getBarrio());
            st.setString(4,cons.getCp());
            st.setString(5,cons.getLocalidad());
            st.setLong(6,cons.getCuit());
            st.setInt(7,cons.getCodigo());           
            return st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }
    
    public int eliminar(Consorcio cons){
        String mysql_var="UPDATE consorcio SET con_fecha_baja=now() WHERE con_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setInt(1,cons.getCodigo());           
            return st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }
    
    public int activar(Consorcio cons){
        String mysql_var="UPDATE consorcio SET con_fecha_baja=NULL WHERE con_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setInt(1,cons.getCodigo());           
            return st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }

    public List buscarActivos(){
        List cons_list=new ArrayList();
        String mysql_var="SELECT * FROM consorcio WHERE con_fecha_baja IS NULL ORDER BY con_denominacion";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Consorcio cons=new Consorcio();
                cons.setBarrio(rs.getString("con_barrio"));
                cons.setCodigo(rs.getInt("con_codigo"));
                cons.setCp(rs.getString("con_cp"));
                cons.setCuit(rs.getLong("con_cuit"));
                cons.setDenominacion(rs.getString("con_denominacion"));
                cons.setDomcilio(rs.getString("con_domicilio"));
                cons.setLocalidad(rs.getString("localidad"));
                cons.setUltima_expensa(this.getMaxExpensa(cons));
                cons_list.add(cons);                 
            }
            return cons_list;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public List buscarxDenominacion(String denominacion){
        List cons_list=new ArrayList();
        String mysql_var="SELECT * FROM consorcio WHERE con_denominacion like ? ORDER BY con_denominacion";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,"%"+denominacion+"%");
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Consorcio cons=new Consorcio();
                cons.setBarrio(rs.getString("con_barrio"));
                cons.setCodigo(rs.getInt("con_codigo"));
                cons.setCp(rs.getString("con_cp"));
                cons.setCuit(rs.getLong("con_cuit"));
                cons.setDenominacion(rs.getString("con_denominacion"));
                cons.setDomcilio(rs.getString("con_domicilio"));
                cons.setLocalidad(rs.getString("localidad"));
                cons.setFecha_baja(rs.getDate("con_fecha_baja"));
                cons.setUltima_expensa(this.getMaxExpensa(cons));
                cons_list.add(cons); 
            }
            return cons_list;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public Consorcio buscarxCodigo(long codigo){
        List cons_list=new ArrayList();
        String mysql_var="SELECT * FROM consorcio WHERE con_codigo=?";
        PreparedStatement st;
        Consorcio cons=null;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,codigo);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                cons=new Consorcio();
                cons.setBarrio(rs.getString("con_barrio"));
                cons.setCodigo(rs.getInt("con_codigo"));
                cons.setCp(rs.getString("con_cp"));
                cons.setCuit(rs.getLong("con_cuit"));
                cons.setDenominacion(rs.getString("con_denominacion"));
                cons.setDomcilio(rs.getString("con_domicilio"));
                cons.setLocalidad(rs.getString("localidad"));
                cons.setUltima_expensa(this.getMaxExpensa(cons));
            }
            return cons;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public Double getTotalByCoeficiente(Coeficiente coe,Consorcio cons,Date periodo){
        String mysql_var="SELECT SUM(gas_importe)" +
                         " FROM gastosimputados gi, concepto conc" +
                         " WHERE gi.gas_conc_codigo=conc.conc_codigo" +
                         " AND gas_periodo=?" +
                         " AND gas_con_codigo=?" + 
                         " AND conc.conc_coe_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setDate(1,new java.sql.Date(periodo.getTime()));
            st.setInt(2,cons.getCodigo());
            st.setLong(3,coe.getCodigo());
            ResultSet rs= st.executeQuery();
            rs.next();
            return rs.getDouble(1);
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }   
    }
    
    public List getCoeficientesDistribuibles(Consorcio cons){
        String mysql_var="SELECT DISTINCT(coef.coe_codigo)" +
                         " FROM coeficiente coef, coeficientexunidad coeuni, unidad uni" +
                         " WHERE coeuni.coeuni_coe_codigo=coef.coe_codigo" +
                         " AND coeuni.coeuni_uni_codigo=uni.uni_codigo" +
                         " AND uni.uni_con_codigo=?" +
                         " AND coef.coe_distribuible=1";

        PreparedStatement st;
        try {
            List listadoCoeficientes = new ArrayList();
            st = con.prepareStatement(mysql_var);
            st.setInt(1,cons.getCodigo());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Coeficiente coe=new Coeficiente();
                listadoCoeficientes.add(coe.buscarCodigo(rs.getInt("coe_codigo")));
            }
            
            return listadoCoeficientes;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }   
    }
    public boolean liquidar(List liquidaciones){
        try{
            
            this.getCon().setAutoCommit(false);
            boolean return_value=true;
            Iterator it=liquidaciones.iterator();
            while(it.hasNext()){
                Liquidacion liq=(Liquidacion)it.next();
                liq.guardar();
                if(liq.getCodigo()==0){
                    return_value=false;
                    break;
                }
                else{
                    return_value=true;
                }
            }
            if(return_value){
              con.commit();
            }
            else{
              con.rollback();
            }
            con.setAutoCommit(true);
            return return_value;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    
    }
    
    
    public boolean eliminarliquidacion(Date periodo,Consorcio cons){
        
      String mysql_var="DELETE FROM listadoliquidacionunidad" +
                       " WHERE listliquni_periodo=?" +
                       " AND listliquni_consorcio_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setDate(1,new java.sql.Date(periodo.getTime()));
            st.setInt(2,cons.getCodigo());
            st.executeUpdate();
            mysql_var="DELETE FROM listadoliquidacionconceptos" +
                      " WHERE liqlist_periodo=?"+
                      " AND liqlist_consorcio_codigo=?";
            st = con.prepareStatement(mysql_var);
            st.setDate(1,new java.sql.Date(periodo.getTime()));
            st.setInt(2,cons.getCodigo());
            st.executeUpdate();
            /*mysql_var="DELETE FROM gastosimputados"+
                      " WHERE gas_periodo=?"+
                      " AND gas_con_codigo=?";
            st = con.prepareStatement(mysql_var);
            st.setDate(1,new java.sql.Date(periodo.getTime()));
            st.setInt(2,cons.getCodigo());
            st.executeUpdate();*/
            return true;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return false;
        }                
    }    
    
    public boolean existeLiquidacion(Consorcio consor,Date periodo){
        boolean return_value=false;
        String mysql_var="SELECT COUNT(1) as cantidad FROM liquidacion,detalle_liquidacion, unidad WHERE detliq_liq_codigo=liq_codigo AND liq_uni_codigo=uni_codigo AND uni_con_codigo=? AND detliq_periodo=?";
        PreparedStatement st;
        int cantidad=0;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,consor.getCodigo());
            st.setDate(2,new java.sql.Date(periodo.getTime()));
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                cantidad=rs.getInt("cantidad");
            }
            if (cantidad==0){
                return_value= false;
            }
            else{
                return_value= true;
            }
            return return_value;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return false;
        }
    }
    public long getMaxExpensa(Consorcio consor){ 
        String mysql_var="SELECT expcon_ultimovalor FROM expensaconsorcionumeracion WHERE expcon_con_codigo=?";
        PreparedStatement st;
        long cantidad=0;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,consor.getCodigo());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                cantidad=rs.getInt("expcon_ultimovalor");
            }
            return cantidad;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }
    
    public int updateMaxExpensa(Consorcio cons,long value){
        String mysql_var="UPDATE expensaconsorcionumeracion SET expcon_ultimovalor=? WHERE expcon_con_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,value);           
            st.setInt(2,cons.getCodigo());
            return st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }

    public double getTotalExpensas(Consorcio consor){
        boolean return_value=false;
        String mysql_var="SELECT ifnull(SUM(detliq_saldo),0) as cantidad FROM detalle_liquidacion,concepto,liquidacion,unidad WHERE liq_codigo=detliq_liq_codigo AND conc_codigo=detliq_conc_codigo AND conc_nombre='Expensas' AND liq_uni_codigo=uni_codigo AND uni_con_codigo=?";
        PreparedStatement st;
        double cantidad=0;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,consor.getCodigo());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                cantidad=rs.getDouble("cantidad");
            }
            return cantidad;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }



}
