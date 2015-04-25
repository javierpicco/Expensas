/*
 * SecurityObject.java
 *
 * Created on 1 de abril de 2008, 19:45
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
public class SecurityObject {
    
    /** Creates a new instance of SecurityObject */
    public SecurityObject() {
    }
    private long id;
    private String name;
    private String uiName;
    private String type;
    private boolean visible;

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

    public String getUiName() {
        return uiName;
    }

    public void setUiName(String uiName) {
        this.uiName = uiName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public List<SecurityObject> getObjectsByScreenRole(User usr,Security sec){
        SecurityObjectDAO scDao=new SecurityObjectDAO();
        return scDao.getObjectsByScreenRole(usr,sec);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
