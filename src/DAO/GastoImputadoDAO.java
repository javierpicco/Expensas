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
import java.util.List;
import model.Coeficiente;
import model.Concepto;
import model.Consorcio;
import model.GastoImputado;
import model.Unidad;

/**
 *
 * @author Javier
 */
public class GastoImputadoDAO {
    

    private ConnectionSing cs;
    /** Creates a new instance of GastoImputadoDAO */
    public GastoImputadoDAO() {
        this.setCon(cs.getConection()); 
    }
    
    private Connection con;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    public void guardar(GastoImputado gasto){
        String mysql_var="insert into gastosimputados(gas_fecha, gas_periodo,gas_con_codigo,gas_uni_codigo,gas_conc_codigo,gas_descripcion,gas_importe,gas_comprobante,gas_periodoConcepto) values (?,?,?,?,?,?,?,?,?)";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            
            st.setDate(1,new java.sql.Date(gasto.getFecha().getTime()));
            st.setDate(2,new java.sql.Date(gasto.getPeriodo().getTime()));
            st.setInt(3,gasto.getConsorcio().getCodigo());
            if (gasto.getUnidad()!=null){
                st.setLong(4,gasto.getUnidad().getCodigo());
            }
            else{
                st.setLong(4,0);
            }
            st.setLong(5,gasto.getConcepto().getCodigo());
            st.setString(6,gasto.getDescripcion());
            st.setDouble(7,gasto.getImporte());
            st.setString(8,gasto.getComprobante());
            st.setDate(9,new java.sql.Date(gasto.getPeriodoGasto().getTime()));
            st.executeUpdate();
            ResultSet rs= st.getGeneratedKeys();
            while (rs.next()){
                gasto.setCodigo(rs.getInt(1));
            }
         }
        catch (SQLException ex) {
             ex.printStackTrace();
         }
    }
    public int transferir(GastoImputado gasto){
        String mysql_var="UPDATE gastosimputados SET gas_periodo=?,gas_con_codigo=?,gas_uni_codigo=?,gas_descripcion=?,gas_importe=?,gas_periodoConcepto=? WHERE gas_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);            
            st.setDate(1,new java.sql.Date(gasto.getPeriodo().getTime()));
            st.setInt(2,gasto.getConsorcio().getCodigo());
            if (gasto.getUnidad()!=null){
                st.setLong(3,gasto.getUnidad().getCodigo());
            }
            else{
                st.setLong(3,0);
            }
            st.setString(4,gasto.getDescripcion());
            st.setDouble(5,gasto.getImporte());
            st.setDate(6,new java.sql.Date(gasto.getPeriodoGasto().getTime()));
            st.setLong(7,gasto.getCodigo());

            return st.executeUpdate();
         }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
         }
    }
    public int eliminar(long consorcio_id,java.util.Date periodo){
        String mysql_var="DELETE FROM gastosimputados WHERE gas_con_codigo=? AND gas_periodo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);            
            st.setLong(1,consorcio_id);
            st.setDate(2,new java.sql.Date(periodo.getTime()));
            return st.executeUpdate();
         }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
         }
    }
    
     public List buscar(Consorcio cons,Unidad uni, Concepto conc, java.util.Date periodo,Coeficiente coe){
        List gastosImputados=new ArrayList();
        StringBuffer mysql_var = new StringBuffer();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        mysql_var.append("SELECT DISTINCT (gi.gas_codigo),gi.* FROM gastosimputados gi,concepto conc,consorcio cons,coeficiente coe"+
        " WHERE gi.gas_con_codigo=cons.con_codigo" + 
        " AND gi.gas_conc_codigo=conc.conc_codigo" );
        if (cons!=null){
            mysql_var.append(" AND gi.gas_con_codigo="+cons.getCodigo());
        }
        if(uni!=null){
            mysql_var.append(" AND gi.gas_uni_codigo ="+uni.getCodigo());
        }
//        else{
//            if (cons!=null){
//                mysql_var.append(" AND gi.gas_uni_codigo = 0");
//            }
            
//        }
        if(periodo!=null){
           mysql_var.append(" AND gi.gas_periodo='"+sdf.format(periodo)+"'");
        }
        if(conc!=null){
            mysql_var.append(" AND gi.gas_conc_codigo="+conc.getCodigo());
        }
        if (coe!=null){
            mysql_var.append(" AND conc_coe_codigo="+coe.getCodigo());
        }
        System.out.print(mysql_var.toString());
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var.toString());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                GastoImputado gi=new GastoImputado();
                gi.setCodigo(rs.getLong("gas_codigo"));
                gi.setComprobante(rs.getString("gas_comprobante"));
                Concepto conce=new Concepto();
                gi.setConcepto(conce.buscarxCodigo(rs.getLong("gas_conc_codigo")));
                Consorcio conso=new Consorcio();
                gi.setConsorcio(conso.buscarxCodigo(rs.getLong("gas_con_codigo")));
                gi.setDescripcion(rs.getString("gas_descripcion"));
                gi.setFecha(rs.getDate("gas_fecha"));
                gi.setImporte(rs.getDouble("gas_importe"));
                gi.setPeriodo(rs.getDate("gas_periodo"));
                gi.setPeriodoGasto(rs.getDate("gas_periodoConcepto"));
                Unidad unid = new Unidad();
                gi.setUnidad(unid.buscarxCodigo(rs.getLong("gas_uni_codigo")));
                gastosImputados.add(gi);
            }
            return gastosImputados;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public int eliminar(GastoImputado gi){
        String mysql_var="DELETE FROM gastosimputados WHERE gas_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);            
            st.setLong(1,gi.getCodigo());
            return st.executeUpdate();
         }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
         }
    }
 
     
}
