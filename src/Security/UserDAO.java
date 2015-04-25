/*
 * User.java
 *
 * Created on 1 de abril de 2008, 19:40
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
public class UserDAO {
    
   private ConnectionSing cs;
   private Connection con;
    /** Creates a new instance of ConceptoDAO */
    public UserDAO() {
        this.setCon(cs.getConection());
    }
    

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    public User validarUser(String nombre,String password){
        String mysql_var=null;
        mysql_var="SELECT * FROM user WHERE usr_name=? AND usr_pass=SHA(?)";
        User usr=null;
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,nombre);
            st.setString(2,password);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                usr=new User();
                usr.setId(rs.getLong("usr_id"));
                usr.setName(rs.getString("usr_name"));
                usr.setPassword(rs.getString("usr_pass"));
                Role rol=new Role();
                usr.setRole(rol.buscarxId(rs.getLong("usr_role")));
            }
            return usr;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public List buscarTodos(){
        String mysql_var=null;
        mysql_var="SELECT * FROM user";
        User usr=null;
        List lst=new ArrayList();
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                usr=new User();
                usr.setId(rs.getLong("usr_id"));
                usr.setName(rs.getString("usr_name"));
                usr.setPassword(rs.getString("usr_pass"));
                Role rol=new Role();
                usr.setRole(rol.buscarxId(rs.getLong("usr_role")));
                lst.add(usr);
            }
            return lst;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    
    public User guardar(User usr){
        String mysql_var="insert into user(usr_name,usr_pass, usr_role) values(?, sha(?), ?)";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,usr.getName());
            st.setString(2,usr.getPassword());
            st.setLong(3,usr.getRole().getId());
            st.executeUpdate();
            ResultSet rs= st.getGeneratedKeys();
            while (rs.next()){
                usr.setId(rs.getInt(1));
            }
            return usr;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return null;
        }
    }
    public boolean cambiarPass(User usr){
        String mysql_var="UPDATE user SET usr_pass=SHA(?) WHERE usr_name=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setString(1,usr.getPassword());
            st.setString(2,usr.getName());
            st.executeUpdate();
            return true;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return false;
        }
    }
    public boolean eliminar(User usr){
        String mysql_var="DELETE FROM user WHERE usr_id=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(mysql_var);
            st.setLong(1,usr.getId());
            st.executeUpdate();
            return true;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return false;
        }
    }
     public int getCantidadAdministradores(){
        String mysql_var="SELECT COUNT(1) as cantidad FROM user,role WHERE rle_id=usr_role AND rle_name='Administrador'";
        PreparedStatement st;
        int cantidad=0;
        try {
            st = con.prepareStatement(mysql_var);
            ResultSet rs= st.executeQuery();
            while (rs.next()){
                cantidad=rs.getInt("cantidad");
            }
            return cantidad;
        }
        catch (SQLException ex) {
             ex.printStackTrace();
             return 0;
        }
    }

}
