/*
 * Consorcio.java
 *
 * Created on 8 de enero de 2008, 18:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package model;

import DAO.ConsorcioDAO;
import DAO.InformeLiquidacionDAO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Javier
 */
public class Consorcio {
    
    /** Creates a new instance of Consorcio */
    public Consorcio() {
    }
    
    private int codigo;
    private String denominacion;
    private String domcilio;
    private String barrio;
    private String cp;
    private String localidad;
    private long cuit;
    private Date fecha_baja;
    private long ultima_expensa;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDomcilio() {
        return domcilio;
    }

    public void setDomcilio(String domcilio) {
        this.domcilio = domcilio;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public long getCuit() {
        return cuit;
    }

    public void setCuit(long cuit) {
        this.cuit = cuit;
    }

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }
    
    
    //Methods
    public void guardar(){
        ConsorcioDAO cd=new ConsorcioDAO();
        cd.guardar(this);
    }
    
    public int modificar(){
        ConsorcioDAO cd=new ConsorcioDAO();
        return cd.modificar(this);
    }
    
    public int eliminar(){
        ConsorcioDAO cd=new ConsorcioDAO();
        return cd.eliminar(this);
    }
    
    public int activar(){
        ConsorcioDAO cd=new ConsorcioDAO();
        return cd.activar(this);
    }
    
    public List buscarActivos(){
        ConsorcioDAO cd=new ConsorcioDAO();
        return cd.buscarActivos();
    }
    
    public Consorcio buscarxCodigo(long codigo){
        ConsorcioDAO cd=new ConsorcioDAO();
        return cd.buscarxCodigo(codigo);
    }
    
    public Double getTotalByCoeficiente(Coeficiente coe, Date periodo){
        ConsorcioDAO coedao=new ConsorcioDAO();
         return coedao.getTotalByCoeficiente(coe,this,periodo);
    }
    
