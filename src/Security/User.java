/*
 * User.java
 *
 * Created on 1 de abril de 2008, 19:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Security;

import java.util.List;

/**
 *
 * @author Javier
 */
public class User {
    
    private long id;
    private String name;
    private String password;
    private Role role;
    /** Creates a new instance of User */
    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public User validarUser(String nombre,String password){
        UserDAO usrdao=new UserDAO();
        return usrdao.validarUser(nombre,password);
    }
     public List buscarTodos(){
        UserDAO usrdao=new UserDAO();
        return usrdao.buscarTodos();
     }
     public User guardar(){
        UserDAO usrdao=new UserDAO();
        return usrdao.guardar(this);
     }
    public boolean cambiarPass(){
        UserDAO usrdao=new UserDAO();
        return usrdao.cambiarPass(this);
    }
    public boolean eliminar(){
        UserDAO usrdao=new UserDAO();
        return usrdao.eliminar(this);
    }
    
    public int getCantidadAdministradores(){
        UserDAO usrdao=new UserDAO();
        return usrdao.getCantidadAdministradores();
    }
}
