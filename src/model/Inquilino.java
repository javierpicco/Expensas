/*
 * Inquilino.java
 *
 * Created on 12 de enero de 2008, 9:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.InquilinoDAO;
import java.util.List;

/**
 *
 * @author Javier
 */
public class Inquilino {
    
    /** Creates a new instance of Inquilino */
    public Inquilino() {
    }
    private long codigo;
    private String nombre;
    private long cuit;
    private String domicilio;
    private String localidad;
    private String cp;
    private Provincia provincia;
    private List unidades;

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

    public long getCuit() {
        return cuit;
    }

    public void setCuit(long cuit) {
        this.cuit = cuit;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public List getUnidades() {
        return unidades;
    }

    public void setUnidades(List unidades) {
        this.unidades = unidades;
    }
    
    public void guardar(){
        InquilinoDAO inqdao = new InquilinoDAO();
        inqdao.guardar(this);
    }
    
    public Inquilino buscarxCodigo(long codigo){
        InquilinoDAO inqdao = new InquilinoDAO();
        return inqdao.buscarxCodigo(codigo);
    }
     public List buscarxCriteria(String nombre,String cuit,long con_codigo,long uni_codigo){
        InquilinoDAO inqdao = new InquilinoDAO();
        return inqdao.buscarxCriteria(nombre,cuit,con_codigo,uni_codigo);
     }
    public int modificar(){
        InquilinoDAO inqdao = new InquilinoDAO();
        return inqdao.modificar(this);
    }
    public int eliminar(){
        InquilinoDAO inqdao = new InquilinoDAO();
        return inqdao.eliminar(this);
    }
    public int validaExiste(){
        InquilinoDAO inqdao = new InquilinoDAO();
        return inqdao.validaExiste(this);
    }
    
}
