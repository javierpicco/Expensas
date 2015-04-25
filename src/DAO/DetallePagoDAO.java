/*
 * DetallePagoDAO.java
 *
 * Created on 13 de marzo de 2008, 19:36
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
import model.DetalleLiquidacion;
import model.DetallePago;
import model.Pago;

/**
 *
 * @author Javier
 */
public class DetallePagoDAO {

    private ConnectionSing cs;
    /** Creates a new instance of DetallePagoDAO */
    public DetallePagoDAO() {
        this.setCon(cs.getConection()); 
    }
    
    private Connection con;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    public List buscarDetallepagoxPago(Pago pago){
        List detallePagos=new ArrayList();
        StringBuffer mysql_var = new StringBuffer();
        mysql_var.append("SELECT * FROM detalleliquidacionxpago WHERE dliqxpago_pag_codigo=?");
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var.toString());
            st.setLong(1,pago.getCodigo());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                DetallePago dp=new DetallePago();
                dp.setImporte(rs.getDouble("dliqxpago_importe"));
                dp.setCodigo(rs.getLong("dliqxpago_codigo"));
                DetalleLiquidacion dl=new DetalleLiquidacion();
                dl=dl.buscarxId(rs.getLong("dliqxpago_detliq_codigo"));
                dp.setDetalleLiquidacion(dl);
                detallePagos.add(dp);
            }
            return detallePagos;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }

    
}