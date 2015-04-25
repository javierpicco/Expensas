/*
 * Propietario.java
 *
 * Created on 11 de enero de 2008, 20:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.PropietarioDAO;
import java.util.List;

/**
 *
 * @author Javier
 */
public class Propietario {
    
    /** Creates a new instance of Propietario */
    public Propietario() {
    }
    
    private long codigo;
    private String nombre;
    private String domicilio;
    private String telefono;
    private String localidad;
    private String cp;
    private long cuit;
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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public long getCuit() {
        return cuit;
    }

    public void setCuit(long cuit) {
        this.cuit = cuit;
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
        PropietarioDAO propdao=new PropietarioDAO();
        propdao.guardar(this);
    }
    
    public Propietario buscarxCodigo(long codigo){
        PropietarioDAO propdao=new PropietarioDAO();
        return propdao.buscarxcodigo(codigo);
    }
    public List buscarxCriteria(String nombre,String cuit,long con_codigo,long uni_codigo){
        PropietarioDAO propdao=new PropietarioDAO();
        return propdao.buscarxCriteria(nombre,cuit,con_codigo,uni_codigo);
    }
    public int modificar(){
        PropietarioDAO propdao=new PropietarioDAO();
        return propdao.modificar(this);
    }
    public int eliminar(){
        PropietarioDAO propdao=new PropietarioDAO();
        return propdao.eliminar(this);
    }
    public int validaExiste(){
        PropietarioDAO propdao=new PropietarioDAO();
        return propdao.validaExiste(this);
    }

    
}
