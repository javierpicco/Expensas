/*
 * Liquidacion.java
 *
 * Created on 22 de enero de 2008, 19:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.LiquidacionDAO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Javier
 */
public class Liquidacion {
    
    /** Creates a new instance of Liquidacion */
    public Liquidacion() {
    }
    
    private long codigo;
    private long numero_liquidacion;
    private Date fecha;
    private Double saldo;
    private Unidad unidad;
    private List detalleLiquidacion;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public long getNumero_liquidacion() {
        return numero_liquidacion;
    }

    public void setNumero_liquidacion(long numero_liquidacion) {
        this.numero_liquidacion = numero_liquidacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public List getDetalleLiquidacion() {
        return detalleLiquidacion;
    }

    public void setDetalleLiquidacion(List detalleLiquidacion) {
        this.detalleLiquidacion = detalleLiquidacion;
    }
    public void guardar(){
        LiquidacionDAO liqDao=new LiquidacionDAO();
        liqDao.guardar(this);
    }
    
}
