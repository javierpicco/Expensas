/*
 * CtaCte.java
 *
 * Created on 11 de marzo de 2008, 6:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.math.BigDecimal;

/**
 *
 * @author Javier
 */
public class CtaCte{
    
    /** Creates a new instance of CtaCte */
    public CtaCte() {
    }
    private Unidad unidad;
    private Propietario propietario;
    private Inquilino inquilino;
    private Double saldo;
    private List items=new ArrayList();

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }
    
    public void cargarCta(Unidad uni){
        DetalleLiquidacion dl=new DetalleLiquidacion();
        List lstDetalle=dl.buscarSaldo(uni);
        Iterator it=lstDetalle.iterator();
        while (it.hasNext()){
            DetalleLiquidacion dli=new DetalleLiquidacion();
            dli=(DetalleLiquidacion)it.next();
            dli.setDebeCta(dli.getImporte_debe());
            dli.setComposicionCta(dli.getImporte_haber());
            dli.setFechaCta(dli.getPeriodo());
            this.getItems().add(dli);
        }
        Pago pago=new Pago();
        List lstPagos=pago.buscarSaldo(uni);
        Iterator itPagos=lstPagos.iterator();
        while (itPagos.hasNext()){
            Pago pagItem=new Pago();
            pagItem=(Pago)itPagos.next();
            pagItem.setFechaCta(pagItem.getFecha());
            pagItem.setHaberCta(pagItem.getImporte());
            this.getItems().add(pagItem);
        }
        Collections.sort(this.getItems());
        Iterator it2=this.getItems().iterator();
        BigDecimal saldo=new BigDecimal(0d);
        while(it2.hasNext()){
            CtaCteItem ctaItem=(CtaCteItem)it2.next();
            if (ctaItem instanceof DetalleLiquidacion){
                saldo=saldo.add(new BigDecimal(ctaItem.getDebeCta()));
                ctaItem.setSaldoCta(saldo.doubleValue());
            }
            else{
                saldo=saldo.subtract(new BigDecimal(ctaItem.getHaberCta()));
                ctaItem.setSaldoCta(saldo.doubleValue());
            }
        }
        this.setSaldo(saldo.doubleValue());
        this.setUnidad(uni);
    }

    public void cargarCta(Unidad uni,Propietario prop, Inquilino inq, Date fechaInicio,Date fechaFin){
        DetalleLiquidacion dl=new DetalleLiquidacion();
        List lstDetalle=dl.buscarSaldoxCriteria(uni,prop,inq,fechaInicio,fechaFin);
        Iterator it=lstDetalle.iterator();
        while (it.hasNext()){
            DetalleLiquidacion dli=new DetalleLiquidacion();
            dli=(DetalleLiquidacion)it.next();
            dli.setDebeCta(dli.getImporte_debe());
            dli.setComposicionCta(dli.getImporte_haber());
            dli.setFechaCta(dli.getPeriodo());
            dli.setUnidadItem(dli.buscarUnidadxId(dli.getCodigo()));
            this.getItems().add(dli);
        }
        Pago pago=new Pago();
        List lstPagos=pago.buscarSaldoxCriteria(uni,prop,inq,fechaInicio,fechaFin);
        Iterator itPagos=lstPagos.iterator();
        while (itPagos.hasNext()){
            Pago pagItem=new Pago();
            pagItem=(Pago)itPagos.next();
            pagItem.setFechaCta(pagItem.getFecha());
            pagItem.setHaberCta(pagItem.getImporte());
            pagItem.setUnidadItem(pagItem.buscarUnidadxId(pagItem.getCodigo()));
            this.getItems().add(pagItem);
        }
        Collections.sort(this.getItems());
        Iterator it2=this.getItems().iterator();
        BigDecimal saldo=new BigDecimal(0d);
        while(it2.hasNext()){
            CtaCteItem ctaItem=(CtaCteItem)it2.next();
            if (ctaItem instanceof DetalleLiquidacion){
                saldo=saldo.add(new BigDecimal(ctaItem.getDebeCta()));
                ctaItem.setSaldoCta(saldo.doubleValue());
            }
            else{
                saldo=saldo.subtract(new BigDecimal(ctaItem.getHaberCta()));
                ctaItem.setSaldoCta(saldo.doubleValue());
            }
        }
        this.setSaldo(saldo.doubleValue());
        this.setUnidad(uni);
        this.setPropietario(prop);
        this.setInquilino(inq);
    }

    
    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }


}
