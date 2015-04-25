/*
 * TmpIGDAO.java
 *
 * Created on 23 de marzo de 2008, 18:15
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
import model.TmpIG;
import model.TmpIGDetails;
import model.Unidad;

/**
 *
 * @author Javier
 */
public class TmpIGDAO {
    
    /** Creates a new instance of TmpIGDAO */
    public TmpIGDAO() {
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
    public List agregar(TmpIG tmpIg){
        String mysql_var="insert into tmpig (tmpig_periodo, tmpig_propietario_nombre,tmpig_unidad,tmpig_importeUnidad, tmpig_importeTotal,tmpig_importeAlquileres, tmpig_importeImpuestos,tmpig_importeIVA,tmpig_tasaImpuesto,tmpig_Impuesto, tmpig_Usuario,tmpig_importeMinimo,tmpig_importeTotalSIVA) values (?, ?,?, ?, ?, ?,?,?,?,?,?,?,?)";
        PreparedStatement st;
        List valuesAdded = new ArrayList();
        try {
            Iterator it=tmpIg.getDetalleTmpIG().iterator();
            while(it.hasNext()){
                TmpIGDetails tid=(TmpIGDetails)it.next();
                st = con.prepareStatement(mysql_var);
                st.setDate(1,new java.sql.Date(tmpIg.getPeriodo().getTime()));
                st.setString(2,tmpIg.getPropietario().getNombre());
                st.setString(3,tid.getUnidad().getDescripcion());
                st.setDouble(4,tid.getImportexUnidad());
                st.setDouble(5,tmpIg.getImporteTotal());
                st.setDouble(6,tmpIg.getImporteAlquiler());
                st.setDouble(7,tmpIg.getImporteImpuesto());
                st.setDouble(8,tmpIg.getImporteIVA());
                st.setDouble(9,tmpIg.getTasaImpuesto());
                st.setDouble(10,tmpIg.getImpuesto());
                st.setLong(11,1);
                st.setDouble(12,tmpIg.getImporteMinimo());
                st.setDouble(13,tmpIg.getImporteTotalSIva());
                st.executeUpdate();
                ResultSet rs= st.getGeneratedKeys();
                while (rs.next()){
                    tmpIg.setCodigo(rs.getInt(1));
                    valuesAdded.add(tmpIg.getCodigo());
                }
            }
            return valuesAdded;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }     
    }   
    public void eliminar(List l){
        String mysql_var="delete from tmpig WHERE tmpig_codigo IN ";
        Iterator it = l.iterator();
        int x=0;
        while (it.hasNext()){
            Long codigo=(Long)it.next();    
            if (x==0){
                mysql_var=mysql_var+"("+codigo;    
            }
            else{
                mysql_var=mysql_var+","+codigo;    
            }
            x++;
        }
        mysql_var=mysql_var+")";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.executeUpdate();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
        }   
    }
}    
