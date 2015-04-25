/*
 * InformeLiquidacionDAO.java
 *
 * Created on 29 de enero de 2008, 6:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author Javier
 */
public class InformeLiquidacionDAO {
    
    /** Creates a new instance of InformeLiquidacionDAO */
    public InformeLiquidacionDAO() {
        this.setCon(cs.getConection()); 
    }
    private ConnectionSing cs;
    private Connection con;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    public void guardarConceptos(Date periodo, String concepto,Double importe,int cons_codigo,String comprobante){
        String mysql_var="insert into listadoliquidacionconceptos (liqlist_periodo, liqlist_concepto,liqlist_importe,liqlist_consorcio_codigo,liqlist_comprobante) values(?,?, ?, ?,?)";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setDate(1,new java.sql.Date(periodo.getTime()));
            st.setString(2,concepto);
            st.setDouble(3,importe);
            st.setInt(4,cons_codigo);
            st.setString(5,comprobante);
            st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
        }

    }
    
    public void guardarDistribucion(Long unidad,Double importeA,Double importeB,Date periodo,Double porcentajeA,int cod_consorcio,Double importeD,Double importeE){
        String mysql_var="insert into listadoliquidacionunidad(listliquni_unidad, listliquni_coeficienteA_importe,listliquni_coeficienteB_importe,listliquni_periodo,listliquni_coeficienteA_importe_porcentaje,listliquni_consorcio_codigo,listliquni_coeficienteD_importe,listliquni_coeficienteE_importe) values (?, ?,?, ?, ?, ?,?,?)";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,unidad);
            st.setDouble(2,importeA);
            st.setDouble(3,importeB);
            st.setDate(4,new java.sql.Date(periodo.getTime()));
            st.setDouble(5,porcentajeA/100);
            st.setInt(6,cod_consorcio);
            st.setDouble(7,importeD);
            st.setDouble(8,importeE);
            st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
        }

    }


}
