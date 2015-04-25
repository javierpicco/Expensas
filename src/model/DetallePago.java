/*
 * DetallePago.java
 *
 * Created on 10 de marzo de 2008, 18:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.DetallePagoDAO;
import java.util.List;

/**
 *
 * @author Javier
 */
public class DetallePago {
    
    /** Creates a new instance of DetallePago */
    public DetallePago() {
    }
    private DetalleLiquidacion detalleLiquidacion = new DetalleLiquidacion();
    private Double importe;
    private long codigo;


    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public DetalleLiquidacion getDetalleLiquidacion() {
        return detalleLiquidacion;
    }

    public void setDetalleLiquidacion(DetalleLiquidacion detalleLiquidacion) {
        this.detalleLiquidacion = detalleLiquidacion;
    }
    public List getDetallePagoxPago(Pago pago){
        DetallePagoDAO dpd=new DetallePagoDAO();
        return dpd.buscarDetallepagoxPago(pago);
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
    
}
