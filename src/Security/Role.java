/*
 * Role.java
 *
 * Created on 1 de abril de 2008, 19:41
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
public class Role {
    
    private long id;
    private String name;
    /** Creates a new instance of Role */
    public Role() {
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
    public Role buscarxId(long id){
        RoleDAO rl=new RoleDAO();
        return rl.buscarxId(id);
    }
    public List buscarTodos(){
        RoleDAO rl=new RoleDAO();
        return rl.buscarTodos();
    }
    
}
