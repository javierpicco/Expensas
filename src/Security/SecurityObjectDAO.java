/*
 * SecurityObject.java
 *
 * Created on 1 de abril de 2008, 19:45
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
public class SecurityObjectDAO {
    
    /** Creates a new instance of SecurityObject */

   private ConnectionSing cs;
   private Connection con;
    /** Creates a new instance of ConceptoDAO */
    public SecurityObjectDAO() {
        this.setCon(cs.getConection());
    }
    

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    public List<SecurityObject> getObjectsByScreenRole(User usr,Security sec){
        String mysql_var=null;
        SecurityObject secObject=null;
        List lst=new ArrayList();
        mysql_var="SELECT objects.*,rlobj_visible FROM objects, roleobjects WHERE rlobj_obj_id=ojb_id AND rlobj_rle_id=? AND obj_ui_name=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,usr.getRole().getId());
            st.setString(2,sec.getUIName());
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                secObject= new SecurityObject();
                secObject.setId(rs.getLong("ojb_id"));
                secObject.setName(rs.getString("obj_name"));
                secObject.setType(rs.getString("obj_type"));
                secObject.setUiName(rs.getString("obj_ui_name"));
                secObject.setVisible(rs.getBoolean("rlobj_visible"));
                lst.add(secObject);
            }
            return lst;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
}
