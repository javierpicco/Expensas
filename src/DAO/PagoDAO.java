/*
 * GastoImputadoDAO.java
 *
 * Created on 16 de enero de 2008, 20:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Concepto;
import model.Consorcio;
import model.DetallePago;
import model.GastoImputado;
import model.Inquilino;
import model.Pago;
import model.Propietario;
import model.Unidad;

/**
 *
 * @author Javier
 */
public class PagoDAO {
    

    private ConnectionSing cs;
    /** Creates a new instance of GastoImputadoDAO */
    public PagoDAO() {
        this.setCon(cs.getConection()); 
    }
    
    private Connection con;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    public boolean guardar(Pago pago){
        String mysql_var="insert into pago (pag_conc_codigo,pag_fecha,pag_importe) values(?,?,?)";
        PreparedStatement st;
        try {
            con.setAutoCommit(false);
            st = con.prepareStatement(mysql_var);            
            st.setLong(1,pago.getConcepto().getCodigo());
            st.setDate(2,new java.sql.Date(pago.getFecha().getTime()));
            st.setDouble(3,pago.getImporte());
            st.executeUpdate();
            ResultSet rs= st.getGeneratedKeys();
            while (rs.next()){
                pago.setCodigo(rs.getInt(1));
            }
            Iterator it=pago.getDetallePago().iterator();
            while (it.hasNext()){
                mysql_var="insert into detalleliquidacionxpago(dliqxpago_pag_codigo,dliqxpago_detliq_codigo, dliqxpago_importe) values (?, ?, ?)";
                st = con.prepareStatement(mysql_var);
                DetallePago dp=(DetallePago)it.next();
                st.setLong(1,pago.getCodigo());
                st.setLong(2,dp.getDetalleLiquidacion().getCodigo());
                st.setDouble(3,dp.getImporte());
                st.executeUpdate();
                mysql_var="UPDATE detalle_liquidacion SET detliq_importe_haber=?, detliq_saldo=? WHERE  detliq_codigo=?";
                st = con.prepareStatement(mysql_var);
                st.setDouble(1,dp.getDetalleLiquidacion().getImporte_haber());
                st.setDouble(2,dp.getDetalleLiquidacion().getSaldo());
                st.setLong(3,dp.getDetalleLiquidacion().getCodigo());
                st.executeUpdate();
            }
            con.commit();
            con.setAutoCommit(true);
            return true;
         }
        catch (SQLException ex) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return false;
            } catch (SQLException ex2) {
                ex2.printStackTrace();
            }
             ex.printStackTrace();
         }
        return false;
    }
    
    public List buscarSaldo(Unidad uni){
        List detallePagos=new ArrayList();
        StringBuffer mysql_var = new StringBuffer();
        mysql_var.append("SELECT distinct(pag_codigo),pago.* FROM pago,detalleliquidacionxpago,detalle_liquidacion,liquidacion WHERE liq_codigo=detliq_liq_codigo AND detliq_codigo=dliqxpago_detliq_codigo AND dliqxpago_pag_codigo=pag_codigo AND liq_uni_codigo=? ORDER BY pag_fecha");
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var.toString());
            st.setLong(1,uni.getCodigo());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Pago pago=new Pago();
                pago.setCodigo(rs.getLong("pag_codigo"));
                Concepto conc=new Concepto();
                pago.setConcepto(conc.buscarxCodigo(rs.getLong("pag_conc_codigo")));
                pago.setFecha(rs.getDate("pag_fecha"));
                pago.setImporte(rs.getDouble("pag_importe"));
                detallePagos.add(pago);
            }
            return detallePagos;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public List buscarSaldoxCriteria(Unidad uni,Propietario prop,Inquilino inq, java.util.Date dateInicio,java.util.Date dateFin){
        List detallePagos=new ArrayList();
        StringBuffer mysql_var = new StringBuffer();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        mysql_var.append("SELECT distinct(pag_codigo),pago.* FROM pago,detalleliquidacionxpago,detalle_liquidacion,liquidacion, unidad WHERE liq_codigo=detliq_liq_codigo AND detliq_codigo=dliqxpago_detliq_codigo  AND dliqxpago_pag_codigo=pag_codigo AND liq_uni_codigo=uni_codigo");
        if (prop!=null){
            mysql_var.append(" AND uni_prop_codigo=");
            mysql_var.append(prop.getCodigo());
        }
        if (inq!=null){
            mysql_var.append(" AND uni_inq_codigo=");
            mysql_var.append(inq.getCodigo());
        }
        if(uni!=null){
            mysql_var.append(" AND uni_codigo=");
            mysql_var.append(uni.getCodigo());            
        }
        if(dateInicio!=null){
            mysql_var.append(" AND pag_fecha>='");
            mysql_var.append(sdf.format(dateInicio)+"'");            
            mysql_var.append(" AND pag_fecha<='");
            mysql_var.append(sdf.format(dateFin)+"'");            
        }
        mysql_var.append(" ORDER BY pag_fecha");
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var.toString());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Pago pago=new Pago();
                pago.setCodigo(rs.getLong("pag_codigo"));
                Concepto conc=new Concepto();
                pago.setConcepto(conc.buscarxCodigo(rs.getLong("pag_conc_codigo")));
                pago.setFecha(rs.getDate("pag_fecha"));
                pago.setImporte(rs.getDouble("pag_importe"));
                detallePagos.add(pago);
            }
            return detallePagos;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }

    public Unidad buscarUnidadxId(long pagoId){
        StringBuffer mysql_var = new StringBuffer();
        mysql_var.append("SELECT uni_codigo FROM pago,detalleliquidacionxpago,detalle_liquidacion,liquidacion, unidad WHERE liq_codigo=detliq_liq_codigo AND detliq_codigo=dliqxpago_detliq_codigo AND dliqxpago_pag_codigo=pag_codigo AND liq_uni_codigo=uni_codigo AND pag_codigo=?");
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var.toString());
            st.setLong(1,pagoId);
            ResultSet rs= st.executeQuery();
            Unidad uni=new Unidad();
            while (rs.next()){
                uni=uni.buscarxCodigo(rs.getLong("uni_codigo"));
            }
            return uni;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }

    public boolean eliminar(Pago pago){
        String mysql_var="UPDATE detalle_liquidacion SET detliq_importe_haber=detliq_importe_haber-?,detliq_saldo=detliq_saldo+? WHERE detliq_codigo=?";
        PreparedStatement st;
        try {
            con.setAutoCommit(false);
            Iterator itDetalles = pago.getDetallePago().iterator();
            while(itDetalles.hasNext()){
                DetallePago dp=(DetallePago)itDetalles.next();
                st = con.prepareStatement(mysql_var);            
                st.setDouble(1,dp.getImporte());
                st.setDouble(2,dp.getImporte());
                st.setLong(3,dp.getDetalleLiquidacion().getCodigo());
                st.executeUpdate();        
            }
            mysql_var="DELETE FROM detalleliquidacionxpago WHERE dliqxpago_pag_codigo=?";
            st = con.prepareStatement(mysql_var);            
            st.setDouble(1,pago.getCodigo());
            st.executeUpdate();        
            mysql_var="DELETE FROM pago WHERE pag_codigo=?";
            st = con.prepareStatement(mysql_var);            
            st.setDouble(1,pago.getCodigo());
            st.executeUpdate();        
            con.commit();   
            con.setAutoCommit(true);
            return true;
        }
        catch (SQLException ex) {
            try {
                con.rollback();
                con.setAutoCommit(true);
                return false;
            } catch (SQLException ex2) {
                ex2.printStackTrace();
            }
             ex.printStackTrace();
         }
        return false;
    }

}
