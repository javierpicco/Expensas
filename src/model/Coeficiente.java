/*
 * Coeficiente.java
 *
 * Created on 12 de enero de 2008, 10:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.CoeficienteDAO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Javier
 */
public class Coeficiente {
    
    /** Creates a new instance of Coeficiente */
    public Coeficiente() {
    }
    private long codigo;
    private String denominacion;
    private boolean distribuible;
    private Date fechaBaja;
    
    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public boolean isDistribuible() {
        return distribuible;
    }

    public void setDistribuible(boolean distribuible) {
        this.distribuible = distribuible;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
    
    public void guardar(){
        CoeficienteDAO coedao=new CoeficienteDAO();
        coedao.guardar(this);
    }
    
    public List buscarActivos(){
        CoeficienteDAO coedao=new CoeficienteDAO();
        return coedao.buscarActivos();
    }
    public List buscarDistribuiblesActivos(){
        CoeficienteDAO coedao=new CoeficienteDAO();
        return coedao.buscarDistribuiblesActivos();
    }
    public Coeficiente buscarCodigo(long codigo){
        CoeficienteDAO coedao=new CoeficienteDAO();
        return coedao.buscarCodigo(codigo);
    }

    public List buscarxCriteria(String nombre){
        CoeficienteDAO coedao=new CoeficienteDAO();
        return coedao.buscarxCriteria(nombre);
    }
    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    public int eliminar(){
        CoeficienteDAO coedao=new CoeficienteDAO();
        return coedao.eliminar(this);
    }
    public int modificar(){
        CoeficienteDAO coedao=new CoeficienteDAO();
        return coedao.modificar(this);
    }
    public int activar(){
        CoeficienteDAO coedao=new CoeficienteDAO();
        return coedao.activar(this);
    }
}
