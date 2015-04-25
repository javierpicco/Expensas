/*
 * informeLiquidacion.java
 *
 * Created on 28 de enero de 2008, 20:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package informes;

import DAO.ConnectionSing;
import java.awt.Dialog;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Javier
 */
public class InformeReciboExpensa {
    
    /** Creates a new instance of informeLiquidacion */
    public InformeReciboExpensa() {
    }
    
    private Map parameters=new HashMap();

    public void displayReport(){
        JasperPrint jasperPrint;
        JasperViewer jasperViewer;
        jasperPrint = generateReport();
        jasperViewer = new JasperViewer(jasperPrint,false);
        //jasperViewer.setAlwaysOnTop(true);
        jasperViewer.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        jasperViewer.setVisible(true);
        jasperViewer=null;
       
    }// TODO: Agrege su codigo aqui:

    private JasperPrint generateReport()
    {
        JasperPrint jasperPrint = null;
        try
        {
            this.getParameters().put("SUBREPORT_DIR",System.getProperty("user.dir")+"/informesTemplate/");
            //this.getParameters().put("consorcio_id","5");
            jasperPrint = JasperFillManager.fillReport(        
            System.getProperty("user.dir")+"/informesTemplate/reciboExpensa.jasper", this.getParameters(),
            ConnectionSing.getConection());
        }
        catch (JRException e)
        {
            e.printStackTrace();
        }
        return jasperPrint;
    }        

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }


}