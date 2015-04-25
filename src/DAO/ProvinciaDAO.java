/*
 * ProvinciaDAO.java
 *
 * Created on 11 de enero de 2008, 18:18
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
import model.Provincia;

/**
 *
 * @author Javier
 */
public class ProvinciaDAO {
    
    /** Creates a new instance of ProvinciaDAO */
    public ProvinciaDAO() {
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
    
    public List buscar(){
        List prov_list=new ArrayList();
        String mysql_var="select * from provincia";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                Provincia prov=new Provincia();
                prov.setCodigo(rs.getInt("prov_codigo"));
                prov.setDescripcion(rs.getString("prov_descripcion"));
                prov_list.add(prov); 
            }
            return prov_list;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }

}
