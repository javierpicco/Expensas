/*
 * TipoConceptoDAO.java
 *
 * Created on 15 de marzo de 2008, 9:32
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
import model.TipoConcepto;

/**
 *
 * @author Javier
 */
public class TipoConceptoDAO {
    
    /** Creates a new instance of TipoConceptoDAO */
    public TipoConceptoDAO() {
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
    
    public List buscarTodos(){
        String mysql_var=null;
        mysql_var="SELECT * FROM tipoconcepto";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            ResultSet rs= st.executeQuery();
            List lst=new ArrayList();
            while (rs.next()){
                TipoConcepto tc=new TipoConcepto();
                tc.setCodigo(rs.getLong("tpo_codigo"));
                tc.setDescripcion(rs.getString("tpo_descripcion"));
                lst.add(tc);
            }
            return lst;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public TipoConcepto buscarxId(long codigo){
        String mysql_var=null;
        mysql_var="SELECT * FROM tipoconcepto WHERE tpo_codigo=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,codigo);
            ResultSet rs= st.executeQuery();
            TipoConcepto tc=new TipoConcepto();
            while (rs.next()){
                tc.setCodigo(rs.getLong("tpo_codigo"));
                tc.setDescripcion(rs.getString("tpo_descripcion"));
            }
            return tc;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }

    
}
