/*
 * GastoImputado.java
 *
 * Created on 16 de enero de 2008, 20:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.GastoImputadoDAO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Javier
 */
public class GastoImputado {
    
    /** Creates a new instance of GastoImputado */
    public GastoImputado() {
    }
    
    private long codigo;
    private Date fecha;
    private Date periodo;
    private Consorcio consorcio;
    private Unidad unidad;
    private Concepto concepto;
    private String descripcion;
    private Double importe;
    private String comprobante;
    private Date periodoGasto;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

    public Consorcio getConsorcio() {
        return consorcio;
    }

    public void setConsorcio(Consorcio consorcio) {
        this.consorcio = consorcio;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }
    
    public void guardar(){
        GastoImputadoDAO gidao = new GastoImputadoDAO();
        gidao.guardar(this);
    }
    
    public List buscar(Consorcio cons,Unidad uni, Concepto conc, Date periodo,Coeficiente coe){
        GastoImputadoDAO gidao = new GastoImputadoDAO();
        return gidao.buscar(cons,uni,conc,periodo,coe);
    }
    public int eliminar(long consorcio_id,Date periodo){
        GastoImputadoDAO gidao = new GastoImputadoDAO();
        return gidao.eliminar(consorcio_id,periodo);
    }
     public int eliminar(){
        GastoImputadoDAO gidao = new GastoImputadoDAO();
        return gidao.eliminar(this);
    }
    public int transferir(){
        GastoImputadoDAO gidao = new GastoImputadoDAO();
        return gidao.transferir(this);
    }

    public Date getPeriodoGasto() {
        return periodoGasto;
    }

    public void setPeriodoGasto(Date periodoGasto) {
        this.periodoGasto = periodoGasto;
    }
}
