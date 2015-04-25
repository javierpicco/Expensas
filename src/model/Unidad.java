/*
 * Unidad.java
 *
 * Created on 11 de enero de 2008, 6:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.UnidadDAO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Javier
 */
public class Unidad {
    
    /** Creates a new instance of Unidad */
    public Unidad() {
    }
    
    private int codigo;
    private String descripcion;
    private Consorcio consorcio;
    private Date fechabaja;
    private List coeficientesAsignados;
    private Propietario propietario;
    private Inquilino inquilino;
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

    public Consorcio getConsorcio() {
        return consorcio;
    }

    public void setConsorcio(Consorcio consorcio) {
        this.consorcio = consorcio;
    }

    public Date getFechabaja() {
        return fechabaja;
    }

    public void setFechabaja(Date fechabaja) {
        this.fechabaja = fechabaja;
    }
    
    public void guardar(){
        UnidadDAO uniDao=new UnidadDAO();
        uniDao.guardar(this);
    }
    
    public List buscarxConsorcioNoAlocadas(Consorcio cons){
        UnidadDAO uniDao = new UnidadDAO();
        return uniDao.buscarxConsorcioNoAlocadas(cons);
    }
    
     public List buscarxConsorcioNoAlquiladas(Consorcio cons){
        UnidadDAO uniDao = new UnidadDAO();
        return uniDao.buscarxConsorcioNoAlquiladas(cons);
    }
    public boolean actualizarPropietario(long prop_codigo)
    {
        UnidadDAO uniDao = new UnidadDAO();
        if (uniDao.actualizarPropietario(prop_codigo,this)==null){
            return false;
        }
        else{
            return true;
        }
    }
    
     public List buscarxConsorcioActivas(Consorcio cons){
        UnidadDAO uniDao = new UnidadDAO();
        return uniDao.buscarxConsorcioActivas(cons);
    }
    

    public List getCoeficientesAsignados() {
        return coeficientesAsignados;
    }

    public void setCoeficientesAsignados(List coeficientesAsignados) {
        this.coeficientesAsignados = coeficientesAsignados;
    }
    
    public void popularCoeficientes(Coeficiente coe){
        UnidadDAO uniDao=new UnidadDAO();
        uniDao.popularCoeficientes(this,coe);
    }
    
    public void guardarCoeficientesAsignados(){
        UnidadDAO uniDao = new UnidadDAO();
        uniDao.guardarCoeficientesAsignados(this);
        
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
    
    public Unidad buscarxCodigo(long codigo){
        UnidadDAO uniDao = new UnidadDAO();
        return uniDao.buscarxCodigo(codigo);
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }
    
    public List buscarxCriteria(Consorcio cons,String unidad,String propietario, String inquilino){
        UnidadDAO uniDao = new UnidadDAO();
        return uniDao.buscarxCriteria(cons,unidad,propietario,inquilino);
    }
    
    public int modificar()
    {
        UnidadDAO uniDao = new UnidadDAO();
        return uniDao.modificar(this);
    }
    public int eliminar()
    {
        UnidadDAO uniDao = new UnidadDAO();
        return uniDao.eliminar(this);
    }
    
    public int activar()
    {
        UnidadDAO uniDao = new UnidadDAO();
        return uniDao.activar(this);
    }
    
    public List buscarxPropietario(Propietario prop){
        UnidadDAO uniDao = new UnidadDAO();
        return uniDao.buscarxPropietario(prop);
    }
    public List buscarxInquilino(Inquilino inq){
        UnidadDAO uniDao = new UnidadDAO();
        return uniDao.buscarxInquilino(inq);
    }
    
}
