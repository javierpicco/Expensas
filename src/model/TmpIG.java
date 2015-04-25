/*
 * TmpIG.java
 *
 * Created on 23 de marzo de 2008, 18:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.TmpIGDAO;
import Security.Usuario;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Javier
 */
public class TmpIG {
    
    /**
     * Creates a new instance of TmpIG
     */
    public TmpIG() {
    }
    private long codigo;
    private Date periodo;
    private Propietario propietario;
    private Usuario usuario;
    private List detalleTmpIG;
    private Double importeTotal;
    private Double importeAlquiler;
    private Double importeImpuesto;
    private Double importeIVA;
    private Double tasaImpuesto;
    private Double importeTotalSIva;
    private Double importeMinimo;
    private Double impuesto;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public Date getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List getDetalleTmpIG() {
        return detalleTmpIG;
    }

    public void setDetalleTmpIG(List detalleTmpIG) {
        this.detalleTmpIG = detalleTmpIG;
    }

    public Double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(Double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Double getImporteAlquiler() {
        return importeAlquiler;
    }

    public void setImporteAlquiler(Double importeAlquiler) {
        this.importeAlquiler = importeAlquiler;
    }

    public Double getImporteImpuesto() {
        return importeImpuesto;
    }

    public void setImporteImpuesto(Double importeImpuesto) {
        this.importeImpuesto = importeImpuesto;
    }

    public Double getImporteIVA() {
        return importeIVA;
    }

    public void setImporteIVA(Double importeIVA) {
        this.importeIVA = importeIVA;
    }

    public Double getTasaImpuesto() {
        return tasaImpuesto;
    }

    public void setTasaImpuesto(Double tasaImpuesto) {
        this.tasaImpuesto = tasaImpuesto;
    }

    public Double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Double impuesto) {
        this.impuesto = impuesto;
    }
    public List agregar(){
        TmpIGDAO tpg=new TmpIGDAO();
        return tpg.agregar(this);
    }

    public Double getImporteTotalSIva() {
        return importeTotalSIva;
    }

    public void setImporteTotalSIva(Double importeTotalSIva) {
        this.importeTotalSIva = importeTotalSIva;
    }

    public Double getImporteMinimo() {
        return importeMinimo;
    }

    public void setImporteMinimo(Double importeMinimo) {
        this.importeMinimo = importeMinimo;
    }
    public void eliminar(List l){
        TmpIGDAO tpgDao=new TmpIGDAO();
        tpgDao.eliminar(l);
    }
    
}
