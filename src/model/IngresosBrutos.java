/*
 * IngresosBrutos.java
 *
 * Created on 20 de marzo de 2008, 20:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import Security.Usuario;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Javier
 */
public class IngresosBrutos {
    
    /** Creates a new instance of IngresosBrutos */
    public IngresosBrutos() {
    }
    public List generarIG(Propietario prop,Date fecha_inicio,Date fecha_fin,Double minimo,Double tasaIngreso){
        Unidad uni=new Unidad();
        List propietarioUnidades=uni.buscarxPropietario(prop);
        Iterator it=propietarioUnidades.iterator();
        DecimalFormat nf=(DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);
        Double totalIngresosBrutos=new Double(0d);
        Double totalAlquileres=new Double(0d);
        Double totalImpuestos=new Double(0d);
        Double totalIva=new Double(0d);
        Double impuestoPagar=new Double(0d);
        Double totalIngresosSinIva=new Double(0d); 
        Propiedades pr=new Propiedades();
        Map a=pr.buscarTodas();
        Double tasaIva=Double.valueOf((String) a.get("valor_iva"));
        List tmpDetalles = new ArrayList();
        while(it.hasNext()){
            DetalleLiquidacion dliq=new DetalleLiquidacion();
            Unidad unid=(Unidad)it.next();
            List detLiqList=dliq.buscarSaldoxCriteria(unid,null,null,fecha_inicio,fecha_fin);
            Iterator itList=detLiqList.iterator();
            Double importexUnidad=new Double(0d);
            while (itList.hasNext()){
                DetalleLiquidacion dliq1=(DetalleLiquidacion)itList.next();
                if (dliq1.getConcepto().isIg()){
                    Double importeSimple=new Double(0d);
                    importeSimple+=dliq1.getImporte_debe();
                    importexUnidad+=dliq1.getImporte_debe();
                    totalIngresosBrutos+=importeSimple;
                  
                    if (dliq1.getConcepto().isIva()){
                        Double result=new Double(0d);
                        result=importeSimple-(importeSimple/(1+(tasaIva/100)));
                        importeSimple-=result;
                        totalIva+=result;
                    }
                    totalIngresosSinIva+=importeSimple;
                    
                    if (dliq1.getConcepto().getTipoConcepto().getDescripcion()!=null && dliq1.getConcepto().getTipoConcepto().getDescripcion().indexOf("Alquiler")!=-1){
                        totalAlquileres+=importeSimple;
                    }
                    else{
                        totalImpuestos+=importeSimple;
                    }
                }
            }
            TmpIGDetails tmpd=new TmpIGDetails();
            tmpd.setUnidad(unid);
            tmpd.setImportexUnidad(importexUnidad);
            tmpDetalles.add(tmpd);
            
            //Fin recorro Conceptos
        }
        //Fin recorro unidades
        Double tmpIngresos=(totalIngresosSinIva-minimo);
        impuestoPagar=tmpIngresos*tasaIngreso/100d;
        TmpIG tmpI=new TmpIG();
        tmpI.setPropietario(prop);
        tmpI.setPeriodo(fecha_inicio);
        tmpI.setDetalleTmpIG(tmpDetalles);
        tmpI.setImporteTotalSIva(Double.valueOf(nf.format(totalIngresosSinIva)));
        tmpI.setImporteMinimo(Double.valueOf(nf.format(minimo)));
        tmpI.setImporteAlquiler(Double.valueOf(nf.format(totalAlquileres)));
        tmpI.setImporteIVA(Double.valueOf(nf.format(totalIva)));
        tmpI.setImporteImpuesto(Double.valueOf(nf.format(totalImpuestos)));
        tmpI.setImporteTotal(Double.valueOf(nf.format(totalIngresosBrutos)));
        tmpI.setTasaImpuesto(Double.valueOf(nf.format(tasaIngreso)));
        tmpI.setImpuesto(Double.valueOf(nf.format(impuestoPagar)));
        Usuario usr=new Usuario();
        usr.setCodigo(1);
        tmpI.setUsuario(usr);
        return tmpI.agregar();
/*        System.out.print(impuestoPagar + "-" + totalIngresosBrutos+"-"+totalAlquileres+"-"+totalImpuestos+"-"+totalIva);
        return nf.format(impuestoPagar) + "-" + nf.format(totalIngresosBrutos)+"-"+nf.format(totalAlquileres)+"-"+nf.format(totalImpuestos)+"-"+nf.format(totalIva);*/
    }
   

}
