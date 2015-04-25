/*
 * Role.java
 *
 * Created on 1 de abril de 2008, 19:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Security;

import DAO.ConnectionSing;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Javier
 */
public class RoleDAO {
   
    
    private ConnectionSing cs;
    private Connection con;
    /** Creates a new instance of ConceptoDAO */
    public RoleDAO() {
        this.setCon(cs.getConection());
    }
    

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    public Role buscarxId(long id){
        String mysql_var=null;
        mysql_var="SELECT * FROM role WHERE rle_id=?";
        Role role=null;
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,id);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                role=new Role();
                role.setId(rs.getLong("rle_id"));
                role.setName(rs.getString("rle_name"));
            }
            return role;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public List buscarTodos(){
        String mysql_var=null;
        mysql_var="SELECT * FROM role";
        List lst=new ArrayList();
        Role role=null;
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                role=new Role();
                role.setId(rs.getLong("rle_id"));
                role.setName(rs.getString("rle_name"));
                lst.add(role);
            }
            return lst;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
}
