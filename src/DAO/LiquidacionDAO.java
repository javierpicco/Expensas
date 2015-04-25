/*
 * LiquidacionDAO.java
 *
 * Created on 24 de enero de 2008, 18:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import model.DetalleLiquidacion;
import model.Liquidacion;

/**
 *
 * @author Javier
 */
public class LiquidacionDAO {
   
    private ConnectionSing cs;
    private Connection con;
    /** Creates a new instance of LiquidacionDAO */
    public LiquidacionDAO() {
        this.setCon(cs.getConection());
    }
    

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
     public Liquidacion guardar(Liquidacion liq){
         String mysql_var="insert into liquidacion(liq_nro_liquidacion, liq_fecha,liq_saldo,liq_uni_codigo) values(?, ?, ?, ?)";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,liq.getNumero_liquidacion());
            st.setDate(2,new java.sql.Date(liq.getFecha().getTime()));
            st.setDouble(3,liq.getSaldo());
            st.setInt(4,liq.getUnidad().getCodigo());
            st.executeUpdate();
            ResultSet rs= st.getGeneratedKeys();
            while (rs.next()){
                liq.setCodigo(rs.getInt(1));
            }
            if (liq.getCodigo()!=0){
                List detalles = liq.getDetalleLiquidacion();
                Iterator itDetalles = detalles.iterator();
                while (itDetalles.hasNext()){
                    DetalleLiquidacion dliq=new DetalleLiquidacion();
                    dliq=(DetalleLiquidacion)itDetalles.next();
                    dliq.setCodigo_liquidacion(liq.getCodigo());
                    dliq.guardar();
                    if (dliq.getCodigo()==0){
                        liq.setCodigo(0);
                        break;
                    }
                }
            }
            return liq;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
}
