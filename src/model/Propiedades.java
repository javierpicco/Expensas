/*
 * Propiedades.java
 *
 * Created on 24 de enero de 2008, 20:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.PropiedadesDAO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Javier
 */
public class Propiedades {
    
    /** Creates a new instance of Propiedades */
    public Propiedades() {
    }
    
    private long codigo;
    private String nombre;
    private String valor;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public Map buscarTodas(){
        PropiedadesDAO propDao=new PropiedadesDAO();
        return propDao.buscarTodas();
    }
    public List buscarTodasList(){
        PropiedadesDAO propDao=new PropiedadesDAO();
        return propDao.buscarTodasList();
    }
    public int modificar(){
        PropiedadesDAO propDao=new PropiedadesDAO();
        return propDao.modificar(this);
    }
        
    
}
