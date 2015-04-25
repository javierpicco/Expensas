/*
 * SecurityManagerFwk.java
 *
 * Created on 1 de abril de 2008, 19:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Security;

import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JMenuItem;

/**
 *
 * @author Javier
 */
public class SecurityManagerFwk {
    
    /**
     * Creates a new instance of SecurityManagerFwk
     */
    public SecurityManagerFwk() {
    }
    
    public void validateSecurity(User usr,Security secObj){
        SecurityObject so=new SecurityObject();
        List<SecurityObject> lst=so.getObjectsByScreenRole(usr,secObj);
        Iterator<SecurityObject> it=lst.iterator();
        JButton jb=null;
        JMenuItem mit=null;
        while(it.hasNext()){
            jb=null;
            mit=null;
            SecurityObject secObj1=it.next();
            if (secObj1.getType().indexOf("JButton")!=-1){
                jb=(JButton)secObj.getObjectsAffected().get(secObj1.getName());               
            }
            if (secObj1.getType().indexOf("JMenuItem")!=-1){
                mit=(JMenuItem)secObj.getObjectsAffected().get(secObj1.getName());               
            }
            if (jb!=null && !secObj1.isVisible()){
                jb.setEnabled(false);
            }
            if (mit!=null && !secObj1.isVisible()){
                mit.setEnabled(false);
            }
        }
    }
    
}
