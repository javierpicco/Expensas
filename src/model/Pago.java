/*
 * Pago.java
 *
 * Created on 10 de marzo de 2008, 18:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.PagoDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author Javier
 */
public class Pago extends CtaCteItem {
    
    /** Creates a new instance of Pago */
    public Pago() {
    }
    private long codigo;
    private Concepto concepto=new Concepto();
    private Date fecha;
    private Double importe;
    private List detallePago = new ArrayList();
    
    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
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

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public List getDetallePago() {
        return detallePago;
    }

    public void setDetallePago(List detallePago) {
        this.detallePago = detallePago;
    }
    
    public boolean generarPago(Unidad uni)
    {
        DetalleLiquidacion dl=new DetalleLiquidacion();
        List pagosPendientes = dl.buscarSaldo(uni);
        Iterator it = pagosPendientes.iterator();
        Double cantidadDistribuir=this.getImporte();
        while (it.hasNext()){
            DetalleLiquidacion gasto=(DetalleLiquidacion)it.next();
            if (cantidadDistribuir==0)
                break;
            if (gasto.getSaldo()>=cantidadDistribuir){
                DetallePago dp=new DetallePago();
                dp.setDetalleLiquidacion(gasto);
                dp.setImporte(cantidadDistribuir);
                this.getDetallePago().add(dp);
                gasto.setImporte_haber(gasto.getImporte_haber()+cantidadDistribuir);
                gasto.setSaldo(gasto.getSaldo()-cantidadDistribuir);
                break;
            }
            else{
                DetallePago dp=new DetallePago();
                dp.setDetalleLiquidacion(gasto);
                dp.setImporte(gasto.getSaldo());
                this.getDetallePago().add(dp);
                cantidadDistribuir=cantidadDistribuir-gasto.getSaldo();
                gasto.setImporte_haber(gasto.getImporte_haber()+gasto.getSaldo());
                gasto.setSaldo(0d);                
            }

        }
        PagoDAO pagodao=new PagoDAO();
        return pagodao.guardar(this);
    }
    public List buscarSaldo(Unidad uni){
        PagoDAO pagodao=new PagoDAO();
        return pagodao.buscarSaldo(uni);
    }
    public List buscarSaldoxCriteria(Unidad uni,Propietario prop,Inquilino inq, Date dateInicio,Date dateFin){
        PagoDAO pagodao=new PagoDAO();
        return pagodao.buscarSaldoxCriteria(uni,prop,inq,dateInicio,dateFin);
    }
    public Unidad buscarUnidadxId(long pagoId){
        PagoDAO pagodao=new PagoDAO();
        return pagodao.buscarUnidadxId(pagoId);
    }
    public boolean eliminar(){
        PagoDAO pagodao=new PagoDAO();
        return pagodao.eliminar(this);
    }
    public void popularDetallePagos(){
        DetallePago dp=new DetallePago();
        this.setDetallePago(dp.getDetallePagoxPago(this));
    }
}
