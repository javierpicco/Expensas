/*
 * DetalleLiquidacionDAO.java
 *
 * Created on 24 de enero de 2008, 18:46
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Concepto;
import model.DetalleLiquidacion;
import model.Inquilino;
import model.Propietario;
import model.Unidad;

/**
 *
 * @author Javier
 */
public class DetalleLiquidacionDAO {
    
    private ConnectionSing cs;
    private Connection con;
    /** Creates a new instance of DetalleLiquidacionDAO */
    public DetalleLiquidacionDAO() {
        this.setCon(cs.getConection());
    }
    

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    public DetalleLiquidacion guardar(DetalleLiquidacion detLiq){
         String mysql_var="insert into detalle_liquidacion(detliq_liq_codigo, detliq_periodo,detliq_conc_codigo,detliq_fecha,detliq_importe_debe,detliq_importe_haber,detliq_saldo,detliq_descripcion,detliq_comprobante,detliq_periodoConcepto) values (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,detLiq.getCodigo_liquidacion());
            st.setDate(2, new java.sql.Date(detLiq.getPeriodo().getTime()));
            st.setLong(3,detLiq.getConcepto().getCodigo());
            st.setDate(4, new java.sql.Date(detLiq.getFecha().getTime()));
            st.setDouble(5,detLiq.getImporte_debe());
            st.setDouble(6,0);
            st.setDouble(7,detLiq.getSaldo());
            st.setString(8,detLiq.getDescripcion());
            st.setString(9,detLiq.getComprobante());
            st.setDate(10, new java.sql.Date(detLiq.getPeriodoConcepto().getTime()));
            st.executeUpdate();
            ResultSet rs= st.getGeneratedKeys();
            while (rs.next()){
                detLiq.setCodigo(rs.getInt(1));
            }
            return detLiq;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public int eliminar(DetalleLiquidacion detLiq){
         String mysql_var="DELETE FROM detalle_liquidacion WHERE detliq_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,detLiq.getCodigo());
            return st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }

    public List buscarSaldo(Unidad uni){
        List detalleLiquidacion=new ArrayList();
        StringBuffer mysql_var = new StringBuffer();
        mysql_var.append("SELECT detalle_liquidacion.* FROM detalle_liquidacion, liquidacion,concepto conc WHERE liq_codigo=detliq_liq_codigo AND conc_codigo=detliq_conc_codigo AND liq_uni_codigo=?  ORDER BY conc_prioridad,conc_origen,detliq_periodo");
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var.toString());
            st.setLong(1,uni.getCodigo());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                DetalleLiquidacion dl=new DetalleLiquidacion();
                dl.setCodigo(rs.getLong("detliq_codigo"));
                dl.setCodigo_liquidacion(rs.getLong("detliq_liq_codigo"));
                dl.setComprobante(rs.getString("detliq_comprobante"));
                Concepto conc=new Concepto();
                dl.setConcepto(conc.buscarxCodigo(rs.getLong("detliq_conc_codigo")));
                dl.setDescripcion(rs.getString("detliq_descripcion"));
                dl.setFecha(rs.getDate("detliq_fecha"));
                dl.setImporte_debe(rs.getDouble("detliq_importe_debe"));
                dl.setImporte_haber(rs.getDouble("detliq_importe_haber"));
                dl.setPeriodo(rs.getDate("detliq_periodo"));
                dl.setSaldo(rs.getDouble("detliq_saldo"));
                dl.setPeriodoConcepto(rs.getDate("detliq_periodoConcepto"));
                detalleLiquidacion.add(dl);
            }
            return detalleLiquidacion;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public DetalleLiquidacion buscarxId(long id){
        List detalleLiquidacion=new ArrayList();
        StringBuffer mysql_var = new StringBuffer();
        mysql_var.append("SELECT detalle_liquidacion.* FROM detalle_liquidacion WHERE detliq_codigo=?");
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var.toString());
            st.setLong(1,id);
            ResultSet rs= st.executeQuery();
            DetalleLiquidacion dl=new DetalleLiquidacion();
            while (rs.next()){
                dl.setCodigo(rs.getLong("detliq_codigo"));
                dl.setCodigo_liquidacion(rs.getLong("detliq_liq_codigo"));
                dl.setComprobante(rs.getString("detliq_comprobante"));
                Concepto conc=new Concepto();
                dl.setConcepto(conc.buscarxCodigo(rs.getLong("detliq_conc_codigo")));
                dl.setDescripcion(rs.getString("detliq_descripcion"));
                dl.setFecha(rs.getDate("detliq_fecha"));
                dl.setImporte_debe(rs.getDouble("detliq_importe_debe"));
                dl.setImporte_haber(rs.getDouble("detliq_importe_haber"));
                dl.setPeriodo(rs.getDate("detliq_periodo"));
                dl.setSaldo(rs.getDouble("detliq_saldo"));
                dl.setPeriodoConcepto(rs.getDate("detliq_periodoConcepto"));
            }
            return dl;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }

    public Unidad buscarUnidadxId(long detalleLiquidacionid){
        StringBuffer mysql_var = new StringBuffer();
        mysql_var.append("SELECT uni_codigo FROM detalle_liquidacion, unidad, liquidacion WHERE detliq_liq_codigo=liq_codigo AND liq_uni_codigo=uni_codigo  AND detliq_codigo=?");
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var.toString());
            st.setLong(1,detalleLiquidacionid);
            ResultSet rs= st.executeQuery();
            Unidad uni=new Unidad();
            while (rs.next()){
                uni = uni.buscarxCodigo(rs.getLong("uni_codigo"));
            }
            return uni;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public List buscarSaldoxCriteria(Unidad uni,Propietario prop,Inquilino inq, java.util.Date dateInicio,java.util.Date dateFin){
        List detalleLiquidacion=new ArrayList();
        StringBuffer mysql_var = new StringBuffer();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        mysql_var.append("SELECT detalle_liquidacion.* FROM detalle_liquidacion, liquidacion,concepto conc,unidad WHERE liq_codigo=detliq_liq_codigo AND conc_codigo=detliq_conc_codigo AND liq_uni_codigo=uni_codigo ");
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
            mysql_var.append(" AND detliq_periodo>='");
            mysql_var.append(sdf.format(dateInicio)+"'");            
            mysql_var.append(" AND detliq_periodo<='");
            mysql_var.append(sdf.format(dateFin)+"'");            
        }
        mysql_var.append(" ORDER BY conc_prioridad,conc_origen,detliq_periodo");
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var.toString());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                DetalleLiquidacion dl=new DetalleLiquidacion();
                dl.setCodigo(rs.getLong("detliq_codigo"));
                dl.setCodigo_liquidacion(rs.getLong("detliq_liq_codigo"));
                dl.setComprobante(rs.getString("detliq_comprobante"));
                Concepto conc=new Concepto();
                dl.setConcepto(conc.buscarxCodigo(rs.getLong("detliq_conc_codigo")));
                dl.setDescripcion(rs.getString("detliq_descripcion"));
                dl.setFecha(rs.getDate("detliq_fecha"));
                dl.setImporte_debe(rs.getDouble("detliq_importe_debe"));
                dl.setImporte_haber(rs.getDouble("detliq_importe_haber"));
                dl.setPeriodo(rs.getDate("detliq_periodo"));
                dl.setSaldo(rs.getDouble("detliq_saldo"));
                dl.setPeriodoConcepto(rs.getDate("detliq_periodoConcepto"));
                detalleLiquidacion.add(dl);
            }
            return detalleLiquidacion;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }


}
