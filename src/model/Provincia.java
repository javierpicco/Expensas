/*
 * Provincia.java
 *
 * Created on 11 de enero de 2008, 18:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.ProvinciaDAO;
import java.util.List;

/**
 *
 * @author Javier
 */
public class Provincia {
    
    /** Creates a new instance of Provincia */
    public Provincia() {
    }
    
    private int codigo;
    private String descripcion;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public List buscar(){
        ProvinciaDAO pvdao=new ProvinciaDAO();
        return pvdao.buscar();
    }
    
}