    public void generarInforme(Date periodo){
        Propiedades prop=new Propiedades();
        DecimalFormat nf=(DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
        nf.setGroupingUsed(false);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        Map props=prop.buscarTodas();
        int cant_digitos= Integer.parseInt((String) props.get("cantidad_decimales_liquidacon"));
        nf.setMaximumFractionDigits(cant_digitos);
        GastoImputado gd=new GastoImputado();
        List gi=gd.buscar(this,null,null,periodo,null);
        Iterator i= gi.iterator();
        InformeLiquidacionDAO ild=new InformeLiquidacionDAO();
        while (i.hasNext()){
            Double valor=new Double(0d);
            GastoImputado gas=(GastoImputado)i.next();
            String concepto = null;
            if(gas.getConcepto().getCoeficiente().isDistribuible()==true){  
                if (gas.getConcepto().isDescripcion()==true){
                    concepto=gas.getDescripcion();
                }
                else{
                    concepto=gas.getConcepto().getNombre();
                }
                
            }
            try {
                                 
                 ild.guardarConceptos(gas.getPeriodo(),concepto,nf.parse(nf.format(gas.getImporte())).doubleValue(),gas.getConsorcio().getCodigo(),gas.getComprobante());
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public Map getTotalesDistribuibles(Date periodo){
        Coeficiente coe = new Coeficiente();
        List lst=coe.buscarDistribuiblesActivos();
        Map totalesDistribuibles=new HashMap();
        Iterator it = lst.iterator();
        while (it.hasNext())
        {
            Coeficiente coefic=new Coeficiente();
            coefic = (Coeficiente)it.next();
            Double total = this.getTotalByCoeficiente(coefic,periodo);
            totalesDistribuibles.put(coefic.getCodigo(),total);
        }
        return totalesDistribuibles;
    }
    
    
    public boolean liquidar(Date periodo,Map param){
        DecimalFormat nf=(DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
        nf.setGroupingUsed(false);
        Propiedades prop = new Propiedades();
        Map props=prop.buscarTodas();
        Map a;
        int cant_digitos= Integer.parseInt((String) props.get("cantidad_decimales_liquidacon"));
        nf.setMaximumFractionDigits(cant_digitos);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        boolean return_value=false;
        Double saldoliq=new Double(0d);
        this.generarInforme(periodo);
        if (param==null){
            a = this.getTotalesDistribuibles(periodo);
        }
        else{
            a = param;
        }
        Double expensas=new Double(0d);
        Coeficiente coe=new Coeficiente();
        CoeficientesAsignados ca=new CoeficientesAsignados();
        List coeficientes = coe.buscarDistribuiblesActivos();
        Unidad uni = new Unidad();
        List unidades = uni.buscarxConsorcioActivas(this);
        Iterator uniIt = unidades.iterator();
        long numero_liquidacion=0;
        numero_liquidacion=this.getUltima_expensa();
        while(uniIt.hasNext()){
            boolean tiene_liquidacion=false;
            List listaDetalle = new ArrayList();
            Unidad uni2=new Unidad();
            uni2=(Unidad)uniIt.next();
            Liquidacion liq=new Liquidacion();
            liq.setFecha(Calendar.getInstance().getTime());
            //liq.setNumero_liquidacion(numero_liquidacion);
            //numero_liquidacion++;
            liq.setUnidad(uni2);
            Iterator i = coeficientes.iterator();
            Double importeA=new Double(0);
            Double importeB=new Double(0);
            Double importeD=new Double(0);
            Double importeE=new Double(0);
            Double porcentajeA = new Double(0);
            int cantCoef=0;
            while (i.hasNext()){
                BigDecimal indice=null;
                Coeficiente coef = new Coeficiente();
                BigDecimal valor=null;  
                coef=(Coeficiente)i.next();
                valor = BigDecimal.valueOf((Double)a.get(coef.getCodigo()));
                if (valor!=null){
                    valor = BigDecimal.valueOf(((Double)a.get(coef.getCodigo())).doubleValue());
                }
                uni2.popularCoeficientes(coef);
                indice=BigDecimal.valueOf(((CoeficientesAsignados)uni2.getCoeficientesAsignados().get(0)).getValor()*100);
                BigDecimal total=valor.multiply(indice).divide(new BigDecimal(100d));               
                switch (cantCoef){
                        case 0:{
                            importeA=total.doubleValue();
                            porcentajeA=indice.doubleValue();  
                            break;
                        }
                        case 1:{
                            importeB=total.doubleValue();
                            break;
                        }
                        case 2:{
                            importeD=total.doubleValue();
                            break;
                        }
                        case 3:{
                            importeE=total.doubleValue();
                            break;
                        }
                }
                try {
            
                    expensas=expensas+nf.parse(nf.format(total.doubleValue())).doubleValue();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                cantCoef++;
            }
            InformeLiquidacionDAO ild=new InformeLiquidacionDAO();
            try {
                ild.guardarDistribucion(new Long(uni2.getCodigo()),nf.parse(nf.format(importeA)).doubleValue(),nf.parse(nf.format(importeB)).doubleValue(),periodo,porcentajeA,uni2.getConsorcio().getCodigo(),nf.parse(nf.format(importeD)).doubleValue(),nf.parse(nf.format(importeE)).doubleValue());
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            DetalleLiquidacion dl=new DetalleLiquidacion();
            Concepto con = new Concepto();
            con = con.buscarExpensa();
            dl.setConcepto(con);
            dl.setFecha(Calendar.getInstance().getTime());
            try {
                dl.setImporte_debe(nf.parse(nf.format(expensas)).doubleValue());
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            dl.setPeriodo(periodo);
            dl.setPeriodoConcepto(periodo);
            
            try {
                dl.setSaldo(nf.parse(nf.format(expensas)).doubleValue());
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            saldoliq=saldoliq+dl.getSaldo();
            if (dl.getSaldo() !=0){
                listaDetalle.add(dl);
                tiene_liquidacion=true;
            }
            expensas=0d;
            GastoImputado gaoi=new GastoImputado();
            List giList=new ArrayList();
            giList= gaoi.buscar(this,uni2,null,periodo,null);
            Iterator itList = giList.iterator();
            while(itList.hasNext()){
                GastoImputado gasImputado=new GastoImputado();
                gasImputado=(GastoImputado)itList.next();
                DetalleLiquidacion dlin = new DetalleLiquidacion();
                dlin.setComprobante(gasImputado.getComprobante());
                dlin.setConcepto(gasImputado.getConcepto());
                dlin.setDescripcion(gasImputado.getDescripcion());
                dlin.setFecha(gasImputado.getFecha());
                dlin.setPeriodoConcepto(gasImputado.getPeriodoGasto());
                BigDecimal bg=new BigDecimal(gasImputado.getImporte());
                dlin.setImporte_debe(Double.valueOf(nf.format(bg.doubleValue())));
                dlin.setImporte_haber(0d);
                dlin.setPeriodo(gasImputado.getPeriodo());
                bg=new BigDecimal(gasImputado.getImporte());
                dlin.setSaldo(Double.valueOf(nf.format(bg.doubleValue())));
                listaDetalle.add(dlin);
                saldoliq=saldoliq+dlin.getSaldo();
                if (saldoliq>0){
                    tiene_liquidacion=true;                    
                }
            }
            liq.setNumero_liquidacion(numero_liquidacion);
            numero_liquidacion++;    
            
            liq.setDetalleLiquidacion(listaDetalle);
            liq.setSaldo(saldoliq);
            liq.guardar();
            saldoliq=0d;
            if(liq.getCodigo()!=0){
                return_value=true;                
                this.updateMaxExpensa(numero_liquidacion);    
            }
            else{
                return_value=false;
               
            }
         }
        if (return_value){
            GastoImputado gaoi=new GastoImputado();
            gaoi.eliminar(this.getCodigo(),periodo);
        }
        return return_value;
    }
    
    public List getCoeficientesDistribuibles(){
        ConsorcioDAO coedao=new ConsorcioDAO();
        return coedao.getCoeficientesDistribuibles(this);
    }
    
     public boolean eliminarliquidacion(Date periodo){
        ConsorcioDAO coedao=new ConsorcioDAO();
        return coedao.eliminarliquidacion(periodo,this);
     }
     
     public List buscarxDenominacion(String denominacion){
        ConsorcioDAO coedao=new ConsorcioDAO();
        return coedao.buscarxDenominacion(denominacion);
    }
    public boolean existeLiquidacion(Consorcio consor,Date periodo){
        ConsorcioDAO coedao=new ConsorcioDAO();
        return coedao.existeLiquidacion(consor,periodo);
    }
    public void updateMaxExpensa(long value){
        ConsorcioDAO coedao=new ConsorcioDAO();
        coedao.updateMaxExpensa(this,value);
    }
    public long getUltima_expensa() {
        return ultima_expensa;
    }

    public void setUltima_expensa(long ultima_expensa) {
        this.ultima_expensa = ultima_expensa;
    }
    public double getTotalExpensas(){
        ConsorcioDAO coedao=new ConsorcioDAO();
        return coedao.getTotalExpensas(this);
    }
}
            

