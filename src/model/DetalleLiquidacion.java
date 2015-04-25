/*
 * DetalleLiquidacion.java
 *
 * Created on 22 de enero de 2008, 19:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.DetalleLiquidacionDAO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Javier
 */
public class DetalleLiquidacion extends CtaCteItem{
    
    /** Creates a new instance of DetalleLiquidacion */
    public DetalleLiquidacion() {
    }
    
    private long codigo;
    private long codigo_liquidacion;
    private Date periodo;
    private Concepto concepto;
    private Date fecha;
    private Double importe_debe;
    private Double importe_haber;
    private Double saldo;
    private String descripcion;
    private String comprobante;
    private Date periodoConcepto;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public long getCodigo_liquidacion() {
        return codigo_liquidacion;
    }

    public void setCodigo_liquidacion(long codigo_liquidacion) {
        this.codigo_liquidacion = codigo_liquidacion;
    }

    public Date getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getImporte_debe() {
        return importe_debe;
    }

    public void setImporte_debe(Double importe_debe) {
        this.importe_debe = importe_debe;
    }

    public Double getImporte_haber() {
        return importe_haber;
    }

    public void setImporte_haber(Double importe_haber) {
        this.importe_haber = importe_haber;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }
    public void guardar(){
        DetalleLiquidacionDAO detLiq=new DetalleLiquidacionDAO();
        detLiq.guardar(this);
    }
    public List buscarSaldo(Unidad uni){
        DetalleLiquidacionDAO detLiq=new DetalleLiquidacionDAO();
        return detLiq.buscarSaldo(uni);
    }
    public List buscarSaldoxCriteria(Unidad uni,Propietario prop,Inquilino inq, Date dateInicio,Date dateFin){
        DetalleLiquidacionDAO detLiq=new DetalleLiquidacionDAO();
        return detLiq.buscarSaldoxCriteria(uni,prop,inq, dateInicio, dateFin);        
    }
    public Unidad buscarUnidadxId(long detalleLiquidacionid){
        DetalleLiquidacionDAO detLiq=new DetalleLiquidacionDAO();
        return detLiq.buscarUnidadxId(detalleLiquidacionid);
    }
    public DetalleLiquidacion buscarxId(long id){
        DetalleLiquidacionDAO detLiq=new DetalleLiquidacionDAO();
        return detLiq.buscarxId(id);
    }
    
    public int eliminar(){
        DetalleLiquidacionDAO detLiq=new DetalleLiquidacionDAO();
        return detLiq.eliminar(this);
     }

    public Date getPeriodoConcepto() {
        return periodoConcepto;
    }

    public void setPeriodoConcepto(Date periodoConcepto) {
        this.periodoConcepto = periodoConcepto;
    }
    
}
