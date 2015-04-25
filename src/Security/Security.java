/*
 * Security.java
 *
 * Created on 1 de abril de 2008, 19:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Security;

import java.util.Map;

/**
 *
 * @author Javier
 */
public interface Security {
    
    abstract public Map getObjectsAffected();
    abstract public String getUIName();
   
}
