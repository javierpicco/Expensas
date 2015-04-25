/*
 * Concepto.java
 *
 * Created on 12 de enero de 2008, 11:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.ConceptoDAO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Javier
 */
public class Concepto {
    
    /** Creates a new instance of Concepto */
    public Concepto() {
    }
    
    private long codigo;
    private String nombre;
    private int prioridad;
    private String origen;
    private boolean descripcion;
    private boolean ig;
    private boolean iva;
    private Date fechaBaja;
    private TipoConcepto tipoConcepto=new TipoConcepto();
    
    private Coeficiente coeficiente;

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

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public boolean isDescripcion() {
        return descripcion;
    }

    public void setDescripcion(boolean descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isIg() {
        return ig;
    }

    public void setIg(boolean ig) {
        this.ig = ig;
    }

    public boolean isIva() {
        return iva;
    }

    public void setIva(boolean iva) {
        this.iva = iva;
    }

    public Coeficiente getCoeficiente() {
        return coeficiente;
    }

    public void setCoeficiente(Coeficiente coeficiente) {
        this.coeficiente = coeficiente;
    }
    
    public void guardar(){
        ConceptoDAO concdao = new ConceptoDAO();
        concdao.guardar(this);
    }
    
    public List buscarVigentesxTipo(boolean distribuible,String origen){
        ConceptoDAO concdao = new ConceptoDAO();
        return concdao.buscarVigentesxTipo(distribuible,origen);
    }
    public List buscarVigentesxTipoSinExpensa(boolean distribuible,String origen){
        ConceptoDAO concdao = new ConceptoDAO();
        return concdao.buscarVigentesxTipoSinExpensa(distribuible,origen);
    }
    
    public List buscarVigentes(String origen){
        ConceptoDAO concdao = new ConceptoDAO();
        return concdao.buscarVigentes(origen);
    }
    public Concepto buscarxCodigo(long codigo){
        ConceptoDAO concdao = new ConceptoDAO();
        return concdao.buscarxCodigo(codigo);
    }
    public Concepto buscarExpensa(){
        ConceptoDAO concdao = new ConceptoDAO();
        return concdao.buscarExpensa();
    }
    public List buscarxCriteria(String nombre,String origen,int prioridad, Coeficiente coe){
        ConceptoDAO concdao = new ConceptoDAO();
        return concdao.buscarxCriteria(nombre,origen,prioridad,coe);
    }
    public int modificar(){
        ConceptoDAO concdao = new ConceptoDAO();
        return concdao.modificar(this);
    }
    
    public int eliminar(){
        ConceptoDAO concdao = new ConceptoDAO();
        return concdao.eliminar(this);
    }
    
    public int activar(){
        ConceptoDAO concdao = new ConceptoDAO();
        return concdao.activar(this);
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public TipoConcepto getTipoConcepto() {
        return tipoConcepto;
    }

    public void setTipoConcepto(TipoConcepto tipoConcepto) {
        this.tipoConcepto = tipoConcepto;
    }

}
